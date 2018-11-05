package gregtech.loaders.recipe;

import gregtech.api.recipes.builders.AssemblyLineRecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.L;

public class AssemblyLineRecipeLoader {

    public static void registerAssemblyLineRecipes() {
        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_MOTOR_IV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 1),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 2),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(L), Materials.Lubricant.getFluid(250))
            .output(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 2),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 16),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L),
                Materials.Lubricant.getFluid(750))
            .output(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(OreDictUnifier.get(OrePrefix.block, Materials.NeodymiumMagnetic, 1),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 16),
                OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
                OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
                OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
                OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(9 * L),
                Materials.Lubricant.getFluid(2000))
            .output(MetaItems.ELECTRIC_MOTOR_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PUMP_IV.getStackForm())
            .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(),
                OreDictUnifier.get(OrePrefix.pipeSmall, MarkerMaterials.Tier.Ultimate, 2),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 8),
                OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 4),
                OreDictUnifier.get(OrePrefix.rotor, Materials.HSSG, 2),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(L), Materials.Lubricant.getFluid(250))
            .output(MetaItems.ELECTRIC_PUMP_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PUMP_LUV.getStackForm())
            .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(),
                OreDictUnifier.get(OrePrefix.pipeMedium, MarkerMaterials.Tier.Ultimate, 2),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 8),
                OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16),
                OreDictUnifier.get(OrePrefix.rotor, Materials.HSSE, 2),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L),
                Materials.Lubricant.getFluid(750))
            .output(MetaItems.ELECTRIC_PUMP_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PUMP_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm(),
                OreDictUnifier.get(OrePrefix.pipeLarge, MarkerMaterials.Tier.Ultimate, 2),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 2),
                OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 8),
                OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16),
                OreDictUnifier.get(OrePrefix.rotor, Materials.Darmstadtium, 2),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(1296),
                Materials.Lubricant.getFluid(2000))
            .output(MetaItems.ELECTRIC_PUMP_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.CONVEYOR_MODULE_IV.getStackForm())
            .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(2),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 32),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
            .fluidInputs(Materials.Rubber.getFluid(10 * L),
                Materials.Lubricant.getFluid(250))
            .output(MetaItems.CONVEYOR_MODULE_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.CONVEYOR_MODULE_LUV.getStackForm())
            .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(2),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 32),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 2))
            .fluidInputs(Materials.Rubber.getFluid(L * 20),
                Materials.Lubricant.getFluid(750))
            .output(MetaItems.CONVEYOR_MODULE_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.CONVEYOR_MODULE_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 2),
                OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 32),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
            .fluidInputs(Materials.Rubber.getFluid(20 * L),
                Materials.Lubricant.getFluid(2000))
            .output(MetaItems.CONVEYOR_MODULE_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PISTON_IV.getStackForm())
            .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 32),
                OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 2),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 4))
            .fluidInputs(Materials.SolderingAlloy.getFluid(L),
                Materials.Lubricant.getFluid(250))
            .output( MetaItems.ELECTRIC_PISTON_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PISTON_LUV.getStackForm())
            .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 32),
                OreDictUnifier.get(OrePrefix.stick, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 2),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 4))
            .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L),
                Materials.Lubricant.getFluid(750))
            .output(MetaItems.ELECTRIC_PISTON_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PISTON_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm(),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 6),
                OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 32),
                OreDictUnifier.get(OrePrefix.stick, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.Darmstadtium, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.Darmstadtium, 2),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 4))
            .fluidInputs(Materials.SolderingAlloy.getFluid(9 * L),
                Materials.Lubricant.getFluid(2000))
            .output(MetaItems.ELECTRIC_PISTON_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ROBOT_ARM_IV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 3),
                MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(2),
                MetaItems.ELECTRIC_PISTON_LUV.getStackForm(),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 2),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 2),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 6),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 6))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L),
                Materials.Lubricant.getFluid(250))
            .output(MetaItems.ROBOT_ARM_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ROBOT_ARM_LUV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 3),
                MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(2),
                MetaItems.ELECTRIC_PISTON_ZPM.getStackForm(),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 12),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 6))
            .fluidInputs(Materials.SolderingAlloy.getFluid(8 * L),
                Materials.Lubricant.getFluid(750))
            .output(MetaItems.ROBOT_ARM_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ROBOT_ARM_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.Darmstadtium, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.Darmstadtium, 3),
                MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
                MetaItems.ELECTRIC_PISTON_UV.getStackForm(),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 8),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 8),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 24),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 6))
            .fluidInputs(Materials.SolderingAlloy.getFluid(16 * L),
                Materials.Lubricant.getFluid(2000))
            .output(MetaItems.ROBOT_ARM_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.EMITTER_IV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                MetaItems.EMITTER_IV.getStackForm(),
                MetaItems.EMITTER_EV.getStackForm(2),
                MetaItems.EMITTER_HV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 7),
                OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(576))
            .output(MetaItems.EMITTER_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.EMITTER_LUV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                MetaItems.EMITTER_LUV.getStackForm(),
                MetaItems.EMITTER_IV.getStackForm(2),
                MetaItems.EMITTER_EV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 7),
                OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .output(MetaItems.EMITTER_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.EMITTER_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
                MetaItems.EMITTER_ZPM.getStackForm(),
                MetaItems.EMITTER_LUV.getStackForm(2),
                MetaItems.EMITTER_IV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 7),
                OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .output(MetaItems.EMITTER_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.SENSOR_IV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                MetaItems.SENSOR_IV.getStackForm(),
                MetaItems.SENSOR_EV.getStackForm(2),
                MetaItems.SENSOR_HV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 7),
                OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .output(MetaItems.SENSOR_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.SENSOR_LUV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                MetaItems.SENSOR_LUV.getStackForm(),
                MetaItems.SENSOR_IV.getStackForm(2),
                MetaItems.SENSOR_EV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 7),
                OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .output(MetaItems.SENSOR_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.SENSOR_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
                MetaItems.SENSOR_ZPM.getStackForm(),
                MetaItems.SENSOR_LUV.getStackForm(2),
                MetaItems.SENSOR_IV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 7),
                OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .output(MetaItems.SENSOR_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.FIELD_GENERATOR_IV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
                MetaItems.QUANTUM_STAR.getStackForm(),
                MetaItems.EMITTER_LUV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 8),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 8))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .output(MetaItems.FIELD_GENERATOR_LUV.getStackForm())
            .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.FIELD_GENERATOR_LUV.getStackForm())
            .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
                MetaItems.QUANTUM_STAR.getStackForm(4),
                MetaItems.EMITTER_ZPM.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 16),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 8))
            .fluidInputs(Materials.SolderingAlloy.getFluid(8 * L))
            .output(MetaItems.FIELD_GENERATOR_ZPM.getStackForm())
            .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.FIELD_GENERATOR_ZPM.getStackForm())
            .researchTime(L * 2000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 6),
                MetaItems.GRAVI_STAR.getStackForm(),
                MetaItems.EMITTER_UV.getStackForm(4),
                OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 8))
            .fluidInputs(Materials.SolderingAlloy.getFluid(16 * L))
            .output(MetaItems.FIELD_GENERATOR_UV.getStackForm())
            .duration(600)
            .EUt(100000)
            .buildAndRegister();
    }

}
