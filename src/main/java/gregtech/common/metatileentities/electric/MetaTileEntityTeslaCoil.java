package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.util.GregFakePlayer;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class MetaTileEntityTeslaCoil extends MetaTileEntity {

    private static final long ENERGY_PER_ONE_HEALTH_POINT_HIT = 2500L;

    private IEnergyContainer energyContainer;

    public MetaTileEntityTeslaCoil(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.energyContainer = new EnergyContainerHandler(this,
            256000L, Long.MAX_VALUE, Long.MAX_VALUE, 0L, 0L);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityTeslaCoil(metaTileEntityId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        Textures.TESLA_COIL.render(renderState, translation, ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()))));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(Textures.TESLA_COIL.getParticleSprite(), getPaintingColor());
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && energyContainer.getEnergyStored() > 0L && getWorld().isBlockPowered(getPos()) && getOffsetTimer() % 20 == 0L) {
            double damageRadius = getDamageRadius();
            List<EntityLivingBase> entities = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPos()).grow(damageRadius));
            if (entities.isEmpty()) return; //no entities found, return
            long energyAmountPerEntity = energyContainer.getEnergyStored() / entities.size();
            int damagePerEntity = (int) (energyAmountPerEntity / ENERGY_PER_ONE_HEALTH_POINT_HIT);
            int damagedEntitiesCount = 0;
            if (damagePerEntity < 1) return; //too small amount of energy, just return
            for (EntityLivingBase entityLiving : entities) {
                DamageSource damageSource = DamageSources.causeElectricDamage(GregFakePlayer.get((WorldServer) getWorld()));
                boolean damaged = entityLiving.attackEntityFrom(damageSource, damagePerEntity);
                if (damaged) damagedEntitiesCount++;
            }
            if (damagedEntitiesCount > 0) {
                energyContainer.removeEnergy(energyAmountPerEntity * damagedEntitiesCount);
            }
        }
    }

    protected double getDamageRadius() {
        return 6.0;
    }

    @Override
    public boolean hasFrontFacing() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }
}
