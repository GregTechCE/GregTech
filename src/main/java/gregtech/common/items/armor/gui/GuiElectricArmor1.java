package gregtech.common.items.armor.gui;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Network;
import gregtech.common.items.armor.ArmorData;
import gregtech.common.items.armor.components.StatType;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

@SideOnly(Side.CLIENT)
public class GuiElectricArmor1 extends GuiContainer {
	ContainerModularArmor cont;
	EntityPlayer player;
	private int tab;
	
	public GuiElectricArmor1(ContainerModularArmor containerModularArmor, EntityPlayer aPlayer) {
		super(containerModularArmor);
		player = aPlayer;
		cont = containerModularArmor;
		tab = 0;
	}
	@Override
	public void onGuiClosed() 
	{
		cont.saveInventory(player);
		super.onGuiClosed();
	};
	public String seperateNumber(long number){
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		return formatter.format(number);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		int x2 = x - xStart;
		int y2 = y - yStart;
		drawTooltip(x2, y2 + 5);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("gregtech", "textures/gui/armorgui3x4.png"));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		//Draw background
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		//Draw active arrows
		drawTexturedModalRect(xStart + 10, yStart + 70, 219, 11, 14, 5);
		//Draw active armor symbol
		switch (cont.mInvArmor.data.type) {
		case 0:
			drawTexturedModalRect(xStart + 73, yStart + 68, 177, 10, 10, 9);
			break;
		case 1:
			drawTexturedModalRect(xStart + 83, yStart + 68, 187, 10, 10, 9);
			break;
		case 2:
			drawTexturedModalRect(xStart + 93, yStart + 68, 197, 10, 10, 9);
			break;
		case 3:
			drawTexturedModalRect(xStart + 103, yStart + 68, 207, 10, 10, 9);
			break;
		default:
			break;
		}
		//Draw active tab
		switch(tab){
		case 0:
			break;
		case 1:
			drawTexturedModalRect(xStart + 7, yStart + 14, 2, 167, 104, 54);
			break;
		case 2:
			drawTexturedModalRect(xStart + 7, yStart + 14, 107, 167, 104, 54);
			break;
		default:
			break;
		}
		float tankCap = cont.mInvArmor.data.mStat.containsKey(StatType.TANKCAP) ? cont.mInvArmor.data.mStat.get(StatType.TANKCAP) :0.0f;
		if(tankCap>0){
			drawTexturedModalRect(xStart + 94, yStart + 32, 231, 69, 16, 34);
		}
		float weight = cont.mInvArmor.data.mStat.containsKey(StatType.WEIGHT) ? cont.mInvArmor.data.mStat.get(StatType.WEIGHT) : 0.0f;
		int bar = (int) Math.floor(18 * (weight)/(float)1000);
		drawTexturedModalRect(xStart + 15, yStart + 7, 217, 26, bar, 5);
		drawTexturedModalRect(xStart + bar + 15, yStart + 7, 197+bar, 26, 18-bar, 5);
		
