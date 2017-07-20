package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class MetalMaterial extends AbstractSolidMaterial {

    public MetalMaterial(String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, List<MaterialStack> materialComponents, List<Material> oreReRegistrations, List<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element, int mOreValue, int oreMultiplier, int mByProductMultiplier, int mSmeltingMultiplier, AbstractSolidMaterial directSmelting, AbstractSolidMaterial smeltInto, AbstractSolidMaterial arcSmeltInto, float toolSpeed, byte toolQuality, int toolDurability, Enchantment toolEnchantment, int toolEnchantmentLevel, int blastFurnaceTemp, AbstractSolidMaterial handleMaterial, DustMaterial macerateInto) {
        super(defaultLocalName, materialRGB, materialIconSet, materialComponents, oreReRegistrations, subTags, materialGenerationFlags, densityMultiplier, element, mOreValue, oreMultiplier, mByProductMultiplier, mSmeltingMultiplier, directSmelting, smeltInto, arcSmeltInto, toolSpeed, toolQuality, toolDurability, toolEnchantment, toolEnchantmentLevel, blastFurnaceTemp, handleMaterial, macerateInto);
    }

}
