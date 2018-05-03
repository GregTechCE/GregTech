package gregtech.api.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.BlockWorldState;
import gregtech.api.multiblock.IPatternCenterPredicate;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.render.ICubeRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public abstract class MultiblockControllerBase extends MetaTileEntity {

    protected BlockPattern structurePattern;
    protected BooleanSupplier validationPredicate;

    private final Map<MultiblockAbility<Object>, List<Object>> multiblockAbilities = new HashMap<>();
    private final List<IMultiblockPart> multiblockParts = new ArrayList<>();
    private boolean structureFormed;
    private boolean validationSuccess;

    public MultiblockControllerBase(String metaTileEntityId) {
        super(metaTileEntityId);
        reinitializeStructurePattern();
    }

    protected void reinitializeStructurePattern() {
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
            if(isStructureFormed()) {
                updateFormedValid();
            }
        }
    }

    /**
     * Called when the multiblock is formed and validation predicate is matched
     */
    protected abstract void updateFormedValid();

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

    public IPatternCenterPredicate selfPredicate() {
        return IPatternCenterPredicate.wrap(tilePredicate((state, tile) -> tile.metaTileEntityId.equals(metaTileEntityId)));
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        getBaseTexture().render(renderState, translation, pipeline);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getBaseTexture().getParticleSprite();
    }

    protected void checkStructurePattern() {
        EnumFacing facing = getFrontFacing().getOpposite();
        PatternMatchContext context = structurePattern.checkPatternAt(getWorld(), getPos(), facing);
        if(context != null && structureFormed) {
            this.validationSuccess = validationPredicate.getAsBoolean();
        }
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
            this.validationSuccess = validationPredicate.getAsBoolean();
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
        this.validationSuccess = false;
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
        return structureFormed && validationSuccess;
    }

}
