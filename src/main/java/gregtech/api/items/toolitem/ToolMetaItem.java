package gregtech.api.items.toolitem;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.enums.material.Materials;
import gregtech.api.interfaces.IDamagableItem;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.IToolStats;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.util.GT_Utility;
import ic2.api.item.IElectricItemManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

import static gregtech.api.enums.GT_Values.MODID;

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
            HashMap<Enchantment, Integer> enchants = new HashMap<>(toolStats.getEnchantments(stack));
            Materials material = getMaterial(stack);
            if(material.toolEnchantment != null) {
                if(enchants.containsKey(material.toolEnchantment)) {
                    enchants.put(material.toolEnchantment, enchants.get(material.toolEnchantment) + material.toolEnchantmentLevel);
                } else enchants.put(material.toolEnchantment, material.toolEnchantmentLevel);
            }
            for(Enchantment enchantment : enchants.keySet()) {
                stack.addEnchantment(enchantment, enchants.get(enchantment));
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
                return getMaterial(stack).toolSpeed * toolStats.getSpeedMultiplier(stack);
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
                return toolStats.getBaseQuality(stack) + getMaterial(stack).toolQuality;
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
            Materials toolMaterial = getMaterial(itemStack);
            return (int) (toolMaterial.durability * metaToolValueItem.getToolStats().getMaxDurabilityMultiplier(itemStack));
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

    public Materials getMaterial(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.ToolMaterial", Constants.NBT.TAG_STRING))
            return Materials._NULL;
        return Materials.get(itemStack.getTagCompound().getString("GT.ToolMaterial"));
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] durabilityBar;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] energyBar;

    @Override
    public void registerIcons(TextureMap textureMap) {
        super.registerIcons(textureMap);
        this.durabilityBar = new TextureAtlasSprite[9];
        this.energyBar = new TextureAtlasSprite[9];
        for(int i = 0; i < this.durabilityBar.length; i++) {
            this.durabilityBar[i] = textureMap.registerSprite(new ResourceLocation(MODID, "items/durability_bar/DURABILITY_BAR_" + i));
            this.energyBar[i] = textureMap.registerSprite(new ResourceLocation(MODID, "items/energy_bar/ENERGY_BAR_" + i));
        }
    }

    public class MetaToolValueItem extends MetaValueItem {

        private IToolStats toolStats;

        private MetaToolValueItem(int metaValue) {
            super(metaValue);
            setNoUnification();
            setHandheld();
        }

        public MetaToolValueItem setToolStats(IToolStats toolStats) {
            if(toolStats == null) {
                throw new IllegalArgumentException("Cannot set Tool Stats to null.");
            }
            this.toolStats = toolStats;
            return this;
        }

        @Override
        @SideOnly(Side.CLIENT)
        protected void registerIcons(TextureMap textureMap) {}

        @Override
        @SideOnly(Side.CLIENT)
        protected TextureAtlasSprite getIcon(ItemStack itemStack, int pass) {
            if(pass == 4) {
                float maxInternalDamage = getMaxInternalDamage(itemStack);
                if(maxInternalDamage > 0) {
                    return durabilityBar[(int) ((getInternalDamage(itemStack) / maxInternalDamage) * 8)];
                }
                return durabilityBar[0];
            }

            if(pass == 5) {
                IElectricItemManager electricItemManager = getManager(itemStack);
                double maxCharge = electricItemManager.getMaxCharge(itemStack);
                if(maxCharge > 0) {
                    return energyBar[(int) ((electricItemManager.getCharge(itemStack) / maxCharge) * 8)];
                }
                return energyBar[0];
            }

            IIconContainer container = toolStats.getIcon(pass / 2 == 1, itemStack);
            if(container == null) {
                return null;
            }
            if(pass % 2 == 0) {
                return container.getIcon();
            }
            return container.getOverlayIcon();
        }

        @Override
        @SideOnly(Side.CLIENT)
        protected int getRenderPasses(ItemStack itemStack) {
            return getManager(itemStack).getMaxCharge(itemStack) > 0 ? 6 : 5;
        }

        @Override
        protected int getColor(ItemStack itemStack, int pass) {
            if(pass % 2 == 0) {
                return toolStats.getColor(pass / 2 == 1, itemStack);
            }
            return 0xFFFFFF;
        }

        public IToolStats getToolStats() {
            return toolStats;
        }

    }

}
