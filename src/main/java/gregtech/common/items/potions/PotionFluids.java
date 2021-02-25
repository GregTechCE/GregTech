package gregtech.common.items.potions;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static gregtech.common.MetaFluids.AUTO_GENERATED_FLUID_TEXTURE;

public class PotionFluids {

    private static final BiMap<ResourceLocation, Fluid> potionFluidMap = HashBiMap.create();
    public static final int POTION_ITEM_FLUID_AMOUNT = 100;

    public static Fluid getFluidForPotion(PotionType potion) {
        return potionFluidMap.get(potion.getRegistryName());
    }

    public static PotionType getPotionForFluid(Fluid potionFluid) {
        ResourceLocation registryName = potionFluidMap.inverse().get(potionFluid);
        return registryName == null ? null : ForgeRegistries.POTION_TYPES.getValue(registryName);
    }

    public static void initPotionFluids() {
        MinecraftForge.EVENT_BUS.register(new PotionFluids());
        for (ResourceLocation registryName : ForgeRegistries.POTION_TYPES.getKeys()) {
            if (registryName.getNamespace().equals("minecraft") &&
                registryName.getPath().equals("empty")) continue;

            PotionType potion = ForgeRegistries.POTION_TYPES.getValue(registryName);
            Preconditions.checkNotNull(potion);
            Fluid potionFluid;
            if (potion != PotionTypes.WATER) {
                String fluidName = String.format("potion.%s.%s", registryName.getNamespace(), registryName.getPath());
                potionFluid = new Fluid(fluidName, AUTO_GENERATED_FLUID_TEXTURE, AUTO_GENERATED_FLUID_TEXTURE) {
                    @Override
                    public String getUnlocalizedName() {
                        return potion.getNamePrefixed("potion.effect.");
                    }
                };
                potionFluid.setColor(GTUtility.convertRGBtoOpaqueRGBA_MC(PotionUtils.getPotionColor(potion)));

                FluidRegistry.registerFluid(potionFluid);
                FluidRegistry.addBucketForFluid(potionFluid);

                BlockFluidBase fluidBlock = new BlockPotionFluid(potionFluid, potion);
                fluidBlock.setRegistryName("fluid." + fluidName);
                MetaBlocks.FLUID_BLOCKS.add(fluidBlock);
            } else {
                potionFluid = FluidRegistry.WATER;
            }

            potionFluidMap.put(potion.getRegistryName(), potionFluid);
        }
    }

    /*
     * Attaches capabilities to potion items, so they can be emptied to drain contained potion
     * and transform into empty glass bottles
     * Also allows filing glass bottles with liquid potion to get potion item
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCapabilityAttach(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack itemStack = event.getObject();
        if (itemStack.getItem() instanceof ItemPotion) {
            ResourceLocation resourceLocation = new ResourceLocation("gregtech", "fluid_container");
            PotionItemFluidHandler fluidHandler = new PotionItemFluidHandler(itemStack);
            event.addCapability(resourceLocation, fluidHandler);
        } else if (itemStack.getItem() instanceof ItemGlassBottle) {
            ResourceLocation resourceLocation = new ResourceLocation("gregtech", "fluid_container");
            GlassBottleFluidHandler fluidHandler = new GlassBottleFluidHandler(itemStack, event.getCapabilities().values());
            event.addCapability(resourceLocation, fluidHandler);
        }
    }

}
