package gregtech.api.items.toolitem;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.GregTechAPI;
import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.IDamagableItem;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.GTUtility;
import ic2.api.item.IElectricItemManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;

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
    protected T constructMetaValueItem(short metaValue, String unlocalizedName, String... nameParameters) {
        return (T) new MetaToolValueItem(metaValue, unlocalizedName, nameParameters);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            toolStats.onToolCrafted(stack, player);
            ArrayList<EnchantmentData> enchantments = new ArrayList<>(toolStats.getEnchantments(stack));
            SolidMaterial material = getPrimaryMaterial(stack);
            for(EnchantmentData enchantmentData : material.toolEnchantments) {
                Optional<EnchantmentData> sameEnchantment = enchantments.stream().filter(it -> it.enchantment == enchantmentData.enchantment).findAny();
                if(sameEnchantment.isPresent()) {
                    enchantments.remove(sameEnchantment.get());
                    int level = Math.min(enchantmentData.level + sameEnchantment.get().level, enchantmentData.enchantment.getMaxLevel());
                    enchantments.add(new EnchantmentData(enchantmentData.enchantment, level));
                } else {
                    enchantments.add(enchantmentData);
                }
            }
            for(EnchantmentData enchantmentData : enchantments) {
                stack.addEnchantment(enchantmentData.enchantment, enchantmentData.level);
            }
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(!doDamageToItem(stack, toolStats.getToolDamagePerContainerCraft(stack)) && getManager(stack).getMaxCharge(stack) == 0) {
                return null;
            }
        }
        return stack;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        T metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(toolStats.isMinableBlock(state, stack)) {
                doDamageToItem(stack, toolStats.getToolDamagePerBlockBreak(stack));
                ResourceLocation mineSound = toolStats.getMiningSound(stack);
                if(mineSound != null) {
                    GTUtility.playSound(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, mineSound, SoundCategory.PLAYERS, 0.27f, 1.0f);
                }
                if(!isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack))) {
                    ResourceLocation breakSound = toolStats.getBreakingSound(stack);
                    if(breakSound != null) {
                        GTUtility.playSound(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, breakSound, SoundCategory.PLAYERS, 1.0f, 1.0f);
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
                return getPrimaryMaterial(stack).toolSpeed * toolStats.getSpeedMultiplier(stack);
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
                return toolStats.getBaseQuality(stack) + getPrimaryMaterial(stack).harvestLevel;
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
            float attackDamage = toolStats.getBaseDamage(stack);
            float attackSpeed = toolStats.getAttackSpeed(stack);

            HashMultimap<String, AttributeModifier> modifiers = HashMultimap.create();
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier("Weapon modifier", attackDamage, 0));
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier("Weapon modifier", attackSpeed, 0));
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
            doDamageToItem(stack, toolStats.getToolDamagePerEntityAttack(stack));
            ResourceLocation hitSound = toolStats.getEntityHitSound(stack);
            if(hitSound != null) {
                GTUtility.playSound(target.getEntityWorld(), target.posX, target.posY, target.posZ, hitSound, SoundCategory.PLAYERS, 0.27f, 1.0f);
            }
            if(!isUsable(stack, toolStats.getToolDamagePerEntityAttack(stack))) {
                ResourceLocation breakSound = toolStats.getBreakingSound(stack);
                if(breakSound != null) {
                    GTUtility.playSound(target.getEntityWorld(), target.posX, target.posY, target.posZ, breakSound, SoundCategory.PLAYERS, 1.0f, 1.0f);
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
    public boolean doDamageToItem(ItemStack stack, int vanillaDamage) {
        if(!isUsable(stack, vanillaDamage)) {
            return false;
        }
        T metaToolValueItem = getItem(stack);
        IElectricItemManager electricItemManager = getManager(stack);
        if(electricItemManager.getMaxCharge(stack) == 0) {
            setInternalDamage(stack, getInternalDamage(stack) + vanillaDamage);
        } else {
            electricItemManager.discharge(stack, vanillaDamage, electricItemManager.getTier(stack), true, false, false);
            setInternalDamage(stack, getInternalDamage(stack) + (vanillaDamage / 10));
        }
        return true;
    }

    public boolean isUsable(ItemStack stack, int damage) {
        T metaToolValueItem = getItem(stack);
        IElectricItemManager electricItemManager = getManager(stack);
        if(electricItemManager.getMaxCharge(stack) == 0) {
            return getInternalDamage(stack) + damage < getMaxInternalDamage(stack);
        }
        return electricItemManager.canUse(stack, damage) && getInternalDamage(stack) + (damage / 10) < getMaxInternalDamage(stack);
    }

    private int getMaxInternalDamage(ItemStack itemStack) {
        T metaToolValueItem = getItem(itemStack);
        if (metaToolValueItem != null) {
            SolidMaterial toolMaterial = getPrimaryMaterial(itemStack);
            return (int) (toolMaterial.toolDurability * metaToolValueItem.getToolStats().getMaxDurabilityMultiplier(itemStack));
        }
        return 0;
    }

    private int getInternalDamage(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.ToolDamage", Constants.NBT.TAG_INT))
            return 0;
        return itemStack.getTagCompound().getInteger("GT.ToolDamage");
    }

    private void setInternalDamage(ItemStack itemStack, int damage) {
        if(!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        tagCompound.setInteger("GT.ToolDamage", damage);
    }

    @Nullable
    public static SolidMaterial getPrimaryMaterial(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.ToolPrimaryMaterial", Constants.NBT.TAG_STRING))
            return null;
        Material material = Material.MATERIAL_REGISTRY.getObject(itemStack.getTagCompound().getString("GT.ToolPrimaryMaterial"));
        if(material instanceof SolidMaterial) {
            return (SolidMaterial) material;
        }
        return null;
    }

    @Nullable
    public static SolidMaterial getHandleMaterial(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.ToolHandleMaterial", Constants.NBT.TAG_STRING))
            return null;
        Material material = Material.MATERIAL_REGISTRY.getObject(itemStack.getTagCompound().getString("GT.ToolHandleMaterial"));
        if(material instanceof SolidMaterial) {
            return (SolidMaterial) material;
        }
        return null;
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] {
            GregTechAPI.TAB_GREGTECH
        };
    }

    public class MetaToolValueItem extends MetaValueItem {

        protected IToolStats toolStats;

        private MetaToolValueItem(int metaValue, String unlocalizedName, String... nameParameters) {
            super(metaValue, unlocalizedName, nameParameters);
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

        public IToolStats getToolStats() {
            if (toolStats == null) {
                throw new IllegalStateException("Someone forgot to assign toolStats to MetaToolValueItem.");
            }
            return toolStats;
        }

        @Override
        public ItemStack getStackForm(int amount) {
            return getStackForm(Materials.Darmstadtium, Materials.Darmstadtium);
        }

        public ItemStack getStackForm(Material primaryMaterial, Material handleMaterial) {
            return getStackForm(primaryMaterial, handleMaterial, 1);
        }

        public final ItemStack getStackForm(Material primaryMaterial, Material handleMaterial, int amount) {
            ItemStack stack = new ItemStack(ToolMetaItem.this, amount, metaItemOffset + metaValue);

            T metaToolValueItem = getItem(stack);
            if (metaToolValueItem != null) {
                if (metaToolValueItem.toolStats != null) {

                    NBTTagCompound toolNBT = new NBTTagCompound();
                    if (primaryMaterial != null && primaryMaterial instanceof SolidMaterial) {
                        toolNBT.setString("GT.ToolPrimaryMaterial", primaryMaterial.toString());
                        toolNBT.setLong("GT.MaxDamage", 100L * (long) ((((SolidMaterial) primaryMaterial).toolDurability) * metaToolValueItem.toolStats.getMaxDurabilityMultiplier(stack)));
                    }
                    if (handleMaterial != null)
                        toolNBT.setString("GT.ToolHandleMaterial", handleMaterial.toString());

                    NBTTagCompound nbtTag = new NBTTagCompound();
                    nbtTag.setTag("GT.ToolStats", toolNBT);
                    stack.setTagCompound(nbtTag);
                }
            }
            return stack;
        }

    }

}
