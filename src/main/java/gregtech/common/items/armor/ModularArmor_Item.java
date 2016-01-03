package gregtech.common.items.armor;

import gregtech.api.damagesources.GT_DamageSources;
import gregtech.api.enums.GT_Values;
import ic2.core.IC2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Optional;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingFallEvent;

@Optional.InterfaceList(value = { @Optional.Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft", striprefs = true),
		@Optional.Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft", striprefs = true) })
public class ModularArmor_Item extends ItemArmor implements ISpecialArmor, IGoggles, IRevealer {

	public String mName;
	public int timer = 160;
	public Item repairMaterial;
	public int openGuiNr;
	public ArmorData data;
	public int jumpticks;

	// public int maxEU;

	public ModularArmor_Item(int aArmorIndex, int aType, String name, int gui) {
		super(ArmorMaterial.DIAMOND, aArmorIndex, aType);
		MinecraftForge.EVENT_BUS.register(this);
		setUnlocalizedName("gregtech:" + name);
		GameRegistry.registerItem(this, name);
		mName = name;
		int mMaxDamage = (gui + 1) * 1024;
		mMaxDamage *= getBaseAbsorptionRatio() * 2.5;
		setMaxDamage(mMaxDamage);
		repairMaterial = Items.leather;
		openGuiNr = gui;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
		if (data == null) {
			data = fillArmorData(aPlayer, aStack);
		}
		if (!aWorld.isRemote) {
			aPlayer.openGui(GT_Values.GT, openGuiNr+1000, aWorld, (int) aPlayer.posX, (int) aPlayer.posY, (int) aPlayer.posZ);
		}
		return aStack;
	}

	@Override
	public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (data == null) {
			data = fillArmorData((EntityPlayer) player, armor);
		}
		if (player != null && armor != null && source != null) {
			double tmp = 0.0d;
			if (source.isMagicDamage()) {
				tmp = data.magicDef;
			} else if (source == GT_DamageSources.getRadioactiveDamage()) {
				tmp = data.radiationDef;
			} else if (source == GT_DamageSources.getElectricDamage()) {
				tmp = data.electricDef;
			} else if (source == DamageSource.wither) {
				tmp = data.witherDef;
			} else if (source.isFireDamage() || source == GT_DamageSources.getHeatDamage()) {
				tmp = data.fireDef;
			} else if (source.isExplosion()) {
				tmp = data.explosionDef;
			} else if (source.isProjectile()) {
				tmp = data.projectileDef;
			} else {
				tmp = data.physicalDef;
			}
			if (data.thorns > 0.1d && source != DamageSource.fall && source.getSourceOfDamage() != null) {
				source.getSourceOfDamage().attackEntityFrom(new DamageSource("Thorns"), data.thorns);
			}

			// fallDamage
			if (source == DamageSource.fall) {
				int fallDef = 0;
				ItemStack stack = player.getEquipmentInSlot(1);
				if (stack != null && stack.getItem() instanceof ModularArmor_Item) {
					fallDef = (int) data.boots.fallDef;
				}
				tmp = 1.0d - (fallDef > damage ? 0.0d : (1.0d - tmp) * 0.5d);
			}
			if (tmp == 0.0d) {
				tmp = data.physicalDef;
			}
			if (openGuiNr == 2) {
				tmp = 1.0f - ((1.0f - tmp) / 2.0f);
			}
			return new ISpecialArmor.ArmorProperties(0, data.getBaseAbsorptionRatio() * tmp, 1000);

		} else {
			return new ISpecialArmor.ArmorProperties(0, 0, 0);
		}
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (data == null) {
			data = fillArmorData(player, armor);
		}
		int tmp = (int) -Math.floor(-(data.getBaseAbsorptionRatio() * 20 * data.physicalDef));

		return tmp;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
		if (data == null) {
			data = fillArmorData(player, itemStack);
		}
		if (data.info != null)
			info.addAll(data.info);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if (data == null) {
			data = fillArmorData((EntityPlayer) entity, stack);
		}
		stack.damageItem(damage, entity);
		ContainerModularArmor tmp = new ContainerBasicArmor((EntityPlayer) entity, new InventoryArmor(ModularArmor_Item.class, stack));
		if (stack.getItemDamage() > stack.getMaxDamage() / 2 && new Random().nextInt(100) < 5) {
			tmp.getSlot(new Random().nextInt(tmp.getSlotCount())).decrStackSize(1);
			tmp.mInvArmor.onGuiSaved((EntityPlayer) entity);
			
				/*public void eject(ItemStack drop)
				{
				if ((!IC2.platform.isSimulating()) || (drop == null)) {
				return;
				}
				float f = 0.7F;
				double d = this.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				double d1 = this.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				double d2 = this.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + d, this.yCoord + d1, this.zCoord + d2, drop);
				entityitem.delayBeforeCanPickup = 10;
				this.worldObj.spawnEntityInWorld(entityitem);
				}*/
			
		}
	}

	@SubscribeEvent
	public void onEntityLivingFallEvent(LivingFallEvent event) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer() && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ItemStack armor = player.inventory.armorInventory[0];
			if (data != null && event != null && data.type == 3 && data.charge >= data.pistonEUusage && event.distance - 3 <= data.fallDef) {
				event.setCanceled(true);
			} else if (data != null && event != null && data.type == 3 && data.charge >= data.pistonEUusage) {
				event.distance -= data.fallDef;
			}
		}
	}

	@Override
	public void onArmorTick(World aWorld, EntityPlayer aPlayer, ItemStack aStack) {
		if (data == null) {
			data = fillArmorData(aPlayer, aStack);
		}
		if (data.tooltipUpdate > 40) {
			data.armorPartsEquipped(aPlayer);
			data.tooltipUpdate = 0;
			data.updateTooltip();
		} else {
			data.tooltipUpdate++;
		}
		if (aPlayer.onGround) {
			jumpticks = 4;
		} else {
			jumpticks--;
		}

		// Breathing
		if (data.type == 0 && aPlayer.getAir() < 100) {
			int air = 0;
			if (data.fluid.getUnlocalizedName().equals("fluid.oxygen") && data.fluid.amount >= 150) {
				aPlayer.setAir(aPlayer.getAir() + 150);
				air = 150;
			} else if (data.fluid.getUnlocalizedName().equals("fluid.air") && data.fluid.amount >= 500) {
				aPlayer.setAir(aPlayer.getAir() + 100);
				air = 500;
			}
			if (air > 0) {
				data.fluid.amount -= air;
				ItemStack stack = aPlayer.getEquipmentInSlot(4);
				if (stack != null && stack.getItem() instanceof ModularArmor_Item) {
					ModularArmor_Item tmp = (ModularArmor_Item) stack.getItem();
					ContainerModularArmor tmp2 = new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, stack));
					ArmorCalculation.useFluid(tmp2.mInvArmor.parts, air);
				}
			}
		}
		// Fill Air Tank
		if (data.tooltipUpdate == 40 && data.processingPower > data.processingPowerUsed && data.type == 0 && data.fluid != null
				&& data.fluid.getUnlocalizedName().equals("oxygen") && data.fluid.amount < data.tankCap && data.charge > data.electrolyzerEUusage) {
			data.charge -= data.electrolyzerEUusage;
			ItemStack stack = aPlayer.getEquipmentInSlot(4);
			if (stack != null && stack.getItem() instanceof ModularArmor_Item) {
				ModularArmor_Item tmp = (ModularArmor_Item) stack.getItem();
				ContainerModularArmor tmp2 = new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, stack));
				ArmorCalculation.useFluid(tmp2.mInvArmor.parts, -data.electrolyzerProd);
			}
		}

		if (data.isTopItem) {
			if(IC2.keyboard.isModeSwitchKeyDown(aPlayer)&&!aWorld.isRemote){
				int typeMod=0;
				switch(data.type){
				case 0:
					typeMod=400;
					break;
				case 1:
					typeMod=300;
					break;
				case 2:
					typeMod=200;
					break;
				case 3:
					typeMod=100;
					break;
				}
					aPlayer.openGui(GT_Values.GT, openGuiNr+(typeMod), aWorld, (int) aPlayer.posX, (int) aPlayer.posY, (int) aPlayer.posZ);
			}
			if(data.helmet!=null&&data.helmet.openGui){data.helmet.openGui=false;aPlayer.openGui(GT_Values.GT, openGuiNr+400, aWorld, (int) aPlayer.posX, (int) aPlayer.posY, (int) aPlayer.posZ);}
			if(data.chestplate!=null&&data.chestplate.openGui){data.chestplate.openGui=false;aPlayer.openGui(GT_Values.GT, openGuiNr+300, aWorld, (int) aPlayer.posX, (int) aPlayer.posY, (int) aPlayer.posZ);}
			if(data.leggings!=null&&data.leggings.openGui){data.leggings.openGui=false;aPlayer.openGui(GT_Values.GT, openGuiNr+200, aWorld, (int) aPlayer.posX, (int) aPlayer.posY, (int) aPlayer.posZ);}
			if(data.boots!=null&&data.boots.openGui){data.boots.openGui=false;aPlayer.openGui(GT_Values.GT, openGuiNr+100, aWorld, (int) aPlayer.posX, (int) aPlayer.posY, (int) aPlayer.posZ);}
			// Night Vision
			if (timer >= 200) {
				timer = 0;
				if (data.processingPower > data.processingPowerUsed && data.helmet != null && data.helmet.nightVision && data.charge > 3) {
					aPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 500, -3));
					data.charge -= 4;
				} else {
					PotionEffect nv = aPlayer.getActivePotionEffect(Potion.nightVision);
					if (nv != null && nv.getAmplifier() == -3) {
						if (aPlayer.worldObj.isRemote) {
							aPlayer.removePotionEffectClient(Potion.nightVision.id);
						} else {
							aPlayer.removePotionEffect(Potion.nightVision.id);
						}
					}
				}
			} else {
				timer++;
			}

			// Item Magnet
			if (data.magnet > 1) {
				double x = aPlayer.posX;
				double y = aPlayer.posY - (aPlayer.worldObj.isRemote ? 1.62 : 0) + 0.75;
				double z = aPlayer.posZ;
				List<EntityItem> items = aPlayer.worldObj.getEntitiesWithinAABB(EntityItem.class,
						AxisAlignedBB.getBoundingBox(x - data.magnet, y - data.magnet, z - data.magnet, x + data.magnet, y + data.magnet, z + data.magnet));
				for (EntityItem item : items) {
					ItemStack stack = item.getEntityItem();
					if (!item.isDead && stack != null) {
						setEntityMotionFromVector(item, new Vector3(x, y, z), 0.45F);
					}
				}
			}
			// Weight limit calcuation
			double motorSpeed = 0;
			if (data.leggings != null) {
				motorSpeed = data.leggings.motorPower;
			}
			if (data.maxWeight > 4000) {
				if (data.leggings != null && data.leggings.charge > data.leggings.motorEUusage) {
					motorSpeed -= data.maxWeight - 4000;
					data.leggings.charge -= (data.leggings.motorEUusage / 100);
				} else {
					aPlayer.motionX *= (4000.0d / data.maxWeight);
					aPlayer.motionZ *= (4000.0d / data.maxWeight);
				}
			}
			if (data.leggings != null && data.leggings.charge > data.leggings.motorEUusage && data.processingPower > data.processingPowerUsed && motorSpeed > 0
					&& aPlayer.isSprinting() && jumpticks > 0
					&& (aPlayer.onGround && Math.abs(aPlayer.motionX) + Math.abs(aPlayer.motionZ) > 0.10000000149011612D)) {
				data.leggings.charge -= data.leggings.motorEUusage;
				motorSpeed = Math.sqrt(motorSpeed) / 3;

				float var7 = (float) (0.02f * motorSpeed);
				if (aPlayer.isInWater()) {
					var7 = 0.1F;
					if (aPlayer.motionY > 0) {
						aPlayer.motionY += 0.10000000149011612D;
					}
				}

				if (var7 > 0.0F) {
					aPlayer.moveFlying(0.0F, 1.0F, var7);
				}
			}

			// jump+step up assist
			if (data.processingPower > data.processingPowerUsed && data.leggings != null) {
				double stepup = data.leggings.pistonJumpboost;
				if (stepup > 1) {
					aPlayer.stepHeight = 1.0f;
				}
				if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump)) {
					if (stepup > 0 && jumpticks > 0) {
						if (data.maxWeight > 2000) {
							stepup *= 2000.0D / data.maxWeight;
						}
						aPlayer.motionY += 0.04 * stepup;
					}
					aPlayer.jumpMovementFactor = aPlayer.getAIMoveSpeed() * .2f;
				}

			}

			// immune effect removal
			List<PotionEffect> effects = new LinkedList(aPlayer.getActivePotionEffects());
			for (PotionEffect effect : effects) {
				int id = effect.getPotionID();
				if (id == 24 && data.fullRadiationDef) {
					aPlayer.removePotionEffect(id);
				}
			}
		}
	}

	public void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.copy().subtract(entityVector);
		if (finalVector.mag() > 1)
			finalVector.normalize();
		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister aIconRegister) {
		this.itemIcon = aIconRegister.registerIcon(GT_Values.MOD_ID+":" + mName);
	}

	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		String armor=GT_Values.RES_PATH_ITEM+"armorhelmet.png";
		String tier="";
		try{
		if (data == null) {
			data = fillArmorData((EntityPlayer) entity, stack);
		}
		if(this.data.armorTier==0){
			tier="basic";
		}else if(this.data.armorTier==1){
			tier="e1";
		}else if(this.data.armorTier==2){
			tier="e2";
		}
		if(this.data.type==0||this.data.type==1||this.data.type==3){
			armor = GT_Values.RES_PATH_MODEL+"armor/"+tier+"_helmet_chest.png";
		}else{
			armor = GT_Values.RES_PATH_MODEL+"armor/"+tier+"_leggings_boots.png";
		}}catch(Exception e){System.err.println(e);}
		return armor;
	}

	@Override
	public boolean showNodes(ItemStack aStack, EntityLivingBase aPlayer) {
		if (data == null) {
			data = fillArmorData((EntityPlayer) aPlayer, aStack);
		}
		return data.thaumicGoggles && data.armorTier > 0 && data.charge > 0;
	}

	@Override
	public boolean showIngamePopups(ItemStack aStack, EntityLivingBase aPlayer) {
		if (data == null) {
			data = fillArmorData((EntityPlayer) aPlayer, aStack);
		}
		return data.thaumicGoggles && data.armorTier > 0 && data.charge > 0;
	}

	public ArmorData fillArmorData(EntityPlayer player, ItemStack stack) {
		return new ArmorData(player, stack, this.armorType, openGuiNr);
	}

	public double getBaseAbsorptionRatio() {
		switch (this.armorType) {
		case 0:
			return 0.15;
		case 1:
			return 0.40;
		case 2:
			return 0.30;
		case 3:
			return 0.15;
		default:
			return 0.00;
		}
	}
}
