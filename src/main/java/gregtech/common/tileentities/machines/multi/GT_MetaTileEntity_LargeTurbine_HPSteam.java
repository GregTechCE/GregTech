package gregtech.common.tileentities.machines.multi;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class GT_MetaTileEntity_LargeTurbine_HPSteam extends GT_MetaTileEntity_LargeTurbine {

    public boolean achievement = false;

    public GT_MetaTileEntity_LargeTurbine_HPSteam(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_LargeTurbine_HPSteam(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex + 1], aFacing == aSide ? aActive ? new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_TI_ACTIVE5) : new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_TI5) : Textures.BlockIcons.CASING_BLOCKS[59]};
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Large High Pressure Steam Turbine",
                "Size(WxHxD): 3x3x4 (Hollow), Controller (Front centered)",
                "1x Input Hatch (Side centered)",
                "1x Maintenance Hatch (Side centered)",
                "1x Muffler Hatch (Side centered)",
                "1x Dynamo Hatch (Back centered)",
                "Titanium Turbine Casings for the rest (24 at least!)",
                "Needs a Turbine Item (Inside controller GUI)",
                "Output depending on Rotor: 210-3360EU/t"};
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_LargeTurbine_HPSteam(mName);
    }

    @Override
    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    @Override
    public byte getCasingMeta() {
        return 11;
    }

    @Override
    public byte getCasingTextureIndex() {
        return 59;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    int fluidIntoPower(ArrayList<FluidStack> aFluids, int aOptFlow, int aBaseEff) {
        int tEU = 0;
        int totalFlow = 0; // Byproducts are based on actual flow
        int flow = 0;
        int remainingFlow = (int) (aOptFlow * 1.25f); // Allowed to use up to 125% of optimal flow
        this.realOptFlow = aOptFlow;

        for (int i = 0; i < aFluids.size() && remainingFlow > 0; i++) {
            String fluidName = aFluids.get(i).getFluid().getUnlocalizedName(aFluids.get(i));
            if (fluidName.equals("ic2.fluidSuperheatedSteam")) {
                flow = aFluids.get(i).amount; // Get all (steam) in hatch
                flow = Math.min(flow, Math.min(remainingFlow, (int) (aOptFlow * 1.25f))); // try to use up to 125% of optimal flow w/o exceeding remainingFlow
                depleteInput(new FluidStack(aFluids.get(i), flow)); // deplete that amount
                this.storedFluid = aFluids.get(i).amount;
                remainingFlow -= flow; // track amount we're allowed to keep depleting from hatches
                totalFlow += flow; // track total used
                if (!achievement) {
                    GT_Mod.instance.achievements.issueAchievement(this.getBaseMetaTileEntity().getWorld().getPlayerEntityByName(this.getBaseMetaTileEntity().getOwnerName()), "efficientsteam");
                    achievement = true;
                }
            }else if(fluidName.equals("fluid.steam") || fluidName.equals("ic2.fluidSteam") || fluidName.equals("fluid.mfr.steam.still.name")){
                depleteInput(new FluidStack(aFluids.get(i), aFluids.get(i).amount));
            }
        }

        tEU = (int) (Math.min((float) aOptFlow, totalFlow));
        addOutput(GT_ModHandler.getSteam(totalFlow));
        if (totalFlow > 0 && totalFlow != aOptFlow) {
            float efficiency = 1.0f - Math.abs(((totalFlow - (float) aOptFlow) / aOptFlow));
            if(totalFlow>aOptFlow){efficiency = 1.0f;}
            tEU *= efficiency;
            tEU = Math.max(1, (int)((long)tEU * (long)aBaseEff / 10000L));
        } else {
            tEU = (int)((long)tEU * (long)aBaseEff / 10000L);
        }

        return tEU;
    }


}
