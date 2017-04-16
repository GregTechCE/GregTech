package gregtech.api.items;

import forestry.api.arboriculture.IToolGrafter;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enchants.Enchantment_Radioactivity;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.interfaces.IDamagableItem;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.IToolStats;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * This is an example on how you can create a Tool ItemStack, in this case a Bismuth Wrench:
 * GT_MetaGenerated_Tool.sInstances.get("gt.metatool.01").getToolWithStats(GT_MetaGenerated_Tool_01.WRENCH, 1, Materials.Bismuth, Materials.Bismuth, null);
 */
@Optional.InterfaceList(value = {
        @Optional.Interface(iface = "forestry.api.arboriculture.IToolGrafter", modid = GT_Values.MOD_ID_FR)
        //@Optional.Interface(iface = "mods.railcraft.api.core.items.IToolCrowbar", modid = MOD_ID_RC),
        //@Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "BuildCraft"),
        //@Optional.Interface(iface = "crazypants.enderio.api.tool.ITool", modid = "EnderIO")
})
public abstract class GT_MetaGenerated_Tool extends GT_MetaBase_Item implements IDamagableItem, IToolGrafter {//, IToolGrafter, IToolCrowbar, IToolWrench, ITool {
    /**
     * All instances of this Item Class are listed here.
     * This gets used to register the Renderer to all Items of this Type, if useStandardMetaItemRenderer() returns true.
     * <p/>
     * You can also use the unlocalized Name gotten from getUnlocalizedName() as Key if you want to get a specific Item.
     */
    public static final HashMap<String, GT_MetaGenerated_Tool> sInstances = new HashMap<>();

	/* ---------- CONSTRUCTOR AND MEMBER VARIABLES ---------- */

    public final HashMap<Short, IToolStats> mToolStats = new HashMap<>();

    /**
     * Creates the Item using these Parameters.
     *
     * @param aUnlocalized The Unlocalized Name of this Item.
     */
    public GT_MetaGenerated_Tool(String aUnlocalized) {
        super(aUnlocalized);
        GT_ModHandler.registerBoxableItemToToolBox(this);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        setMaxStackSize(1);
        sInstances.put(getUnlocalizedName(), this);
    }

	/* ---------- FOR ADDING CUSTOM ITEMS INTO THE REMAINING 766 RANGE ---------- */

