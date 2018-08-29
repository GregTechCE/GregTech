package gregtech.common.items;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.GTValues;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static gregtech.common.MetaFluids.AUTO_GENERATED_FLUID_TEXTURE;

public class PotionFluids {

    private static final BiMap<ResourceLocation, Fluid> potionFluidMap = HashBiMap.create();

    public static Fluid getFluidForPotion(PotionType potion) {
        return potionFluidMap.get(potion.getRegistryName());
    }

    public static PotionType getPotionForFluid(Fluid potionFluid) {
        ResourceLocation registryName = potionFluidMap.inverse().get(potionFluid);
        return registryName == null ? null : ForgeRegistries.POTION_TYPES.getValue(registryName);
    }

    public static void initPotionFluids() {
        MinecraftForge.EVENT_BUS.register(new PotionFluids());
        for(ResourceLocation registryName : ForgeRegistries.POTION_TYPES.getKeys()) {
            if(registryName.getResourceDomain().equals("minecraft") &&
                registryName.getResourcePath().equals("empty")) continue;

            PotionType potion = ForgeRegistries.POTION_TYPES.getValue(registryName);
            Preconditions.checkNotNull(potion);
            Fluid potionFluid;
            if(potion != PotionTypes.WATER) {
                String fluidName = String.format("potion.%s.%s", registryName.getResourceDomain(), registryName.getResourcePath());
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
    @SubscribeEvent
    public void onCapabilityAttach(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack itemStack = event.getObject();
        if(itemStack.getItem() instanceof ItemPotion) {
            ResourceLocation resourceLocation = new ResourceLocation("gregtech", "fluid_container");
            PotionItemFluidHandler fluidHandler = new PotionItemFluidHandler(itemStack);
            event.addCapability(resourceLocation, fluidHandler);
        } else if(itemStack.getItem() instanceof ItemGlassBottle) {
            ResourceLocation resourceLocation = new ResourceLocation("gregtech", "fluid_container");
            GlassBottleFluidHandler fluidHandler = new GlassBottleFluidHandler(itemStack);
            event.addCapability(resourceLocation, fluidHandler);
        }
    }

    private static class PotionItemFluidHandler extends FluidHandlerItemStackSimple {

        public PotionItemFluidHandler(@Nonnull ItemStack container) {
            super(container, GTValues.L);
        }

        @Override
        public FluidStack getFluid() {
            PotionType potionType = PotionUtils.getPotionFromItem(container);
            if(potionType == PotionTypes.EMPTY)
                return null;
            Fluid fluid = getFluidForPotion(potionType);
            //because some mods are dumb enough to register potion types after block registry event
            if(fluid == null)
                return null;
            return new FluidStack(fluid, capacity);
        }

        @Override
        protected void setFluid(FluidStack fluid) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return false; //we allow only emptying of potion containers
        }

        @Override
        protected void setContainerToEmpty() {
            GTUtility.setItem(container, new ItemStack(Items.GLASS_BOTTLE));
            container.setTagCompound(null);
        }
    }

    private static class GlassBottleFluidHandler extends FluidHandlerItemStackSimple {

        public GlassBottleFluidHandler(@Nonnull ItemStack container) {
            super(container, GTValues.L);
        }

        @Nullable
        @Override
        public FluidStack getFluid() {
            return null;
        }

        @Override
        protected void setFluid(FluidStack fluid) {
            PotionType potionType = getPotionForFluid(fluid.getFluid());
            if(potionType != null && potionType != PotionTypes.EMPTY) {
                GTUtility.setItem(container, new ItemStack(Items.POTIONITEM));
                PotionUtils.addPotionToItemStack(container, potionType);
            }
        }

        @Override
        protected void setContainerToEmpty() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            PotionType potionType = getPotionForFluid(fluid.getFluid());
            //we allow filling only with potions
            return potionType != null && potionType != PotionTypes.EMPTY;
        }

        @Override
        public boolean canDrainFluidType(FluidStack fluid) {
            return false; //we allow only filling of glass bottles
        }
    }

    private static class BlockPotionFluid extends BlockFluidFinite {

        private final PotionType potionType;

        public BlockPotionFluid(Fluid fluid, PotionType potionType) {
            super(fluid, Material.WATER);
            this.potionType = potionType;
        }

        @Override
        public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
            if(!(entityIn instanceof EntityLivingBase) ||
                worldIn.getTotalWorldTime() % 20 != 0) return;
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            for(PotionEffect potionEffect : potionType.getEffects()) {
                if(!potionEffect.getPotion().isInstant()) {
                    PotionEffect instantEffect = new PotionEffect(potionEffect.getPotion(),
                        60, potionEffect.getAmplifier(), true, true);
                    entity.addPotionEffect(instantEffect);
                } else {
                    potionEffect.getPotion().affectEntity(null, null,
                        entity, potionEffect.getAmplifier(), 1.0);
                }
            }
        }

    }

}
