package mffs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_ModularForceFieldSystem;

import org.lwjgl.opengl.GL11;

public class GuiGenerator extends GuiContainer {

	private TileEntityGeneratorCore Core;

	public GuiGenerator(InventoryPlayer inventoryPlayer, TileEntityGeneratorCore tileentity) {

		super(new ContainerGenerator(inventoryPlayer, tileentity));
		Core = tileentity;

	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/GUIid.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
		int i1 = Core.gaugeFuelScaled(69);
		drawTexturedModalRect(w + 93, k + 30, 176, 0, i1 + 1, 69);

	}

	protected void drawGuiContainerForegroundLayer() {

		fontRenderer.drawString("MFFS Generator Core", 30, 5, 0x404040);
		fontRenderer.drawString("Force Power", 10, 30, 0x404040);
		fontRenderer.drawString("storage", 22, 40, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Core.getForcepower()).toString(), 100, 45, 0x404040);

		fontRenderer.drawString("transmit range:", 10, 65, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Core.getTransmitrange()).toString(), 120, 65, 0x404040);
		fontRenderer.drawString("linked projektor:", 10, 80, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Core.getLinketprojektor()).toString(), 120, 80, 0x404040);
		fontRenderer.drawString("linked projektor:", 10, 80, 0x404040);
		fontRenderer.drawString("Frequency Card:", 10, 123, 0x404040);

	}

}