    public static final Materials getPrimaryMaterial(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null) return Materials.getRealMaterial(aNBT.getString("PrimaryMaterial"));
        }
        return Materials._NULL;
    }

    public static final Materials getSecondaryMaterial(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null) return Materials.getRealMaterial(aNBT.getString("SecondaryMaterial"));
        }
        return Materials._NULL;
    }

	/* ---------- INTERNAL OVERRIDES ---------- */

    public static final long getToolMaxDamage(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null) return aNBT.getLong("MaxDamage");
        }
        return 0;
    }

    public static final long getToolDamage(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null) return aNBT.getLong("Damage");
        }
        return 0;
    }

    public static final boolean setToolDamage(ItemStack aStack, long aDamage) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null) {
                aNBT.setLong("Damage", aDamage);
                return true;
            }
        }
        return false;
    }

    /**
     * This adds a Custom Item to the ending Range.
     *
     * @param aID                     The Id of the assigned Tool Class [0 - 32765] (only even Numbers allowed! Uneven ID's are empty electric Items)
     * @param aEnglish                The Default Localized Name of the created Item
     * @param aToolTip                The Default ToolTip of the created Item, you can also insert null for having no ToolTip
     * @param aToolStats              The Food Value of this Item. Can be null as well.
     * @param aOreDictNamesAndAspects The OreDict Names you want to give the Item. Also used to assign Thaumcraft Aspects.
     * @return An ItemStack containing the newly created Item, but without specific Stats.
     */
    public final ItemStack addTool(int aID, String aEnglish, String aToolTip, IToolStats aToolStats, Object... aOreDictNamesAndAspects) {
        if (aToolTip == null) aToolTip = "";
        if (aID >= 0 && aID < 32766 && aID % 2 == 0) {
            GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + aID + ".name", aEnglish);
            GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + aID + ".tooltip", aToolTip);
            GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (aID + 1) + ".name", aEnglish + " (Empty)");
            GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (aID + 1) + ".tooltip", "You need to recharge it");
            mToolStats.put((short) aID, aToolStats);
            mToolStats.put((short) (aID + 1), aToolStats);
            aToolStats.onStatsAddedToTool(this, aID);
            ItemStack rStack = new ItemStack(this, 1, aID);
            List<TC_AspectStack> tAspects = new ArrayList<TC_AspectStack>();
            for (Object tOreDictNameOrAspect : aOreDictNamesAndAspects) {
                if (tOreDictNameOrAspect instanceof TC_AspectStack)
                    ((TC_AspectStack) tOreDictNameOrAspect).addToAspectList(tAspects);
                else
                    GT_OreDictUnificator.registerOre(tOreDictNameOrAspect, rStack);
            }
            if (GregTech_API.sThaumcraftCompat != null)
                GregTech_API.sThaumcraftCompat.registerThaumcraftAspectsToItem(rStack, tAspects, false);
            return rStack;
        }
        return null;
    }

    /**
     * This Function gets an ItemStack Version of this Tool
     *
     * @param aToolID            the ID of the Tool Class
     * @param aAmount            Amount of Items (well normally you only need 1)
     * @param aPrimaryMaterial   Primary Material of this Tool
     * @param aSecondaryMaterial Secondary (Rod/Handle) Material of this Tool
     * @param aElectricArray     The Electric Stats of this Tool (or null if not electric)
     */
    public final ItemStack getToolWithStats(int aToolID, int aAmount, Materials aPrimaryMaterial, Materials aSecondaryMaterial, long[] aElectricArray) {
        ItemStack rStack = new ItemStack(this, aAmount, aToolID);
        IToolStats tToolStats = getToolStats(rStack);
        if (tToolStats != null) {
            NBTTagCompound tMainNBT = new NBTTagCompound(), tToolNBT = new NBTTagCompound();
            if (aPrimaryMaterial != null) {
                tToolNBT.setString("PrimaryMaterial", aPrimaryMaterial.mName);
                tToolNBT.setLong("MaxDamage", 100L * (long) (aPrimaryMaterial.mDurability * tToolStats.getMaxDurabilityMultiplier()));
            }
            if (aSecondaryMaterial != null) tToolNBT.setString("SecondaryMaterial", aSecondaryMaterial.mName);

            if (aElectricArray != null) {
                tToolNBT.setBoolean("Electric", true);
                tToolNBT.setLong("MaxCharge", aElectricArray[0]);
                tToolNBT.setLong("Voltage", aElectricArray[1]);
                tToolNBT.setLong("Tier", aElectricArray[2]);
                tToolNBT.setLong("SpecialData", aElectricArray[3]);
            }

            tMainNBT.setTag("GT.ToolStats", tToolNBT);
            rStack.setTagCompound(tMainNBT);
        }
        isItemStackUsable(rStack);
        return rStack;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        IToolStats toolStats = getToolStats(stack);
        return toolStats != null && toolStats.isMinableBlock(state);
    }

    /**
     * Called by the Block Harvesting Event within the GT_Proxy
     */
    public void onHarvestBlockEvent(ArrayList<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, IBlockState aBlock, BlockPos pos, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        IToolStats tStats = getToolStats(aStack);
        if (isItemStackUsable(aStack) && getStrVsBlock(aStack, aBlock) > 0.0F)
            doDamage(aStack, tStats.convertBlockDrops(aDrops, aStack, aPlayer, aBlock, pos, aFortune, aSilkTouch, aEvent) * tStats.getToolDamagePerDropConversion());
    }

    @Override
    public boolean onBlockStartBreak(ItemStack aStack, BlockPos blockPos, EntityPlayer aPlayer) {
        if(aPlayer.worldObj.isRemote){
            return false;
        }
        IToolStats tStats = getToolStats(aStack);
        IBlockState aBlock = aPlayer.worldObj.getBlockState(blockPos);
        if (tStats.isChainsaw()&&(aBlock instanceof IShearable))
        {
            IShearable target = (IShearable)aBlock;
            if ((target.isShearable(aStack, aPlayer.worldObj, blockPos)))
            {
                List<ItemStack> drops = target.onSheared(aStack, aPlayer.worldObj, blockPos, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), aStack));
                for (ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(aPlayer.worldObj,
                            blockPos.getX() + d,
                            blockPos.getY() + d1,
                            blockPos.getZ() + d2,
                            stack);
                    entityitem.setPickupDelay(10);
                    aPlayer.worldObj.spawnEntityInWorld(entityitem);
                }
                //aPlayer.addStat(net.minecraft.stats.StatList.mine[Block.getIdFromBlock(aBlock.getBlock())], 1);
                onBlockDestroyed(aStack, aPlayer.worldObj, aBlock, blockPos, aPlayer);
            }
            return false;
        }
        return super.onBlockStartBreak(aStack, blockPos, aPlayer);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        IToolStats tStats = getToolStats(aStack);
        if (tStats == null || !isItemStackUsable(aStack)) return true;
        GT_Utility.doSoundAtClient(tStats.getEntityHitSound(), 1, 1.0F);
        if (super.onLeftClickEntity(aStack, aPlayer, aEntity)) return true;
        if (aEntity.canBeAttackedWithItem() && !aEntity.hitByEntity(aPlayer)) {
            float tMagicDamage = tStats.getMagicDamageAgainstEntity(aEntity instanceof EntityLivingBase ? EnchantmentHelper.getModifierForCreature(aStack, ((EntityLivingBase) aEntity).getCreatureAttribute()) : 0.0F, aEntity, aStack, aPlayer);
            float tDamage = tStats.getNormalDamageAgainstEntity((float) aPlayer.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + getToolCombatDamage(aStack), aEntity, aStack, aPlayer);
            if (tDamage + tMagicDamage > 0.0F) {
                boolean tCriticalHit = aPlayer.fallDistance > 0.0F &&
                        !aPlayer.onGround && !aPlayer.isOnLadder() &&
                        !aPlayer.isInWater() &&
                        !aPlayer.isPotionActive(MobEffects.BLINDNESS) &&
                        aPlayer.getRidingEntity() == null && aEntity instanceof EntityLivingBase;
                if (tCriticalHit && tDamage > 0.0F) tDamage *= 1.5F;
                tDamage += tMagicDamage;
                if (aEntity.attackEntityFrom(tStats.getDamageSource(aPlayer, aEntity), tDamage)) {
                    if (aEntity instanceof EntityLivingBase)
                        aEntity.setFire(EnchantmentHelper.getFireAspectModifier(aPlayer) * 4);
                    int tKnockcack = (aPlayer.isSprinting() ? 1 : 0) + (aEntity instanceof EntityLivingBase ? EnchantmentHelper.getKnockbackModifier((EntityLivingBase) aEntity) : 0);
                    if (tKnockcack > 0) {
                        aEntity.addVelocity(-MathHelper.sin(aPlayer.rotationYaw * (float) Math.PI / 180.0F) * tKnockcack * 0.5F, 0.1D, MathHelper.cos(aPlayer.rotationYaw * (float) Math.PI / 180.0F) * tKnockcack * 0.5F);
                        aPlayer.motionX *= 0.6D;
                        aPlayer.motionZ *= 0.6D;
                        aPlayer.setSprinting(false);
                    }
                    if (tCriticalHit) aPlayer.onCriticalHit(aEntity);
                    if (tMagicDamage > 0.0F) aPlayer.onEnchantmentCritical(aEntity);
                    if (tDamage >= 18.0F) aPlayer.addStat(AchievementList.OVERKILL);
                    aPlayer.setLastAttacker(aEntity);
                    //if (aEntity instanceof EntityLivingBase)
                    //    EnchantmentHelper.((EntityLivingBase) aEntity, aPlayer);
                    //EnchantmentHelper.(aPlayer, aEntity);
                    if (aEntity instanceof EntityLivingBase)
                        aPlayer.addStat(StatList.DAMAGE_DEALT, Math.round(tDamage * 10.0F));
                    aEntity.hurtResistantTime = Math.max(1, tStats.getHurtResistanceTime(aEntity.hurtResistantTime, aEntity));
                    aPlayer.addExhaustion(0.3F);
                    doDamage(aStack, tStats.getToolDamagePerEntityAttack());
                }
            }
        }
        if (aStack.stackSize <= 0) {
            aPlayer.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
        }
        return true;
    }

    @Override
    public final int getMaxItemUseDuration(ItemStack aStack) {
        return 72000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems(Item var1, CreativeTabs aCreativeTab, List<ItemStack> aList) {
        for (int i = 0; i < 32766; i += 2)
            if (getToolStats(new ItemStack(this, 1, i)) != null) {
                ItemStack tStack = new ItemStack(this, 1, i);
                isItemStackUsable(tStack);
                aList.add(tStack);
            }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void registerIcons(TextureMap aIconRegister) {}

    @Override
    public void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        long tMaxDamage = getToolMaxDamage(aStack);
        Materials tMaterial = getPrimaryMaterial(aStack);
        IToolStats tStats = getToolStats(aStack);
        int tOffset = getElectricStats(aStack) != null ? 2 : 1;
        if (tStats != null) {
            String name = aStack.getUnlocalizedName();
            if (name.equals("gt.metatool.01.170") || name.equals("gt.metatool.01.172") || name.equals("gt.metatool.01.174") || name.equals("gt.metatool.01.176")) {
                aList.add(tOffset, TextFormatting.WHITE + "Durability: " + TextFormatting.GREEN + (tMaxDamage - getToolDamage(aStack)) + " / " + tMaxDamage + TextFormatting.GRAY);
                aList.add(tOffset + 1, TextFormatting.WHITE + tMaterial.mDefaultLocalName + TextFormatting.YELLOW + " lvl " + getHarvestLevel(aStack, "") + TextFormatting.GRAY);
                aList.add(tOffset + 2, TextFormatting.WHITE + "Turbine Efficency: " + TextFormatting.BLUE + (50.0F + (10.0F * getToolCombatDamage(aStack))) + TextFormatting.GRAY);
                aList.add(tOffset + 3, TextFormatting.WHITE + "Optimal Steam flow: " + TextFormatting.LIGHT_PURPLE + Math.max(Float.MIN_NORMAL, tStats.getSpeedMultiplier() * getPrimaryMaterial(aStack).mToolSpeed * 1000) + TextFormatting.GRAY + "L/sec");
                aList.add(tOffset + 3, TextFormatting.WHITE + "Optimal Gas flow(EU burnvalue per tick): " + TextFormatting.LIGHT_PURPLE + Math.max(Float.MIN_NORMAL, tStats.getSpeedMultiplier() * getPrimaryMaterial(aStack).mToolSpeed * 50) + TextFormatting.GRAY + "EU/t");
                aList.add(tOffset + 3, TextFormatting.WHITE + "Optimal Plasma flow(Plasma energyvalue per tick): " + TextFormatting.LIGHT_PURPLE + Math.max(Float.MIN_NORMAL, tStats.getSpeedMultiplier() * getPrimaryMaterial(aStack).mToolSpeed * 2000) + TextFormatting.GRAY + "EU/t");

            } else {
                aList.add(tOffset + 0, TextFormatting.WHITE + "Durability: " + TextFormatting.GREEN + (tMaxDamage - getToolDamage(aStack)) + " / " + tMaxDamage + TextFormatting.GRAY);
                aList.add(tOffset + 1, TextFormatting.WHITE + tMaterial.mDefaultLocalName + TextFormatting.YELLOW + " lvl " + getHarvestLevel(aStack, "") + TextFormatting.GRAY);
                aList.add(tOffset + 2, TextFormatting.WHITE + "Attack Damage: " + TextFormatting.BLUE + getToolCombatDamage(aStack) + TextFormatting.GRAY);
                aList.add(tOffset + 3, TextFormatting.WHITE + "Mining Speed: " + TextFormatting.LIGHT_PURPLE + Math.max(Float.MIN_NORMAL, tStats.getSpeedMultiplier() * getPrimaryMaterial(aStack).mToolSpeed) + TextFormatting.GRAY);
                NBTTagCompound aNBT = aStack.getTagCompound();
                if (aNBT != null) {
                    aNBT = aNBT.getCompoundTag("GT.ToolStats");
                    if (aNBT != null && aNBT.hasKey("Heat")){
                        int tHeat = aNBT.getInteger("Heat");
                        long tWorldTime = aPlayer.getEntityWorld().getWorldTime();
                        if(aNBT.hasKey("HeatTime")){
                            long tHeatTime = aNBT.getLong("HeatTime");
                            if(tWorldTime>(tHeatTime+10)){
                                tHeat = (int) (tHeat - ((tWorldTime-tHeatTime)/10));
                                if(tHeat<300&&tHeat>-10000)tHeat=300;
                            }
                            aNBT.setLong("HeatTime", tWorldTime);
                            if(tHeat>-10000)aNBT.setInteger("Heat", tHeat);
                        }

                        aList.add(tOffset + 3, TextFormatting.RED + "Heat: " + aNBT.getInteger("Heat")+" K" + TextFormatting.GRAY);
                    }
                }
            }
        }
    }

    @Override
    public Long[] getFluidContainerStats(ItemStack aStack) {
        return null;
    }

    @Override
    public Long[] getElectricStats(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null && aNBT.getBoolean("Electric"))
                return new Long[]{aNBT.getLong("MaxCharge"), aNBT.getLong("Voltage"), aNBT.getLong("Tier"), aNBT.getLong("SpecialData")};
        }
        return null;
    }

    public float getToolCombatDamage(ItemStack aStack) {
        IToolStats tStats = getToolStats(aStack);
        if (tStats == null) return 0;
        return tStats.getBaseDamage() + getPrimaryMaterial(aStack).mToolQuality;
    }

    @Override
    public final boolean doDamageToItem(ItemStack aStack, int aVanillaDamage) {
        return doDamage(aStack, aVanillaDamage * 100);
    }

    public final boolean doDamage(ItemStack aStack, long aAmount) {
        if (!isItemStackUsable(aStack)) return false;
        Long[] tElectric = getElectricStats(aStack);
        if (tElectric == null) {
            long tNewDamage = getToolDamage(aStack) + aAmount;
            setToolDamage(aStack, tNewDamage);
            if (tNewDamage >= getToolMaxDamage(aStack)) {
                IToolStats tStats = getToolStats(aStack);
                if (tStats == null || GT_Utility.setStack(aStack, tStats.getBrokenItem(aStack)) == null) {
                    if (tStats != null) GT_Utility.doSoundAtClient(tStats.getBreakingSound(), 1, 1.0F);
                    if (aStack.stackSize > 0) aStack.stackSize--;

                }
            }
            return true;
        }
        if (use(aStack, (int) aAmount, null)) {
            if (java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 25) == 0) {
                long tNewDamage = getToolDamage(aStack) + aAmount;
                setToolDamage(aStack, tNewDamage);
                if (tNewDamage >= getToolMaxDamage(aStack)) {
                    IToolStats tStats = getToolStats(aStack);
                    if (tStats == null || GT_Utility.setStack(aStack, tStats.getBrokenItem(aStack)) == null) {
                        if (tStats != null) GT_Utility.doSoundAtClient(tStats.getBreakingSound(), 1, 1.0F);
                        if (aStack.stackSize > 0) aStack.stackSize--;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public float getStrVsBlock(ItemStack aStack, IBlockState state) {
        //defSpeed is default item mining speed divided by 2
        float defSpeed = 0.5f;

        if (isItemStackUsable(aStack)) {
            IToolStats toolStats = getToolStats(aStack);
            if(toolStats != null && toolStats.isMinableBlock(state)) {
                if(getHarvestLevel(aStack, null) >= state.getBlock().getHarvestLevel(state)) {
                    float toolSpeed = toolStats.getSpeedMultiplier() * getPrimaryMaterial(aStack).mToolSpeed;
                    return Math.max(defSpeed, toolSpeed);
                }
                return defSpeed;
            }
            return defSpeed;
        }
        return defSpeed;
    }

    @Override
    public final int getHarvestLevel(ItemStack aStack, String aToolClass) {
        IToolStats tStats = getToolStats(aStack);
        return tStats == null ? -1 : tStats.getBaseQuality() + getPrimaryMaterial(aStack).mToolQuality;
    }



    @Override
    public boolean onBlockDestroyed(ItemStack aStack, World aWorld, IBlockState state, BlockPos blockPos, EntityLivingBase aPlayer) {
        if (!isItemStackUsable(aStack)) return false;
        IToolStats tStats = getToolStats(aStack);
        if (tStats == null) return false;
        GT_Utility.doSoundAtClient(tStats.getMiningSound(), 1, 1.0F);
        doDamage(aStack, (int) Math.max(1, state.getBlockHardness(aWorld, blockPos) * tStats.getToolDamagePerBlockBreak()));
        return true;
    }

    @Override
    public final ItemStack getContainerItem(ItemStack aStack) {
        return getContainerItem(aStack, true);
    }

    @Override
    public final boolean hasContainerItem(ItemStack aStack) {
        return getContainerItem(aStack, false) != null;
    }

    private ItemStack getContainerItem(ItemStack aStack, boolean playSound) {
        if (!isItemStackUsable(aStack)) return null;
        aStack = GT_Utility.copyAmount(1, aStack);
        IToolStats tStats = getToolStats(aStack);
        if (tStats == null) return null;
        doDamage(aStack, tStats.getToolDamagePerContainerCraft());
        aStack = aStack.stackSize > 0 ? aStack : null;
        if (playSound && GT_Mod.gregtechproxy.mTicksUntilNextCraftSound <= 0) {
            GT_Mod.gregtechproxy.mTicksUntilNextCraftSound = 10;
            String sound = (aStack == null) ? tStats.getBreakingSound() : tStats.getCraftingSound();
            GT_Utility.doSoundAtClient(sound, 1, 1.0F);
        }
        return aStack;
    }

    public IToolStats getToolStats(ItemStack aStack) {
        // if(isItemStackUsable(aStack)) { // why?
        return getToolStatsInternal(aStack);
    }

    private IToolStats getToolStatsInternal(ItemStack aStack) {
        return aStack == null ? null : mToolStats.get((short) aStack.getItemDamage());
    }

    /*@Override
    public float getSaplingModifier(ItemStack aStack, World aWorld, EntityPlayer aPlayer, int aX, int aY, int aZ) {
        IToolStats tStats = getToolStats(aStack);
        return tStats != null && tStats.isGrafter() ? Math.min(100.0F, (1 + getHarvestLevel(aStack, "")) * 20.0F) : 0.0F;
    }

    @Override
    public boolean canWhack(EntityPlayer aPlayer, ItemStack aStack, int aX, int aY, int aZ) {
        if (!isItemStackUsable(aStack)) return false;
        IToolStats tStats = getToolStats(aStack);
        return tStats != null && tStats.isCrowbar();
    }

    @Override
    public void onWhack(EntityPlayer aPlayer, ItemStack aStack, int aX, int aY, int aZ) {
        IToolStats tStats = getToolStats(aStack);
        if (tStats != null) doDamage(aStack, tStats.getToolDamagePerEntityAttack());
    }

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		if(player==null)return false;
		if(player.getCurrentEquippedItem()==null)return false;
        if (!isItemStackUsable(player.getCurrentEquippedItem())) return false;
        IToolStats tStats = getToolStats(player.getCurrentEquippedItem());
        return tStats != null && tStats.isWrench();
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		if(player==null)return;
		if(player.getCurrentEquippedItem()==null)return;
        IToolStats tStats = getToolStats(player.getCurrentEquippedItem());
        if (tStats != null) doDamage(player.getCurrentEquippedItem(), tStats.getToolDamagePerEntityAttack());
	}

	@Override
	public boolean canUse(ItemStack stack, EntityPlayer player, int x, int y, int z){
		 return canWrench(player, x, y, z);
	}

	@Override
	public void used(ItemStack stack, EntityPlayer player, int x, int y, int z){
		wrenchUsed(player, x, y, z);
	}

	@Override
	public boolean shouldHideFacades(ItemStack stack, EntityPlayer player) {
		if(player==null)return false;
		if(player.getCurrentEquippedItem()==null)return false;
        if (!isItemStackUsable(player.getCurrentEquippedItem())) return false;
		IToolStats tStats = getToolStats(player.getCurrentEquippedItem());
		return tStats.isWrench();
	}


    @Override
    public boolean canLink(EntityPlayer aPlayer, ItemStack aStack, EntityMinecart cart) {
        if (!isItemStackUsable(aStack)) return false;
        IToolStats tStats = getToolStats(aStack);
        return tStats != null && tStats.isCrowbar();
    }

    @Override
    public void onLink(EntityPlayer aPlayer, ItemStack aStack, EntityMinecart cart) {
        IToolStats tStats = getToolStats(aStack);
        if (tStats != null) doDamage(aStack, tStats.getToolDamagePerEntityAttack());
    }

    @Override
    public boolean canBoost(EntityPlayer aPlayer, ItemStack aStack, EntityMinecart cart) {
        if (!isItemStackUsable(aStack)) return false;
        IToolStats tStats = getToolStats(aStack);
        return tStats != null && tStats.isCrowbar();
    }

    @Override
    public void onBoost(EntityPlayer aPlayer, ItemStack aStack, EntityMinecart cart) {
        IToolStats tStats = getToolStats(aStack);
        if (tStats != null) doDamage(aStack, tStats.getToolDamagePerEntityAttack());
    }*/

    @Override
    public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        IToolStats tStats = getToolStats(aStack);
        if (tStats != null && aPlayer != null) tStats.onToolCrafted(aStack, aPlayer);
        super.onCreated(aStack, aWorld, aPlayer);
    }


    @Override
    public final int getItemStackLimit(ItemStack aStack) {
        return 1;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean isItemStackUsable(ItemStack aStack) {
        IToolStats tStats = getToolStatsInternal(aStack);
        if (aStack.getItemDamage() % 2 != 0 || tStats == null) {
            NBTTagCompound aNBT = aStack.getTagCompound();
            if (aNBT != null) aNBT.removeTag("ench");
            return false;
        }
        Materials aMaterial = getPrimaryMaterial(aStack);
        HashMap<Enchantment, Integer> tMap = new HashMap<>(), tResult = new HashMap<>();
        if (aMaterial.mEnchantmentTools != null) {
            tMap.put(aMaterial.mEnchantmentTools, (int) aMaterial.mEnchantmentToolsLevel);
            if (aMaterial.mEnchantmentTools == Enchantment.getEnchantmentByLocation("fortune"))
                tMap.put(Enchantment.getEnchantmentByLocation("looting"), (int) aMaterial.mEnchantmentToolsLevel);
            if (aMaterial.mEnchantmentTools == Enchantment.getEnchantmentByLocation("knockback"))
                tMap.put(Enchantment.getEnchantmentByLocation("power"), (int) aMaterial.mEnchantmentToolsLevel);
            if (aMaterial.mEnchantmentTools == Enchantment.getEnchantmentByLocation("fire_aspect"))
                tMap.put(Enchantment.getEnchantmentByLocation("flame"), (int) aMaterial.mEnchantmentToolsLevel);
        }
        Enchantment[] tEnchants = tStats.getEnchantments(aStack);
        int[] tLevels = tStats.getEnchantmentLevels(aStack);
        for (int i = 0; i < tEnchants.length; i++)
            if (tLevels[i] > 0) {
                Integer tLevel = tMap.get(tEnchants[i]);
                tMap.put(tEnchants[i], tLevel == null ? tLevels[i] : tLevel == tLevels[i] ? tLevel + 1 : Math.max(tLevel, tLevels[i]));
            }
        for (Entry<Enchantment, Integer> tEntry : tMap.entrySet()) {
            int id = Enchantment.getEnchantmentID(tEntry.getKey());
            if (id == 33 || (id == 20 && id > 2) || id == Enchantment.getEnchantmentID(Enchantment_Radioactivity.INSTANCE))
                tResult.put(tEntry.getKey(), tEntry.getValue());
            else
                switch (tEntry.getKey().type) {
                    case WEAPON:
                        if (tStats.isWeapon()) tResult.put(tEntry.getKey(), tEntry.getValue());
                        break;
                    case ALL:
                        tResult.put(tEntry.getKey(), tEntry.getValue());
                        break;
                    case ARMOR:
                    case ARMOR_FEET:
                    case ARMOR_HEAD:
                    case ARMOR_LEGS:
                    case ARMOR_CHEST:
                        break;
                    case BOW:
                        if (tStats.isRangedWeapon()) tResult.put(tEntry.getKey(), tEntry.getValue());
                        break;
                    case BREAKABLE:
                        break;
                    case FISHING_ROD:
                        break;
                    case DIGGER:
                        if (tStats.isMiningTool()) tResult.put(tEntry.getKey(), tEntry.getValue());
                        break;
                }
        }
        EnchantmentHelper.setEnchantments(tResult, aStack);
        return true;
    }

    @Override
    public short getChargedMetaData(ItemStack aStack) {
        return (short) (aStack.getItemDamage() - (aStack.getItemDamage() % 2));
    }

    @Override
    public short getEmptyMetaData(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) aNBT.removeTag("ench");
        return (short) (aStack.getItemDamage() + 1 - (aStack.getItemDamage() % 2));
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack aStack, ItemStack aBook) {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack aStack, ItemStack aMaterial) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int tintIndex) {
        IToolStats toolStats = getToolStats(stack);
        if(tintIndex > 1) {
            short[] colorsHead = toolStats.getRGBa(true, stack);
            if(colorsHead != null)
                return ITexture.color(colorsHead, true);
        }
        short[] colors = toolStats.getRGBa(false, stack);
        if(colors != null)
            return ITexture.color(colors, true);
        return 0xFFFFFF;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return GT_LanguageManager.getTranslation(getUnlocalizedName(stack) + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(ItemStack stack, int pass) {
        IToolStats toolStats = getToolStats(stack);
        IIconContainer icon = null;
        switch (pass) {
            case 0:
            case 1:
                // Handle
                icon = toolStats.getIcon(false, stack);
                break;
            case 2:
            case 3:
                // Head
                icon = toolStats.getIcon(true, stack);
                break;
            case 4:
            case 5:
                // Durability Bar
                long tDamage = getToolDamage(stack);
                long tMaxDamage = getToolMaxDamage(stack);
                if (tDamage <= 0L) {
                    icon = gregtech.api.enums.Textures.ItemIcons.DURABILITY_BAR[8];
                } else if (tDamage >= tMaxDamage) {
                    icon = gregtech.api.enums.Textures.ItemIcons.DURABILITY_BAR[0];
                } else {
                    icon = gregtech.api.enums.Textures.ItemIcons.DURABILITY_BAR[((int) java.lang.Math.max(0L, java.lang.Math.min(7L, (tMaxDamage - tDamage) * 8L / tMaxDamage)))];
                }
                break;
            case 6:
            case 7:
                // Energy Bar
                Long[] tStats = getElectricStats(stack);
                if ((tStats != null) && (tStats[3].longValue() < 0L)) {
                long tCharge = getRealCharge(stack);
                    if (tCharge <= 0L) {
                        icon = gregtech.api.enums.Textures.ItemIcons.ENERGY_BAR[0];
                    } else if (tCharge >= tStats[0].longValue()) {
                        icon = gregtech.api.enums.Textures.ItemIcons.ENERGY_BAR[8];
                    } else {
                        icon = gregtech.api.enums.Textures.ItemIcons.ENERGY_BAR[(7 - (int) java.lang.Math.max(0L, java.lang.Math.min(6L, (tStats[0].longValue() - tCharge) * 7L / tStats[0].longValue())))];
                    }
                }
                break;
            default:
                break;
        }
        if (icon != null) {
            if ((pass & 1) == 0) {
                return icon.getIcon();
            } else {
                return icon.getOverlayIcon();
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(ItemStack stack) {
        return 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isHandheld(ItemStack stack) {
        return true;
    }

    @Override
    public float getSaplingModifier(ItemStack stack, World world, EntityPlayer player, BlockPos pos) {
        IToolStats toolStats = getToolStats(stack);
        if(toolStats.isGrafter()) {
            return 0.78F;
        }
        return 0.0F;
    }

}