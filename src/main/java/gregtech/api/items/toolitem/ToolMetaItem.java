package gregtech.api.items.toolitem;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.IDamagableItem;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_Utility;
import ic2.api.item.IElectricItemManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Optional;

/**
 * ToolMetaItem is item that can have up to Short.MAX_VALUE tools inside it
 * These tools can be made from different materials, have special behaviours, and basically do everything that standard MetaItem can do.
 *
 * Tool behaviours are implemented by {@link IToolStats} objects
 *
 * As example, with this code you can add LV electric drill tool:
 * {@code addItem(0).addStats(new ElectricStats(10000, 1, true, false)).setToolStats(new ToolStatsExampleDrill()) }
 *
 * @see IToolStats
 * @see MetaItem
 */
@SuppressWarnings("unchecked")
public class ToolMetaItem<T extends ToolMetaItem.MetaToolValueItem> extends MetaItem<T> implements IDamagableItem {

    public ToolMetaItem(String unlocalizedName) {
        super(unlocalizedName, (short) 0);
    }

    @Override
    protected T constructMetaValueItem(short metaValue) {
        return (T) new MetaToolValueItem(metaValue);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        MetaToolValueItem metaToolValueItem = getItem(stack);
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
        MetaToolValueItem metaToolValueItem = getItem(stack);
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
        MetaToolValueItem metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(toolStats.isMinableBlock(state, stack)) {
                doDamageToItem(stack, toolStats.getToolDamagePerBlockBreak(stack));
                ResourceLocation mineSound = toolStats.getMiningSound(stack);
                if(mineSound != null) {
                    GT_Utility.playSound(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, mineSound, SoundCategory.PLAYERS, 0.27f, 1.0f);
                }
                if(!isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack))) {
                    ResourceLocation breakSound = toolStats.getBreakingSound(stack);
                    if(breakSound != null) {
                        GT_Utility.playSound(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, breakSound, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        MetaToolValueItem metaToolValueItem = getItem(stack);
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
        MetaToolValueItem metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            IToolStats toolStats = metaToolValueItem.getToolStats();
            if(isUsable(stack, toolStats.getToolDamagePerBlockBreak(stack)) && toolStats.isMinableBlock(blockState, stack)) {
                return toolStats.getBaseQuality(stack) + getPrimaryMaterial(stack).toolQuality;
            }
        }
        return 0;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        MetaToolValueItem metaValueItem = getItem(stack);
        if(metaValueItem != null && slot == EntityEquipmentSlot.MAINHAND) {
            IToolStats toolStats = metaValueItem.getToolStats();
            float attackDamage = toolStats.getBaseDamage(stack);
            float attackSpeed = toolStats.getAttackSpeed(stack);

            HashMultimap<String, AttributeModifier> modifiers = HashMultimap.create();
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier("Weapon modifier", attackDamage, 0));
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier("Weapon modifier", attackSpeed, 0));
            return modifiers;
        }
        return HashMultimap.create();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        //cancel attack if broken or out of charge
        MetaToolValueItem metaToolValueItem = getItem(stack);
        if(metaToolValueItem != null) {
            int damagePerAttack = metaToolValueItem.getToolStats().getToolDamagePerEntityAttack(stack);
            if(!isUsable(stack, damagePerAttack)) return true;
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        MetaToolValueItem metaValueItem = getItem(stack);
        if(metaValueItem != null) {
            IToolStats toolStats = metaValueItem.getToolStats();
            doDamageToItem(stack, toolStats.getToolDamagePerEntityAttack(stack));
            ResourceLocation hitSound = toolStats.getEntityHitSound(stack);
            if(hitSound != null) {
                GT_Utility.playSound(target.worldObj, target.posX, target.posY, target.posZ, hitSound, SoundCategory.PLAYERS, 0.27f, 1.0f);
            }
            if(!isUsable(stack, toolStats.getToolDamagePerEntityAttack(stack))) {
                ResourceLocation breakSound = toolStats.getBreakingSound(stack);
                if(breakSound != null) {
                    GT_Utility.playSound(target.worldObj, target.posX, target.posY, target.posZ, breakSound, SoundCategory.PLAYERS, 1.0f, 1.0f);
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
        MetaToolValueItem metaToolValueItem = getItem(stack);
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
        MetaToolValueItem metaToolValueItem = getItem(stack);
        IElectricItemManager electricItemManager = getManager(stack);
        if(electricItemManager.getMaxCharge(stack) == 0) {
            return getInternalDamage(stack) + damage < getMaxInternalDamage(stack);
        }
        return electricItemManager.canUse(stack, damage) && getInternalDamage(stack) + (damage / 10) < getMaxInternalDamage(stack);
    }


    private int getMaxInternalDamage(ItemStack itemStack) {
        MetaToolValueItem metaToolValueItem = getItem(itemStack);
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

    public static SolidMaterial getPrimaryMaterial(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.ToolPrimaryMaterial", Constants.NBT.TAG_STRING))
            return Materials.Iron;
        Material material = Material.MATERIAL_REGISTRY.getObject(itemStack.getTagCompound().getString("GT.ToolPrimaryMaterial"));
        if(material instanceof SolidMaterial) {
            return (SolidMaterial) material;
        }
        return Materials.Iron;
    }

    public static SolidMaterial getSecondaryMaterial(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.ToolSecondaryMaterial", Constants.NBT.TAG_STRING))
            return Materials.Iron;
        Material material = Material.MATERIAL_REGISTRY.getObject(itemStack.getTagCompound().getString("GT.ToolSecondaryMaterial"));
        if(material instanceof SolidMaterial) {
            return (SolidMaterial) material;
        }
        return Materials.Iron;
    }

    public final ItemStack addTool(int toolID, String english, String tooltip, IToolStats toolStats, String... craftingNames) {
        if (tooltip == null) tooltip = "";
        if (toolID >= 0 && toolID < 32766 && toolID % 2 == 0) {
            //GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + toolID + ".name", english);
            //GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + toolID + ".tooltip", tooltip);
            //GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (toolID + 1) + ".name", english + " (Empty)");
            //GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (toolID + 1) + ".tooltip", "You need to recharge it");

            ItemStack stack = new ItemStack(this, 1, toolID);
            toolStats.onStatsAddedToTool(getItem(stack), toolID);
            mToolStats.put((short) toolID, toolStats);
            mToolStats.put((short) (toolID + 1), toolStats);
            for (String oreName : craftingNames) {
                OreDictionaryUnifier.registerOre(oreName, stack);
            }
            return stack;
        }
        return null;
    }

    public final ItemStack getToolWithStats(int toolID, int amount, Material primaryMaterial, Material secondaryMaterial, long[] electricData) {
        ItemStack stack = new ItemStack(this, amount, toolID);
        MetaToolValueItem metaToolValueItem = getItem(stack);
        if (metaToolValueItem != null) {
            if (metaToolValueItem.toolStats != null) {
                NBTTagCompound tMainNBT = new NBTTagCompound(), tToolNBT = new NBTTagCompound();
                if (primaryMaterial != null && primaryMaterial instanceof SolidMaterial) {
                    tToolNBT.setString("GT.ToolPrimaryMaterial", primaryMaterial.toString());
                    tToolNBT.setLong("GT.MaxDamage", 100L * (long) ((((SolidMaterial) primaryMaterial).toolDurability) * metaToolValueItem.toolStats.getMaxDurabilityMultiplier(stack)));
                }
                if (secondaryMaterial != null)
                    tToolNBT.setString("GT.ToolSecondaryMaterial", secondaryMaterial.toString());

                if (electricData != null) {
                    tToolNBT.setBoolean("GT.Electric", true);
                    tToolNBT.setLong("GT.MaxCharge", electricData[0]);
                    tToolNBT.setLong("GT.Voltage", electricData[1]);
                    tToolNBT.setLong("GT.Tier", electricData[2]);
                    tToolNBT.setLong("GT.SpecialData", electricData[3]);
                }

                tMainNBT.setTag("GT.ToolStats", tToolNBT);
                stack.setTagCompound(tMainNBT);
            }
        }
        isItemStackUsable(stack);
        return stack;
    }

    public class MetaToolValueItem extends MetaValueItem {

        private IToolStats toolStats;

        private MetaToolValueItem(int metaValue) {
            super(metaValue);
            setNoUnification();
        }

        public MetaToolValueItem setToolStats(IToolStats toolStats) {
            if(toolStats == null) {
                throw new IllegalArgumentException("Cannot set Tool Stats to null.");
            }
            this.toolStats = toolStats;
            return this;
        }

        public IToolStats getToolStats() {
            return toolStats;
        }

    }

}
