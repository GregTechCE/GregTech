package gregtech.common.blocks.itemblocks;

import java.util.List;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemCasingsAbstract extends ItemBlock {

    protected final String noMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
    protected final String noTileEntityToolTip = GT_LanguageManager.addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");
    protected final String coil01Tooltip = GT_LanguageManager.addStringLocalization("gt.coil01tooltip", "Base Heating Capacity = 1800 Kelvin");
    protected final String coil02Tooltip = GT_LanguageManager.addStringLocalization("gt.coil02tooltip", "Base Heating Capacity = 2700 Kelvin");
    protected final String coil03Tooltip = GT_LanguageManager.addStringLocalization("gt.coil03tooltip", "Base Heating Capacity = 3600 Kelvin");
    protected final String coil04Tooltip = GT_LanguageManager.addStringLocalization("gt.coil04tooltip", "Base Heating Capacity = 4500 Kelvin");
    protected final String coil05Tooltip = GT_LanguageManager.addStringLocalization("gt.coil05tooltip", "Base Heating Capacity = 5400 Kelvin");
    protected final String coil06Tooltip = GT_LanguageManager.addStringLocalization("gt.coil06tooltip", "Base Heating Capacity = 7200 Kelvin");
    protected final String coil07Tooltip = GT_LanguageManager.addStringLocalization("gt.coil07tooltip", "Base Heating Capacity = 9001 Kelvin");
    protected final String blastProofTooltip = GT_LanguageManager.addStringLocalization("gt.blastprooftooltip", "This Block is Blast Proof");

    public ItemCasingsAbstract(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return GT_LanguageManager.getTranslation(getUnlocalizedName(stack) + ".name");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName() + "." + getDamage(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(this.noMobsToolTip);
        tooltip.add(this.noTileEntityToolTip);
    }
}
