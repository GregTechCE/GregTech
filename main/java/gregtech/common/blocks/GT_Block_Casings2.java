/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.entity.Entity;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.util.IIcon;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class GT_Block_Casings2
/* 15:   */   extends GT_Block_Casings_Abstract
/* 16:   */ {
/* 17:   */   public GT_Block_Casings2()
/* 18:   */   {
/* 19:15 */     super(GT_Item_Casings2.class, "gt.blockcasings2", GT_Material_Casings.INSTANCE);
/* 20:16 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/* 21:16 */       Textures.BlockIcons.CASING_BLOCKS[(i + 16)] = new GT_CopiedBlockTexture(this, 6, i);
/* 22:   */     }
/* 23:17 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Solid Steel Machine Casing");
/* 24:18 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Frost Proof Machine Casing");
/* 25:19 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Bronze Gear Box Casing");
/* 26:20 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Steel Gear Box Casing");
/* 27:21 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Titanium Gear Box Casing");
/* 28:22 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Tungstensteel Gear Box Casing");
/* 29:23 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Processor Machine Casing");
/* 30:24 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Data Drive Machine Casing");
/* 31:25 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Containment Field Machine Casing");
/* 32:26 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Assembler Machine Casing");
/* 33:27 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Pump Machine Casing");
/* 34:28 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Motor Machine Casing");
/* 35:29 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Bronze Pipe Machine Casing");
/* 36:30 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Steel Pipe Machine Casing");
/* 37:31 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Titanium Pipe Machine Casing");
/* 38:32 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Tungstensteel Pipe Machine Casing");
/* 39:33 */     ItemList.Casing_SolidSteel.set(new ItemStack(this, 1, 0));
/* 40:34 */     ItemList.Casing_FrostProof.set(new ItemStack(this, 1, 1));
/* 41:35 */     ItemList.Casing_Gearbox_Bronze.set(new ItemStack(this, 1, 2));
/* 42:36 */     ItemList.Casing_Gearbox_Steel.set(new ItemStack(this, 1, 3));
/* 43:37 */     ItemList.Casing_Gearbox_Titanium.set(new ItemStack(this, 1, 4));
/* 44:38 */     ItemList.Casing_Gearbox_TungstenSteel.set(new ItemStack(this, 1, 5));
/* 45:39 */     ItemList.Casing_Processor.set(new ItemStack(this, 1, 6));
/* 46:40 */     ItemList.Casing_DataDrive.set(new ItemStack(this, 1, 7));
/* 47:41 */     ItemList.Casing_ContainmentField.set(new ItemStack(this, 1, 8));
/* 48:42 */     ItemList.Casing_Assembler.set(new ItemStack(this, 1, 9));
/* 49:43 */     ItemList.Casing_Pump.set(new ItemStack(this, 1, 10));
/* 50:44 */     ItemList.Casing_Motor.set(new ItemStack(this, 1, 11));
/* 51:45 */     ItemList.Casing_Pipe_Bronze.set(new ItemStack(this, 1, 12));
/* 52:46 */     ItemList.Casing_Pipe_Steel.set(new ItemStack(this, 1, 13));
/* 53:47 */     ItemList.Casing_Pipe_Titanium.set(new ItemStack(this, 1, 14));
/* 54:48 */     ItemList.Casing_Pipe_TungstenSteel.set(new ItemStack(this, 1, 15));
/* 55:   */   }
/* 56:   */   
/* 57:   */   public IIcon getIcon(int aSide, int aMeta)
/* 58:   */   {
/* 59:53 */     switch (aMeta)
/* 60:   */     {
/* 61:   */     case 0: 
/* 62:54 */       return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 63:   */     case 1: 
/* 64:55 */       return Textures.BlockIcons.MACHINE_CASING_FROST_PROOF.getIcon();
/* 65:   */     case 2: 
/* 66:56 */       return Textures.BlockIcons.MACHINE_CASING_GEARBOX_BRONZE.getIcon();
/* 67:   */     case 3: 
/* 68:57 */       return Textures.BlockIcons.MACHINE_CASING_GEARBOX_STEEL.getIcon();
/* 69:   */     case 4: 
/* 70:58 */       return Textures.BlockIcons.MACHINE_CASING_GEARBOX_TITANIUM.getIcon();
/* 71:   */     case 5: 
/* 72:59 */       return Textures.BlockIcons.MACHINE_CASING_GEARBOX_TUNGSTENSTEEL.getIcon();
/* 73:   */     case 6: 
/* 74:60 */       return Textures.BlockIcons.MACHINE_CASING_PROCESSOR.getIcon();
/* 75:   */     case 7: 
/* 76:61 */       return Textures.BlockIcons.MACHINE_CASING_DATA_DRIVE.getIcon();
/* 77:   */     case 8: 
/* 78:62 */       return Textures.BlockIcons.MACHINE_CASING_CONTAINMENT_FIELD.getIcon();
/* 79:   */     case 9: 
/* 80:63 */       return Textures.BlockIcons.MACHINE_CASING_ASSEMBLER.getIcon();
/* 81:   */     case 10: 
/* 82:64 */       return Textures.BlockIcons.MACHINE_CASING_PUMP.getIcon();
/* 83:   */     case 11: 
/* 84:65 */       return Textures.BlockIcons.MACHINE_CASING_MOTOR.getIcon();
/* 85:   */     case 12: 
/* 86:66 */       return Textures.BlockIcons.MACHINE_CASING_PIPE_BRONZE.getIcon();
/* 87:   */     case 13: 
/* 88:67 */       return Textures.BlockIcons.MACHINE_CASING_PIPE_STEEL.getIcon();
/* 89:   */     case 14: 
/* 90:68 */       return Textures.BlockIcons.MACHINE_CASING_PIPE_TITANIUM.getIcon();
/* 91:   */     case 15: 
/* 92:69 */       return Textures.BlockIcons.MACHINE_CASING_PIPE_TUNGSTENSTEEL.getIcon();
/* 93:   */     }
/* 94:71 */     return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 95:   */   }
/* 96:   */   
/* 97:   */   public float getExplosionResistance(Entity aTNT, World aWorld, int aX, int aY, int aZ, double eX, double eY, double eZ)
/* 98:   */   {
/* 99:76 */     return aWorld.getBlockMetadata(aX, aY, aZ) == 8 ? Blocks.bedrock.getExplosionResistance(aTNT) : super.getExplosionResistance(aTNT, aWorld, aX, aY, aZ, eX, eY, eZ);
/* :0:   */   }
/* :1:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Casings2
 * JD-Core Version:    0.7.0.1
 */