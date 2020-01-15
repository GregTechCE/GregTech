package gregtech.common.util;

import com.google.common.base.Preconditions;
import gregtech.GregTechMod;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LegacyV2Adapter;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Fixes resource pack path in development environment for unpacked mod
 * Because, you know, i don't like waiting 5 minutes and wasting SSD resources on copying resources every
 * time i want to hot-swap code, and forge is too stupid to properly detect separated resource folder
 * in development configuration
 */
@SideOnly(Side.CLIENT)
public class ResourcePackFix {

    private static AbstractResourcePack getModResourcePack(String modid) {
        IResourcePack rawPack = FMLClientHandler.instance().getResourcePackFor(modid);
        AbstractResourcePack resourcePack;
        if (rawPack instanceof LegacyV2Adapter) {
            resourcePack = ObfuscationReflectionHelper.getPrivateValue(LegacyV2Adapter.class, (LegacyV2Adapter) rawPack, 0);
        } else if (rawPack instanceof AbstractResourcePack) {
            resourcePack = (AbstractResourcePack) rawPack;
        } else {
            throw new UnsupportedOperationException("Unknown resource pack class " + rawPack.getClass());
        }
        return resourcePack;
    }

    private static void setModResourcePack(String modid, AbstractResourcePack newPack) {
        FMLClientHandler clientHandler = FMLClientHandler.instance();
        IResourcePack oldPack = clientHandler.getResourcePackFor(modid);
        Map<String, IResourcePack> resourcePackMap = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, clientHandler, "resourcePackMap");
        resourcePackMap.put(modid, newPack);
        List<IResourcePack> resourcePackList = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, clientHandler, "resourcePackList");
        resourcePackList.remove(oldPack);
        resourcePackList.add(newPack);
    }

    private static File getGTCEResourcePackRoot() {
        URL mcModURL = GregTechMod.class.getResource("/mcmod.info");
        Preconditions.checkState(mcModURL.getProtocol().equals("file"), "Protocol is not file");
        return new File(mcModURL.getPath()).getParentFile();
    }

    public static void fixResourcePackLocation(ModContainer selfContainer) {
        File sourceFile = selfContainer.getSource();
        if (sourceFile.isDirectory()) {
            AbstractResourcePack resourcePack = getModResourcePack(selfContainer.getModId());
            File actualPackRoot = ObfuscationReflectionHelper.getPrivateValue(AbstractResourcePack.class, resourcePack, "field_110597_b");
            File expectedPackRoot = getGTCEResourcePackRoot();

            if (!expectedPackRoot.getAbsolutePath().equals(actualPackRoot.getAbsolutePath())) {
                System.out.println("[GTCE] Found unexpected resource pack path in dev environment");
                System.out.println("[GTCE] Expected path: " + expectedPackRoot.getAbsolutePath());
                System.out.println("[GTCE] Actual path: " + actualPackRoot.getAbsolutePath());
                System.out.println("[GTCE] Fixed resource pack patch automatically.");
                ObfuscationReflectionHelper.setPrivateValue(AbstractResourcePack.class, resourcePack, expectedPackRoot, "field_110597_b");
                setModResourcePack(selfContainer.getModId(), resourcePack);
            }
        }
    }

}
