package gregtech.api.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.BlockWorldState;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.render.ICubeRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public abstract class MultiblockControllerBase extends MetaTileEntity {

    protected final BlockPattern structurePattern;
    protected final BooleanSupplier validationPredicate;

    private final Map<MultiblockAbility<Object>, List<Object>> multiblockAbilities = new HashMap<>();
    private final List<IMultiblockPart> multiblockParts = new ArrayList<>();
    private boolean structureFormed;

    public MultiblockControllerBase(String metaTileEntityId) {
        super(metaTileEntityId);
        this.structurePattern = createStructurePattern();
        this.validationPredicate = getValidationPredicate();
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            if(getTimer() % 20 == 0) {
                checkStructurePattern();
            }
            if(structureFormed && validationPredicate.getAsBoolean()) {
                updateFormedValid();
            }
        }
    }

    /**
     * Called when the multiblock is formed and validation predicate is matched
     */
    protected abstract void updateFormedValid();

    /**
     * @return offset vector in format {back, up, left}
     */
    protected abstract Vec3d getCenterOffset();

    /**
     * @return structure pattern of this multiblock
     */
    protected abstract BlockPattern createStructurePattern();

    public abstract ICubeRenderer getBaseTexture();

    protected BooleanSupplier getValidationPredicate() {
        return () -> true;
    }

    public static Predicate<BlockWorldState> tilePredicate(BiFunction<BlockWorldState, MetaTileEntity, Boolean> predicate) {
        return blockWorldState -> {
            TileEntity tileEntity = blockWorldState.getTileEntity();
            if(!(tileEntity instanceof MetaTileEntityHolder))
                return false;
            MetaTileEntity metaTileEntity = ((MetaTileEntityHolder) tileEntity).getMetaTileEntity();
            if(predicate.apply(blockWorldState, metaTileEntity)) {
                if(metaTileEntity instanceof IMultiblockPart) {
                    List<IMultiblockPart> partsFound = blockWorldState.getMatchContext().get("MultiblockParts", ArrayList::new);
                    partsFound.add((IMultiblockPart) metaTileEntity);
                }
                return true;
            }
            return false;
        };
    }

    public static Predicate<BlockWorldState> abilityPartPredicate(MultiblockAbility<?>... allowedAbilities) {
        return tilePredicate((state, tile) -> tile instanceof IMultiblockAbilityPart<?> &&
            ArrayUtils.contains(allowedAbilities, ((IMultiblockAbilityPart<?>) tile).getAbility()));
    }

    public static Predicate<BlockWorldState> partPredicate(Class<? extends IMultiblockPart> baseClass) {
        return tilePredicate((state, tile) -> tile instanceof IMultiblockPart && baseClass.isAssignableFrom(tile.getClass()));
    }

    public static Predicate<BlockWorldState> statePredicate(IBlockState... allowedStates) {
        return blockWorldState -> ArrayUtils.contains(allowedStates, blockWorldState.getBlockState());
    }

    public static Predicate<BlockWorldState> blockPredicate(Block... block) {
        return blockWorldState -> ArrayUtils.contains(block, blockWorldState.getBlockState().getBlock());
    }

    public Predicate<BlockWorldState> selfPredicate() {
        return tilePredicate((state, tile) -> tile.metaTileEntityId.equals(metaTileEntityId));
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        getBaseTexture().render(renderState, pipeline);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getBaseTexture().getParticleSprite();
    }

    protected void checkStructurePattern() {
        EnumFacing facing = getFrontFacing().getOpposite();
        Vec3d offset = getCenterOffset();
        int floorY = (int) (getPos().getY() + offset.y);
        //don't even ask why 0.5 is needed on z and not needed on x. i don't fucking know.
        double centerX = getPos().getX() + facing.getFrontOffsetX() * offset.x + (1 - facing.getFrontOffsetX()) * offset.z;
        double centerZ = getPos().getZ() + 0.5 + facing.getFrontOffsetZ() * offset.x + (1 - facing.getFrontOffsetZ()) * offset.z;
        PatternMatchContext context = structurePattern.checkPatternAt(getWorld(), centerX, floorY, centerZ, facing);
        if(context != null && !structureFormed) {
            List<IMultiblockPart> partsFound = context.get("MultiblockParts", ArrayList::new);
            this.multiblockParts.addAll(partsFound);
            multiblockParts.forEach(part -> {
                part.addToMultiBlock(this);
                if(part instanceof IMultiblockAbilityPart<?>) {
                    IMultiblockAbilityPart<Object> abilityPart = (IMultiblockAbilityPart<Object>) part;
                    MultiblockAbility<Object> ability = abilityPart.getAbility();
                    List<Object> abilityList = multiblockAbilities.computeIfAbsent(ability, k -> new ArrayList<>());
                    abilityPart.registerAbilities(abilityList);
                }
            });
            this.structureFormed = true;
            formStructure(context);
        } else if(context == null && structureFormed) {
            invalidateStructure();
        }
    }

    protected void formStructure(PatternMatchContext context) {
    }

    public void invalidateStructure() {
        this.multiblockParts.forEach(part -> part.removeFromMultiblock(this));
        this.multiblockAbilities.clear();
        this.multiblockParts.clear();
        this.structureFormed = false;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAbilities(MultiblockAbility<T> ability) {
        List<T> rawList = (List<T>) multiblockAbilities.getOrDefault(ability, Collections.emptyList());
        return Collections.unmodifiableList(rawList);
    }

    public List<IMultiblockPart> getMultiblockParts() {
        return Collections.unmodifiableList(multiblockParts);
    }

    public boolean isStructureFormed() {
        return structureFormed;
    }

}
