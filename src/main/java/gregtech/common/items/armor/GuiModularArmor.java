package gregtech.common.items.armor;

import gregtech.api.util.GT_LanguageManager;

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
				list.add(GT_LanguageManager.getTranslation("weight") + ": " + cont.mInvArmor.data.weight);
				list.add("Total Weight: "+cont.mInvArmor.data.maxWeight);
				if (cont.mInvArmor.data.weight > 1000)
					list.add("Too Heavy!");
			} else if (y >= 29 && y <= 36) {
				list.add(GT_LanguageManager.getTranslation("phydef") + ": " + (Math.round(cont.mInvArmor.data.physicalDef * 1000) / 10.0) + "%");
			} else if (y >= 38 && y <= 45) {
				list.add(GT_LanguageManager.getTranslation("prodef") + ": " + (Math.round(cont.mInvArmor.data.projectileDef * 1000) / 10.0) + "%");
			} else if (y >= 47 && y <= 54) {
				list.add(GT_LanguageManager.getTranslation("firedef") + ": " + (Math.round(cont.mInvArmor.data.fireDef * 1000) / 10.0) + "%");
			} else if (y >= 56 && y <= 63) {
				list.add(GT_LanguageManager.getTranslation("magdef") + ": " + (Math.round(cont.mInvArmor.data.magicDef * 1000) / 10.0) + "%");
			} else if (y >= 65 && y <= 72) {
				list.add(GT_LanguageManager.getTranslation("expdef") + ": " + (Math.round(cont.mInvArmor.data.explosionDef * 1000) / 10.0) + "%");
			}
		} else if (x >= 59 && x <= 66) {
			if (y >= 20 && y <= 27) {
				list.add(GT_LanguageManager.getTranslation("thorns") + ": " + (int) Math.round(cont.mInvArmor.data.thornsSingle) + " Dmg");
				list.add("Total "+GT_LanguageManager.getTranslation("thorns") + ": " + (int) Math.round(cont.mInvArmor.data.thorns) + " Dmg");
			} else if (y >= 29 && y <= 36) {
				list.add(GT_LanguageManager.getTranslation("magnet") + ": " + cont.mInvArmor.data.magnetSingle + " m");
				list.add("Total "+GT_LanguageManager.getTranslation("magnet") + ": " + cont.mInvArmor.data.magnet + " m");
			} else if (y >= 38 && y <= 45) {
				list.add(GT_LanguageManager.getTranslation("raddef") + ": " + (Math.round(cont.mInvArmor.data.radiationDef * 1000) / 10.0) + "%");
				if(cont.mInvArmor.data.fullRadiationDef){
				list.add("Radiation Immunity");}
			} else if (y >= 47 && y <= 54) {
				list.add(GT_LanguageManager.getTranslation("eledef") + ": " + (Math.round(cont.mInvArmor.data.electricDef * 1000) / 10.0) + "%");
				if(cont.mInvArmor.data.fullElectricDef){
					list.add("Electric Immunity");}
			} else if (y >= 56 && y <= 63) {
				list.add(GT_LanguageManager.getTranslation("whidef") + ": " + (Math.round(cont.mInvArmor.data.witherDef * 1000) / 10.0) + "%");
			} else if (y >= 65 && y <= 72) {
				if(cont.mInvArmor.data.type!=3){
					list.add("Fall Damage absorbtion");
					list.add("Only for Boots");
				}else{
				list.add(GT_LanguageManager.getTranslation("abs1") + " " + (int) Math.round(cont.mInvArmor.data.fallDef) + GT_LanguageManager.getTranslation("abs2"));}
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
		if(cont.mInvArmor.data.weight>0){
			drawTexturedModalRect(xStart + 10, yStart + 15, 191, 20, 7, 7);
		}
		// Physical Def: 10, 24 =191, 29
		if(cont.mInvArmor.data.physicalDef>0){
			drawTexturedModalRect(xStart + 10, yStart + 24, 191, 29, 7, 7);
		}
		// Projectile Def: 10, 33 =191, 38
		if(cont.mInvArmor.data.projectileDef>0){
			drawTexturedModalRect(xStart + 10, yStart + 33, 191, 38, 7, 7);
		}
		// Fire Def: 10, 42 =191, 47
		if(cont.mInvArmor.data.fireDef>0){
			drawTexturedModalRect(xStart + 10, yStart + 42, 191, 47, 7, 7);
		}
		// Magic Def: 10, 51 =191, 56
		if(cont.mInvArmor.data.magicDef>0){
			drawTexturedModalRect(xStart + 10, yStart + 51, 191, 56, 7, 7);
		}
		// Explosive Def: 10, 60 =191, 65
		if(cont.mInvArmor.data.explosionDef>0){
			drawTexturedModalRect(xStart + 10, yStart + 60, 191, 65, 7, 7);
		}
		// Thorns: 59, 15 =198, 20
		if(cont.mInvArmor.data.thorns>0){
			drawTexturedModalRect(xStart + 59, yStart + 15, 198, 20, 7, 7);
		}
		// Magnet: 59, 24 =198, 29
		if(cont.mInvArmor.data.magnetSingle>0){
			drawTexturedModalRect(xStart + 59, yStart + 24, 198, 29, 7, 7);
		}
		// Radiation Def: 59, 33 =198, 38
		if(cont.mInvArmor.data.radiationDef>0){
			drawTexturedModalRect(xStart + 59, yStart + 33, 198, 38, 7, 7);
		}
		// Electric Def: 59, 42 =198, 47
		if(cont.mInvArmor.data.electricDef>0){
			drawTexturedModalRect(xStart + 59, yStart + 42, 198, 47, 7, 7);
		}
		// Wither Def: 59, 51 =198, 56
		if(cont.mInvArmor.data.witherDef>0){
			drawTexturedModalRect(xStart + 59, yStart + 51, 198, 56, 7, 7);
		}
		// Fall Reduction: 59, 60 =198, 65
		if(cont.mInvArmor.data.fallDef>0){
			drawTexturedModalRect(xStart + 59, yStart + 60, 198, 65, 7, 7);
		}

		drawBars(10, 24, cont.mInvArmor.data.physicalDef);
		drawBars(10, 33, cont.mInvArmor.data.projectileDef);
		drawBars(10, 42, cont.mInvArmor.data.fireDef);
		drawBars(10, 51, cont.mInvArmor.data.magicDef);
		drawBars(10, 60, cont.mInvArmor.data.explosionDef);
		drawBars(59, 33, cont.mInvArmor.data.radiationDef);
		drawBars(59, 42, cont.mInvArmor.data.electricDef);
		drawBars(59, 51, cont.mInvArmor.data.witherDef);
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
				if(xStart>124&&xStart<133&&cont.mInvArmor.data.helmet!=null){cont.mInvArmor.data.helmet.openGui=true;player.closeScreen();}
				if(xStart>134&&xStart<143&&cont.mInvArmor.data.chestplate!=null){cont.mInvArmor.data.chestplate.openGui=true;player.closeScreen();}
				if(xStart>144&&xStart<153&&cont.mInvArmor.data.leggings!=null){cont.mInvArmor.data.leggings.openGui=true;player.closeScreen();}
				if(xStart>154&&xStart<163&&cont.mInvArmor.data.boots!=null){cont.mInvArmor.data.boots.openGui=true;player.closeScreen();}
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseBtn);
	}

}
