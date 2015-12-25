package gregtech.common.items.armor;

import java.util.HashMap;
import java.util.Map;

import gregtech.api.enums.Materials;

public class Values {
	public static Values INSTANCE;
	public static final Map<Materials, Values> MATERIAL_MAP = new HashMap<Materials, Values>();

	public int weight;
	public float physicalDef;
	public float projectileDef;
	public float fireDef;
	public float magicDef;
	public float explosionDef;

	public Values(Materials material, int weight, float physicalDef, float projectileDef, float fireDef, float magicDef, float explosionDef) {
		this.weight = weight;
		this.physicalDef = physicalDef;
		this.projectileDef = projectileDef;
		this.fireDef = fireDef;
		this.magicDef = magicDef;
		this.explosionDef = explosionDef;
	}

	public Values() {
		INSTANCE = this;
		MATERIAL_MAP.put(Materials._NULL, new Values(Materials._NULL, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f));
		MATERIAL_MAP.put(Materials.Rubber, new Values(Materials.Rubber, 60, 0.06f, 0.06f, 0.02f, 0.1f, 0.1f));
		MATERIAL_MAP.put(Materials.Wood, new Values(Materials.Wood, 80, 0.08f, 0.09f, 0.02f, 0.08f, 0.08f));
		MATERIAL_MAP.put(Materials.Brass, new Values(Materials.Brass, 140, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f));
		MATERIAL_MAP.put(Materials.Copper, new Values(Materials.Copper, 140, 0.11f, 0.11f, 0.10f, 0.10f, 0.11f));
		MATERIAL_MAP.put(Materials.Lead, new Values(Materials.Lead, 280, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f));
		MATERIAL_MAP.put(Materials.Plastic, new Values(Materials.Plastic, 60, 0.10f, 0.10f, 0.02f, 0.02f, 0.10f));
		MATERIAL_MAP.put(Materials.Aluminium, new Values(Materials.Aluminium, 120, 0.14f, 0.14f, 0.12f, 0.12f, 0.14f));
		MATERIAL_MAP.put(Materials.AstralSilver, new Values(Materials.AstralSilver, 180, 0.10f, 0.10f, 0.10f, 0.18f, 0.10f));
		MATERIAL_MAP.put(Materials.BismuthBronze, new Values(Materials.BismuthBronze, 160, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f));
		MATERIAL_MAP.put(Materials.BlackBronze, new Values(Materials.BlackBronze, 160, 0.13f, 0.13f, 0.10f, 0.10f, 0.13f));
		MATERIAL_MAP.put(Materials.BlackSteel, new Values(Materials.BlackSteel, 200, 0.19f, 0.19f, 0.17f, 0.17f, 0.19f));
		MATERIAL_MAP.put(Materials.BlueSteel, new Values(Materials.BlueSteel, 200, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f));
		MATERIAL_MAP.put(Materials.Bronze, new Values(Materials.Bronze, 160, 0.13f, 0.13f, 0.12f, 0.12f, 0.13f));
		MATERIAL_MAP.put(Materials.CobaltBrass, new Values(Materials.CobaltBrass, 180, 0.15f, 0.15f, 0.14f, 0.14f, 0.15f));
		MATERIAL_MAP.put(Materials.DamascusSteel, new Values(Materials.DamascusSteel, 200, 0.22f, 0.22f, 0.20f, 0.20f, 0.22f));
		MATERIAL_MAP.put(Materials.Electrum, new Values(Materials.Electrum, 250, 0.11f, 0.11f, 0.10f, 0.10f, 0.11f));
		MATERIAL_MAP.put(Materials.Emerald, new Values(Materials.Emerald, 160, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Gold, new Values(Materials.Gold, 300, 0.09f, 0.09f, 0.05f, 0.25f, 0.09f));
		MATERIAL_MAP.put(Materials.GreenSapphire, new Values(Materials.GreenSapphire, 160, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Invar, new Values(Materials.Invar, 190, 0.10f, 0.10f, 0.22f, 0.22f, 0.10f));
		MATERIAL_MAP.put(Materials.Iron, new Values(Materials.Iron, 200, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f));
		MATERIAL_MAP.put(Materials.IronWood, new Values(Materials.IronWood, 150, 0.17f, 0.17f, 0.02f, 0.02f, 0.17f));
		MATERIAL_MAP.put(Materials.Magnalium, new Values(Materials.Magnalium, 120, 0.15f, 0.15f, 0.17f, 0.17f, 0.15f));
		MATERIAL_MAP.put(Materials.NeodymiumMagnetic, new Values(Materials.NeodymiumMagnetic, 220, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f));
		MATERIAL_MAP.put(Materials.Manganese, new Values(Materials.Manganese, 180, 0.15f, 0.15f, 0.14f, 0.14f, 0.15f));
		MATERIAL_MAP.put(Materials.MeteoricIron, new Values(Materials.MeteoricIron, 200, 0.18f, 0.18f, 0.16f, 0.16f, 0.18f));
		MATERIAL_MAP.put(Materials.MeteoricSteel, new Values(Materials.MeteoricSteel, 200, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f));
		MATERIAL_MAP.put(Materials.Molybdenum, new Values(Materials.Molybdenum, 140, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f));
		MATERIAL_MAP.put(Materials.Nickel, new Values(Materials.Nickel, 180, 0.12f, 0.12f, 0.15f, 0.15f, 0.12f));
		MATERIAL_MAP.put(Materials.Olivine, new Values(Materials.Olivine, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Opal, new Values(Materials.Opal, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Palladium, new Values(Materials.Palladium, 280, 0.14f, 0.14f, 0.12f, 0.12f, 0.14f));
		MATERIAL_MAP.put(Materials.Platinum, new Values(Materials.Platinum, 290, 0.15f, 0.15f, 0.13f, 0.13f, 0.15f));
		MATERIAL_MAP.put(Materials.GarnetRed, new Values(Materials.GarnetRed, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.RedSteel, new Values(Materials.RedSteel, 200, 0.20f, 0.20f, 0.18f, 0.18f, 0.20f));
		MATERIAL_MAP.put(Materials.RoseGold, new Values(Materials.RoseGold, 240, 0.10f, 0.10f, 0.08f, 0.18f, 0.10f));
		MATERIAL_MAP.put(Materials.Ruby, new Values(Materials.Ruby, 180, 0.10f, 0.10f, 0.20f, 0.20f, 0.10f));
		MATERIAL_MAP.put(Materials.Sapphire, new Values(Materials.Sapphire, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Silver, new Values(Materials.Silver, 220, 0.11f, 0.11f, 0.07f, 0.24f, 0.11f));
		MATERIAL_MAP.put(Materials.StainlessSteel, new Values(Materials.StainlessSteel, 200, 0.16f, 0.16f, 0.21f, 0.21f, 0.16f));
		MATERIAL_MAP.put(Materials.Steel, new Values(Materials.Steel, 200, 0.18f, 0.18f, 0.16f, 0.16f, 0.18f));
		MATERIAL_MAP.put(Materials.SterlingSilver, new Values(Materials.SterlingSilver, 210, 0.15f, 0.15f, 0.13f, 0.13f, 0.15f));
		MATERIAL_MAP.put(Materials.Tanzanite, new Values(Materials.Tanzanite, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Thorium, new Values(Materials.Thorium, 280, 0.13f, 0.13f, 0.16f, 0.16f, 0.13f));
		MATERIAL_MAP.put(Materials.WroughtIron, new Values(Materials.WroughtIron, 200, 0.14f, 0.14f, 0.12f, 0.12f, 0.14f));
		MATERIAL_MAP.put(Materials.GarnetYellow, new Values(Materials.GarnetYellow, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Carbon, new Values(Materials.Carbon, 60, 0.06f, 0.23f, 0.05f, 0.05f, 0.06f));
		MATERIAL_MAP.put(Materials.InfusedAir,new Values(Materials.InfusedAir, 10, 0.10f, 0.10f, 0.10f,0.10f, 0.10f));
		MATERIAL_MAP.put(Materials.Amethyst, new Values(Materials.Amethyst, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.InfusedWater,new Values(Materials.InfusedWater, 150, 0.10f, 0.10f,0.20f, 0.20f, 0.10f));
		MATERIAL_MAP.put(Materials.BlueTopaz, new Values(Materials.BlueTopaz, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Chrome, new Values(Materials.Chrome, 200, 0.12f, 0.12f, 0.21f, 0.21f, 0.12f));
		MATERIAL_MAP.put(Materials.Cobalt, new Values(Materials.Cobalt, 220, 0.16f, 0.16f, 0.14f, 0.14f, 0.16f));
		MATERIAL_MAP.put(Materials.DarkIron, new Values(Materials.DarkIron, 200, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f));
		MATERIAL_MAP.put(Materials.Diamond, new Values(Materials.Diamond, 200, 0.20f, 0.20f, 0.22f, 0.22f, 0.20f));
		MATERIAL_MAP.put(Materials.Enderium, new Values(Materials.Enderium, 250, 0.22f, 0.22f, 0.21f, 0.21f, 0.22f));
		MATERIAL_MAP.put(Materials.ElectrumFlux,new Values(Materials.ElectrumFlux, 180, 0.19f, 0.19f, 0.16f, 0.16f, 0.19f));
		MATERIAL_MAP.put(Materials.Force, new Values(Materials.Force, 180, 0.10f, 0.10f, 0.20f, 0.20f, 0.10f));
		MATERIAL_MAP.put(Materials.HSLA, new Values(Materials.HSLA, 200, 0.21f, 0.21f, 0.17f, 0.17f, 0.21f));
		MATERIAL_MAP.put(Materials.InfusedFire,new Values(Materials.InfusedFire, 150, 0.12f, 0.10f, 0.30f, 0.30f, 0.12f));
		MATERIAL_MAP.put(Materials.InfusedGold, new Values(Materials.InfusedGold, 300, 0.15f, 0.15f, 0.05f, 0.05f, 0.15f));
		MATERIAL_MAP.put(Materials.Mithril, new Values(Materials.Mithril, 200, 0.25f, 0.25f, 0.10f, 0.30f, 0.25f));
		MATERIAL_MAP.put(Materials.InfusedOrder,new Values(Materials.InfusedOrder, 150, 0.18f, 0.22f,0.22f, 0.25f, 0.22f));
		MATERIAL_MAP.put(Materials.Steeleaf, new Values(Materials.Steeleaf, 120, 0.16f, 0.16f, 0.06f, 0.06f, 0.16f));
		MATERIAL_MAP.put(Materials.InfusedEarth,new Values(Materials.InfusedEarth, 350, 0.30f, 0.30f,0.30f, 0.30f, 0.30f));
		MATERIAL_MAP.put(Materials.Thaumium, new Values(Materials.Thaumium, 200, 0.18f, 0.19f, 0.20f, 0.30f, 0.18f));
		MATERIAL_MAP.put(Materials.Titanium, new Values(Materials.Titanium, 140, 0.20f, 0.20f, 0.18f, 0.18f, 0.20f));
		MATERIAL_MAP.put(Materials.Tungsten, new Values(Materials.Tungsten, 270, 0.27f, 0.26f, 0.23f, 0.26f, 0.26f));
		MATERIAL_MAP.put(Materials.Topaz, new Values(Materials.Topaz, 180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f));
		MATERIAL_MAP.put(Materials.Ultimet, new Values(Materials.Ultimet, 180, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f));
		MATERIAL_MAP.put(Materials.Uranium, new Values(Materials.Uranium, 290, 0.27f, 0.23f, 0.20f, 0.15f, 0.21f));
		MATERIAL_MAP.put(Materials.Vinteum, new Values(Materials.Vinteum, 180, 0.10f, 0.12f, 0.14f, 0.28f, 0.12f));
		MATERIAL_MAP.put(Materials.Duranium, new Values(Materials.Duranium, 140, 0.24f, 0.24f, 0.24f, 0.24f, 0.24f));
		MATERIAL_MAP.put(Materials.Iridium, new Values(Materials.Iridium, 220, 0.24f, 0.24f, 0.22f, 0.22f, 0.24f));
		MATERIAL_MAP.put(Materials.Osmiridium, new Values(Materials.Osmiridium, 240, 0.18f, 0.18f, 0.16f, 0.16f, 0.18f));
		MATERIAL_MAP.put(Materials.Osmium, new Values(Materials.Osmium, 250, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f));
		MATERIAL_MAP.put(Materials.Naquadah, new Values(Materials.Naquadah, 250, 0.27f, 0.27f, 0.25f, 0.25f, 0.27f));
		MATERIAL_MAP.put(Materials.NetherStar, new Values(Materials.NetherStar, 140, 0.22f, 0.22f, 0.24f, 0.24f, 0.22f));
		MATERIAL_MAP.put(Materials.InfusedEntropy,new Values(Materials.InfusedEntropy, 150, 0.10f, 0.10f,0.10f, 0.10f, 0.10f));
		MATERIAL_MAP.put(Materials.Tritanium, new Values(Materials.Tritanium, 140, 0.26f, 0.26f, 0.26f, 0.26f, 0.26f));
		MATERIAL_MAP.put(Materials.TungstenSteel, new Values(Materials.TungstenSteel, 270, 0.30f, 0.28f, 0.25f, 0.28f, 0.30f));
		MATERIAL_MAP.put(Materials.Adamantium, new Values(Materials.Adamantium, 200, 0.28f, 0.28f, 0.26f, 0.30f, 0.30f));
		MATERIAL_MAP.put(Materials.NaquadahAlloy, new Values(Materials.NaquadahAlloy, 300, 0.33f, 0.33f, 0.33f, 0.33f, 0.33f));
		MATERIAL_MAP.put(Materials.Neutronium, new Values(Materials.Neutronium, 600, 0.50f, 0.50f, 0.50f, 0.50f, 0.50f));
	}

	public Values getValues(Materials mat) {
		Values tmp = MATERIAL_MAP.get(mat);

		if (tmp == null) {
			return MATERIAL_MAP.get(Materials._NULL);
		}
		return tmp;
	}
}
