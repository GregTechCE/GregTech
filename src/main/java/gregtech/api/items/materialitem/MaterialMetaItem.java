package gregtech.api.items.materialitem;

import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.MarkerMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MaterialMetaItem extends StandardMetaItem {

    protected OrePrefix[] orePrefixes;
    private ArrayList<Short> generatedItems = new ArrayList<>();

    public MaterialMetaItem(OrePrefix... orePrefixes) {
        super((short) (1000 * orePrefixes.length));
        Preconditions.checkArgument(orePrefixes.length <= 32, "Max allowed OrePrefix count on MaterialMetaItem is 32.");
        this.orePrefixes = orePrefixes;
        for(Material material : Material.MATERIAL_REGISTRY.getObjectsWithIds()) {
            if(!(material instanceof MarkerMaterial)) {
                int i = Material.MATERIAL_REGISTRY.getIDForObject(material);
                for(int j = 0; j < orePrefixes.length; j++) {
                    OrePrefix orePrefix = orePrefixes[j];
                    if(orePrefix != null && canGenerate(orePrefix, material)) {
                        short metadata = (short) (j * 1000 + i);
                        generatedItems.add(metadata);
                    }
                }
            }
        }
    }

    public void registerOreDict() {
        for(short metaItem : generatedItems) {
            OrePrefix prefix = this.orePrefixes[metaItem / 1000];
            Material material = Material.MATERIAL_REGISTRY.getObjectById(metaItem % 1000);
            OreDictUnifier.registerOre(new ItemStack(this, 1, metaItem), prefix, material);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for(short metaItem : generatedItems) {
            OrePrefix prefix = this.orePrefixes[metaItem / 1000];
            Material material = Material.MATERIAL_REGISTRY.getObjectById(metaItem % 1000);
            ModelBakery.registerItemVariants(this, prefix.materialIconType.getItemModelPath(material.materialIconSet));
        }

        int orePrefixAmount = (int) Arrays.stream(this.orePrefixes).filter(Objects::nonNull).count();

        ModelLoader.setCustomMeshDefinition(this, stack -> {
            if (stack.getMetadata() < orePrefixAmount * 1000) {
                OrePrefix prefix = this.orePrefixes[stack.getMetadata() / 1000];
                Material material = Material.MATERIAL_REGISTRY.getObjectById(stack.getMetadata() % 1000);
                return new ModelResourceLocation(prefix.materialIconType.getItemModelPath(material.materialIconSet), "inventory");
            }
            return new ModelResourceLocation("builtin/missing", "missing");
        });
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
                GregTechAPI.TAB_GREGTECH,
                GregTechAPI.TAB_GREGTECH_MATERIALS
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(this.isInCreativeTab(tab)) {
            super.getSubItems(tab, subItems);
            if(tab == GregTechAPI.TAB_GREGTECH_MATERIALS || tab == CreativeTabs.SEARCH) {
                for(short metadata : generatedItems) {
                    subItems.add(new ItemStack(this, 1, metadata));
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(itemStack.getItemDamage() < metaItemOffset && generatedItems.contains((short) itemStack.getItemDamage()) && entityIn instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            Material material = Material.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000);
            OrePrefix prefix = orePrefixes[itemStack.getItemDamage() / 1000];
            if(prefix.heatDamage > 0.0 && GTUtility.isWearingFullHeatHazmat(entity) && worldIn.getTotalWorldTime() % 20 == 0) {
                entity.attackEntityFrom(DamageSources.getHeatDamage(), prefix.heatDamage);
            } else if(prefix.heatDamage < 0.0 && GTUtility.isWearingFullFrostHazmat(entity) && worldIn.getTotalWorldTime() % 20 == 0) {
                entity.attackEntityFrom(DamageSources.getFrostDamage(), -prefix.heatDamage);
            }
            if(prefix.name().contains("Dense")) {
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
            }
        }
    }

    //TODO DELETE ON RELEASE
    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        if (itemStack.getMetadata() >= 32000) return;
        OrePrefix prefix = this.orePrefixes[itemStack.getMetadata() / 1000];
        Material material = Material.MATERIAL_REGISTRY.getObjectById(itemStack.getMetadata() % 1000);
        if (prefix != null) {
            lines.add("IconType: " + prefix.materialIconType);
        }
        if (material != null) {
            lines.add("IconSet: " + material.materialIconSet);
        }
    }
}
