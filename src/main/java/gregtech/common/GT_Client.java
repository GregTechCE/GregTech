// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GT_Client.java

package gregtech.common;

import codechicken.lib.vec.Rotation;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.ITurnable;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.objects.GT_FluidStack;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_PlayedSound;
import gregtech.api.util.GT_Utility;
import gregtech.common.entities.GT_Entity_Arrow;
import gregtech.common.entities.GT_Entity_Arrow_Potion;
import gregtech.common.render.*;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.opengl.GL11;

import java.net.URL;
import java.util.*;

// Referenced classes of package gregtech.common:
//            GT_Proxy

public class GT_Client extends GT_Proxy
        implements Runnable {

    private static List ROTATABLE_VANILLA_BLOCKS;

    static {
        ROTATABLE_VANILLA_BLOCKS = Arrays.asList(new Block[]{
                Blocks.piston, Blocks.sticky_piston, Blocks.furnace, Blocks.lit_furnace, Blocks.dropper, Blocks.dispenser, Blocks.chest, Blocks.trapped_chest, Blocks.ender_chest, Blocks.hopper,
                Blocks.pumpkin, Blocks.lit_pumpkin
        });
    }

    private final HashSet mCapeList = new HashSet();
    private final GT_CapeRenderer mCapeRenderer;
    private final List mPosR;
    private final List mPosG;
    private final List mPosB;
    private final List mPosA = Arrays.asList(new Object[0]);
    private final List mNegR;
    private final List mNegG;
    private final List mNegB;
    private final List mNegA = Arrays.asList(new Object[0]);
    private final List mMoltenPosR;
    private final List mMoltenPosG;
    private final List mMoltenPosB;
    private final List mMoltenPosA = Arrays.asList(new Object[0]);
    private final List mMoltenNegR;
    private final List mMoltenNegG;
    private final List mMoltenNegB;
    private final List mMoltenNegA = Arrays.asList(new Object[0]);
    private long mAnimationTick;
    private boolean mAnimationDirection;
    private boolean isFirstClientPlayerTick;
    private String mMessage;
    public GT_Client() {
        mCapeRenderer = new GT_CapeRenderer(mCapeList);
        mAnimationTick = 0L;
        mAnimationDirection = false;
        isFirstClientPlayerTick = true;
        mMessage = "";
        mPosR = Arrays.asList(new Materials[]{
                /**Materials.ChargedCertusQuartz, **/Materials.Enderium, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.Thaumium, Materials.InfusedVis, Materials.InfusedAir, Materials.InfusedFire, Materials.FierySteel, Materials.Firestone
        });
        mPosG = Arrays.asList(new Materials[]{
                /**Materials.ChargedCertusQuartz, **/Materials.Enderium, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.InfusedAir, Materials.InfusedEarth
        });
        mPosB = Arrays.asList(new Materials[]{
                /**Materials.ChargedCertusQuartz, **/Materials.Enderium, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.InfusedVis,
                Materials.InfusedWater, Materials.Thaumium
        });
        mNegR = Arrays.asList(new Materials[]{
                Materials.InfusedEntropy, Materials.NetherStar
        });
        mNegG = Arrays.asList(new Materials[]{
                Materials.InfusedEntropy, Materials.NetherStar
        });
        mNegB = Arrays.asList(new Materials[]{
                Materials.InfusedEntropy, Materials.NetherStar
        });
        mMoltenPosR = Arrays.asList(new Materials[]{
                Materials.Enderium, Materials.NetherStar, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.Thaumium, Materials.InfusedVis, Materials.InfusedAir, Materials.InfusedFire, Materials.FierySteel, Materials.Firestone
        });
        mMoltenPosG = Arrays.asList(new Materials[]{
                Materials.Enderium, Materials.NetherStar, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.InfusedAir, Materials.InfusedEarth
        });
        mMoltenPosB = Arrays.asList(new Materials[]{
                Materials.Enderium, Materials.NetherStar, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.InfusedVis,
                Materials.InfusedWater, Materials.Thaumium
        });
        mMoltenNegR = Arrays.asList(new Materials[]{
                Materials.InfusedEntropy
        });
        mMoltenNegG = Arrays.asList(new Materials[]{
                Materials.InfusedEntropy
        });
        mMoltenNegB = Arrays.asList(new Materials[]{
                Materials.InfusedEntropy
        });
    }

    private static void drawGrid(DrawBlockHighlightEvent aEvent) {
        GL11.glPushMatrix();
        GL11.glTranslated(-(aEvent.player.lastTickPosX + (aEvent.player.posX - aEvent.player.lastTickPosX) * (double) aEvent.partialTicks), -(aEvent.player.lastTickPosY + (aEvent.player.posY - aEvent.player.lastTickPosY) * (double) aEvent.partialTicks), -(aEvent.player.lastTickPosZ + (aEvent.player.posZ - aEvent.player.lastTickPosZ) * (double) aEvent.partialTicks));
        GL11.glTranslated((float) aEvent.target.blockX + 0.5F, (float) aEvent.target.blockY + 0.5F, (float) aEvent.target.blockZ + 0.5F);
        Rotation.sideRotations[aEvent.target.sideHit].glApply();
        GL11.glTranslated(0.0D, -0.501D, 0.0D);
        GL11.glLineWidth(2.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
        GL11.glBegin(1);
        GL11.glVertex3d(0.5D, 0.0D, -0.25D);
        GL11.glVertex3d(-0.5D, 0.0D, -0.25D);
        GL11.glVertex3d(0.5D, 0.0D, 0.25D);
        GL11.glVertex3d(-0.5D, 0.0D, 0.25D);
        GL11.glVertex3d(0.25D, 0.0D, -0.5D);
        GL11.glVertex3d(0.25D, 0.0D, 0.5D);
        GL11.glVertex3d(-0.25D, 0.0D, -0.5D);
        GL11.glVertex3d(-0.25D, 0.0D, 0.5D);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public boolean isServerSide() {
        return true;
    }

    public boolean isClientSide() {
        return true;
    }

    public boolean isBukkitSide() {
        return false;
    }

    public EntityPlayer getThePlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public int addArmor(String aPrefix) {
        return RenderingRegistry.addNewArmourRendererPrefix(aPrefix);
    }

    public void onPreLoad() {
        super.onPreLoad();
        String arr$[] = {
                "renadi", "hanakocz", "MysteryDump", "Flaver4", "x_Fame", "Peluche321", "Goshen_Ithilien", "manf", "Bimgo", "leagris",
                "IAmMinecrafter02", "Cerous", "Devilin_Pixy", "Bkarlsson87", "BadAlchemy", "CaballoCraft", "melanclock", "Resursator", "demanzke", "AndrewAmmerlaan",
                "Deathlycraft", "Jirajha", "Axlegear", "kei_kouma", "Dracion", "dungi", "Dorfschwein", "Zero Tw0", "mattiagraz85", "sebastiank30",
                "Plem", "invultri", "grillo126", "malcanteth", "Malevolence_", "Nicholas_Manuel", "Sirbab", "kehaan", "bpgames123", "semig0d",
                "9000bowser", "Sovereignty89", "Kris1432", "xander_cage_", "samuraijp", "bsaa", "SpwnX", "tworf", "Kadah", "kanni",
                "Stute", "Hegik", "Onlyme", "t3hero", "Hotchi", "jagoly", "Nullav", "BH5432", "Sibmer", "inceee",
                "foxxx0", "Hartok", "TMSama", "Shlnen", "Carsso", "zessirb", "meep310", "Seldron", "yttr1um", "hohounk",
                "freebug", "Sylphio", "jmarler", "Saberawr", "r00teniy", "Neonbeta", "yinscape", "voooon24", "Quintine", "peach774",
                "lepthymo", "bildeman", "Kremnari", "Aerosalo", "OndraSter", "oscares91", "mr10movie", "Daxx367x2", "EGERTRONx", "aka13_404",
                "Abouttabs", "Johnstaal", "djshiny99", "megatronp", "DZCreeper", "Kane_Hart", "Truculent", "vidplace7", "simon6689", "MomoNasty",
                "UnknownXLV", "goreacraft", "Fluttermine", "Daddy_Cecil", "MrMaleficus", "TigersFangs", "cublikefoot", "chainman564", "NikitaBuker", "Misha999777",
                "25FiveDetail", "AntiCivilBoy", "michaelbrady", "xXxIceFirexXx", "Speedynutty68", "GarretSidzaka", "HallowCharm977", "mastermind1919", "The_Hypersonic", "diamondguy2798",
                "zF4ll3nPr3d4t0r", "CrafterOfMines57", "XxELIT3xSNIP3RxX", "SuterusuKusanagi", "xavier0014", "adamros", "alexbegt"
        };
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String tName = arr$[i$];
            mCapeList.add(tName.toLowerCase());
        }

        (new Thread(this)).start();
    }

    public void onLoad() {
        super.onLoad();
        new GT_Renderer_Block();
        new GT_MetaGenerated_Item_Renderer();
        new GT_MetaGenerated_Tool_Renderer();
        new GT_Renderer_Entity_Arrow(GT_Entity_Arrow.class, "arrow");
        new GT_Renderer_Entity_Arrow(GT_Entity_Arrow_Potion.class, "arrow_potions");
    }

    public void onPostLoad() {
        super.onPostLoad();
        label0:
        for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++)
            try {
                do {
                    if (i >= GregTech_API.METATILEENTITIES.length)
                        continue label0;
                    if (GregTech_API.METATILEENTITIES[i] != null)
                        GregTech_API.METATILEENTITIES[i].getStackForm(1L).getTooltip(null, true);
                    i++;
                } while (true);
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }


//        super.onPostLoad();
//
//            for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
//              try {
//                for (; i < GregTech_API.METATILEENTITIES.length; i++) if (GregTech_API.METATILEENTITIES[i] != null) GregTech_API.METATILEENTITIES[i].getStackForm(1L).getTooltip(null, true);
//              } catch (Throwable e) {
//                e.printStackTrace(GT_Log.err);
//              }
//            }
    }

    public void run() {
        try {
            GT_Log.out.println("GT_Mod: Downloading Cape List.");
            @SuppressWarnings("resource")
            Scanner tScanner = new Scanner(new URL("http://files.minecraftforge.net/maven/com/gregoriust/gregtech/capelist.txt").openStream());
            while (tScanner.hasNextLine()) {
                String tName = tScanner.nextLine();
                if (!this.mCapeList.contains(tName.toLowerCase())) {
                    this.mCapeList.add(tName.toLowerCase());
                }
            }
        } catch (Throwable e) {
        }
        try {
            GT_Log.out.println("GT_Mod: Downloading News.");
            @SuppressWarnings("resource")
            Scanner tScanner = new Scanner(new URL("http://files.minecraftforge.net/maven/com/gregoriust/gregtech/message.txt").openStream());
            while (tScanner.hasNextLine()) {
                this.mMessage = (this.mMessage + tScanner.nextLine() + " ");
            }
        } catch (Throwable e) {
        }
    }

    @SubscribeEvent
    public void onPlayerTickEventClient(TickEvent.PlayerTickEvent aEvent) {
        if ((!aEvent.player.isDead) && (aEvent.phase == TickEvent.Phase.END) && (aEvent.side.isClient())) {
            ArrayList<GT_PlayedSound> tList = new ArrayList();
            for (Map.Entry<GT_PlayedSound, Integer> tEntry : GT_Utility.sPlayedSoundMap.entrySet()) {
                if (((Integer) tEntry.getValue()).intValue() < 0) {
                    tList.add(tEntry.getKey());
                } else {
                    tEntry.setValue(Integer.valueOf(((Integer) tEntry.getValue()).intValue() - 1));
                }
            }
            GT_PlayedSound tKey;
            for (Iterator i$ = tList.iterator(); i$.hasNext(); GT_Utility.sPlayedSoundMap.remove(tKey)) {
                tKey = (GT_PlayedSound) i$.next();
            }
            if ((this.isFirstClientPlayerTick) && (aEvent.player == GT_Values.GT.getThePlayer())) {
                this.isFirstClientPlayerTick = false;
                GT_FluidStack.fixAllThoseFuckingFluidIDs();
                if ((this.mMessage.length() > 5) && (GregTech_API.sSpecialFile.get(ConfigCategories.news, this.mMessage, true))) {
                    aEvent.player.addChatComponentMessage(new ChatComponentText(this.mMessage));
                }
                try {
                    int tVersion = Integer.parseInt(((String) Class.forName("ic2.core.IC2").getField("VERSION").get(null)).substring(4, 7));
                    if (GT_Values.D1) {
                        GT_Log.out.println("Industrialcraft Version: " + tVersion);
                    }
                    if (tVersion < 624) {
                        aEvent.player.addChatComponentMessage(new ChatComponentText("GregTech: Please update your IndustrialCraft here:"));
                        aEvent.player.addChatComponentMessage(new ChatComponentText("ic2api.player.to:8080/job/IC2_experimental/" + (GT_Mod.MAX_IC2 < Integer.MAX_VALUE ? GT_Mod.MAX_IC2 : 624) + "/"));
                    } else if (tVersion > GT_Mod.MAX_IC2) {
                        aEvent.player.addChatComponentMessage(new ChatComponentText("GregTech: Please downgrade your IndustrialCraft here:"));
                        aEvent.player.addChatComponentMessage(new ChatComponentText("ic2api.player.to:8080/job/IC2_experimental/" + GT_Mod.MAX_IC2 + "/"));
                    }
                } catch (Throwable e) {
                    aEvent.player.addChatComponentMessage(new ChatComponentText("GregTech: Please get the recommended Version of IndustrialCraft here:"));
                    aEvent.player.addChatComponentMessage(new ChatComponentText("ic2api.player.to:8080/job/IC2_experimental/" + (GT_Mod.MAX_IC2 < Integer.MAX_VALUE ? GT_Mod.MAX_IC2 : 624) + "/"));
                }
            }
        }
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent aEvent) {
        if (GT_Utility.isStackValid(aEvent.currentItem)) {
            Block aBlock = aEvent.player.worldObj.getBlock(aEvent.target.blockX, aEvent.target.blockY, aEvent.target.blockZ);
            TileEntity aTileEntity = aEvent.player.worldObj.getTileEntity(aEvent.target.blockX, aEvent.target.blockY, aEvent.target.blockZ);
            try {
                Class.forName("codechicken.lib.vec.Rotation");
                if (((aTileEntity instanceof BaseMetaPipeEntity)) && (((ICoverable) aTileEntity).getCoverIDAtSide((byte) aEvent.target.sideHit) == 0) && ((GT_Utility.isStackInList(aEvent.currentItem, GregTech_API.sCovers.keySet())) || (GT_Utility.isStackInList(aEvent.currentItem, GregTech_API.sCrowbarList)) || (GT_Utility.isStackInList(aEvent.currentItem, GregTech_API.sScrewdriverList)))) {
                    drawGrid(aEvent);
                    return;
                }
                if ((((aTileEntity instanceof ITurnable)) || (ROTATABLE_VANILLA_BLOCKS.contains(aBlock)) || ((aTileEntity instanceof IWrenchable))) && (GT_Utility.isStackInList(aEvent.currentItem, GregTech_API.sWrenchList))) {
                    drawGrid(aEvent);
                    return;
                }
            } catch (Throwable e) {
                if (GT_Values.D1) {
                    e.printStackTrace(GT_Log.err);
                }
            }
        }
    }

    @SubscribeEvent
    public void receiveRenderEvent(net.minecraftforge.client.event.RenderPlayerEvent.Pre aEvent) {
        if (GT_Utility.getFullInvisibility(aEvent.entityPlayer)) {
            aEvent.setCanceled(true);
            return;
        } else {
            return;
        }
    }

    @SubscribeEvent
    public void receiveRenderSpecialsEvent(net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre aEvent) {
        mCapeRenderer.receiveRenderSpecialsEvent(aEvent);
    }

    @SubscribeEvent
    public void onClientTickEvent(cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent aEvent) {
        if (aEvent.phase == cpw.mods.fml.common.gameevent.TickEvent.Phase.END) {
            mAnimationTick++;
            if (mAnimationTick % 50L == 0L)
                mAnimationDirection = !mAnimationDirection;
            int tDirection = mAnimationDirection ? 1 : -1;
            for (Iterator i$ = mPosR.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[0] += tDirection;
            }

            for (Iterator i$ = mPosG.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[1] += tDirection;
            }

            for (Iterator i$ = mPosB.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[2] += tDirection;
            }

            for (Iterator i$ = mPosA.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[3] += tDirection;
            }

            for (Iterator i$ = mNegR.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[0] -= tDirection;
            }

            for (Iterator i$ = mNegG.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[1] -= tDirection;
            }

            for (Iterator i$ = mNegB.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[2] -= tDirection;
            }

            for (Iterator i$ = mNegA.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mRGBa[3] -= tDirection;
            }

            for (Iterator i$ = mMoltenPosR.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[0] += tDirection;
            }

            for (Iterator i$ = mMoltenPosG.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[1] += tDirection;
            }

            for (Iterator i$ = mMoltenPosB.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[2] += tDirection;
            }

            for (Iterator i$ = mMoltenPosA.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[3] += tDirection;
            }

            for (Iterator i$ = mMoltenNegR.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[0] -= tDirection;
            }

            for (Iterator i$ = mMoltenNegG.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[1] -= tDirection;
            }

            for (Iterator i$ = mMoltenNegB.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[2] -= tDirection;
            }

            for (Iterator i$ = mMoltenNegA.iterator(); i$.hasNext(); ) {
                Materials tMaterial = (Materials) i$.next();
                tMaterial.mMoltenRGBa[3] -= tDirection;
            }

        }
    }

    public void doSonictronSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        if (GT_Utility.isStackInvalid(aStack))
            return;
        String tString = "note.harp";
        int i = 0;
        int j = mSoundItems.size();
        do {
            if (i >= j)
                break;
            if (GT_Utility.areStacksEqual((ItemStack) mSoundItems.get(i), aStack)) {
                tString = (String) mSoundNames.get(i);
                break;
            }
            i++;
        } while (true);
        if (tString.startsWith("random.explode"))
            if (aStack.stackSize == 3)
                tString = "random.fuse";
            else if (aStack.stackSize == 2)
                tString = "random.old_explode";
        if (tString.startsWith("streaming."))
            switch (aStack.stackSize) {
                case 1: // '\001'
                    tString = (new StringBuilder()).append(tString).append("13").toString();
                    break;

                case 2: // '\002'
                    tString = (new StringBuilder()).append(tString).append("cat").toString();
                    break;

                case 3: // '\003'
                    tString = (new StringBuilder()).append(tString).append("blocks").toString();
                    break;

                case 4: // '\004'
                    tString = (new StringBuilder()).append(tString).append("chirp").toString();
                    break;

                case 5: // '\005'
                    tString = (new StringBuilder()).append(tString).append("far").toString();
                    break;

                case 6: // '\006'
                    tString = (new StringBuilder()).append(tString).append("mall").toString();
                    break;

                case 7: // '\007'
                    tString = (new StringBuilder()).append(tString).append("mellohi").toString();
                    break;

                case 8: // '\b'
                    tString = (new StringBuilder()).append(tString).append("stal").toString();
                    break;

                case 9: // '\t'
                    tString = (new StringBuilder()).append(tString).append("strad").toString();
                    break;

                case 10: // '\n'
                    tString = (new StringBuilder()).append(tString).append("ward").toString();
                    break;

                case 11: // '\013'
                    tString = (new StringBuilder()).append(tString).append("11").toString();
                    break;

                case 12: // '\f'
                    tString = (new StringBuilder()).append(tString).append("wait").toString();
                    break;

                default:
                    tString = (new StringBuilder()).append(tString).append("wherearewenow").toString();
                    break;
            }
        if (tString.startsWith("streaming."))
            aWorld.playRecord(tString.substring(10, tString.length()), (int) aX, (int) aY, (int) aZ);
        else
            aWorld.playSound(aX, aY, aZ, tString, 3F, tString.startsWith("note.") ? (float) Math.pow(2D, (double) (aStack.stackSize - 13) / 12D) : 1.0F, false);
    }
}