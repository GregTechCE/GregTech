package gregtech.common.blocks;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class GT_Block_Ores extends GT_Block_Ores_Abstract {
    public GT_Block_Ores() {
        super("gt.blockores", false, Material.ROCK);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        GT_TileEntity_Ores tileEntity_ores = (GT_TileEntity_Ores) world.getTileEntity(pos);
        if(tileEntity_ores != null && tileEntity_ores.mMetaData != 0) {
            return new ItemStack(this, 1, tileEntity_ores.mMetaData);
        }
        return null;
    }

    @Override
    public String getUnlocalizedName() {
        return "gt.blockores";
    }

    @Override
    public OrePrefixes[] getProcessingPrefix() { //Must have 8 entries; an entry can be null to disable automatic recipes.
        return new OrePrefixes[]{OrePrefixes.ore, OrePrefixes.oreNetherrack, OrePrefixes.oreEndstone, OrePrefixes.oreBlackgranite, OrePrefixes.oreRedgranite, OrePrefixes.oreMarble, OrePrefixes.oreBasalt, null};
    }

    @Override
    public Block getDroppedBlock() {
        return GregTech_API.sBlockOres1;
    }

    @Override
    public Materials[] getDroppedDusts() { //Must have 8 entries; can be null.
        return new Materials[]{Materials.Stone, Materials.Netherrack, Materials.Endstone, Materials.GraniteBlack, Materials.GraniteRed, Materials.Marble, Materials.Basalt, Materials.Stone};
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        int aMeta = state.getValue(METADATA);
        return aMeta == 5 || aMeta == 6 ? 2 : aMeta % 8;
    }

    @Override
    public ITexture[] getTextureSet() { //Must have 16 entries.
        return new ITexture[]{
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/netherrack", null),
                new GT_RenderedTexture("minecraft:blocks/end_stone", null),
                new GT_RenderedTexture(Textures.BlockIcons.GRANITE_BLACK_STONE),
                new GT_RenderedTexture(Textures.BlockIcons.GRANITE_RED_STONE), 
                new GT_RenderedTexture(Textures.BlockIcons.MARBLE_STONE),
                new GT_RenderedTexture(Textures.BlockIcons.BASALT_STONE),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture("minecraft:blocks/stone", null)};
    }
}