		if(tab==0){
			//processing power bar
			bar = Math.min((int) Math.floor(52 * ((float)cont.mInvArmor.data.mStat.get(StatType.PROCESSINGPOWERUSED)/(float)cont.mInvArmor.data.mStat.get(StatType.PROCESSINGPOWER))),52);
			drawTexturedModalRect(xStart + 17, yStart + 17, 177, 146, bar, 5);
			drawTexturedModalRect(xStart + bar + 17, yStart + 17, 177+bar, 139, 52-bar, 5);
		}else if(tab==1){
			
		}else{
			//Def tab values
			if(cont.mInvArmor.data.mStat.get(StatType.PHYSICALDEFENCE)>0)drawTexturedModalRect(xStart + 30, yStart + 20, 186, 33, 7, 7);
			drawBars(31, 20, cont.mInvArmor.data.mStat.get(StatType.PHYSICALDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.PROJECTILEDEFENCE)>0)drawTexturedModalRect(xStart + 30, yStart + 29, 186, 42, 7, 7);
			drawBars(31, 29, cont.mInvArmor.data.mStat.get(StatType.PROJECTILEDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.FIREDEFENCE)>0)drawTexturedModalRect(xStart + 30, yStart + 38, 186, 51, 7, 7);
			drawBars(31, 38, cont.mInvArmor.data.mStat.get(StatType.FIREDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.MAGICDEFENCE)>0)drawTexturedModalRect(xStart + 30, yStart + 47, 186, 60, 7, 7);
			drawBars(31, 47, cont.mInvArmor.data.mStat.get(StatType.MAGICDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.EXPLOSIONDEFENCE)>0)drawTexturedModalRect(xStart + 30, yStart + 56, 186, 69, 7, 7);
			drawBars(31, 56, cont.mInvArmor.data.mStat.get(StatType.EXPLOSIONDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.RADIATIONDEFENCE)>0)drawTexturedModalRect(xStart + 61, yStart + 20, 186, 87, 7, 7);
			drawBars(62, 20, cont.mInvArmor.data.mStat.get(StatType.RADIATIONDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.ELECTRICALDEFENCE)>0)drawTexturedModalRect(xStart + 61, yStart + 29, 186, 96, 7, 7);
			drawBars(62, 29, cont.mInvArmor.data.mStat.get(StatType.ELECTRICALDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.WITHERDEFENCE)>0)drawTexturedModalRect(xStart + 61, yStart + 38, 186, 105, 7, 7);
			drawBars(62, 38, cont.mInvArmor.data.mStat.get(StatType.WITHERDEFENCE));
			if(cont.mInvArmor.data.mStat.get(StatType.FALLDEFENCE)>0)drawTexturedModalRect(xStart + 61, yStart + 47, 186, 114, 7, 7);
			if(cont.mInvArmor.data.mStat.get(StatType.THORNS)>0)drawTexturedModalRect(xStart + 61, yStart + 56, 186, 123, 7, 7);
			if(cont.mInvArmor.data.mStat.get(StatType.MAGNET)>0)drawTexturedModalRect(xStart + 70, yStart + 56, 186, 78, 7, 7);
		}
		
		
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseBtn) {
		int xStart = mouseX-((width - xSize) / 2);
		int yStart = mouseY-((height - ySize) / 2);
		if(yStart>68&&yStart<77){
			if(xStart>18&&xStart<26){
				tab++;
			}else if(xStart>8&&xStart<17){
				tab--;
			}
			if(tab>2){tab=0;}
			if(tab<0){tab=2;}
			if(xStart>72&&xStart<112){
				if(xStart>72&&xStart<81&&cont.mInvArmor.data.helmet!=null){cont.mInvArmor.data.helmet.openGui=true;}
				if(xStart>82&&xStart<91&&cont.mInvArmor.data.chestplate!=null){cont.mInvArmor.data.chestplate.openGui=true;}
				if(xStart>92&&xStart<101&&cont.mInvArmor.data.leggings!=null){cont.mInvArmor.data.leggings.openGui=true;}
				if(xStart>102&&xStart<112&&cont.mInvArmor.data.boots!=null){cont.mInvArmor.data.boots.openGui=true;}
				
//				if(xStart>72&&xStart<81&&cont.mInvArmor.data.helmet!=null){cont.mInvArmor.data.helmet.openGui=true;player.closeScreen();}
//				if(xStart>82&&xStart<91&&cont.mInvArmor.data.chestplate!=null){cont.mInvArmor.data.chestplate.openGui=true;player.closeScreen();}
//				if(xStart>92&&xStart<101&&cont.mInvArmor.data.leggings!=null){cont.mInvArmor.data.leggings.openGui=true;player.closeScreen();}
//				if(xStart>102&&xStart<112&&cont.mInvArmor.data.boots!=null){cont.mInvArmor.data.boots.openGui=true;player.closeScreen();}
			}
		}
//		Slot slot = getSlotAtPosition(mouseX, mouseY);
//		if (slot != null && slot instanceof SlotFluid && player != null && cont.mInvArmor.data.helmet!=null) {
//			ItemStack tmp = player.inventory.getItemStack();
//			//GT_Network gtn = new GT_Network();
//			if (tmp == null) {
//				//GT_Values.NW.sendToServer(new FluidSync(player.getCommandSenderName(), 0, "null"));				
//			}
//			//ArmorData armorData = new ArmorData(player,);
//			if (tmp != null && tmp.getItem() instanceof IFluidContainerItem) {
//				FluidStack tmp2 = ((IFluidContainerItem) tmp.getItem()).getFluid(tmp);
//				if (!slot.getHasStack() && tmp2 != null) {
//					slot.putStack(GT_Utility.getFluidDisplayStack(tmp2, true));					
//					
//					if(cont.mInvArmor.data.helmet.fluid == null)
//					{
//						cont.mInvArmor.data.helmet.fluid = new FluidStack(tmp2.getFluid(), tmp2.amount);
//					}
//					else
//					{
//						cont.mInvArmor.data.helmet.fluid.amount += tmp2.amount;
//					}
//					
//					//GT_Values.NW.sendToServer(new FluidSync(player.getCommandSenderName(), tmp2.amount, GT_Utility.getFluidName(tmp2, false)));
//					ItemStack tmp4 = GT_Utility.getContainerForFilledItem(tmp, true);					
//					tmp4.stackSize = 1;
//					if (tmp.stackSize > 1) {
//						player.inventory.addItemStackToInventory(tmp4);
//						tmp.stackSize--;
//						player.inventory.setItemStack(tmp);
//						//GT_Values.NW.sendToServer(new FluidSync2(player.getCommandSenderName()));
//					} else {
//						player.inventory.setItemStack(null);
//						player.inventory.addItemStackToInventory(tmp4);
//						//GT_Values.NW.sendToServer(new FluidSync2(player.getCommandSenderName()));
//					}
//
//				} else if (slot.getHasStack() && tmp2 != null) {
//					Item fluidSlot = slot.getStack().getItem();
//					if (fluidSlot.getDamage(slot.getStack()) == tmp2.getFluidID()) {
//						NBTTagCompound nbt = slot.getStack().getTagCompound();
//						if (nbt != null) {
//							tmp2.amount += nbt.getLong("mFluidDisplayAmount");
//							ItemStack tmp3 = player.inventory.getItemStack();
//							if (tmp3.stackSize <= 1) {
//								tmp3 = null;
//							} else {
//								tmp3.stackSize--;
//							}
//							player.inventory.setItemStack(tmp3);
//							//GT_Values.NW.sendToServer(new FluidSync2(player.getCommandSenderName()));
//							slot.putStack(GT_Utility.getFluidDisplayStack(tmp2, true));
//							if(cont.mInvArmor.data.helmet.fluid == null)
//							{
//								cont.mInvArmor.data.helmet.fluid = new FluidStack(tmp2.getFluid(), tmp2.amount);
//							}
//							else
//							{
//								cont.mInvArmor.data.helmet.fluid.amount += tmp2.amount;
//							}
//							//GT_Values.NW.sendToServer(new FluidSync(player.getCommandSenderName(), tmp2.amount, GT_Utility.getFluidName(tmp2, false)));
//						}
//					}
//				}
//			}
//		}
		super.mouseClicked(mouseX, mouseY, mouseBtn);
	}
	
	protected void drawTooltip(int x, int y) {
		List<String> list = new ArrayList<String>();
		//General tooltips
		if(x>=7&&y>=11&&x<=33&&y<=17){
				list.add(GT_LanguageManager.getTranslation("Weight") + ": " + cont.mInvArmor.data.mStat.get(StatType.WEIGHT));
				list.add("Total Weight: "+cont.mInvArmor.data.maxWeight);
				if (cont.mInvArmor.data.mStat.get(StatType.WEIGHT) > 1000)
					list.add("Too Heavy!");
		}
		if(x>=56&&y>=11&&x<=112&&y<=17){
			list.add("Stored Energy: "+seperateNumber(cont.mInvArmor.data.charge)+" EU");
		}
		if(y>74&&y<83){
			if(x>8&&x<17){
				list.add("Previous Page");
			}else if(x>18&&x<27){
				list.add("Next Page");
			}else if(x>72&&x<80){
				list.add("Helmet");
			}else if(x>81&&x<90){
				list.add("Chestplate");
			}else if(x>91&&x<100){
				list.add("Leggings");
			}else if(x>101&&x<110){
				list.add("Boots");
			}
		}
		if(tab==0){
			if(x>=93&&y>=36&&x<=110&&y<=71){list.add("Tank Capacity: "+cont.mInvArmor.data.mStat.get(StatType.TANKCAP)+"L");
			}
			if(x>=7&&y>=22&&x<=70&&y<=28){list.add("Processing Power Provided: "+cont.mInvArmor.data.mStat.get(StatType.PROCESSINGPOWER));
										  list.add("Processing Power Used: "+cont.mInvArmor.data.mStat.get(StatType.PROCESSINGPOWERUSED));
			}
		}else if(tab==1){
			
		}else{
					if (x >= 28 && x <= 58) {
			if (y >= 25 && y <= 32) {
				list.add(GT_LanguageManager.getTranslation("Physical Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.PHYSICALDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 33 && y <= 41) {
				list.add(GT_LanguageManager.getTranslation("Projectile Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.PROJECTILEDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 42 && y <= 50) {
				list.add(GT_LanguageManager.getTranslation("Fire Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.FIREDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 51 && y <= 59) {
				list.add(GT_LanguageManager.getTranslation("Magical Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.MAGICDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 60 && y <= 68) {
				list.add(GT_LanguageManager.getTranslation("Explosion Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.EXPLOSIONDEFENCE) * 1000) / 10.0) + "%");
			}
		} else if (x >= 59 && x <= 90) {
			if (y >= 25 && y <= 32) {
				list.add(GT_LanguageManager.getTranslation("Radiation Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.RADIATIONDEFENCE) * 1000) / 10.0) + "%");
				if(cont.mInvArmor.data.mBStat.get(StatType.FULLRADIATIONARMOR)){
				list.add(GT_LanguageManager.getTranslation("Radiation Immunity"));}
			} else if (y >= 33 && y <= 41) {
				list.add(GT_LanguageManager.getTranslation("Electrical Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.ELECTRICALDEFENCE) * 1000) / 10.0) + "%");
				if(cont.mInvArmor.data.mBStat.get(StatType.FULLELECTRICARMOR)){
					list.add(GT_LanguageManager.getTranslation("Electric Immunity"));}
			} else if (y >= 42 && y <= 50) {
				list.add(GT_LanguageManager.getTranslation("Wither Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.WITHERDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 51 && y <= 59) {
				if(cont.mInvArmor.data.type!=3){
					list.add(GT_LanguageManager.getTranslation("Fall Damage absorbtion"));
					list.add(GT_LanguageManager.getTranslation("Only for Boots"));
				}else{
				list.add(GT_LanguageManager.getTranslation("Absorbs") + " " + (int) Math.round(cont.mInvArmor.data.mStat.get(StatType.FALLDEFENCE)) + GT_LanguageManager.getTranslation("m of Fall Damage"));}
			} else if (y >= 60 && y <= 68) {
				if(x<69){
				list.add(GT_LanguageManager.getTranslation("Thorns") + ": " + (int) Math.round(cont.mInvArmor.data.mStat.get(StatType.THORNSSINGLE)) + " Dmg");
				list.add(GT_LanguageManager.getTranslation("Total Thorns") + ": " + (int) Math.round(cont.mInvArmor.data.mStat.get(StatType.THORNS)) + " Dmg");
				}else{
				list.add(GT_LanguageManager.getTranslation("Magnet") + ": " + cont.mInvArmor.data.mStat.get(StatType.MAGNETSINGLE) + " m");
				list.add(GT_LanguageManager.getTranslation("Total Magnet") + ": " + cont.mInvArmor.data.mStat.get(StatType.MAGNET) + " m");}
			}
		}
		}
		if (!list.isEmpty())
			drawHoveringText(list, x, y, fontRendererObj);
	}
	
	public void drawBars(int x, int y, float value) {

		int bar = (int) Math.floor(18 * value);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		xStart += 8;
		yStart += 1;
		drawTexturedModalRect(xStart + x, yStart + y, 217, 26, bar, 5);
		drawTexturedModalRect(xStart+ bar + x, yStart + y, 197+bar, 26, 18-bar, 5);
	}

	protected Slot getSlotAtPosition(int p_146975_1_, int p_146975_2_) {
		for (int k = 0; k < cont.inventorySlots.size(); ++k) {
			Slot slot = (Slot) cont.inventorySlots.get(k);
			if (this.isMouseOverSlot(slot, p_146975_1_, p_146975_2_)) {
				return slot;
			}
		}
		return null;
	}

	private boolean isMouseOverSlot(Slot p_146981_1_, int p_146981_2_, int p_146981_3_) {
		return this.func_146978_c(p_146981_1_.xDisplayPosition, p_146981_1_.yDisplayPosition, 16, 16, p_146981_2_, p_146981_3_);
	}
}
