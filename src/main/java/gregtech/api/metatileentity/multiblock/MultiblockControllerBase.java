package gregtech.api.metatileentity.multiblock;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockPattern.PatternHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiblockControllerBase extends MetaTileEntity {

    private final List<IFluidTank> importFluidTanks = new ArrayList<>();
    private final List<IFluidTank> exportFluidTanks = new ArrayList<>();
    private final List<IItemHandlerModifiable> importItemInventories = new ArrayList<>();
    private final List<IItemHandlerModifiable> exportItemInventories = new ArrayList<>();
    private final List<IMultiblockPart> multiblockParts = new ArrayList<>();
    private boolean structureFormed;
    private BlockPattern structurePattern;

    public void invalidateCaches() {

    }

    public MultiblockControllerBase(String metaTileEntityId) {
        super(metaTileEntityId);
        this.structurePattern = createStructurePattern();
    }

    @Override
    public void update() {
        super.update();
    }

    protected PatternHelper matchPattern() {
        BlockPos selfPos = getPos().add(getCenterOffset());
        return structurePattern.match(getWorld(), selfPos);
    }

    protected abstract Vec3i getCenterOffset();
    protected abstract BlockPattern createStructurePattern();

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

}
