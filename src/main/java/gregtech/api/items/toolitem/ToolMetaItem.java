package gregtech.api.items.toolitem;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.IDamagableItem;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ShapedOreIngredientAwareRecipe;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.*;

/**
 * ToolMetaItem is item that can have up to Short.MAX_VALUE tools inside it
 * These tools can be made from different materials, have special behaviours, and basically do everything that standard MetaItem can do.
 *
 * Tool behaviours are implemented by {@link IToolStats} objects
 *
 * As example, with this code you can add LV electric drill tool:
 * {@code addItem(0, "test_item").addStats(new ElectricStats(10000, 1, true, false)).setToolStats(new ToolStatsExampleDrill()) }
 *
 * @see IToolStats
 * @see MetaItem
 */
public class ToolMetaItem<T extends ToolMetaItem<?>.MetaToolValueItem> extends MetaItem<T> implements IDamagableItem {

    public ToolMetaItem() {
        super((short) 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T constructMetaValueItem(short metaValue, String unlocalizedName) {
        return (T) new MetaToolValueItem(metaValue, unlocalizedName);
    }

    @Override
    protected String formatModelPath(T metaValueItem) {
        String name = metaValueItem.unlocalizedName;
        return "tools/" + (name.indexOf('.') == -1 ? name : name.substring(name.indexOf(".") + 1));
    }

    @Override
    protected short formatRawItemDamage(short metaValue) {
        return (short) (metaValue % 16000);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        T item = getItem(stack);
        if(item == null) {
            return 0xFFFFFF;
        }
        IToolStats toolStats = item.getToolStats();
        switch (tintIndex) {
            case 0: return toolStats.getColor(false, stack);
            case 1: return 0xFFFFFF;
            case 2: return toolStats.getColor(true, stack);
            case 3: return 0xFFFFFF;
        }
        return 0xFFFFFF;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true; //required for machine wrenching
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        //don't show durability if item is not electric and it's damage is zero
        return stack.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null) ||
            getInternalDamage(stack) != 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        //if itemstack has electric charge ability, show electric charge percentage
        if(stack.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null)) {
            IElectricItem electricItem = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            long currentCharge = electricItem.discharge(Long.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
            return 1.0 - (currentCharge / (electricItem.getMaxCharge() * 1.0));
        }
        //otherwise, show actual durability percentage
        return getInternalDamage(stack) / (getMaxInternalDamage(stack) * 1.0);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        //always color durability bar as item internal damage
        double internalDamage = getInternalDamage(stack) / (getMaxInternalDamage(stack) * 1.0);
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0 - internalDamage)) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            toolStats.onToolCrafted(stack, playerIn);
        }
    }

    public static List<ItemStack> getToolComponents(ItemStack toolStack) {
        return ShapedOreIngredientAwareRecipe.getCraftingComponents(toolStack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        stack = stack.copy();
        stack.setCount(1);
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null && metaToolValueItem.toolStats != null) {
            IToolStats toolStats = metaToolValueItem.toolStats;
            int toolDamagePerCraft =  toolStats.getToolDamagePerContainerCraft(stack);
            boolean canApplyDamage = doDamageToItem(stack, toolDamagePerCraft, false);
            if(!canApplyDamage) return stack;
        }
        return stack;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(toolStats.isMinableBlock(state, stack)) {
                doDamageToItem(stack, toolStats.getToolDamagePerBlockBreak(stack), false);
                ResourceLocation mineSound = toolStats.getUseSound(stack);
                if(mineSound != null) {
                    SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(mineSound);
                    world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, soundEvent, SoundCategory.PLAYERS, 0.27f, 1.0f, false);
                }
            }
        }
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack)) && toolStats.isMinableBlock(state, stack)) {
                SolidMaterial material = getPrimaryMaterial(stack);
                if (material != null) {
                    return material.toolSpeed * toolStats.getDigSpeedMultiplier(stack);
                }
            }
        }
        return 1.0f;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            return isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack)) && toolStats.isMinableBlock(state, stack);
        }
        return false;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack)) && toolStats.isMinableBlock(blockState, stack)) {
                SolidMaterial material = getPrimaryMaterial(stack);
                if (material != null) {
                    return toolStats.getBaseQuality(stack) + material.harvestLevel;
                }
            }
        }
        return -1;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        T metaValueItem = getItem(stack);
        if(metaValueItem != null && slot == EntityEquipmentSlot.MAINHAND) {
            IToolStats toolStats = metaValueItem.getToolStats();
            if (toolStats == null) {
                return HashMultimap.create();
            }
            SolidMaterial baseMaterial = getPrimaryMaterial(stack);
            float attackDamage = toolStats.getBaseDamage(stack) + (baseMaterial == null ? 0 : baseMaterial.harvestLevel);
            float attackSpeed = toolStats.getAttackSpeed(stack);

            HashMultimap<String, AttributeModifier> modifiers = HashMultimap.create();
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, 0));
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed, 0));
            return modifiers;
        }
        return HashMultimap.create();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        //cancel attack if broken or out of charge
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            int damagePerAttack = metaToolValueItem.getToolStats().getToolDamagePerEntityAttack(stack);
            if(!isUsable(stack, damagePerAttack)) return true;
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        T metaValueItem = getItem(stack);
        if(metaValueItem != null) {
            IToolStats toolStats = metaValueItem.getToolStats();
            if(!doDamageToItem(stack, toolStats.getToolDamagePerEntityAttack(stack), false))
                return true;
            ResourceLocation hitSound = toolStats.getUseSound(stack);
            if(hitSound != null) {
                SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(hitSound);
                target.getEntityWorld().playSound(target.posX, target.posY, target.posZ, soundEvent, SoundCategory.PLAYERS, 0.27f, 1.0f, false);
            }
            float additionalDamage = toolStats.getNormalDamageBonus(target, stack, attacker);
            float additionalMagicDamage = toolStats.getMagicDamageBonus(target, stack, attacker);
            if(additionalDamage > 0.0f) {
                target.attackEntityFrom(new EntityDamageSource(attacker instanceof EntityPlayer ? "player" : "mob", attacker), additionalDamage);
            }
            if(additionalMagicDamage > 0.0f) {
                target.attackEntityFrom(new EntityDamageSource("indirectMagic", attacker), additionalMagicDamage);
            }
        }
        return true;
    }

    @Override
    public boolean doDamageToItem(ItemStack stack, int vanillaDamage, boolean simulate) {
        IElectricItem capability = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (capability == null) {
            int newDamageValue = getInternalDamage(stack) + vanillaDamage * 10;
            if(!simulate && !setInternalDamage(stack, newDamageValue)) {
                stack.shrink(1);
            }
            //non-electric tools are always damagable, and just break in case
            //they don't have enough durability left
            return true;
        } else {
            int energyAmount = ConfigHolder.energyUsageMultiplier * vanillaDamage;
            if(capability.discharge(energyAmount, capability.getTier(), true, false, true) < energyAmount) {
                //if we can't discharge full amount of energy, just return false
                //and don't attempt to discharge left amount of energy
                return false;
            }
            capability.discharge(energyAmount, capability.getTier(), true, false, simulate);
            int newDamageValue = getInternalDamage(stack) + vanillaDamage;
            if (!simulate && !setInternalDamage(stack, newDamageValue)) {
                GTUtility.setItem(stack, MetaItems.TOOL_PARTS_BOX.getStackForm());
                stack.removeSubCompound("GT.ToolStats");
            }
            return true;
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        ICapabilityProvider capabilityProvider = super.initCapabilities(stack, nbt);
        if(capabilityProvider != null && capabilityProvider.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null)) {
            IElectricItem electricItem = capabilityProvider.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            //noinspection ConstantConditions
            electricItem.addChargeListener((itemStack, newCharge) -> {
                int newDamage = (newCharge == 0 ? 16000 : 0) + itemStack.getItemDamage() % 16000;
                if(newDamage != itemStack.getItemDamage()) {
                    itemStack.setItemDamage(newDamage);
                }
            });
        }
        return capabilityProvider;
    }

    public boolean isUsable(ItemStack stack, int damage) {
        IElectricItem capability = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        return capability == null || capability.canUse(damage);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.getItemDamage() >= metaItemOffset) {
            T item = getItem(stack);
            SolidMaterial primaryMaterial = getPrimaryMaterial(stack);
            String materialName = primaryMaterial == null ? "" : String.valueOf(primaryMaterial.getLocalizedName());
            return I18n.format("metaitem." + item.unlocalizedName + ".name", materialName);
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        T item = getItem(itemStack);
        if(item == null) {
            return;
        }
        IToolStats toolStats = item.getToolStats();
        SolidMaterial primaryMaterial = getPrimaryMaterial(itemStack);
        SolidMaterial handleMaterial = getHandleMaterial(itemStack);
        int maxInternalDamage = getMaxInternalDamage(itemStack);

        if (maxInternalDamage > 0) {
            lines.add(I18n.format("metaitem.tool.tooltip.durability", maxInternalDamage - getInternalDamage(itemStack), maxInternalDamage));
        }
        if (primaryMaterial != null) {
            lines.add(I18n.format("metaitem.tool.tooltip.primary_material", primaryMaterial.getLocalizedName(), primaryMaterial.harvestLevel));
        }
        if (handleMaterial != null) {
            lines.add(I18n.format("metaitem.tool.tooltip.handle_material", handleMaterial.getLocalizedName(), handleMaterial.harvestLevel));
        }
        if (primaryMaterial != null && toolStats.showBasicAttributes()) {
            lines.add(I18n.format("metaitem.tool.tooltip.attack_damage", toolStats.getBaseDamage(itemStack) + primaryMaterial.harvestLevel));
            lines.add(I18n.format("metaitem.tool.tooltip.mining_speed", primaryMaterial.toolSpeed));
        }

        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        toolStats.addInformation(itemStack, lines, tooltipFlag.isAdvanced());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        SolidMaterial primaryMaterial = getPrimaryMaterial(stack);
        SolidMaterial handleMaterial = getHandleMaterial(stack);
        return Math.max(primaryMaterial == null ? 0 : getMaterialEnchantability(primaryMaterial),
            handleMaterial == null ? 0 : getMaterialEnchantability(handleMaterial));
    }

    private static int getMaterialEnchantability(SolidMaterial material) {
        if(material.materialIconSet == MaterialIconSet.SHINY ||
            material.materialIconSet == MaterialIconSet.RUBY) {
            return 33; //all shiny metals have gold enchantability
        } else if(material.materialIconSet == MaterialIconSet.DULL ||
            material.materialIconSet == MaterialIconSet.METALLIC) {
            return 21; //dull metals have iron enchantability
        } else if(material.materialIconSet == MaterialIconSet.GEM_VERTICAL ||
            material.materialIconSet == MaterialIconSet.GEM_HORIZONTAL ||
            material.materialIconSet == MaterialIconSet.DIAMOND ||
            material.materialIconSet == MaterialIconSet.OPAL ||
            material.materialIconSet == MaterialIconSet.NETHERSTAR) {
            return 15; //standard gems have diamond enchantability
        } else if(material.materialIconSet == MaterialIconSet.WOOD ||
            material.materialIconSet == MaterialIconSet.ROUGH ||
            material.materialIconSet == MaterialIconSet.FINE) {
            return 11; //wood and stone has their default enchantability
        }
        return 10; //otherwise return lowest enchantability
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if(enchantment == Enchantments.MENDING ||
            enchantment == Enchantments.UNBREAKING)
            return false; //disallow applying of unbreaking and mending
        T metaToolValueItem = getItem(stack);
        if (metaToolValueItem != null && metaToolValueItem.toolStats != null) {
            return metaToolValueItem.toolStats.canApplyEnchantment(stack, enchantment);
        }
        return false;
    }

    @Override
    public int getMaxInternalDamage(ItemStack itemStack) {
        T metaToolValueItem = getItem(itemStack);
        if (metaToolValueItem != null) {
            SolidMaterial toolMaterial = getPrimaryMaterial(itemStack);
            if (toolMaterial != null) {
                return (int) (toolMaterial.toolDurability * metaToolValueItem.getToolStats().getMaxDurabilityMultiplier(itemStack) * 10);
            }
        }
        return 0;
    }

    @Override
    public int getInternalDamage(ItemStack itemStack) {
        NBTTagCompound statsTag = itemStack.getSubCompound("GT.ToolStats");
        if (statsTag == null || !statsTag.hasKey("Damage", Constants.NBT.TAG_INT)) {
            return 0;
        }
        return statsTag.getInteger("Damage");
    }

    private boolean setInternalDamage(ItemStack itemStack, int damage) {
        NBTTagCompound statsTag = itemStack.getOrCreateSubCompound("GT.ToolStats");
        statsTag.setInteger("Damage", damage);
        return getInternalDamage(itemStack) < getMaxInternalDamage(itemStack);
    }

    @Nullable
    public static SolidMaterial getPrimaryMaterial(ItemStack itemStack) {
        NBTTagCompound statsTag = itemStack.getSubCompound("GT.ToolStats");
        if(statsTag == null || !statsTag.hasKey("PrimaryMaterial", Constants.NBT.TAG_STRING))
            return null;
        Material material = Material.MATERIAL_REGISTRY.getObject(statsTag.getString("PrimaryMaterial"));
        if(material instanceof SolidMaterial) {
            return (SolidMaterial) material;
        }
        return null;
    }

    @Nullable
    public static SolidMaterial getHandleMaterial(ItemStack itemStack) {
        NBTTagCompound statsTag = itemStack.getSubCompound("GT.ToolStats");
        if(statsTag == null || !statsTag.hasKey("HandleMaterial", Constants.NBT.TAG_STRING))
            return null;
        Material material = Material.MATERIAL_REGISTRY.getObject(statsTag.getString("HandleMaterial"));
        if(material instanceof SolidMaterial) {
            return (SolidMaterial) material;
        }
        return null;
    }

    public class MetaToolValueItem extends MetaValueItem {

        protected IToolStats toolStats;

        private MetaToolValueItem(int metaValue, String unlocalizedName) {
            super(metaValue, unlocalizedName);
            setMaxStackSize(1);
        }

        @Override
        public MetaToolValueItem addStats(IMetaItemStats... stats) {
            for(IMetaItemStats metaItemStats : stats) {
                if(metaItemStats instanceof IToolStats) {
                    setToolStats((IToolStats) metaItemStats);
                }
            }
            super.addStats(stats);
            return this;
        }

        public MetaToolValueItem setToolStats(IToolStats toolStats) {
            if (toolStats == null) {
                throw new IllegalArgumentException("Cannot set Tool Stats to null.");
            }
            this.toolStats = toolStats;
            toolStats.onStatsAddedToTool(this);
            return this;
        }

        public MetaToolValueItem addOreDict(ToolDictNames... oreDictNames) {
            Validate.notNull(oreDictNames, "Cannot add null ToolDictName.");
            Validate.noNullElements(oreDictNames, "Cannot add null ToolDictName.");

			for (ToolDictNames oreDict : oreDictNames) {
				OreDictionary.registerOre(oreDict.name(), getStackForm());
			}
            return this;
        }

        public MetaToolValueItem addToList(Collection<SimpleItemStack> toolList) {
            Validate.notNull(toolList, "Cannot add toll null list.");
            toolList.add(new SimpleItemStack(this.getStackForm(1)));
            return this;
        }

        public IToolStats getToolStats() {
            if (toolStats == null) {
                throw new IllegalStateException("Someone forgot to assign toolStats to MetaToolValueItem.");
            }
            return toolStats;
        }

        @Override
        public ItemStack getStackForm(int amount) {
            ItemStack rawStack = super.getStackForm(amount);
            applyToolNBT(rawStack, Materials.Darmstadtium,
                toolStats.hasMaterialHandle() ? Materials.Darmstadtium : null);
            return rawStack;
        }

        public ItemStack getStackForm(SolidMaterial primaryMaterial, SolidMaterial handleMaterial) {
            ItemStack rawStack = super.getStackForm(1);
            applyToolNBT(rawStack, primaryMaterial,
                toolStats.hasMaterialHandle() ? handleMaterial : null);
            return rawStack;
        }

        public ItemStack getChargedStack(SolidMaterial primaryMaterial, SolidMaterial handleMaterial, long chargeAmount) {
            ItemStack rawStack = super.getChargedStack(chargeAmount);
            applyToolNBT(rawStack, primaryMaterial,
                toolStats.hasMaterialHandle() ? handleMaterial : null);
            return rawStack;
        }

        public ItemStack getMaxChargeOverrideStack(SolidMaterial primaryMaterial, SolidMaterial handleMaterial, long maxCharge) {
            ItemStack rawStack = super.getMaxChargeOverrideStack(maxCharge);
            applyToolNBT(rawStack, primaryMaterial,
                toolStats.hasMaterialHandle() ? handleMaterial : null);
            return rawStack;
        }

        public final ItemStack getStackForm(SolidMaterial primaryMaterial, SolidMaterial handleMaterial, int amount) {
            ItemStack stack = new ItemStack(ToolMetaItem.this, amount, metaItemOffset + metaValue);
            T metaToolValueItem = getItem(stack);
            return stack;
        }

        private void applyToolNBT(ItemStack stack, SolidMaterial primaryMaterial, SolidMaterial handleMaterial) {
            NBTTagCompound toolNBT = new NBTTagCompound();
            ArrayList<SolidMaterial> materials = new ArrayList<>();

            toolNBT.setString("PrimaryMaterial", primaryMaterial.toString());
            materials.add(primaryMaterial);

            if (this.getToolStats().hasMaterialHandle() && handleMaterial != null) {
                toolNBT.setString("HandleMaterial", handleMaterial.toString());
                materials.add(handleMaterial);
            }

            NBTTagCompound nbtTag = stack.getTagCompound();
            if (nbtTag == null) {
                nbtTag = new NBTTagCompound();
            }
            nbtTag.setTag("GT.ToolStats", toolNBT);
            stack.setTagCompound(nbtTag);

            Map<Enchantment, Integer> enchantments = bakeEnchantmentsMap(stack, materials);
            EnchantmentHelper.setEnchantments(enchantments, stack);
        }

        private Map<Enchantment, Integer> bakeEnchantmentsMap(ItemStack itemStack, Collection<SolidMaterial> materials) {
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            for(SolidMaterial material : materials) {
                for(EnchantmentData enchantmentData : material.toolEnchantments) {
                    if(enchantments.containsKey(enchantmentData.enchantment)) {
                        int level = Math.min(enchantments.get(enchantmentData.enchantment) + enchantmentData.level,
                            enchantmentData.enchantment.getMaxLevel());
                        enchantments.put(enchantmentData.enchantment, level);
                    } else {
                        enchantments.put(enchantmentData.enchantment, enchantmentData.level);
                    }
                }
            }
            for(EnchantmentData enchantmentData : toolStats.getEnchantments(itemStack)) {
                if(enchantments.containsKey(enchantmentData.enchantment)) {
                    int level = Math.min(enchantments.get(enchantmentData.enchantment) + enchantmentData.level,
                        enchantmentData.enchantment.getMaxLevel());
                    enchantments.put(enchantmentData.enchantment, level);
                } else {
                    enchantments.put(enchantmentData.enchantment, enchantmentData.level);
                }
            }
            return enchantments;
        }

    }

}
