package gregtech.api.model;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class ResourcePackHook implements IResourceManagerReloadListener, IResourcePack {

    public static final ResourcePackHook instance = new ResourcePackHook();

    private ResourcePackHook() {
    }

    public static void init() {
        IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        Minecraft.getMinecraft().defaultResourcePacks.add(instance);
        if (resourceManager instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) resourceManager).registerReloadListener(instance);
        }
    }

    public interface IResourcePackFileHook {

        boolean resourceExists(ResourceLocation location);

        InputStream getInputStream(ResourceLocation location) throws IOException;

        void onResourceManagerReload(SimpleReloadableResourceManager resourceManager);

    }

    private static ArrayList<IResourcePackFileHook> hooks = new ArrayList<>();

    public static void addResourcePackFileHook(IResourcePackFileHook hook) {
        hooks.add(hook);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        for (IResourcePackFileHook hook : hooks) {
            hook.onResourceManagerReload((SimpleReloadableResourceManager) resourceManager);
        }
    }

    @Override
    public InputStream getInputStream(ResourceLocation location) throws IOException {
        for (IResourcePackFileHook hook : hooks) {
            if (hook.resourceExists(location))
                return hook.getInputStream(location);
        }
        throw new FileNotFoundException(location.toString());
    }

    @Override
    public boolean resourceExists(ResourceLocation location) {
        for (IResourcePackFileHook hook : hooks) {
            if (hook.resourceExists(location))
                return true;
        }
        return false;
    }

    @Override
    public Set<String> getResourceDomains() {
        return ImmutableSet.of("gregtech");
    }

    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        if (metadataSectionName.equals("pack")) {
            return (T) new PackMetadataSection(new TextComponentString(getPackName()), 1);
        }
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        return ImageIO.read(Minecraft.class.getResource("/pack.png"));
    }

    @Override
    public String getPackName() {
        return "Gregtech Internal Resource Pack";
    }

}
