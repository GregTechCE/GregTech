// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GT_Client.java

package gregtech.common;

import codechicken.lib.vec.Rotation;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.ITurnable;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import gregtech.common.entities.GT_Entity_Arrow;
import gregtech.common.entities.GT_Entity_Arrow_Potion;
import gregtech.common.render.*;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.net.URL;
import java.util.*;

// Referenced classes of package gregtech.common:
//            GT_Proxy

@SideOnly(Side.CLIENT)
public class GT_Client extends GT_Proxy
        implements Runnable {

    private static List<Block> ROTATABLE_VANILLA_BLOCKS;

    static {
        ROTATABLE_VANILLA_BLOCKS = Arrays.asList(Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.FURNACE, Blocks.LIT_FURNACE,
                Blocks.DROPPER, Blocks.DISPENSER, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.HOPPER,
                Blocks.PUMPKIN, Blocks.LIT_PUMPKIN);
    }

    private final HashSet<String> mCapeList = new HashSet<>();
    private final List<Materials> mPosR;
    private final List<Materials> mPosG;
    private final List<Materials> mPosB;
    private final List<Materials> mPosA = new ArrayList<>();
    private final List<Materials> mNegR;
    private final List<Materials> mNegG;
    private final List<Materials> mNegB;
    private final List<Materials> mNegA = new ArrayList<>();
    private final List<Materials> mMoltenPosR;
    private final List<Materials> mMoltenPosG;
    private final List<Materials> mMoltenPosB;
    private final List<Materials> mMoltenPosA = new ArrayList<>();
    private final List<Materials> mMoltenNegR;
    private final List<Materials> mMoltenNegG;
    private final List<Materials> mMoltenNegB;
    private final List<Materials> mMoltenNegA = new ArrayList<>();
    private long mAnimationTick;
    private boolean mAnimationDirection;

    public GT_Client() {
        mAnimationTick = 0L;
        mAnimationDirection = false;

        mPosR = Arrays.asList( /**Materials.ChargedCertusQuartz, **/Materials.Enderium, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.Thaumium, Materials.InfusedVis, Materials.InfusedAir, Materials.InfusedFire, Materials.FierySteel, Materials.Firestone);

        mPosG = Arrays.asList( /**Materials.ChargedCertusQuartz, **/Materials.Enderium, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.InfusedAir, Materials.InfusedEarth);

        mPosB = Arrays.asList( /**Materials.ChargedCertusQuartz, **/Materials.Enderium, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.InfusedVis,
                Materials.InfusedWater, Materials.Thaumium);

        mNegR = Arrays.asList(Materials.InfusedEntropy, Materials.NetherStar);

        mNegG = Arrays.asList(Materials.InfusedEntropy, Materials.NetherStar);

        mNegB = Arrays.asList(Materials.InfusedEntropy, Materials.NetherStar);

        mMoltenPosR = Arrays.asList(Materials.Enderium, Materials.NetherStar, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.Thaumium, Materials.InfusedVis, Materials.InfusedAir, Materials.InfusedFire, Materials.FierySteel, Materials.Firestone);

        mMoltenPosG = Arrays.asList(Materials.Enderium, Materials.NetherStar, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.Force,
                Materials.Pyrotheum, Materials.Sunnarium, Materials.Glowstone, Materials.InfusedAir, Materials.InfusedEarth);

        mMoltenPosB = Arrays.asList(Materials.Enderium, Materials.NetherStar, Materials.Vinteum, Materials.Uranium235, Materials.InfusedGold, Materials.Plutonium241, Materials.NaquadahEnriched, Materials.Naquadria, Materials.InfusedOrder, Materials.InfusedVis,
                Materials.InfusedWater, Materials.Thaumium);

        mMoltenNegR = Arrays.asList(Materials.InfusedEntropy);
        mMoltenNegG = Arrays.asList(Materials.InfusedEntropy);
        mMoltenNegB = Arrays.asList(Materials.InfusedEntropy);

    }

    private static void drawGrid(DrawBlockHighlightEvent aEvent) {
        GL11.glPushMatrix();
        EntityPlayer player = aEvent.getPlayer();
        float partialTicks = aEvent.getPartialTicks();
        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
        BlockPos pos = aEvent.getTarget().getBlockPos();
        GL11.glTranslated(-d0, -d1, -d2);
        GL11.glTranslated(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        Rotation.sideRotations[aEvent.getTarget().sideHit.getIndex()].glApply();
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
                "zF4ll3nPr3d4t0r", "CrafterOfMines57", "XxELIT3xSNIP3RxX", "SuterusuKusanagi", "xavier0014", "adamros", "alexbegt", "Archengius"
        };
        for (String tName : arr$) {
            mCapeList.add(tName.toLowerCase());
        }
        new Thread(this).start();
    }

    public void onLoad() {
        super.onLoad();

        RenderingRegistry.registerEntityRenderingHandler(GT_Entity_Arrow.class, manager -> {
            return new GT_Renderer_Entity_Arrow(manager, GT_Entity_Arrow.class, "arrow");
        });

        RenderingRegistry.registerEntityRenderingHandler(GT_Entity_Arrow.class, manager -> {
            return new GT_Renderer_Entity_Arrow(manager, GT_Entity_Arrow_Potion.class, "arrow_potions");
        });
    }


    //Models init
    public void onPostLoad() {
        super.onPostLoad();

        Textures.BlockIcons.BASALT_BRICKS.getClass();
        Textures.ItemIcons.BUTCHERYKNIFE.getClass();
        TextureSet.SET_DIAMOND.getClass();

        RenderBlocks.INSTANCE.init();
        RenderGeneratedOres.INSTANCE.init();
        GT_Renderer_Block.INSTANCE.init();
        ItemRenderer.INSTANCE.init();

        RenderManager manager = Minecraft.getMinecraft().getRenderManager();
        Map<String, RenderPlayer> map = manager.getSkinMap();
        map.get("default").addLayer(new GT_CapeRendererLayer(mCapeList, map.get("default")));
        map.get("slim").addLayer(new GT_CapeRendererLayer(mCapeList, map.get("slim")));
    }

    public void run() {
        downloadStuff();
    }

    public void downloadStuff() {
        try {
            GT_Log.out.println("GT_Mod: Downloading Cape List.");
            Scanner tScanner = new Scanner(new URL("http://gregtech.overminddl1.com/com/gregoriust/gregtech/supporterlist.txt").openStream());
            while (tScanner.hasNextLine()) {
                String tName = tScanner.nextLine();
                if (!this.mCapeList.contains(tName.toLowerCase())) {
                    this.mCapeList.add(tName.toLowerCase());
                }
            }
        } catch (Throwable e) {}
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent aEvent) {
        if(aEvent.getPlayer() != null && aEvent.getTarget().typeOfHit == RayTraceResult.Type.BLOCK &&
                aEvent.getTarget().getBlockPos() != null) {
            EntityPlayer aPlayer = aEvent.getPlayer();
            ItemStack currentItem = aPlayer.getHeldItemMainhand();
            BlockPos aPos = aEvent.getTarget().getBlockPos();
            Block block = aPlayer.worldObj.getBlockState(aPos).getBlock();
            if (GT_Utility.isStackValid(currentItem)) {
                TileEntity aTileEntity = aPlayer.worldObj.getTileEntity(aPos);
                if (((aTileEntity instanceof BaseMetaPipeEntity)) && (((ICoverable) aTileEntity).getCoverIDAtSide((byte) aEvent.getTarget().sideHit.getIndex()) == 0) && ((GT_Utility.isStackInList(currentItem, GregTech_API.sCoverItems.keySet())) || (GT_Utility.isStackInList(currentItem, GregTech_API.sCrowbarList)) || (GT_Utility.isStackInList(currentItem, GregTech_API.sScrewdriverList)))) {
                    drawGrid(aEvent);
                }
                else if ((aTileEntity instanceof ITurnable || aTileEntity instanceof IWrenchable || ROTATABLE_VANILLA_BLOCKS.contains(block)) && GT_Utility.isStackInList(currentItem, GregTech_API.sWrenchList)) {
                    drawGrid(aEvent);
                }
            }
        }
    }


    @SubscribeEvent
    public void onClientTickEvent(net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent aEvent) {
        if (aEvent.phase == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) {
            mAnimationTick++;
            if (mAnimationTick % 50L == 0L)
                mAnimationDirection = !mAnimationDirection;
            int tDirection = mAnimationDirection ? 1 : -1;
            for (Materials tMaterial : mPosR) {
                tMaterial.mRGBa[0] += tDirection;
            }

            for (Materials tMaterial : mPosG) {
                tMaterial.mRGBa[1] += tDirection;
            }

            for (Materials tMaterial : mPosB) {
                tMaterial.mRGBa[2] += tDirection;
            }

            for (Materials tMaterial : mPosA) {
                tMaterial.mRGBa[3] += tDirection;
            }

            for (Materials tMaterial : mNegR) {
                tMaterial.mRGBa[0] -= tDirection;
            }

            for (Materials tMaterial : mNegG) {
                tMaterial.mRGBa[1] -= tDirection;
            }

            for (Materials tMaterial : mNegB) {
                tMaterial.mRGBa[2] -= tDirection;
            }

            for (Materials tMaterial : mNegA) {
                tMaterial.mRGBa[3] -= tDirection;
            }

            for (Materials tMaterial : mMoltenPosR) {
                tMaterial.mMoltenRGBa[0] += tDirection;
            }

            for (Materials tMaterial : mMoltenPosG) {
                tMaterial.mMoltenRGBa[1] += tDirection;
            }

            for (Materials tMaterial : mMoltenPosB) {
                tMaterial.mMoltenRGBa[2] += tDirection;
            }

            for (Materials tMaterial : mMoltenPosA) {
                tMaterial.mMoltenRGBa[3] += tDirection;
            }

            for (Materials tMaterial : mMoltenNegR) {
                tMaterial.mMoltenRGBa[0] -= tDirection;
            }

            for (Materials tMaterial : mMoltenNegG) {
                tMaterial.mMoltenRGBa[1] -= tDirection;
            }

            for (Materials tMaterial : mMoltenNegB) {
                tMaterial.mMoltenRGBa[2] -= tDirection;
            }

            for (Materials tMaterial : mMoltenNegA) {
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
            if (GT_Utility.areStacksEqual(mSoundItems.get(i), aStack)) {
                tString = mSoundNames.get(i);
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
        //TODO FIXME
        //if (tString.startsWith("streaming."))
        //aWorld.playRecord(tString.substring(10, tString.length()), (int) aX, (int) aY, (int) aZ);
        //else
        //aWorld.playSound(aX, aY, aZ, tString, 3F, tString.startsWith("note.") ? (float) Math.pow(2D, (double) (aStack.stackSize - 13) / 12D) : 1.0F, false);
    }
}