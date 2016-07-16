package gregtech.common.blocks;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GT_Block_Stones extends GT_Block_Stones_Abstract {
    public GT_Block_Stones() {
        super(GT_Item_Granites.class, "gt.blockstones");
        setResistance(60.0F);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Marble");
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Stone, new ItemStack(this, 1, 0));
    }

    public int getHarvestLevel(int aMeta) {
        return 3;
    }

    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        return this.blockHardness = Blocks.stone.getBlockHardness(aWorld, aX, aY, aZ) * 3.0F;
    }

    public IIcon getIcon(int aSide, int aMeta) {
        //if ((aMeta >= 0) && (aMeta < 16)) {
        if (aMeta == 0) {
            return gregtech.api.enums.Textures.BlockIcons.STONES[aMeta].getIcon();
        }
        return gregtech.api.enums.Textures.BlockIcons.STONES[0].getIcon();
    }

    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityWither);
    }
}
