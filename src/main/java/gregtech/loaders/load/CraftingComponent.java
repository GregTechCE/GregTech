package gregtech.loaders.load;

import gregtech.api.items.OreDictNames;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;

public enum CraftingComponent {
    CIRCUIT {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Primitive);
                case 1:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
                case 2:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good);
                case 3:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
                case 4:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
                case 5:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master);
                case 6:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
                default:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    BETTER_CIRCUIT {
        @Override
        Object getIngredient(int tier) {
            switch (tier + 1) {
                case 0:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Primitive);
                case 1:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
                case 2:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good);
                case 3:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
                case 4:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
                case 5:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master);
                case 6:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
                default:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    PUMP {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.ELECTRIC_PUMP_LV;
                case 2:
                    return MetaItems.ELECTRIC_PUMP_MV;
                case 3:
                    return MetaItems.ELECTRIC_PUMP_HV;
                case 4:
                    return MetaItems.ELECTRIC_PUMP_EV;
                case 5:
                    return MetaItems.ELECTRIC_PUMP_IV;
                case 6:
                    return MetaItems.ELECTRIC_PUMP_LUV;
                case 7:
                    return MetaItems.ELECTRIC_PUMP_ZPM;
                default:
                    return MetaItems.ELECTRIC_PUMP_UV;
            }
        }
    },
    CABLE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead);
                case 1:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin);
                case 2:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper);
                case 3:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold);
                case 4:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium);
                case 5:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Platinum);
                case 6:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.NiobiumTitanium);
                case 7:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah);
                case 8:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy);
                default:
                    return new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    WIRE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Gold);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Silver);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Electrum);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Platinum);
                default:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Osmium);
            }
        }
    },
    CABLE_QUAD {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Lead);
                case 1:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Tin);
                case 2:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Copper);
                case 3:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Gold);
                case 4:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Aluminium);
                case 5:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Platinum);
                case 6:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium);
                case 7:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Naquadah);
                case 8:
                    return new UnificationEntry(OrePrefix.cableGtSingle, MarkerMaterials.Tier.Superconductor);
                default:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    HULL {
        @Override
        Object getIngredient(int tier) {
            return MetaTileEntities.HULL[tier].getStackForm();
        }
    },
    WORSE_HULL {
        @Override
        Object getIngredient(int tier) {
            return MetaTileEntities.HULL[tier - 1].getStackForm();
        }
    },
    PIPE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze);
                case 2:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel);
                case 3:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel);
                case 4:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium);
                case 5:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel);
                default:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel);
            }
        }
    },
    GLASS {
        @Override
        Object getIngredient(int tier) {
            return new ItemStack(Blocks.GLASS, 1, W);
        }
    },
    PLATE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.plate, Materials.Steel);
                case 2:
                    return new UnificationEntry(OrePrefix.plate, Materials.Aluminium);
                case 3:
                    return new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel);
                case 4:
                    return new UnificationEntry(OrePrefix.plate, Materials.Titanium);
                case 5:
                    return new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel);
                case 6:
                    return new UnificationEntry(OrePrefix.plate, Materials.HSSG);
                case 7:
                    return new UnificationEntry(OrePrefix.plate, Materials.HSSE);
                case 8:
                    return new UnificationEntry(OrePrefix.plate, Materials.Darmstadtium);
                default:
                    return new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel);
            }
        }
    },
    MOTOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.ELECTRIC_MOTOR_LV;
                case 2:
                    return MetaItems.ELECTRIC_MOTOR_MV;
                case 3:
                    return MetaItems.ELECTRIC_MOTOR_HV;
                case 4:
                    return MetaItems.ELECTRIC_MOTOR_EV;
                case 5:
                    return MetaItems.ELECTRIC_MOTOR_IV;
                case 6:
                    return MetaItems.ELECTRIC_MOTOR_LUV;
                case 7:
                    return MetaItems.ELECTRIC_MOTOR_ZPM;
                default:
                    return MetaItems.ELECTRIC_MOTOR_UV;
            }
        }
    },
    ROTOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Tin);
                case 2:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Bronze);
                case 3:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Steel);
                case 4:
                    return new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel);
                case 5:
                    return new UnificationEntry(OrePrefix.rotor, Materials.TungstenSteel);
                case 6:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Chrome);
                case 7:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Iridium);
                default:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Osmium);
            }
        }
    },
    SENSOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.SENSOR_LV;
                case 2:
                    return MetaItems.SENSOR_MV;
                case 3:
                    return MetaItems.SENSOR_HV;
                case 4:
                    return MetaItems.SENSOR_EV;
                case 5:
                    return MetaItems.SENSOR_IV;
                case 6:
                    return MetaItems.SENSOR_LUV;
                case 7:
                    return MetaItems.SENSOR_ZPM;
                default:
                    return MetaItems.SENSOR_UV;
            }
        }
    },
    GRINDER {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.gem, Materials.Diamond);
                case 2:
                    return new UnificationEntry(OrePrefix.gem, Materials.Diamond);
                default:
                    return OreDictNames.craftingGrinder;
            }
        }
    },
    DIAMOND {
        @Override
        Object getIngredient(int tier) {
            return new UnificationEntry(OrePrefix.gem, Materials.Diamond);
        }
    },
    PISTON {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.ELECTRIC_PISTON_LV;
                case 2:
                    return MetaItems.ELECTRIC_PISTON_MV;
                case 3:
                    return MetaItems.ELECTRIC_PISTON_HV;
                case 4:
                    return MetaItems.ELECTRIC_PISTON_EV;
                case 5:
                    return MetaItems.ELECTRIC_PISTON_IV;
                case 6:
                    return MetaItems.ELECTRIC_PISTON_LUV;
                case 7:
                    return MetaItems.ELECTRIC_PISTON_ZPM;
                default:
                    return MetaItems.ELECTRIC_PISTON_UV;
            }
        }
    },
    EMITTER {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.EMITTER_LV;
                case 2:
                    return MetaItems.EMITTER_MV;
                case 3:
                    return MetaItems.EMITTER_HV;
                case 4:
                    return MetaItems.EMITTER_EV;
                case 5:
                    return MetaItems.EMITTER_IV;
                case 6:
                    return MetaItems.EMITTER_LUV;
                case 7:
                    return MetaItems.EMITTER_ZPM;
                default:
                    return MetaItems.EMITTER_UV;
            }
        }
    },
    CONVEYOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.CONVEYOR_MODULE_LV;
                case 2:
                    return MetaItems.CONVEYOR_MODULE_MV;
                case 3:
                    return MetaItems.CONVEYOR_MODULE_HV;
                case 4:
                    return MetaItems.CONVEYOR_MODULE_EV;
                case 5:
                    return MetaItems.CONVEYOR_MODULE_IV;
                case 6:
                    return MetaItems.CONVEYOR_MODULE_LUV;
                case 7:
                    return MetaItems.CONVEYOR_MODULE_ZPM;
                default:
                    return MetaItems.CONVEYOR_MODULE_UV;
            }
        }
    },
    ROBOT_ARM {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.ROBOT_ARM_LV;
                case 2:
                    return MetaItems.ROBOT_ARM_MV;
                case 3:
                    return MetaItems.ROBOT_ARM_HV;
                case 4:
                    return MetaItems.ROBOT_ARM_EV;
                case 5:
                    return MetaItems.ROBOT_ARM_IV;
                case 6:
                    return MetaItems.ROBOT_ARM_LUV;
                case 7:
                    return MetaItems.ROBOT_ARM_ZPM;
                default:
                    return MetaItems.ROBOT_ARM_UV;
            }
        }
    },
    COIL_HEATING {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Copper);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Cupronickel);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Kanthal);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Nichrome);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.TungstenSteel);
                case 6:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.HSSG);
                case 7:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Naquadah);
                case 8:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.NaquadahAlloy);
                default:
                    return new UnificationEntry(OrePrefix.wireGtOctal, Materials.Nichrome);
            }
        }
    },
    COIL_ELECTRIC {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin);
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Tin);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Copper);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Copper);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtOctal, Materials.AnnealedCopper);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtOctal, Materials.AnnealedCopper);
                case 6:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.YttriumBariumCuprate);
                case 7:
                    return new UnificationEntry(OrePrefix.wireGtOctal, MarkerMaterials.Tier.Superconductor);
                default:
                    return new UnificationEntry(OrePrefix.wireGtHex, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    STICK_MAGNETIC {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.stick, Materials.IronMagnetic);
                case 2:
                case 3:
                    return new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic);
                case 4:
                case 5:
                    return new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic);
                case 6:
                case 7:
                    return new UnificationEntry(OrePrefix.stickLong, Materials.NeodymiumMagnetic);
                default:
                    return new UnificationEntry(OrePrefix.block, Materials.NeodymiumMagnetic);
            }
        }
    },
    STICK_DISTILLATION {
        @Override
        Object getIngredient(int tier) {
            return new UnificationEntry(OrePrefix.stick, Materials.Blaze);
        }
    },
    FIELD_GENERATOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return MetaItems.FIELD_GENERATOR_LV;
                case 2:
                    return MetaItems.FIELD_GENERATOR_MV;
                case 3:
                    return MetaItems.FIELD_GENERATOR_HV;
                case 4:
                    return MetaItems.FIELD_GENERATOR_EV;
                case 5:
                    return MetaItems.FIELD_GENERATOR_IV;
                case 6:
                    return MetaItems.FIELD_GENERATOR_LUV;
                case 7:
                    return MetaItems.FIELD_GENERATOR_ZPM;
                default:
                    return MetaItems.FIELD_GENERATOR_UV;
            }
        }
    },
    COIL_HEATING_DOUBLE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Copper);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Cupronickel);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Kanthal);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Nichrome);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.TungstenSteel);
                case 6:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.HSSG);
                case 7:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Naquadah);
                case 8:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy);
                default:
                    return new UnificationEntry(OrePrefix.wireGtHex, Materials.Nichrome);
            }
        }
    },
    STICK_ELECTROMAGNETIC {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.stick, Materials.Iron);
                case 2:
                case 3:
                    return new UnificationEntry(OrePrefix.stick, Materials.Steel);
                case 4:
                    return new UnificationEntry(OrePrefix.stick, Materials.Neodymium);
                default:
                    return new UnificationEntry(OrePrefix.stick, Materials.VanadiumGallium);
            }
        }
    };

    abstract Object getIngredient(int tier);
}
