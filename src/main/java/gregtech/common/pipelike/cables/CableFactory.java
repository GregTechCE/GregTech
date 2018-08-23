package gregtech.common.pipelike.cables;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.pipelike.BlockPipeLike;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class CableFactory extends PipeFactory<Insulation, WireProperties, IEnergyContainer> {

    public static final CableFactory INSTANCE = new CableFactory();

    private CableFactory() {
        super("cable", IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, Insulation.class, WireProperties.class);
    }

    public static class CableRegistryEvent extends PipeRegistryEvent<Insulation, WireProperties> {
        CableRegistryEvent(CableFactory factory) {
            super(factory);
        }

        public void registerCable(Material material, long voltage, int baseAmperage, int lossPerBlock) {
            registerPropertyForMaterial(material, new WireProperties((int) voltage, baseAmperage, lossPerBlock));
        }

        public void setNotInsulable(Material material) {
            setIgnored(Insulation.CABLE_SINGLE, material);
            setIgnored(Insulation.CABLE_DOUBLE, material);
            setIgnored(Insulation.CABLE_QUADRUPLE, material);
            setIgnored(Insulation.CABLE_OCTAL, material);
            setIgnored(Insulation.CABLE_HEX, material);
        }
    }

    @Override
    protected PipeRegistryEvent<Insulation, WireProperties> getRegistryEvent() {
        return new CableRegistryEvent(this);
    }

    @Override
    protected void initBlock(BlockPipeLike<Insulation, WireProperties, IEnergyContainer> block, Material material, WireProperties materialProperty) {
        block.setCreativeTab(GregTechAPI.TAB_GREGTECH);
        block.setSoundType(SoundType.METAL);
        block.setHarvestLevel("cutter", material instanceof IngotMaterial ? ((IngotMaterial) material).harvestLevel : 1);
        block.setHardness(2.0f);
        block.setResistance(3.0f);
        block.setLightOpacity(1);
    }

    @Override
    protected WireProperties createActualProperty(Insulation baseProperty, WireProperties materialProperty) {
        return new WireProperties(materialProperty.getVoltage(),
            materialProperty.getAmperage() * baseProperty.amperage,
            materialProperty.getLossPerBlock() * baseProperty.lossMultiplier);
    }

    @Override
    protected void onEntityCollided(Entity entity, ITilePipeLike<Insulation, WireProperties> tile) {
        if (tile.getBaseProperty().insulationLevel < 0 && entity instanceof EntityLivingBase) {
            EnergyNet net = getPipeNetAt(tile);
            if (net != null) {
                long voltage = (long) net.getStatisticData(tile.getTilePos())[1];
                GTUtility.applyElectricDamage((EntityLivingBase) entity, voltage);
            }
        }
    }

    @Override
    public int getDefaultColor() {
        return 0x777777;
    }

    @Override
    public IEnergyContainer createCapability(ITilePipeLike<Insulation, WireProperties> tile) {
        return new CableEnergyContainer(tile);
    }

    @Override
    public PipeNet<Insulation, WireProperties, IEnergyContainer> createPipeNet(WorldPipeNet worldNet) {
        return new EnergyNet(worldNet);
    }

    @Override
    public EnergyNet getPipeNetAt(ITilePipeLike<Insulation, WireProperties> tile) {
        return (EnergyNet) super.getPipeNetAt(tile);
    }

    @Override
    public WireProperties createEmptyProperty() {
        return new WireProperties();
    }
}
