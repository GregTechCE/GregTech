package gregtech.common.worldgen;

import gregtech.api.util.GTLog;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TemplateManager {

    private static final Map<ResourceLocation, Template> templateMap = new HashMap<>();

    public static Template getBuiltinTemplate(World world, ResourceLocation templateId) {
        if (templateMap.containsKey(templateId)) {
            return templateMap.get(templateId);
        }
        Template template = new Template();
        String resourcePath = "/assets/" + templateId.getNamespace() + "/structures/" + templateId.getPath() + ".nbt";
        InputStream inputStream = TemplateManager.class.getResourceAsStream(resourcePath);
        if (inputStream != null) {
            try {
                NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(inputStream);
                if (!nbttagcompound.hasKey("DataVersion", 99)) {
                    nbttagcompound.setInteger("DataVersion", 500);
                }
                DataFixer dataFixer = world.getMinecraftServer().getDataFixer();
                template.read(dataFixer.process(FixTypes.STRUCTURE, nbttagcompound));
            } catch (IOException exception) {
                GTLog.logger.error("Failed to load builtin template {}", templateId, exception);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        } else {
            GTLog.logger.warn("Failed to find builtin structure with path {}", resourcePath);
        }
        templateMap.put(templateId, template);
        return template;
    }

    public static BlockPos calculateAverageGroundLevel(World worldIn, BlockPos origin, BlockPos sizes) {
        BlockPos dest = origin.add(sizes.getX(), 0, sizes.getZ());
        int minGroundLevel = 256;
        for (MutableBlockPos blockPos : MutableBlockPos.getAllInBoxMutable(origin, dest)) {
            int groundLevel = worldIn.getTopSolidOrLiquidBlock(blockPos).getY();
            minGroundLevel = Math.min(minGroundLevel, groundLevel);
        }
        return new BlockPos(origin.getX(), minGroundLevel, origin.getZ());
    }
}
