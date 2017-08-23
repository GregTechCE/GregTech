package gregtech.common;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.ToolDictNames;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.MetaTool;
import ic2.core.block.wiring.CableType;
import ic2.core.item.ItemIC2FluidContainer;
import ic2.core.ref.ItemName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class EventHandlers {

//	private final Collection<String> mIgnoredItems = new HashSet<String>(Arrays.asList("itemGhastTear", "itemFlint", "itemClay", "itemBucketSaltWater",
//			"itemBucketFreshWater", "itemBucketWater", "itemRock", "itemReed", "itemArrow", "itemSaw", "itemKnife", "itemHammer", "itemChisel", "itemRubber",
//			"itemEssence", "itemIlluminatedPanel", "itemSkull", "itemRawRubber", "itemBacon", "itemJetpackAccelerator", "itemLazurite", "itemIridium",
//			"itemTear", "itemClaw", "itemFertilizer", "itemTar", "itemSlimeball", "itemCoke", "itemBeeswax", "itemBeeQueen", "itemForcicium", "itemForcillium",
//			"itemRoyalJelly", "itemHoneydew", "itemHoney", "itemPollen", "itemReedTypha", "itemSulfuricAcid", "itemPotash", "itemCompressedCarbon",
//			"itemBitumen", "itemBioFuel", "itemCokeSugar", "itemCokeCactus", "itemCharcoalSugar", "itemCharcoalCactus", "itemSludge", "itemEnrichedAlloy",
//			"itemQuicksilver", "itemMercury", "itemOsmium", "itemUltimateCircuit", "itemEnergizedStar", "itemAntimatterMolecule", "itemAntimatterGlob",
//			"itemCoal", "itemBoat", "itemHerbalMedicineCake", "itemCakeSponge", "itemFishandPumpkinCakeSponge", "itemSoulCleaver", "itemInstantCake",
//			"itemWhippingCream", "itemGlisteningWhippingCream", "itemCleaver", "itemHerbalMedicineWhippingCream", "itemStrangeWhippingCream",
//			"itemBlazeCleaver", "itemBakedCakeSponge", "itemMagmaCake", "itemGlisteningCake", "itemOgreCleaver", "itemFishandPumpkinCake",
//			"itemMagmaWhippingCream", "itemMultimeter", "itemSuperconductor"));
//	private final Collection<String> mIgnoredNames = new HashSet<String>(Arrays.asList("grubBee", "chainLink", "candyCane", "bRedString", "bVial",
//			"bFlask", "anorthositeSmooth", "migmatiteSmooth", "slateSmooth", "travertineSmooth", "limestoneSmooth", "orthogneissSmooth", "marbleSmooth",
//			"honeyDrop", "lumpClay", "honeyEqualssugar", "flourEqualswheat", "bluestoneInsulated", "blockWaterstone", "blockSand", "blockTorch",
//			"blockPumpkin", "blockClothRock", "blockStainedHardenedClay", "blockQuartzPillar", "blockQuartzChiselled", "blockSpawner", "blockCloth", "mobHead",
//			"mobEgg", "enderFlower", "enderChest", "clayHardened", "dayGemMaterial", "nightGemMaterial", "snowLayer", "bPlaceholder", "hardenedClay",
//			"eternalLifeEssence", "sandstone", "wheatRice", "transdimBlock", "bambooBasket", "lexicaBotania", "livingwoodTwig", "redstoneCrystal",
//			"pestleAndMortar", "glowstone", "whiteStone", "stoneSlab", "transdimBlock", "clayBowl", "clayPlate", "ceramicBowl", "ceramicPlate", "ovenRack",
//			"clayCup", "ceramicCup", "batteryBox", "transmutationStone", "torchRedstoneActive", "coal", "charcoal", "cloth", "cobblestoneSlab",
//			"stoneBrickSlab", "cobblestoneWall", "stoneBrickWall", "cobblestoneStair", "stoneBrickStair", "blockCloud", "blockDirt", "blockTyrian",
//			"blockCarpet", "blockFft", "blockLavastone", "blockHolystone", "blockConcrete", "sunnariumPart", "brSmallMachineCyaniteProcessor", "meteoriteCoal",
//			"blockCobble", "pressOreProcessor", "crusherOreProcessor", "grinderOreProcessor", "blockRubber", "blockHoney", "blockHoneydew", "blockPeat",
//			"blockRadioactive", "blockSlime", "blockCocoa", "blockSugarCane", "blockLeather", "blockClayBrick", "solarPanelHV", "cableRedNet", "stoneBowl",
//			"crafterWood", "taintedSoil", "brickXyEngineering", "breederUranium", "wireMill", "chunkLazurite", "aluminumNatural", "aluminiumNatural",
//			"naturalAluminum", "naturalAluminium", "antimatterMilligram", "antimatterGram", "strangeMatter", "coalGenerator", "electricFurnace",
//			"unfinishedTank", "valvePart", "aquaRegia", "leatherSeal", "leatherSlimeSeal", "hambone", "slimeball", "clay", "enrichedUranium", "camoPaste",
//			"antiBlock", "burntQuartz", "salmonRaw", "blockHopper", "blockEnderObsidian", "blockIcestone", "blockMagicWood", "blockEnderCore", "blockHeeEndium",
//			"oreHeeEndPowder", "oreHeeStardust", "oreHeeIgneousRock", "oreHeeInstabilityOrb", "crystalPureFluix", "shardNether", "gemFluorite",
//			"stickObsidian", "caveCrystal", "shardCrystal", "DYECrystal","shardFire","shardWater","shardAir","shardEarth","ingotRefinedIron","blockMarble","ingotUnstable",
//			"blockCactus", "blockPrismarineBrick", "blockPrismarineDark", "stoneGranitePolished", "stoneDioritePolished", "stoneAndesitePolished", "doorWood", "doorIron"));
//	private final Collection<String> mInvalidNames = new HashSet<String>(Arrays.asList("diamondShard", "redstoneRoot", "obsidianStick", "bloodstoneOre",
//			"universalCable", "bronzeTube", "ironTube", "netherTube", "obbyTube", "infiniteBattery", "eliteBattery", "advancedBattery", "10kEUStore",
//			"blueDye", "MonazitOre", "quartzCrystal", "whiteLuminiteCrystal", "darkStoneIngot", "invisiumIngot", "demoniteOrb", "enderGem", "starconiumGem",
//			"osmoniumIngot", "tapaziteGem", "zectiumIngot", "foolsRubyGem", "rubyGem", "meteoriteGem", "adamiteShard", "sapphireGem", "copperIngot",
//			"ironStick", "goldStick", "diamondStick", "reinforcedStick", "draconicStick", "emeraldStick", "copperStick", "tinStick", "silverStick",
//			"bronzeStick", "steelStick", "leadStick", "manyullynStick", "arditeStick", "cobaltStick", "aluminiumStick", "alumiteStick", "oilsandsOre",
//			"copperWire", "superconductorWire", "sulfuricAcid", "conveyorBelt", "ironWire", "aluminumWire", "aluminiumWire", "silverWire", "tinWire",
//			"dustSiliconSmall", "AluminumOre", "plateHeavyT2", "blockWool", "alloyPlateEnergizedHardened", "gasWood", "alloyPlateEnergized", "SilverOre",
//			"LeadOre", "TinOre", "CopperOre", "silverOre", "leadOre", "tinOre", "copperOre", "bauxiteOre", "HSLivingmetalIngot", "oilMoving", "oilStill",
//			"oilBucket", "petroleumOre", "dieselFuel", "diamondNugget", "planks", "wood", "stick", "sticks", "naquadah", "obsidianRod", "stoneRod",
//			"thaumiumRod", "steelRod", "netherrackRod", "woodRod", "ironRod", "cactusRod", "flintRod", "copperRod", "cobaltRod", "alumiteRod", "blueslimeRod",
//			"arditeRod", "manyullynRod", "bronzeRod", "boneRod", "slimeRod", "redalloyBundled", "bluestoneBundled", "infusedteslatiteInsulated",
//			"redalloyInsulated", "infusedteslatiteBundled"));
//
//	private static final EnumSet<OreGenEvent.GenerateMinable.EventType> PREVENTED_ORES = EnumSet.of(OreGenEvent.GenerateMinable.EventType.COAL,
//			OreGenEvent.GenerateMinable.EventType.IRON, OreGenEvent.GenerateMinable.EventType.GOLD,
//			OreGenEvent.GenerateMinable.EventType.DIAMOND, OreGenEvent.GenerateMinable.EventType.REDSTONE, OreGenEvent.GenerateMinable.EventType.LAPIS,
//			OreGenEvent.GenerateMinable.EventType.QUARTZ);
//
//	public static final HashMap<ChunkPos, int[]> chunkData = new HashMap<>(5000);
//
//	@SubscribeEvent
//	public void onEndermanTeleportEvent(EnderTeleportEvent aEvent) {
//		if (aEvent.getEntity() instanceof EntityEnderman && aEvent.getEntityLiving()
//				.getActivePotionEffect(MobEffects.WEAKNESS) != null) {
//			aEvent.setCanceled(true);
//		}
//	}
//
//	@SubscribeEvent
//	public void onEntitySpawningEvent(EntityJoinWorldEvent event) {
//		if (event.getEntity() != null && !event.getEntity().worldObj.isRemote) {
//			if (event.getEntity() instanceof EntityItem) {
//				((EntityItem) event.getEntity()).setEntityItemStack(OreDictionaryUnifier.getUnificated(((EntityItem) event.getEntity()).getEntityItem()));
//			}
//		}
//	}
//
//	@SubscribeEvent
//	public void onOreGenEvent(OreGenEvent.GenerateMinable aGenerator) {
//		if (aGenerator.getGenerator() instanceof WorldGenMinable) {
//			if (PREVENTED_ORES.contains(aGenerator.getType())) {
//				if (mDisableVanillaOres) {
//					aGenerator.setResult(Event.Result.DENY);
//				}
//				return;
//			}
//			if (mDisableModdedOres) {
//				WorldGenMinable worldGenMinable = (WorldGenMinable) aGenerator.getGenerator();
//				IBlockState oreBlock = ObfuscationReflectionHelper.getPrivateValue(WorldGenMinable.class, worldGenMinable, 0);
//				ItemMaterialInfo itemUnificationEntry = OreDictionaryUnifier.getAssociation(oreBlock);
//				if(itemUnificationEntry != null && itemUnificationEntry.mPrefix.toString().startsWith("ore") && (itemUnificationEntry.mMaterial.mMaterial.mTypes & 0x08) != 0) {
//					aGenerator.setResult(Event.Result.DENY);
//				}
//			}
//		}
//	}
//
//	@SubscribeEvent
//	public void onPlayerInteraction(PlayerInteractEvent.RightClickBlock aEvent) {
//		ItemStack aStack = aEvent.getItemStack();
//		if (aStack != null && aStack.getItem() == Items.FLINT_AND_STEEL) {
//			if (!aEvent.getWorld().isRemote &&
//					!aEvent.getEntityPlayer().capabilities.isCreativeMode &&
//					aEvent.getWorld().rand.nextInt(100) >= this.mFlintChance) {
//				aStack.damageItem(1, aEvent.getEntityPlayer());
//				if (aStack.getItemDamage() >= aStack.getMaxDamage())
//					aStack.stackSize -= 1;
//				aEvent.setCanceled(true);
//			}
//		}
//	}
//
//	@SubscribeEvent
//	public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent aEvent) {
//		if (aEvent.getHarvester() != null) {
//			ItemStack aStack = aEvent.getHarvester().getHeldItemMainhand();
//			if (aStack != null) {
//				if ((aStack.getItem() instanceof GT_MetaGenerated_Tool)) {
//					((GT_MetaGenerated_Tool) aStack.getItem())
//							.onHarvestBlockEvent(new ArrayList<>(aEvent.getDrops()), aStack, aEvent.getHarvester(),
//									aEvent.getState(), aEvent.getPos(),
//									aEvent.getFortuneLevel(), aEvent.isSilkTouching(), aEvent);
//				}
//				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, aStack) > 2) {
//					for (ItemStack tDrop : aEvent.getDrops()) {
//						ItemStack tSmeltingOutput = GT_ModHandler.getSmeltingOutput(tDrop, false, null);
//						if (tSmeltingOutput != null) {
//							tDrop.stackSize *= tSmeltingOutput.stackSize;
//							tSmeltingOutput.stackSize = tDrop.stackSize;
//							GT_Utility.setStack(tDrop, tSmeltingOutput);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	@SubscribeEvent
//	public void onFluidContainerRegistration(FluidContainerRegistry.FluidContainerRegisterEvent aFluidEvent) {
//		if ((aFluidEvent.getData().filledContainer.getItem() == Items.POTIONITEM) && (aFluidEvent.getData().filledContainer.getItemDamage() == 0)) {
//			aFluidEvent.getData().fluid.amount = 0;
//		}
//		OreDictionaryUnifier.addToBlacklist(aFluidEvent.getData().emptyContainer);
//		OreDictionaryUnifier.addToBlacklist(aFluidEvent.getData().filledContainer);
//		GT_Utility.addFluidContainerData(aFluidEvent.getData());
//	}
//
//	@SubscribeEvent
//	public void onServerTickEvent(TickEvent.ServerTickEvent aEvent) {
//	}
//
//	@SubscribeEvent
//	public void onWorldTickEvent(TickEvent.WorldTickEvent aEvent) {
//		if(aEvent.world.provider.getDimension() == 0)
//			mTicksUntilNextCraftSound--;
//		if (aEvent.side.isServer()) {
//			if (this.mUniverse == null) {
//				this.mUniverse = aEvent.world;
//			}
//			if (this.isFirstServerWorldTick) {
//				File tSaveDiretory = getSaveDirectory();
//				if (tSaveDiretory != null) {
//					this.isFirstServerWorldTick = false;
//					try {
//						for (IMetaTileEntity tMetaTileEntity : GregTechAPI.METATILEENTITIES) {
//							if (tMetaTileEntity != null) {
//								tMetaTileEntity.onWorldLoad(tSaveDiretory);
//							}
//						}
//					} catch (Throwable e) {
//						e.printStackTrace(GTLog.err);
//					}
//				}
//			}
//			if ((aEvent.world.getTotalWorldTime() % 100L == 0L) && ((this.mItemDespawnTime != 6000) || (this.mMaxEqualEntitiesAtOneSpot > 0))) {
//				for (int i = 0; i < aEvent.world.loadedEntityList.size(); i++) {
//					if ((aEvent.world.loadedEntityList.get(i) instanceof Entity)) {
//						Entity tEntity = aEvent.world.loadedEntityList.get(i);
//						if (((tEntity instanceof EntityItem)) && (this.mItemDespawnTime != 6000) && (((EntityItem) tEntity).lifespan == 6000)) {
//							((EntityItem) tEntity).lifespan = this.mItemDespawnTime;
//						} else if (((tEntity instanceof EntityLivingBase)) && (this.mMaxEqualEntitiesAtOneSpot > 0) && (!(tEntity instanceof EntityPlayer))
//								&& (tEntity.canBePushed()) && (((EntityLivingBase) tEntity).getHealth() > 0.0F)) {
//							AxisAlignedBB boundingBox = tEntity.getCollisionBoundingBox();
//							if (boundingBox != null) {
//								List tList = tEntity.worldObj.getEntitiesWithinAABBExcludingEntity(tEntity, boundingBox.expand(0.2D, 0.0D, 0.2D));
//								Class tClass = tEntity.getClass();
//								int tEntityCount = 1;
//								if (tList != null) {
//									for (Object aTList : tList) {
//										if ((aTList != null) && (aTList.getClass() == tClass)) {
//											tEntityCount++;
//										}
//									}
//								}
//								if (tEntityCount > this.mMaxEqualEntitiesAtOneSpot) {
//									tEntity.attackEntityFrom(DamageSource.inWall, tEntityCount - this.mMaxEqualEntitiesAtOneSpot);
//								}
//							}
//						}
//					}
//				}
//			}
//			if (aEvent.world.provider.getDimension() == 0)
//				GT_Pollution.onWorldTick(aEvent.world, (int) (aEvent.world.getTotalWorldTime() % 1200));
//		}
//	}
//
//	@SubscribeEvent
//	public void onPlayerTickEventServer(TickEvent.PlayerTickEvent aEvent) {
//		if ((aEvent.side.isServer()) && (aEvent.phase == TickEvent.Phase.END) && (!aEvent.player.isDead)) {
//			if ((aEvent.player.ticksExisted % 200 == 0) && (aEvent.player.capabilities.allowEdit) && (!aEvent.player.capabilities.isCreativeMode)
//					&& (this.mSurvivalIntoAdventure)) {
//				aEvent.player.setGameType(GameType.ADVENTURE);
//				aEvent.player.capabilities.allowEdit = false;
//				if (this.mAxeWhenAdventure) {
//					GT_Utility.sendChatToPlayer(aEvent.player, "It's dangerous to go alone! Take this.");
//					aEvent.player.worldObj.spawnEntityInWorld(new EntityItem(aEvent.player.worldObj, aEvent.player.posX, aEvent.player.posY,
//							aEvent.player.posZ, MetaTool.INSTANCE.getToolWithStats(MetaTool.AXE, 1, Materials.Flint, Materials.Wood, null)));
//				}
//			}
//			boolean tHungerEffect = (this.mHungerEffect) && (aEvent.player.ticksExisted % 2400 == 1200);
//			if (aEvent.player.ticksExisted % 120 == 0) {
//				int tCount = 64;
//				for (int i = 0; i < 36; i++) {
//					ItemStack tStack;
//					if ((tStack = aEvent.player.inventory.getStackInSlot(i)) != null) {
//						if (!aEvent.player.capabilities.isCreativeMode) {
//							GT_Utility.applyRadioactivity(aEvent.player, GT_Utility.getRadioactivityLevel(tStack), tStack.stackSize);
//							float tHeat = GT_Utility.getHeatDamageFromItem(tStack);
//							if (tHeat != 0.0F) {
//								if (tHeat > 0.0F) {
//									GT_Utility.applyHeatDamage(aEvent.player, tHeat);
//								} else {
//									GT_Utility.applyFrostDamage(aEvent.player, -tHeat);
//								}
//							}
//						}
//						if (tHungerEffect) {
//							tCount += tStack.stackSize * 64 / Math.max(1, tStack.getMaxStackSize());
//						}
//						if (this.mInventoryUnification) {
//							OreDictionaryUnifier.setStack(true, tStack);
//						}
//					}
//				}
//				for (int i = 0; i < 4; i++) {
//					ItemStack tStack;
//					if ((tStack = aEvent.player.inventory.armorInventory[i]) != null) {
//						if (!aEvent.player.capabilities.isCreativeMode) {
//							GT_Utility.applyRadioactivity(aEvent.player, GT_Utility.getRadioactivityLevel(tStack), tStack.stackSize);
//							float tHeat = GT_Utility.getHeatDamageFromItem(tStack);
//							if (tHeat != 0.0F) {
//								if (tHeat > 0.0F) {
//									GT_Utility.applyHeatDamage(aEvent.player, tHeat);
//								} else {
//									GT_Utility.applyFrostDamage(aEvent.player, -tHeat);
//								}
//							}
//						}
//						if (tHungerEffect) {
//							tCount += 256;
//						}
//					}
//				}
//				if (tHungerEffect) {
//					aEvent.player.addExhaustion(Math.max(1.0F, tCount / 666.6F));
//				}
//			}
//		}
//	}
//
//	@SubscribeEvent
//	public void handleChunkSaveEvent(ChunkDataEvent.Save event)
//	{
//		ChunkPos tPos = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);
//		if(chunkData.containsKey(tPos)){
//			int[] tInts = chunkData.get(tPos);
//			if(tInts.length>0){event.getData().setInteger("GTOIL", tInts[0]);}
//			if(tInts.length>1){event.getData().setInteger("GTPOLLUTION", tInts[1]);}}
//	}
//
//	@SubscribeEvent
//	public void handleChunkLoadEvent(ChunkDataEvent.Load event)
//	{
//		int tOil = 0;
//		int tPollution = 0;
//
//		ChunkPos tPos = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);
//		int[] tData = new int[2];
//		if(chunkData.containsKey(tPos)){
//			tData = chunkData.get(tPos);
//			chunkData.remove(tPos);
//		}
//
//		if(event.getData().hasKey("GTOIL")){
//			if(tData.length>2){
//				tOil = tData[0];
//			}else{
//				tOil += event.getData().getInteger("GTOIL");
//			}
//		}else{
//			if(tData[0]!=0){
//				tOil = tData[0];
//			}
//		}
//
//		if(event.getData().hasKey("GTPOLLUTION")){
//			if(tData.length>2){
//				tPollution = tData[1];
//			}else{
//				tPollution += event.getData().getInteger("GTPOLLUTION");
//			}
//		}else{
//			if(tData[1]!=0){
//				tPollution = tData[1];
//			}
//		}
//
//		chunkData.put(tPos, new int[]{ tOil, tPollution,-1});
//	}
}
