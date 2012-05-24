package mffs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.mod_ModularForceFieldSystem;

import org.lwjgl.opengl.GL11;

public class GuiCamouflageUpgrade extends GuiContainer {

	private TileEntityCamoflageUpgrade inventory;

	public GuiCamouflageUpgrade(InventoryPlayer inventoryplayer, TileEntityCamoflageUpgrade tileEntity_Camoflage_Upgrade) {

		super(new ContainerCamoflage(inventoryplayer, tileEntity_Camoflage_Upgrade));
		inventory = tileEntity_Camoflage_Upgrade;

	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/camo.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
	}

	protected void drawGuiContainerForegroundLayer() {

		fontRenderer.drawString("MFFD Camouflaage Upgrade", 20, 5, 0x404040);

		fontRenderer.drawString("Block pattern:", 20, 35, 0x404040);

		if (mod_ModularForceFieldSystem.idtotextur.get(inventory.getItem_ID()) != null) {
			fontRenderer.drawString("OK", 140, 35, 0x404040);
		}

	}

}
