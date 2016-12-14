package gregtech.common.items.armor.gui;

import gregtech.api.util.GT_LanguageManager;
import gregtech.common.items.armor.components.StatType;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiModularArmor extends GuiContainer {
	ContainerModularArmor cont;
	EntityPlayer player;

	public GuiModularArmor(ContainerModularArmor containerModularArmor,EntityPlayer aPlayer) {
		super(containerModularArmor);
		cont = containerModularArmor;
		this.player = aPlayer;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		int x2 = x - xStart;
		int y2 = y - yStart;
		drawTooltip(x2, y2 + 5);
	}

	protected void drawTooltip(int x, int y) {
		List<String> list = new ArrayList<String>();
		if (x >= 10 && x <= 17) {
			if (y >= 20 && y <= 27) {
				list.add(GT_LanguageManager.getTranslation("Weight") + ": " + cont.mInvArmor.data.mStat.get(StatType.WEIGHT));
				list.add("Total Weight: "+cont.mInvArmor.data.maxWeight);
				if (cont.mInvArmor.data.mStat.get(StatType.WEIGHT) > 1000)
					list.add("Too Heavy!");
			} else if (y >= 29 && y <= 36) {
				list.add(GT_LanguageManager.getTranslation("Physical Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.PHYSICALDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 38 && y <= 45) {
				list.add(GT_LanguageManager.getTranslation("Projectile Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.PROJECTILEDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 47 && y <= 54) {
				list.add(GT_LanguageManager.getTranslation("Fire Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.FIREDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 56 && y <= 63) {
				list.add(GT_LanguageManager.getTranslation("Magic Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.MAGICDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 65 && y <= 72) {
				list.add(GT_LanguageManager.getTranslation("Explosion Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.EXPLOSIONDEFENCE) * 1000) / 10.0) + "%");
			}
		} else if (x >= 59 && x <= 66) {
			if (y >= 20 && y <= 27) {
				list.add(GT_LanguageManager.getTranslation("Thorns") + ": " + (int) Math.round(cont.mInvArmor.data.mStat.get(StatType.THORNSSINGLE)) + " Dmg");
				list.add(GT_LanguageManager.getTranslation("Total Thorns") + ": " + (int) Math.round(cont.mInvArmor.data.mStat.get(StatType.THORNS)) + " Dmg");
			} else if (y >= 29 && y <= 36) {
				list.add(GT_LanguageManager.getTranslation("Magnet") + ": " + cont.mInvArmor.data.mStat.get(StatType.MAGNETSINGLE) + " m");
				list.add(GT_LanguageManager.getTranslation("Total Magnet") + ": " + cont.mInvArmor.data.mStat.get(StatType.MAGNET) + " m");
			} else if (y >= 38 && y <= 45) {
				list.add(GT_LanguageManager.getTranslation("Radiation Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.RADIATIONDEFENCE) * 1000) / 10.0) + "%");
				if(cont.mInvArmor.data.mBStat.get(StatType.FULLRADIATIONARMOR)){
				list.add(GT_LanguageManager.getTranslation("Radiation Immunity"));}
			} else if (y >= 47 && y <= 54) {
				list.add(GT_LanguageManager.getTranslation("Electrical Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.ELECTRICALDEFENCE) * 1000) / 10.0) + "%");
				if(cont.mInvArmor.data.mBStat.get(StatType.FULLELECTRICARMOR)){
					list.add("Electric Immunity");}
			} else if (y >= 56 && y <= 63) {
				list.add(GT_LanguageManager.getTranslation("Wither Defence") + ": " + (Math.round(cont.mInvArmor.data.mStat.get(StatType.WITHERDEFENCE) * 1000) / 10.0) + "%");
			} else if (y >= 65 && y <= 72) {
				if(cont.mInvArmor.data.type!=3){
					list.add(GT_LanguageManager.getTranslation("Fall Damage absorbtion"));
					list.add(GT_LanguageManager.getTranslation("Only for Boots"));
				}else{
				list.add(GT_LanguageManager.getTranslation("Absorbs") + " " + (int) Math.round(cont.mInvArmor.data.mStat.get(StatType.FALLDEFENCE)) + GT_LanguageManager.getTranslation("m of Fall Damage"));}
			}
		}
		if (!list.isEmpty())
			drawHoveringText(list, x, y, fontRendererObj);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("gregtech", "textures/gui/armorgui3x3.png"));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

		switch (cont.mInvArmor.data.type) {
		case 0:
			drawTexturedModalRect(xStart + 124, yStart + 67, 177, 10, 10, 9);
			break;
		case 1:
			drawTexturedModalRect(xStart + 134, yStart + 67, 187, 10, 10, 9);
			break;
		case 2:
			drawTexturedModalRect(xStart + 144, yStart + 67, 197, 10, 10, 9);
			break;
		case 3:
			drawTexturedModalRect(xStart + 154, yStart + 67, 207, 10, 10, 9);
			break;
		default:
			break;
		}
		
		// Weight: 10, 15 =191, 20
		if(getF(StatType.WEIGHT)>0){
			drawTexturedModalRect(xStart + 10, yStart + 15, 191, 20, 7, 7);
		}
		// Physical Def: 10, 24 =191, 29
		if(getF(StatType.PHYSICALDEFENCE)>0){
			drawTexturedModalRect(xStart + 10, yStart + 24, 191, 29, 7, 7);
		}
		// Projectile Def: 10, 33 =191, 38
		if(getF(StatType.PROJECTILEDEFENCE)>0){
			drawTexturedModalRect(xStart + 10, yStart + 33, 191, 38, 7, 7);
		}
		// Fire Def: 10, 42 =191, 47
		if(getF(StatType.FIREDEFENCE)>0){
			drawTexturedModalRect(xStart + 10, yStart + 42, 191, 47, 7, 7);
		}
		// Magic Def: 10, 51 =191, 56
		if(getF(StatType.MAGICDEFENCE)>0){
			drawTexturedModalRect(xStart + 10, yStart + 51, 191, 56, 7, 7);
		}
		// Explosive Def: 10, 60 =191, 65
		if(getF(StatType.EXPLOSIONDEFENCE)>0){
			drawTexturedModalRect(xStart + 10, yStart + 60, 191, 65, 7, 7);
		}
		// Thorns: 59, 15 =198, 20
		if(getF(StatType.THORNS)>0){
			drawTexturedModalRect(xStart + 59, yStart + 15, 198, 20, 7, 7);
		}
		// Magnet: 59, 24 =198, 29
		if(getF(StatType.MAGNETSINGLE)>0){
			drawTexturedModalRect(xStart + 59, yStart + 24, 198, 29, 7, 7);
		}
		// Radiation Def: 59, 33 =198, 38
		if(getF(StatType.RADIATIONDEFENCE)>0){
			drawTexturedModalRect(xStart + 59, yStart + 33, 198, 38, 7, 7);
		}
		// Electric Def: 59, 42 =198, 47
		if(getF(StatType.ELECTRICALDEFENCE)>0){
			drawTexturedModalRect(xStart + 59, yStart + 42, 198, 47, 7, 7);
		}
		// Wither Def: 59, 51 =198, 56
		if(getF(StatType.WITHERDEFENCE)>0){
			drawTexturedModalRect(xStart + 59, yStart + 51, 198, 56, 7, 7);
		}
		// Fall Reduction: 59, 60 =198, 65
		if(getF(StatType.FALLDEFENCE)>0){
			drawTexturedModalRect(xStart + 59, yStart + 60, 198, 65, 7, 7);
		}

		drawBars(10, 24, getF(StatType.PHYSICALDEFENCE));
		drawBars(10, 33, getF(StatType.PROJECTILEDEFENCE));
		drawBars(10, 42, getF(StatType.FIREDEFENCE));
		drawBars(10, 51, getF(StatType.MAGICDEFENCE));
		drawBars(10, 60, getF(StatType.EXPLOSIONDEFENCE));
		drawBars(59, 33, getF(StatType.RADIATIONDEFENCE));
		drawBars(59, 42, getF(StatType.ELECTRICALDEFENCE));
		drawBars(59, 51, getF(StatType.WITHERDEFENCE));
	}
	
	public float getF(StatType aType){
		if(cont.mInvArmor.data.mStat.containsKey(aType))
			return cont.mInvArmor.data.mStat.get(aType);
		return .0f;
	}

	public void drawBars(int x, int y, float value) {

		int bar = (int) Math.floor(35 * value);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		xStart += 8;
		yStart += 1;
		//drawRect(x + xStart, y + yStart, x + bar + xStart, y + 5 + yStart, 0x8000FF00);
		//drawRect(x + bar + xStart, y + yStart, x + 36 + xStart, y + 5 + yStart, 0x80FF0000);
		drawTexturedModalRect(xStart + x, yStart + y, 177, 78, bar, 5);
		drawTexturedModalRect(xStart+ bar + x, yStart + y, 177, 73, 35-bar, 5);
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseBtn) {
		int xStart = mouseX-((width - xSize) / 2);
		int yStart = mouseY-((height - ySize) / 2);
		if(yStart>67&&yStart<77){
			if(xStart>124&&xStart<163){
				if(xStart>124&&xStart<133&&cont.mInvArmor.data.helmet!=null){cont.mInvArmor.data.helmet.openGui=true;}
				if(xStart>134&&xStart<143&&cont.mInvArmor.data.chestplate!=null){cont.mInvArmor.data.chestplate.openGui=true;}
				if(xStart>144&&xStart<153&&cont.mInvArmor.data.leggings!=null){cont.mInvArmor.data.leggings.openGui=true;}
				if(xStart>154&&xStart<163&&cont.mInvArmor.data.boots!=null){cont.mInvArmor.data.boots.openGui=true;}
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseBtn);
	}

}
