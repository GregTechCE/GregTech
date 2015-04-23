/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.item.ItemBlock;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class GT_Item_Ores
/* 11:   */   extends ItemBlock
/* 12:   */ {
/* 13:   */   public GT_Item_Ores(Block par1)
/* 14:   */   {
/* 15:12 */     super(par1);
/* 16:13 */     setMaxDamage(0);
/* 17:14 */     setHasSubtypes(true);
/* 18:15 */     setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/* 22:   */   {
/* 23:20 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getUnlocalizedName(ItemStack aStack)
/* 27:   */   {
/* 28:25 */     return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta)
/* 32:   */   {
/* 33:30 */     short tDamage = (short)getDamage(aStack);
/* 34:31 */     if (tDamage > 0)
/* 35:   */     {
/* 36:32 */       if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, GT_TileEntity_Ores.getHarvestData(tDamage), 3)) {
/* 37:32 */         return false;
/* 38:   */       }
/* 39:33 */       GT_TileEntity_Ores tTileEntity = (GT_TileEntity_Ores)aWorld.getTileEntity(aX, aY, aZ);
/* 40:34 */       tTileEntity.mMetaData = tDamage;
/* 41:35 */       tTileEntity.mNatural = false;
/* 42:   */     }
/* 43:37 */     else if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, 0, 3))
/* 44:   */     {
/* 45:37 */       return false;
/* 46:   */     }
/* 47:40 */     if (aWorld.getBlock(aX, aY, aZ) == this.field_150939_a)
/* 48:   */     {
/* 49:41 */       this.field_150939_a.onBlockPlacedBy(aWorld, aX, aY, aZ, aPlayer, aStack);
/* 50:42 */       this.field_150939_a.onPostBlockPlaced(aWorld, aX, aY, aZ, tDamage);
/* 51:   */     }
/* 52:44 */     return true;
/* 53:   */   }
/* 54:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Ores
 * JD-Core Version:    0.7.0.1
 */