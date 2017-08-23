package gregtech.api.items.materialitem;

import com.google.common.base.Preconditions;
import gnu.trove.list.array.TShortArrayList;
import gregtech.api.GregTech_API;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.material.type.MarkerMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.util.GT_Utility;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MaterialMetaItem extends StandardMetaItem {

    protected OrePrefix[] orePrefixes;
    private TShortArrayList generatedItems = new TShortArrayList();

    public MaterialMetaItem(OrePrefix... orePrefixes) {
        super((short) (1000 * orePrefixes.length));
        Preconditions.checkArgument(orePrefixes.length <= 32, "Max allowed OrePrefix count on MaterialMetaItem is 32.");
        this.orePrefixes = orePrefixes;
        for(Material material : Material.MATERIAL_REGISTRY) {
            if(material != null && !(material instanceof MarkerMaterial)) {
                int i = Material.MATERIAL_REGISTRY.getIDForObject(material);
                for(int j = 0; j < orePrefixes.length; j++) {
                    OrePrefix orePrefix = orePrefixes[j];
                    if(orePrefix != null && canGenerate(orePrefix, material)) {
                        short metadata = (short) (j * 1000 + i);
                        generatedItems.add(metadata);
                        OreDictionaryUnifier.registerOre(new ItemStack(this, 1, metadata), orePrefix, material);
                    }
                }
            }
        }
    }

    protected boolean canGenerate(OrePrefix orePrefix, Material material) {
        return orePrefix.doGenerateItem(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack itemStack) {
        if(itemStack.getItemDamage() < metaItemOffset) {
            if (!generatedItems.contains((short) itemStack.getItemDamage())) {
                return "";
            }
            Material material = Material.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000);
            OrePrefix prefix = orePrefixes[itemStack.getItemDamage() / 1000];
            return prefix.getLocalNameForItem(material);
        }
        return super.getItemStackDisplayName(itemStack);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] {
                GregTech_API.TAB_GREGTECH,
                GregTech_API.TAB_GREGTECH_MATERIALS
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        if(tab == GregTech_API.TAB_GREGTECH) {
            super.getSubItems(itemIn, tab, subItems);
        }
        if(tab == GregTech_API.TAB_GREGTECH_MATERIALS) {
            for(short metadata : generatedItems.toArray()) {
                subItems.add(new ItemStack(this, 1, metadata));
            }
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(itemStack.getItemDamage() < metaItemOffset && generatedItems.contains((short) itemStack.getItemDamage()) && entityIn instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            Material material = Material.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000);
            OrePrefix prefix = orePrefixes[itemStack.getItemDamage() / 1000];
            if(prefix.heatDamage > 0.0 && GT_Utility.isWearingFullHeatHazmat(entity) && worldIn.getTotalWorldTime() % 20 == 0) {
                entity.attackEntityFrom(DamageSources.getHeatDamage(), prefix.heatDamage);
            } else if(prefix.heatDamage < 0.0 && GT_Utility.isWearingFullFrostHazmat(entity) && worldIn.getTotalWorldTime() % 20 == 0) {
                entity.attackEntityFrom(DamageSources.getFrostDamage(), -prefix.heatDamage);
            }
            if(prefix.name().contains("Quintuple") || prefix.name().contains("Quadruple")) {
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0));
            } else if(prefix.name().contains("Dense")) {
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
            }
        }
    }

}
