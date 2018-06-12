package gregtech.api.items.toolitem;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.capability.IElectricItem;
import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.IDamagableItem;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.stack.SimpleItemStack;
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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryCrafting;
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
import net.minecraftforge.common.util.Constants;
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
    @SideOnly(Side.CLIENT)
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        SolidMaterial primaryMaterial = getPrimaryMaterial(stack);
        SolidMaterial handleMaterial = getHandleMaterial(stack);

        switch (tintIndex) {
            case 0:
                return handleMaterial != null ? handleMaterial.materialRGB : 0xFFFFFF;
            case 1:
                return 0xFFFFFF;
            case 2:
                return primaryMaterial != null ? primaryMaterial.materialRGB : 0xFFFFFF;
            case 3:
                return 0xFFFFFF;
        }
        return 0xFFFFFF;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true; //required for machine wrenching
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        //don't show durability if item is not electric and it's damage is zero
        return stack.hasCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null) ||
            getInternalDamage(stack) != 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        //if itemstack has electric charge ability, show electric charge percentage
        if(stack.hasCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null)) {
            IElectricItem electricItem = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
            long currentCharge = electricItem.discharge(Long.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
            return currentCharge / (electricItem.getMaxCharge() * 1.0);
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

    public void onToolCreated(ItemStack toolStack, InventoryCrafting ingredients) {
        NBTTagList componentList = new NBTTagList();
        for(int slotIndex = 0; slotIndex < ingredients.getSizeInventory(); slotIndex++) {
            ItemStack stackInSlot = ingredients.getStackInSlot(slotIndex).copy();
            stackInSlot.setCount(1);
            if(!stackInSlot.isEmpty()) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackInSlot.writeToNBT(stackTag);
                componentList.appendTag(stackTag);
            }
        }
        toolStack.setTagInfo("CraftingComponents", componentList);
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
                ResourceLocation mineSound = toolStats.getMiningSound(stack);
                if(mineSound != null) {
                    SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(mineSound);
                    world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, soundEvent, SoundCategory.PLAYERS, 0.27f, 1.0f, false);
                }
                if(!isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack))) {
                    ResourceLocation breakSound = toolStats.getBreakingSound(stack);
                    if(breakSound != null) {
                        SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(breakSound);
                        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
                    }
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
            ResourceLocation hitSound = toolStats.getEntityHitSound(stack);
            if(hitSound != null) {
                SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(hitSound);
                target.getEntityWorld().playSound(target.posX, target.posY, target.posZ, soundEvent, SoundCategory.PLAYERS, 0.27f, 1.0f, false);
            }
            if(!isUsable(stack, toolStats.getToolDamagePerEntityAttack(stack))) {
                ResourceLocation breakSound = toolStats.getBreakingSound(stack);
                if(breakSound != null) {
                    SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(breakSound);
                    target.getEntityWorld().playSound(target.posX, target.posY, target.posZ, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
                }
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
        if(!isUsable(stack, vanillaDamage)) {
            return false;
        }
        IElectricItem capability = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
        if(!simulate) {
            if(capability == null) {
                setInternalDamage(stack, getInternalDamage(stack) + vanillaDamage);
            } else {
                capability.discharge(vanillaDamage, capability.getTier(), true, false, false);
                setInternalDamage(stack, getInternalDamage(stack) + (vanillaDamage / 10));
            }
        }
        return true;
    }

    public boolean isUsable(ItemStack stack, int damage) {
        IElectricItem capability = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
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
        if (primaryMaterial != null) {
            lines.add(I18n.format("metaitem.tool.tooltip.attack_damage", toolStats.getBaseDamage(itemStack) + primaryMaterial.harvestLevel));
            lines.add(I18n.format("metaitem.tool.tooltip.mining_speed", primaryMaterial.toolSpeed));
        }
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);
    }

    @Override
    public int getMaxInternalDamage(ItemStack itemStack) {
        T metaToolValueItem = getItem(itemStack);
        if (metaToolValueItem != null) {
            SolidMaterial toolMaterial = getPrimaryMaterial(itemStack);
            if (toolMaterial != null) {
                return (int) (toolMaterial.toolDurability * metaToolValueItem.getToolStats().getMaxDurabilityMultiplier(itemStack));
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

    private void setInternalDamage(ItemStack itemStack, int damage) {
        NBTTagCompound statsTag = itemStack.getOrCreateSubCompound("GT.ToolStats");
        statsTag.setInteger("Damage", damage);
        if(getInternalDamage(itemStack) >= getMaxInternalDamage(itemStack)) {
            itemStack.shrink(1);
        }
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
        public MetaValueItem addStats(IMetaItemStats... stats) {
            for(IMetaItemStats metaItemStats : stats) {
                if(metaItemStats instanceof IToolStats) {
                    setToolStats((IToolStats) metaItemStats);
                }
            }
            return super.addStats(stats);
        }

        public MetaToolValueItem setToolStats(IToolStats toolStats) {
            if (toolStats == null) {
                throw new IllegalArgumentException("Cannot set Tool Stats to null.");
            }
            this.toolStats = toolStats;
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

            Map<Enchantment, Integer> enchantments = bakeEnchantmentsMap(materials);
            EnchantmentHelper.setEnchantments(enchantments, stack);
        }

        private Map<Enchantment, Integer> bakeEnchantmentsMap(Collection<SolidMaterial> materials) {
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
            return enchantments;
        }

    }

}
