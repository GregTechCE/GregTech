package gregtech.common.pipelike.cables;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.pipelike.BlockPipeLike;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;

import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class CableFactory extends PipeFactory<Insulation, WireProperties, IEnergyContainer> {

    public static final CableFactory INSTANCE = new CableFactory();

    private CableFactory() {
        super("cable", IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, Insulation.class, WireProperties.class);
        registerDefaultCables();
    }

    private void registerDefaultCables() {
        registerCable(RedAlloy, V[0], 1, 0);

        registerCable(Cobalt, V[1], 2, 2);
        registerCable(Lead, V[1], 2, 2);
        registerCable(Tin, V[1], 1, 1);
        registerCable(Zinc, V[1], 1, 1);
        registerCable(SolderingAlloy, V[1], 1, 1);

        registerCable(Iron, V[2], 2, 3);
        registerCable(Nickel, V[2], 3, 3);
        registerCable(Cupronickel, V[2], 2, 3);
        registerCable(Copper, V[2], 1, 2);
        registerCable(AnnealedCopper, V[2], 1, 1);

        registerCable(Kanthal, V[3], 4, 3);
        registerCable(Gold, V[3], 3, 2);
        registerCable(Electrum, V[3], 2, 2);
        registerCable(Silver, V[3], 1, 1);

        registerCable(Nichrome, V[4], 3, 4);
        registerCable(Steel, V[4], 2, 2);
        registerCable(BlackSteel, V[4], 3, 2);
        registerCable(Titanium, V[4], 4, 2);
        registerCable(Aluminium, V[4], 1, 1);

        registerCable(Graphene, V[5], 1, 1);
        setNotInsulable(Graphene);
        registerCable(Osmium, V[5], 4, 2);
        registerCable(Platinum, V[5], 2, 1);
        registerCable(TungstenSteel, V[5], 3, 2);
        registerCable(Tungsten, V[5], 2, 2);

        registerCable(HSSG, V[6], 4, 2);
        registerCable(NiobiumTitanium, V[6], 4, 2);
        registerCable(VanadiumGallium, V[6], 4, 2);
        registerCable(YttriumBariumCuprate, V[6], 4, 4);

        registerCable(Naquadah, V[7], 2, 4);
        registerCable(NaquadahEnriched, V[7], 4, 2);

        registerCable(NaquadahAlloy, V[8], 2, 4);
        registerCable(Duranium, V[8], 1, 8);

        registerCable(MarkerMaterials.Tier.Superconductor, Integer.MAX_VALUE, 4, 0);
        setNotInsulable(MarkerMaterials.Tier.Superconductor);
        specifyMaterialColor(MarkerMaterials.Tier.Superconductor, 0xDCFAFA);
    }

    public void registerCable(Material material, long voltage, int baseAmperage, int lossPerBlock) {
        registerPropertyForMaterial(material, new WireProperties((int) voltage, baseAmperage, lossPerBlock));
    }

    public void setNotInsulable(Material material) {
        cableGtSingle.setIgnored(material);
        cableGtDouble.setIgnored(material);
        cableGtQuadruple.setIgnored(material);
        cableGtOctal.setIgnored(material);
        cableGtHex.setIgnored(material);
    }

    public void setMustInsulated(Material material) {
        wireGtSingle.setIgnored(material);
        wireGtDouble.setIgnored(material);
        wireGtQuadruple.setIgnored(material);
        wireGtOctal.setIgnored(material);
        wireGtHex.setIgnored(material);
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
        //TODO electric shock
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
