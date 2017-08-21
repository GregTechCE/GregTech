package gregtech.common;

import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
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

	private final Collection<String> mIgnoredItems = new HashSet<String>(Arrays.asList("itemGhastTear", "itemFlint", "itemClay", "itemBucketSaltWater",
			"itemBucketFreshWater", "itemBucketWater", "itemRock", "itemReed", "itemArrow", "itemSaw", "itemKnife", "itemHammer", "itemChisel", "itemRubber",
			"itemEssence", "itemIlluminatedPanel", "itemSkull", "itemRawRubber", "itemBacon", "itemJetpackAccelerator", "itemLazurite", "itemIridium",
			"itemTear", "itemClaw", "itemFertilizer", "itemTar", "itemSlimeball", "itemCoke", "itemBeeswax", "itemBeeQueen", "itemForcicium", "itemForcillium",
			"itemRoyalJelly", "itemHoneydew", "itemHoney", "itemPollen", "itemReedTypha", "itemSulfuricAcid", "itemPotash", "itemCompressedCarbon",
			"itemBitumen", "itemBioFuel", "itemCokeSugar", "itemCokeCactus", "itemCharcoalSugar", "itemCharcoalCactus", "itemSludge", "itemEnrichedAlloy",
			"itemQuicksilver", "itemMercury", "itemOsmium", "itemUltimateCircuit", "itemEnergizedStar", "itemAntimatterMolecule", "itemAntimatterGlob",
			"itemCoal", "itemBoat", "itemHerbalMedicineCake", "itemCakeSponge", "itemFishandPumpkinCakeSponge", "itemSoulCleaver", "itemInstantCake",
			"itemWhippingCream", "itemGlisteningWhippingCream", "itemCleaver", "itemHerbalMedicineWhippingCream", "itemStrangeWhippingCream",
			"itemBlazeCleaver", "itemBakedCakeSponge", "itemMagmaCake", "itemGlisteningCake", "itemOgreCleaver", "itemFishandPumpkinCake",
			"itemMagmaWhippingCream", "itemMultimeter", "itemSuperconductor"));
	private final Collection<String> mIgnoredNames = new HashSet<String>(Arrays.asList("grubBee", "chainLink", "candyCane", "bRedString", "bVial",
			"bFlask", "anorthositeSmooth", "migmatiteSmooth", "slateSmooth", "travertineSmooth", "limestoneSmooth", "orthogneissSmooth", "marbleSmooth",
			"honeyDrop", "lumpClay", "honeyEqualssugar", "flourEqualswheat", "bluestoneInsulated", "blockWaterstone", "blockSand", "blockTorch",
			"blockPumpkin", "blockClothRock", "blockStainedHardenedClay", "blockQuartzPillar", "blockQuartzChiselled", "blockSpawner", "blockCloth", "mobHead",
			"mobEgg", "enderFlower", "enderChest", "clayHardened", "dayGemMaterial", "nightGemMaterial", "snowLayer", "bPlaceholder", "hardenedClay",
			"eternalLifeEssence", "sandstone", "wheatRice", "transdimBlock", "bambooBasket", "lexicaBotania", "livingwoodTwig", "redstoneCrystal",
			"pestleAndMortar", "glowstone", "whiteStone", "stoneSlab", "transdimBlock", "clayBowl", "clayPlate", "ceramicBowl", "ceramicPlate", "ovenRack",
			"clayCup", "ceramicCup", "batteryBox", "transmutationStone", "torchRedstoneActive", "coal", "charcoal", "cloth", "cobblestoneSlab",
			"stoneBrickSlab", "cobblestoneWall", "stoneBrickWall", "cobblestoneStair", "stoneBrickStair", "blockCloud", "blockDirt", "blockTyrian",
			"blockCarpet", "blockFft", "blockLavastone", "blockHolystone", "blockConcrete", "sunnariumPart", "brSmallMachineCyaniteProcessor", "meteoriteCoal",
			"blockCobble", "pressOreProcessor", "crusherOreProcessor", "grinderOreProcessor", "blockRubber", "blockHoney", "blockHoneydew", "blockPeat",
			"blockRadioactive", "blockSlime", "blockCocoa", "blockSugarCane", "blockLeather", "blockClayBrick", "solarPanelHV", "cableRedNet", "stoneBowl",
			"crafterWood", "taintedSoil", "brickXyEngineering", "breederUranium", "wireMill", "chunkLazurite", "aluminumNatural", "aluminiumNatural",
			"naturalAluminum", "naturalAluminium", "antimatterMilligram", "antimatterGram", "strangeMatter", "coalGenerator", "electricFurnace",
			"unfinishedTank", "valvePart", "aquaRegia", "leatherSeal", "leatherSlimeSeal", "hambone", "slimeball", "clay", "enrichedUranium", "camoPaste",
			"antiBlock", "burntQuartz", "salmonRaw", "blockHopper", "blockEnderObsidian", "blockIcestone", "blockMagicWood", "blockEnderCore", "blockHeeEndium",
			"oreHeeEndPowder", "oreHeeStardust", "oreHeeIgneousRock", "oreHeeInstabilityOrb", "crystalPureFluix", "shardNether", "gemFluorite",
			"stickObsidian", "caveCrystal", "shardCrystal", "DYECrystal","shardFire","shardWater","shardAir","shardEarth","ingotRefinedIron","blockMarble","ingotUnstable",
			"blockCactus", "blockPrismarineBrick", "blockPrismarineDark", "stoneGranitePolished", "stoneDioritePolished", "stoneAndesitePolished", "doorWood", "doorIron"));
	private final Collection<String> mInvalidNames = new HashSet<String>(Arrays.asList("diamondShard", "redstoneRoot", "obsidianStick", "bloodstoneOre",
			"universalCable", "bronzeTube", "ironTube", "netherTube", "obbyTube", "infiniteBattery", "eliteBattery", "advancedBattery", "10kEUStore",
			"blueDye", "MonazitOre", "quartzCrystal", "whiteLuminiteCrystal", "darkStoneIngot", "invisiumIngot", "demoniteOrb", "enderGem", "starconiumGem",
			"osmoniumIngot", "tapaziteGem", "zectiumIngot", "foolsRubyGem", "rubyGem", "meteoriteGem", "adamiteShard", "sapphireGem", "copperIngot",
			"ironStick", "goldStick", "diamondStick", "reinforcedStick", "draconicStick", "emeraldStick", "copperStick", "tinStick", "silverStick",
			"bronzeStick", "steelStick", "leadStick", "manyullynStick", "arditeStick", "cobaltStick", "aluminiumStick", "alumiteStick", "oilsandsOre",
			"copperWire", "superconductorWire", "sulfuricAcid", "conveyorBelt", "ironWire", "aluminumWire", "aluminiumWire", "silverWire", "tinWire",
			"dustSiliconSmall", "AluminumOre", "plateHeavyT2", "blockWool", "alloyPlateEnergizedHardened", "gasWood", "alloyPlateEnergized", "SilverOre",
			"LeadOre", "TinOre", "CopperOre", "silverOre", "leadOre", "tinOre", "copperOre", "bauxiteOre", "HSLivingmetalIngot", "oilMoving", "oilStill",
			"oilBucket", "petroleumOre", "dieselFuel", "diamondNugget", "planks", "wood", "stick", "sticks", "naquadah", "obsidianRod", "stoneRod",
			"thaumiumRod", "steelRod", "netherrackRod", "woodRod", "ironRod", "cactusRod", "flintRod", "copperRod", "cobaltRod", "alumiteRod", "blueslimeRod",
			"arditeRod", "manyullynRod", "bronzeRod", "boneRod", "slimeRod", "redalloyBundled", "bluestoneBundled", "infusedteslatiteInsulated",
			"redalloyInsulated", "infusedteslatiteBundled"));

	private static final EnumSet<OreGenEvent.GenerateMinable.EventType> PREVENTED_ORES = EnumSet.of(OreGenEvent.GenerateMinable.EventType.COAL,
			OreGenEvent.GenerateMinable.EventType.IRON, OreGenEvent.GenerateMinable.EventType.GOLD,
			OreGenEvent.GenerateMinable.EventType.DIAMOND, OreGenEvent.GenerateMinable.EventType.REDSTONE, OreGenEvent.GenerateMinable.EventType.LAPIS,
			OreGenEvent.GenerateMinable.EventType.QUARTZ);

	public static final HashMap<ChunkPos, int[]> chunkData = new HashMap<>(5000);

	@SubscribeEvent
	public void onEndermanTeleportEvent(EnderTeleportEvent aEvent) {
		if (aEvent.getEntity() instanceof EntityEnderman && aEvent.getEntityLiving()
				.getActivePotionEffect(MobEffects.WEAKNESS) != null) {
			aEvent.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntitySpawningEvent(EntityJoinWorldEvent event) {
		if (event.getEntity() != null && !event.getEntity().worldObj.isRemote) {
			if (event.getEntity() instanceof EntityItem) {
				((EntityItem) event.getEntity()).setEntityItemStack(OreDictionaryUnifier.getUnificated(((EntityItem) event.getEntity()).getEntityItem()));
			}
		}
	}

	@SubscribeEvent
	public void onOreGenEvent(OreGenEvent.GenerateMinable aGenerator) {
		if (aGenerator.getGenerator() instanceof WorldGenMinable) {
			if (PREVENTED_ORES.contains(aGenerator.getType())) {
				if (mDisableVanillaOres) {
					aGenerator.setResult(Event.Result.DENY);
				}
				return;
			}
			if (mDisableModdedOres) {
				WorldGenMinable worldGenMinable = (WorldGenMinable) aGenerator.getGenerator();
				IBlockState oreBlock = ObfuscationReflectionHelper.getPrivateValue(WorldGenMinable.class, worldGenMinable, 0);
				ItemMaterialInfo itemUnificationEntry = OreDictionaryUnifier.getAssociation(oreBlock);
				if(itemUnificationEntry != null && itemUnificationEntry.mPrefix.toString().startsWith("ore") && (itemUnificationEntry.mMaterial.mMaterial.mTypes & 0x08) != 0) {
					aGenerator.setResult(Event.Result.DENY);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteraction(PlayerInteractEvent.RightClickBlock aEvent) {
		ItemStack aStack = aEvent.getItemStack();
		if (aStack != null && aStack.getItem() == Items.FLINT_AND_STEEL) {
			if (!aEvent.getWorld().isRemote &&
					!aEvent.getEntityPlayer().capabilities.isCreativeMode &&
					aEvent.getWorld().rand.nextInt(100) >= this.mFlintChance) {
				aStack.damageItem(1, aEvent.getEntityPlayer());
				if (aStack.getItemDamage() >= aStack.getMaxDamage())
					aStack.stackSize -= 1;
				aEvent.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent aEvent) {
		if (aEvent.getHarvester() != null) {
			ItemStack aStack = aEvent.getHarvester().getHeldItemMainhand();
			if (aStack != null) {
				if ((aStack.getItem() instanceof GT_MetaGenerated_Tool)) {
					((GT_MetaGenerated_Tool) aStack.getItem())
							.onHarvestBlockEvent(new ArrayList<>(aEvent.getDrops()), aStack, aEvent.getHarvester(),
									aEvent.getState(), aEvent.getPos(),
									aEvent.getFortuneLevel(), aEvent.isSilkTouching(), aEvent);
				}
				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, aStack) > 2) {
					for (ItemStack tDrop : aEvent.getDrops()) {
						ItemStack tSmeltingOutput = GT_ModHandler.getSmeltingOutput(tDrop, false, null);
						if (tSmeltingOutput != null) {
							tDrop.stackSize *= tSmeltingOutput.stackSize;
							tSmeltingOutput.stackSize = tDrop.stackSize;
							GT_Utility.setStack(tDrop, tSmeltingOutput);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void registerOre(OreDictionary.OreRegisterEvent aEvent) {
		ModContainer tContainer = Loader.instance().activeModContainer();
		String aMod = tContainer == null ? "UNKNOWN" : tContainer.getModId();
		String aOriginalMod = aMod;
		if (OreDictionaryUnifier.isRegisteringOres()) {
			aMod = "gregtech";
		} else if (aMod.equals("gregtech")) {
			aMod = "UNKNOWN";
		}
		if ((aEvent == null) || (aEvent.getOre() == null) || (aEvent.getOre().getItem() == null) || (aEvent.getName() == null) || (aEvent.getName().isEmpty())
				|| (aEvent.getName().replaceAll("_", "").length() - aEvent.getName().length() == 9)) {
			if (aOriginalMod.equals("gregtech")) {
				aOriginalMod = "UNKNOWN";
			}
			GTLog.ore
					.println(aOriginalMod
							+ " did something very bad! The registration is too invalid to even be shown properly. This happens only if you register null, invalid Items, empty Strings or even nonexisting Events to the OreDict.");
			throw new IllegalArgumentException(
					aOriginalMod
							+ " did something very bad! The registration is too invalid to even be shown properly. This happens only if you register null, invalid Items, empty Strings or even nonexisting Events to the OreDict.");
		}
		try {
			aEvent.getOre().stackSize = 1;
			if (this.mIgnoreTcon || aEvent.getOre().getUnlocalizedName().startsWith("item.oreberry")) {
				if ((aOriginalMod.toLowerCase(Locale.ENGLISH).contains("xycraft")) || (aOriginalMod.toLowerCase(Locale.ENGLISH).contains("tconstruct"))
						|| ((aOriginalMod.toLowerCase(Locale.ENGLISH).contains("natura")) && (!aOriginalMod.toLowerCase(Locale.ENGLISH).contains("natural")))) {
					if (GT_Values.D1) {
						GTLog.ore.println(aMod + " -> " + aEvent.getName() + " is getting ignored, because of racism. :P");
					}
					return;
				}
			}
			if(aEvent.getOre().getItem() instanceof ItemIC2FluidContainer) {
				System.out.println("Ignoring IC2 fluid container " + aEvent.getOre() + " registration as " + aEvent.getName() + " because ic2 fluid containers are mad.");
				OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
				return;
			}
			String tModToName = aMod + " -> " + aEvent.getName();
			if ((this.mOreDictActivated) || (GregTech_API.sPostloadStarted) || ((this.mSortToTheEnd) && (GregTech_API.sLoadFinished))) {
				tModToName = aOriginalMod + " --Late--> " + aEvent.getName();
			}
			if (((aEvent.getOre().getItem() instanceof ItemBlock)) || (GT_Utility.getBlockFromStack(aEvent.getOre()) != null)) {
				OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
			}
			this.mRegisteredOres.add(aEvent.getOre());

			if (this.mIgnoredItems.contains(aEvent.getName())) {
				if ((aEvent.getName().startsWith("item"))) {
					GTLog.ore.println(tModToName);
					if (aEvent.getName().equals("itemCopperWire")) {
						OreDictionaryUnifier.registerOre(OreDictNames.craftingWireCopper, aEvent.getOre());
					}
					if (aEvent.getName().equals("itemRubber")) {
						OreDictionaryUnifier.registerOre(OrePrefix.ingot, Materials.Rubber, aEvent.getOre());
					}
					return;
				}
			} else if (this.mIgnoredNames.contains(aEvent.getName())){
				GTLog.ore.println(tModToName + " is getting ignored via hardcode.");
				return;
			} else if (aEvent.getName().equals("stone")) {
				OreDictionaryUnifier.registerOre("stoneSmooth", aEvent.getOre());
				return;
			} else if (aEvent.getName().equals("cobblestone")) {
				OreDictionaryUnifier.registerOre("stoneCobble", aEvent.getOre());
				return;
			} else if ((aEvent.getName().contains("|")) || (aEvent.getName().contains("*")) || (aEvent.getName().contains(":")) || (aEvent.getName().contains("."))
					|| (aEvent.getName().contains("$"))) {
				GTLog.ore.println(tModToName + " is using a private Prefix and is therefor getting ignored properly.");
				return;
			} else if (aEvent.getName().equals("copperWire")) {
				OreDictionaryUnifier.registerOre(OreDictNames.craftingWireCopper, aEvent.getOre());
			} else if (aEvent.getName().equals("oreHeeEndrium")) {
				OreDictionaryUnifier.registerOre(OrePrefix.ore, Materials.Endium, aEvent.getOre());
			} else if (aEvent.getName().equals("sheetPlastic")) {
				OreDictionaryUnifier.registerOre(OrePrefix.plate, Materials.Plastic, aEvent.getOre());
			} else if (aEvent.getName().equals("shard")) {
				if (aEvent.getName().equals("shardAir")) {
					OreDictionaryUnifier.registerOre(OrePrefix.gem, Materials.InfusedAir, aEvent.getOre());
					return;
				} else if (aEvent.getName().equals("shardWater")) {
					OreDictionaryUnifier.registerOre(OrePrefix.gem, Materials.InfusedWater, aEvent.getOre());
					return;
				} else if (aEvent.getName().equals("shardFire")) {
					OreDictionaryUnifier.registerOre(OrePrefix.gem, Materials.InfusedFire, aEvent.getOre());
					return;
				} else if (aEvent.getName().equals("shardEarth")) {
					OreDictionaryUnifier.registerOre(OrePrefix.gem, Materials.InfusedEarth, aEvent.getOre());
					return;
				} else if (aEvent.getName().equals("shardOrder")) {
					OreDictionaryUnifier.registerOre(OrePrefix.gem, Materials.InfusedOrder, aEvent.getOre());
					return;
				} else if (aEvent.getName().equals("shardEntropy")) {
					OreDictionaryUnifier.registerOre(OrePrefix.gem, Materials.InfusedEntropy, aEvent.getOre());
					return;
				}
			} else if (aEvent.getName().equals("fieryIngot")) {
				OreDictionaryUnifier.registerOre(OrePrefix.ingot, Materials.FierySteel, aEvent.getOre());
				return;
			} else if (aEvent.getName().equals("ironwood")) {
				OreDictionaryUnifier.registerOre(OrePrefix.ingot, Materials.IronWood, aEvent.getOre());
				return;
			} else if (aEvent.getName().equals("steeleaf")) {
				OreDictionaryUnifier.registerOre(OrePrefix.ingot, Materials.Steeleaf, aEvent.getOre());
				return;
			} else if (aEvent.getName().equals("knightmetal")) {
				OreDictionaryUnifier.registerOre(OrePrefix.ingot, Materials.Knightmetal, aEvent.getOre());
				return;
			} else if (aEvent.getName().equals("compressedAluminum")) {
				OreDictionaryUnifier.registerOre(OrePrefix.compressed, Materials.Aluminium, aEvent.getOre());
				return;
			} else if (aEvent.getName().contains(" ")) {
				GTLog.ore.println(tModToName + " is getting re-registered because the OreDict Name containing invalid spaces.");
				OreDictionaryUnifier.registerOre(aEvent.getName().replaceAll(" ", ""), GT_Utility.copyAmount(1, aEvent.getOre()));
				aEvent.getOre().setStackDisplayName("Invalid OreDictionary Tag");
				return;
			} else if (this.mInvalidNames.contains(aEvent.getName())) {
				GTLog.ore.println(tModToName + " is wrongly registered and therefor getting ignored.");

				return;
			}
			OrePrefix aPrefix = OrePrefix.getOrePrefix(aEvent.getName());
			Materials aMaterial = Materials._NULL;
			if ((aPrefix == OrePrefix.nugget) && (aMod.equals("Thaumcraft")) && (aEvent.getOre().getItem().getUnlocalizedName().contains("ItemResource"))) {
				return;
			}
			if (aPrefix == null) {
				if (aEvent.getName().toLowerCase().equals(aEvent.getName())) {
					GTLog.ore.println(tModToName + " is invalid due to being solely lowercased.");
					return;
				} else if (aEvent.getName().toUpperCase().equals(aEvent.getName())) {
					GTLog.ore.println(tModToName + " is invalid due to being solely uppercased.");
					return;
				} else if (Character.isUpperCase(aEvent.getName().charAt(0))) {
					GTLog.ore.println(tModToName + " is invalid due to the first character being uppercased.");
				}
			} else {
				if (aPrefix.mDontUnificateActively) {
					OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
				}
				if (aPrefix != aPrefix.mPrefixInto) {
					String tNewName = aEvent.getName().replaceFirst(aPrefix.toString(), aPrefix.mPrefixInto.toString());
					if (!OreDictionaryUnifier.isRegisteringOres()) {
						GTLog.ore.println(tModToName + " uses a depricated Prefix, and is getting re-registered as " + tNewName);
					}
					OreDictionaryUnifier.registerOre(tNewName, aEvent.getOre());
					return;
				}
				String tName = aEvent.getName().replaceFirst(aPrefix.toString(), "");
				if (tName.length() > 0) {
					char firstChar = tName.charAt(0);
					if (Character.isUpperCase(firstChar) || Character.isLowerCase(firstChar) || firstChar == '_') {
						if (aPrefix.mIsMaterialBased) {
							aMaterial = Materials.get(tName);
							if (aMaterial != aMaterial.mMaterialInto) {
								OreDictionaryUnifier.registerOre(aPrefix, aMaterial.mMaterialInto, aEvent.getOre());
								if (!OreDictionaryUnifier.isRegisteringOres()) {
									GTLog.ore.println(tModToName + " uses a deprecated Material and is getting re-registered as "
											+ aPrefix.get(aMaterial.mMaterialInto));
								}
								return;
							}
							if (!aPrefix.isIgnored(aMaterial)) {
								aPrefix.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));
							}
							if (aMaterial != Materials._NULL) {
								Materials tReRegisteredMaterial;
								for (Iterator i$ = aMaterial.mOreReRegistrations.iterator(); i$.hasNext(); OreDictionaryUnifier.registerOre(aPrefix,
										tReRegisteredMaterial, aEvent.getOre())) {
									tReRegisteredMaterial = (Materials) i$.next();
								}
								aMaterial.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));

								if (GregTech_API.sThaumcraftCompat != null && aPrefix.doGenerateItem(aMaterial) && !aPrefix.isIgnored(aMaterial)) {
									List<TC_AspectStack> tAspects = new ArrayList<TC_AspectStack>();
									for (TC_AspectStack tAspect : aPrefix.mAspects) tAspect.addToAspectList(tAspects);
									if (aPrefix.mMaterialAmount >= 3628800 || aPrefix.mMaterialAmount < 0) for (TC_AspectStack tAspect : aMaterial.mAspects) tAspect.addToAspectList(tAspects);
									GregTech_API.sThaumcraftCompat.registerThaumcraftAspectsToItem(GT_Utility.copyAmount(1, aEvent.getOre()), tAspects, aEvent.getName());
								}

								switch (aPrefix) {
									case crystal:
										if ((aMaterial == Materials.CertusQuartz) || (aMaterial == Materials.NetherQuartz) || (aMaterial == Materials.Fluix)) {
											OreDictionaryUnifier.registerOre(OrePrefix.gem, aMaterial, aEvent.getOre());
										}
										break;
									case gem:
										if (aMaterial == Materials.Lapis || aMaterial == Materials.Sodalite) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBlue, aEvent.getOre());
										} else if (aMaterial == Materials.Lazurite) {
											OreDictionaryUnifier.registerOre(Dyes.dyeCyan, aEvent.getOre());
										} else if (aMaterial == Materials.InfusedAir || aMaterial == Materials.InfusedWater || aMaterial == Materials.InfusedFire || aMaterial == Materials.InfusedEarth || aMaterial == Materials.InfusedOrder || aMaterial == Materials.InfusedEntropy) {
											OreDictionaryUnifier.registerOre(aMaterial.mName.replaceFirst("Infused", "shard"), aEvent.getOre());
										} else if (aMaterial == Materials.Chocolate) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBrown, aEvent.getOre());
										} else if (aMaterial == Materials.CertusQuartz || aMaterial == Materials.NetherQuartz) {
											OreDictionaryUnifier.registerOre(OrePrefix.item.get(aMaterial), aEvent.getOre());
											OreDictionaryUnifier.registerOre(OrePrefix.crystal, aMaterial, aEvent.getOre());
											OreDictionaryUnifier.registerOre(OreDictNames.craftingQuartz, aEvent.getOre());
										} else if (aMaterial == Materials.Fluix || aMaterial == Materials.Quartz || aMaterial == Materials.Quartzite) {
											OreDictionaryUnifier.registerOre(OrePrefix.crystal, aMaterial, aEvent.getOre());
											OreDictionaryUnifier.registerOre(OreDictNames.craftingQuartz, aEvent.getOre());
										}
										break;
									case cableGt01:
										if (aMaterial == Materials.Tin) {
											OreDictionaryUnifier.registerOre(OreDictNames.craftingWireTin, aEvent.getOre());
										} else if (aMaterial == Materials.AnyCopper) {
											OreDictionaryUnifier.registerOre(OreDictNames.craftingWireCopper, aEvent.getOre());
										} else if (aMaterial == Materials.Gold) {
											OreDictionaryUnifier.registerOre(OreDictNames.craftingWireGold, aEvent.getOre());
										} else if (aMaterial == Materials.AnyIron) {
											OreDictionaryUnifier.registerOre(OreDictNames.craftingWireIron, aEvent.getOre());
										}
										break;
									case lens:
										if ((aMaterial.contains(SubTag.TRANSPARENT)) && (aMaterial.mColor != Dyes._NULL)) {
											OreDictionaryUnifier.registerOre("craftingLens" + aMaterial.mColor.toString().replaceFirst("DYE", ""), aEvent.getOre());
										}
										break;
									case plate:
										if ((aMaterial == Materials.Plastic) || (aMaterial == Materials.Rubber)) {
											OreDictionaryUnifier.registerOre(OrePrefix.sheet, aMaterial, aEvent.getOre());
										} else if (aMaterial == Materials.Silicon) {
											OreDictionaryUnifier.registerOre(OrePrefix.item, aMaterial, aEvent.getOre());
										} else if (aMaterial == Materials.Wood) {
											OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
											OreDictionaryUnifier.registerOre(OrePrefix.plank, aMaterial, aEvent.getOre());
										}
										break;
									case cell:
										if (aMaterial == Materials.Empty) {
											OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
										}
										break;
									case gearGt:
										OreDictionaryUnifier.registerOre(OrePrefix.gear, aMaterial, aEvent.getOre());
										break;
									case stick:
										if (!GT_RecipeRegistrator.sRodMaterialList.contains(aMaterial)) {
											GT_RecipeRegistrator.sRodMaterialList.add(aMaterial);
										} else if (aMaterial == Materials.Wood) {
											OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
										} else if ((aMaterial == Materials.Tin) || (aMaterial == Materials.Lead) || (aMaterial == Materials.SolderingAlloy)) {
											OreDictionaryUnifier.registerOre(ToolDictNames.craftingToolSolderingMetal, aEvent.getOre());
										}
										break;
									case dust:
										if (aMaterial == Materials.Salt) {
											OreDictionaryUnifier.registerOre("itemSalt", aEvent.getOre());
										} else if (aMaterial == Materials.Wood) {
											OreDictionaryUnifier.registerOre("pulpWood", aEvent.getOre());
										} else if (aMaterial == Materials.Wheat) {
											OreDictionaryUnifier.registerOre("foodFlour", aEvent.getOre());
										} else if (aMaterial == Materials.Lapis) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBlue, aEvent.getOre());
										} else if (aMaterial == Materials.Lazurite) {
											OreDictionaryUnifier.registerOre(Dyes.dyeCyan, aEvent.getOre());
										} else if (aMaterial == Materials.Sodalite) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBlue, aEvent.getOre());
										} else if (aMaterial == Materials.Cocoa) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBrown, aEvent.getOre());
											OreDictionaryUnifier.registerOre("foodCocoapowder", aEvent.getOre());
										} else if (aMaterial == Materials.Coffee) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBrown, aEvent.getOre());
										} else if (aMaterial == Materials.BrownLimonite) {
											OreDictionaryUnifier.registerOre(Dyes.dyeBrown, aEvent.getOre());
										} else if (aMaterial == Materials.YellowLimonite) {
											OreDictionaryUnifier.registerOre(Dyes.dyeYellow, aEvent.getOre());
										}
										break;
									case ingot:
										if (aMaterial == Materials.Rubber) {
											OreDictionaryUnifier.registerOre("itemRubber", aEvent.getOre());
										} else if (aMaterial == Materials.FierySteel) {
											OreDictionaryUnifier.registerOre("fieryIngot", aEvent.getOre());
										} else if (aMaterial == Materials.IronWood) {
											OreDictionaryUnifier.registerOre("ironwood", aEvent.getOre());
										} else if (aMaterial == Materials.Steeleaf) {
											OreDictionaryUnifier.registerOre("steeleaf", aEvent.getOre());
										} else if (aMaterial == Materials.Knightmetal) {
											OreDictionaryUnifier.registerOre("knightmetal", aEvent.getOre());
										} else if ((aMaterial == Materials.Brass) && (aEvent.getOre().getItemDamage() == 2)
												&& (aEvent.getOre().getUnlocalizedName().equals("item.ingotBrass"))
												&& (new ItemStack(aEvent.getOre().getItem(), 1, 0).getUnlocalizedName().contains("red"))) {
											OreDictionaryUnifier.set(OrePrefix.ingot, Materials.RedAlloy, new ItemStack(aEvent.getOre().getItem(), 1, 0));
											OreDictionaryUnifier.set(OrePrefix.ingot, Materials.BlueAlloy, new ItemStack(aEvent.getOre().getItem(), 1, 1));
											OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Brass, new ItemStack(aEvent.getOre().getItem(), 1, 2));
											if (!mDisableIC2Cables) {
												GT_Values.RA.addWiremillRecipe(
														GT_ModHandler.getIC2Item(ItemName.cable, CableType.copper, 3),
														new ItemStack(aEvent.getOre().getItem(), 1,
																8), 400, 1);
												GT_Values.RA.addWiremillRecipe(
														GT_ModHandler.getIC2Item(ItemName.cable, CableType.iron, 6),
														new ItemStack(aEvent.getOre().getItem(), 1, 9), 400, 2);
											}
											GT_Values.RA.addCutterRecipe(new ItemStack(aEvent.getOre().getItem(), 1, 3), new ItemStack(aEvent.getOre().getItem(), 16, 4),
													null, 400, 8);
										}
										break;
									default:
										break;
								}
								if (aPrefix.mIsUnificatable && !aMaterial.mUnificatable) {
									return;
								}
							} else {
								for (Dyes tDye : Dyes.VALUES) {
									if (aEvent.getName().endsWith(tDye.name().replaceFirst("dye", ""))) {
										OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
										GTLog.ore.println(tModToName + " Oh man, why the fuck would anyone need a OreDictified Color for this, that is even too much for GregTech... do not report this, this is just a random Comment about how ridiculous this is.");
										return;
									}
								}
								//System.out.println("Material Name: "+aEvent.getName()+ " !!!Unknown Material detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, an Issue nor a Lag Source, it is just an Information, which you should pass to me.");
								//GTLog.ore.println(tModToName + " uses an unknown Material. Report this to GregTech.");
								return;
							}
						} else {
							aPrefix.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));
						}
					}
				} else if (aPrefix.mIsSelfReferencing) {
					aPrefix.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));
				} else {
					GTLog.ore.println(tModToName + " uses a Prefix as full OreDict Name, and is therefor invalid.");
					aEvent.getOre().setStackDisplayName("Invalid OreDictionary Tag");
					return;
				}
				switch (aPrefix) {
					case dye:
						if (GT_Utility.isStringValid(tName)) {
							OreDictionaryUnifier.registerOre(OrePrefix.dye, aEvent.getOre());
						}
						break;
					case stoneSmooth:
						OreDictionaryUnifier.registerOre("stone", aEvent.getOre());
						break;
					case stoneCobble:
						OreDictionaryUnifier.registerOre("cobblestone", aEvent.getOre());
						break;
					case plank:
						if (tName.equals("Wood")) {
							OreDictionaryUnifier.addItemData(aEvent.getOre(), new ItemMaterialInfo(Materials.Wood, 3628800L));
						}
						break;
					case slab:
						if (tName.equals("Wood")) {
							OreDictionaryUnifier.addItemData(aEvent.getOre(), new ItemMaterialInfo(Materials.Wood, 1814400L));
						}
						break;
					case sheet:
						if (tName.equals("Plastic")) {
							OreDictionaryUnifier.registerOre(OrePrefix.plate, Materials.Plastic, aEvent.getOre());
						} else if (tName.equals("Rubber")) {
							OreDictionaryUnifier.registerOre(OrePrefix.plate, Materials.Rubber, aEvent.getOre());
						}
						break;
					case crafting:
						if (tName.equals("ToolSolderingMetal")) {
							GregTech_API.registerSolderingMetal(aEvent.getOre());
						} else if (tName.equals("IndustrialDiamond")) {
							OreDictionaryUnifier.addToBlacklist(aEvent.getOre());
						} else if (tName.equals("WireCopper")) {
							OreDictionaryUnifier.registerOre(OrePrefix.wire, Materials.Copper, aEvent.getOre());
						}
						break;
					case wood:
						if (tName.equals("Rubber")) {
							OreDictionaryUnifier.registerOre("logRubber", aEvent.getOre());
						}
						break;
					case food:
						if (tName.equals("Cocoapowder")) {
							OreDictionaryUnifier.registerOre(OrePrefix.dust, Materials.Cocoa, aEvent.getOre());
						}
						break;
					default:
						break;
				}
			}
			GTLog.ore.println(tModToName);

			GT_Proxy.OreDictEventContainer tOre = new GT_Proxy.OreDictEventContainer(aEvent, aPrefix, aMaterial, aMod);
			if ((!this.mOreDictActivated) || (!GregTech_API.sUnificationEntriesRegistered)) {
				this.mEvents.add(tOre);
			} else {
				this.mEvents.clear();
			}
			if (this.mOreDictActivated) {
				registerRecipes(tOre);
			}
		} catch (Throwable e) {
			e.printStackTrace(GTLog.err);
		}
	}

	@SubscribeEvent
	public void onFluidContainerRegistration(FluidContainerRegistry.FluidContainerRegisterEvent aFluidEvent) {
		if ((aFluidEvent.getData().filledContainer.getItem() == Items.POTIONITEM) && (aFluidEvent.getData().filledContainer.getItemDamage() == 0)) {
			aFluidEvent.getData().fluid.amount = 0;
		}
		OreDictionaryUnifier.addToBlacklist(aFluidEvent.getData().emptyContainer);
		OreDictionaryUnifier.addToBlacklist(aFluidEvent.getData().filledContainer);
		GT_Utility.addFluidContainerData(aFluidEvent.getData());
	}

	@SubscribeEvent
	public void onServerTickEvent(TickEvent.ServerTickEvent aEvent) {
	}

	@SubscribeEvent
	public void onWorldTickEvent(TickEvent.WorldTickEvent aEvent) {
		if(aEvent.world.provider.getDimension() == 0)
			mTicksUntilNextCraftSound--;
		if (aEvent.side.isServer()) {
			if (this.mUniverse == null) {
				this.mUniverse = aEvent.world;
			}
			if (this.isFirstServerWorldTick) {
				File tSaveDiretory = getSaveDirectory();
				if (tSaveDiretory != null) {
					this.isFirstServerWorldTick = false;
					try {
						for (IMetaTileEntity tMetaTileEntity : GregTech_API.METATILEENTITIES) {
							if (tMetaTileEntity != null) {
								tMetaTileEntity.onWorldLoad(tSaveDiretory);
							}
						}
					} catch (Throwable e) {
						e.printStackTrace(GTLog.err);
					}
				}
			}
			if ((aEvent.world.getTotalWorldTime() % 100L == 0L) && ((this.mItemDespawnTime != 6000) || (this.mMaxEqualEntitiesAtOneSpot > 0))) {
				for (int i = 0; i < aEvent.world.loadedEntityList.size(); i++) {
					if ((aEvent.world.loadedEntityList.get(i) instanceof Entity)) {
						Entity tEntity = aEvent.world.loadedEntityList.get(i);
						if (((tEntity instanceof EntityItem)) && (this.mItemDespawnTime != 6000) && (((EntityItem) tEntity).lifespan == 6000)) {
							((EntityItem) tEntity).lifespan = this.mItemDespawnTime;
						} else if (((tEntity instanceof EntityLivingBase)) && (this.mMaxEqualEntitiesAtOneSpot > 0) && (!(tEntity instanceof EntityPlayer))
								&& (tEntity.canBePushed()) && (((EntityLivingBase) tEntity).getHealth() > 0.0F)) {
							AxisAlignedBB boundingBox = tEntity.getCollisionBoundingBox();
							if (boundingBox != null) {
								List tList = tEntity.worldObj.getEntitiesWithinAABBExcludingEntity(tEntity, boundingBox.expand(0.2D, 0.0D, 0.2D));
								Class tClass = tEntity.getClass();
								int tEntityCount = 1;
								if (tList != null) {
									for (Object aTList : tList) {
										if ((aTList != null) && (aTList.getClass() == tClass)) {
											tEntityCount++;
										}
									}
								}
								if (tEntityCount > this.mMaxEqualEntitiesAtOneSpot) {
									tEntity.attackEntityFrom(DamageSource.inWall, tEntityCount - this.mMaxEqualEntitiesAtOneSpot);
								}
							}
						}
					}
				}
			}
			if (aEvent.world.provider.getDimension() == 0)
				GT_Pollution.onWorldTick(aEvent.world, (int) (aEvent.world.getTotalWorldTime() % 1200));
		}
	}

	@SubscribeEvent
	public void onPlayerTickEventServer(TickEvent.PlayerTickEvent aEvent) {
		if ((aEvent.side.isServer()) && (aEvent.phase == TickEvent.Phase.END) && (!aEvent.player.isDead)) {
			if ((aEvent.player.ticksExisted % 200 == 0) && (aEvent.player.capabilities.allowEdit) && (!aEvent.player.capabilities.isCreativeMode)
					&& (this.mSurvivalIntoAdventure)) {
				aEvent.player.setGameType(GameType.ADVENTURE);
				aEvent.player.capabilities.allowEdit = false;
				if (this.mAxeWhenAdventure) {
					GT_Utility.sendChatToPlayer(aEvent.player, "It's dangerous to go alone! Take this.");
					aEvent.player.worldObj.spawnEntityInWorld(new EntityItem(aEvent.player.worldObj, aEvent.player.posX, aEvent.player.posY,
							aEvent.player.posZ, MetaTool.INSTANCE.getToolWithStats(MetaTool.AXE, 1, Materials.Flint, Materials.Wood, null)));
				}
			}
			boolean tHungerEffect = (this.mHungerEffect) && (aEvent.player.ticksExisted % 2400 == 1200);
			if (aEvent.player.ticksExisted % 120 == 0) {
				int tCount = 64;
				for (int i = 0; i < 36; i++) {
					ItemStack tStack;
					if ((tStack = aEvent.player.inventory.getStackInSlot(i)) != null) {
						if (!aEvent.player.capabilities.isCreativeMode) {
							GT_Utility.applyRadioactivity(aEvent.player, GT_Utility.getRadioactivityLevel(tStack), tStack.stackSize);
							float tHeat = GT_Utility.getHeatDamageFromItem(tStack);
							if (tHeat != 0.0F) {
								if (tHeat > 0.0F) {
									GT_Utility.applyHeatDamage(aEvent.player, tHeat);
								} else {
									GT_Utility.applyFrostDamage(aEvent.player, -tHeat);
								}
							}
						}
						if (tHungerEffect) {
							tCount += tStack.stackSize * 64 / Math.max(1, tStack.getMaxStackSize());
						}
						if (this.mInventoryUnification) {
							OreDictionaryUnifier.setStack(true, tStack);
						}
					}
				}
				for (int i = 0; i < 4; i++) {
					ItemStack tStack;
					if ((tStack = aEvent.player.inventory.armorInventory[i]) != null) {
						if (!aEvent.player.capabilities.isCreativeMode) {
							GT_Utility.applyRadioactivity(aEvent.player, GT_Utility.getRadioactivityLevel(tStack), tStack.stackSize);
							float tHeat = GT_Utility.getHeatDamageFromItem(tStack);
							if (tHeat != 0.0F) {
								if (tHeat > 0.0F) {
									GT_Utility.applyHeatDamage(aEvent.player, tHeat);
								} else {
									GT_Utility.applyFrostDamage(aEvent.player, -tHeat);
								}
							}
						}
						if (tHungerEffect) {
							tCount += 256;
						}
					}
				}
				if (tHungerEffect) {
					aEvent.player.addExhaustion(Math.max(1.0F, tCount / 666.6F));
				}
			}
		}
	}

	@SubscribeEvent
	public void handleChunkSaveEvent(ChunkDataEvent.Save event)
	{
		ChunkPos tPos = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);
		if(chunkData.containsKey(tPos)){
			int[] tInts = chunkData.get(tPos);
			if(tInts.length>0){event.getData().setInteger("GTOIL", tInts[0]);}
			if(tInts.length>1){event.getData().setInteger("GTPOLLUTION", tInts[1]);}}
	}

	@SubscribeEvent
	public void handleChunkLoadEvent(ChunkDataEvent.Load event)
	{
		int tOil = 0;
		int tPollution = 0;

		ChunkPos tPos = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);
		int[] tData = new int[2];
		if(chunkData.containsKey(tPos)){
			tData = chunkData.get(tPos);
			chunkData.remove(tPos);
		}

		if(event.getData().hasKey("GTOIL")){
			if(tData.length>2){
				tOil = tData[0];
			}else{
				tOil += event.getData().getInteger("GTOIL");
			}
		}else{
			if(tData[0]!=0){
				tOil = tData[0];
			}
		}

		if(event.getData().hasKey("GTPOLLUTION")){
			if(tData.length>2){
				tPollution = tData[1];
			}else{
				tPollution += event.getData().getInteger("GTPOLLUTION");
			}
		}else{
			if(tData[1]!=0){
				tPollution = tData[1];
			}
		}

		chunkData.put(tPos, new int[]{ tOil, tPollution,-1});
	}
}
