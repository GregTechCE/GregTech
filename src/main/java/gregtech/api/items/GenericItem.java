package gregtech.api.items;

import gregtech.api.GregTech_API;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.GT_Values.MODID;

/**
 * Extended by GT items.
 */
public class GenericItem extends Item {

    private String unlocalizedName;
    private final String[] tooltip;

    public GenericItem(String unlocalizedName, String... tooltip) {
        super();
        this.unlocalizedName = unlocalizedName;
        this.tooltip = tooltip;
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        setRegistryName(MODID, unlocalizedName);
        GameRegistry.register(this);
    }

    @Override
    public final Item setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public final String getUnlocalizedNameWithoutPrefix() {
        return unlocalizedName;
    }

    @Override
    public final String getUnlocalizedName() {
        return "item." + unlocalizedName;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return getHasSubtypes() ? getUnlocalizedName() + "." + itemStack.getItemDamage() : getUnlocalizedName();
    }

    @Override
    public final String getUnlocalizedNameInefficiently(ItemStack itemStack) {
        return getUnlocalizedName(itemStack);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack itemStack, EntityPlayer aPlayer, List<String> lines, boolean showAdditionalInfo) {
        if (!getHasSubtypes() && itemStack.getMaxDamage() > 0) {
            lines.add((itemStack.getMaxDamage() - itemStack.getItemDamage()) + " / " + itemStack.getMaxDamage());
        }
        for(String tooltipLine : tooltip) {
            lines.add(I18n.translateToLocal(tooltipLine));
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return null;
    }

    @Override
    public final boolean hasContainerItem(ItemStack itemStack) {
        return getContainerItem(itemStack) != null;
    }

}