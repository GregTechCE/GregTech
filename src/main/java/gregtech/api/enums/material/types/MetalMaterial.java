package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import net.minecraft.enchantment.Enchantment;

public class MetalMaterial extends AbstractSolidMaterial {

    public MetalMaterial(String defaultLocalName, int materialRGB, String chemicalFormula, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element, int mOreValue, int oreMultiplier, int mByProductMultiplier, int mSmeltingMultiplier, AbstractSolidMaterial directSmelting, AbstractSolidMaterial smeltInto, AbstractSolidMaterial arcSmeltInto, float toolSpeed, byte toolQuality, int toolDurability, Enchantment toolEnchantment, int toolEnchantmentLevel, int blastFurnaceTemp, AbstractSolidMaterial handleMaterial, DustMaterial macerateInto) {
        super(defaultLocalName, materialRGB, chemicalFormula, materialIconSet, materialComponents, oreReRegistrations, subTags, materialGenerationFlags, densityMultiplier, element, mOreValue, oreMultiplier, mByProductMultiplier, mSmeltingMultiplier, directSmelting, smeltInto, arcSmeltInto, toolSpeed, toolQuality, toolDurability, toolEnchantment, toolEnchantmentLevel, blastFurnaceTemp, handleMaterial, macerateInto);
    }

}
