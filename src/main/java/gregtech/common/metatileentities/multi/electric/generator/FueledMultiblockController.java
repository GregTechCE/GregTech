package gregtech.common.metatileentities.multi.electric.generator;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IThrottle;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.FuelRecipeLogic;
import gregtech.api.gui.Widget.ClickData;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.api.render.Textures;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import static gregtech.api.gui.widgets.AdvancedTextWidget.withButton;
import static gregtech.api.gui.widgets.AdvancedTextWidget.withHoverTextTranslate;

import java.util.List;
import java.util.Map;

public abstract class FueledMultiblockController extends MultiblockWithDisplayBase implements IThrottle {

    protected final FuelRecipeMap recipeMap;
    protected FuelRecipeLogic workableHandler;
    protected IEnergyContainer energyContainer;
    protected IMultipleTankHandler importFluidHandler;
    protected int throttlePercentage = 100;

    public FueledMultiblockController(ResourceLocation metaTileEntityId, FuelRecipeMap recipeMap, long maxVoltage) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.workableHandler = createWorkable(maxVoltage);
        this.workableHandler.setThrottle(this);
    }

    protected FuelRecipeLogic createWorkable(long maxVoltage) {
        return new FuelRecipeLogic(this, recipeMap,
            () -> energyContainer,
            () -> importFluidHandler, maxVoltage);
    }

    @Override
    public int getThrottlePercentage() {
        return this.throttlePercentage;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            ITextComponent throttleText = new TextComponentTranslation("gregtech.multiblock.large_boiler.throttle", this.throttlePercentage, 100);
            withHoverTextTranslate(throttleText, "gregtech.multiblock.large_boiler.throttle.tooltip");
            textList.add(throttleText);
            ITextComponent buttonText = new TextComponentTranslation("gregtech.multiblock.large_boiler.throttle_modify");
            buttonText.appendText(" ");
            buttonText.appendSibling(withButton(new TextComponentString("[-]"), "sub"));
            buttonText.appendText(" ");
            buttonText.appendSibling(withButton(new TextComponentString("[+]"), "add"));
            textList.add(buttonText);
            if (!workableHandler.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            } else if (workableHandler.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                textList.add(new TextComponentTranslation("gregtech.multiblock.generation_eu", workableHandler.getRecipeOutputVoltage()));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
                textList.add(new TextComponentTranslation("gregtech.multiblock.generation_eu", this.workableHandler.getRecipeOutputVoltage()));
            }
        }
    }

    @Override
    protected void handleDisplayClick(String componentData, ClickData clickData) {
        super.handleDisplayClick(componentData, clickData);
        int modifier = componentData.equals("add") ? 1 : -1;
        int result = (clickData.isShiftClick ? 1 : 5) * modifier;
        this.throttlePercentage = MathHelper.clamp(this.throttlePercentage + result, 20, 100);
        markDirty();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    private void initializeAbilities() {
        this.importFluidHandler = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.importFluidHandler = null;
        this.energyContainer = null;
    }

    @Override
    protected void updateFormedValid() {
        this.workableHandler.update();
    }

    @Override
    protected boolean shouldUpdate(MTETrait trait) {
        return !(trait instanceof FuelRecipeLogic);
    }

    @Override
    protected boolean checkStructureComponents(List<IMultiblockPart> parts, Map<MultiblockAbility<Object>, List<Object>> abilities) {
        //noinspection SuspiciousMethodCalls
        return abilities.containsKey(MultiblockAbility.IMPORT_FLUIDS) &&
            abilities.containsKey(MultiblockAbility.OUTPUT_ENERGY);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(),
            isStructureFormed() && workableHandler.isActive());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("ThrottlePercentage", this.throttlePercentage);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if(data.hasKey("ThrottlePercentage")) {
            this.throttlePercentage = data.getInteger("ThrottlePercentage");
        }
    }
}
