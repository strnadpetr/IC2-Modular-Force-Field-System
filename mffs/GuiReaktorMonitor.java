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

public class GuiReaktorMonitor extends GuiContainer {

	private TileEntityReaktorMonitor Monitor;

	public GuiReaktorMonitor(InventoryPlayer inventoryPlayer, TileEntityReaktorMonitor tileentity) {

		super(new ContainerReaktorMonitor(inventoryPlayer, tileentity));
		Monitor = tileentity;

	}

	public void initGui() {

		controlList.add(new GuiButton(0, (width / 2) + 30, (height / 2) - 50, 10, 10, "+"));
		controlList.add(new GuiButton(1, (width / 2) - 20, (height / 2) - 50, 10, 10, "-"));
		controlList.add(new GuiButton(2, (width / 2) + 30, (height / 2) - 35, 10, 10, "+"));
		controlList.add(new GuiButton(3, (width / 2) - 20, (height / 2) - 35, 10, 10, "-"));
		controlList.add(new GuiButton(4, (width / 2) + 30, (height / 2) - 20, 10, 10, "+"));
		controlList.add(new GuiButton(5, (width / 2) - 20, (height / 2) - 20, 10, 10, "-"));
		controlList.add(new GuiButton(6, (width / 2) + 30, (height / 2) - 5, 10, 10, "+"));
		controlList.add(new GuiButton(7, (width / 2) - 20, (height / 2) - 5, 10, 10, "-"));
		controlList.add(new GuiButton(8, (width / 2) + 30, (height / 2) + 10, 10, 10, "+"));
		controlList.add(new GuiButton(9, (width / 2) - 20, (height / 2) + 10, 10, 10, "-"));

		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		switch (guibutton.id) {

		case 0:
			if (Monitor.getTargetheat(0) <= Monitor.getMaxheat()) {
				Monitor.setTargetheat(0, Monitor.getTargetheat(0) + 100);
				if (Monitor.getTargetheat(0) > Monitor.getMaxheat()) {
					Monitor.setTargetheat(0, Monitor.getMaxheat());
				}
			}
			break;
		case 1:
			if (Monitor.getTargetheat(0) >= 0) {
				Monitor.setTargetheat(0, Monitor.getTargetheat(0) - 100);
				if (Monitor.getTargetheat(0) < 0) {
					Monitor.setTargetheat(0, 0);
				}
			}
			break;
		case 2:
			if (Monitor.getTargetheat(1) <= Monitor.getMaxheat()) {
				Monitor.setTargetheat(1, Monitor.getTargetheat(1) + 100);
				if (Monitor.getTargetheat(1) > Monitor.getMaxheat()) {
					Monitor.setTargetheat(1, Monitor.getMaxheat());
				}
			}
			break;
		case 3:
			if (Monitor.getTargetheat(1) >= 0) {
				Monitor.setTargetheat(1, Monitor.getTargetheat(1) - 100);
				if (Monitor.getTargetheat(1) < 0) {
					Monitor.setTargetheat(1, 0);
				}
			}
			break;
		case 4:
			if (Monitor.getTargetheat(2) <= Monitor.getMaxheat()) {
				Monitor.setTargetheat(2, Monitor.getTargetheat(2) + 100);
				if (Monitor.getTargetheat(2) > Monitor.getMaxheat()) {
					Monitor.setTargetheat(2, Monitor.getMaxheat());
				}
			}
			break;
		case 5:
			if (Monitor.getTargetheat(2) >= 0) {
				Monitor.setTargetheat(2, Monitor.getTargetheat(2) - 100);
				if (Monitor.getTargetheat(2) < 0) {
					Monitor.setTargetheat(2, 0);
				}
			}
			break;
		case 6:
			if (Monitor.getTargetheat(3) <= Monitor.getMaxheat()) {
				Monitor.setTargetheat(3, Monitor.getTargetheat(3) + 100);
				if (Monitor.getTargetheat(3) > Monitor.getMaxheat()) {
					Monitor.setTargetheat(3, Monitor.getMaxheat());
				}
			}
			break;
		case 7:
			if (Monitor.getTargetheat(3) >= 0) {
				Monitor.setTargetheat(3, Monitor.getTargetheat(3) - 100);
				if (Monitor.getTargetheat(3) < 0) {
					Monitor.setTargetheat(3, 0);
				}
			}
			break;
		case 8:
			if (Monitor.getTargetheat(4) <= Monitor.getMaxheat()) {
				Monitor.setTargetheat(4, Monitor.getTargetheat(4) + 100);
				if (Monitor.getTargetheat(4) > Monitor.getMaxheat()) {
					Monitor.setTargetheat(4, Monitor.getMaxheat());
				}
			}
			break;
		case 9:
			if (Monitor.getTargetheat(4) >= 0) {
				Monitor.setTargetheat(4, Monitor.getTargetheat(4) - 100);
				if (Monitor.getTargetheat(4) < 0) {
					Monitor.setTargetheat(4, 0);
				}
			}
			break;
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/GUImonitor.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);

	}

	protected void drawGuiContainerForegroundLayer() {

		fontRenderer.drawString("MFFS Reaktor Monitor", 30, 5, 0x404040);

		fontRenderer.drawString("Channel 0:", 10, 20, 0x404040);
		fontRenderer.drawString("Reaktor working", 80, 20, 0x404040);

		fontRenderer.drawString("Channel 1:", 10, 35, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitor.getTargetheat(0)).toString(), 80, 35, 0x404040);
		fontRenderer.drawString("heat", 140, 35, 0x404040);

		fontRenderer.drawString("Channel 2:", 10, 50, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitor.getTargetheat(1)).toString(), 80, 50, 0x404040);
		fontRenderer.drawString("heat", 140, 50, 0x404040);

		fontRenderer.drawString("Channel 3:", 10, 65, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitor.getTargetheat(2)).toString(), 80, 65, 0x404040);
		fontRenderer.drawString("heat", 140, 65, 0x404040);

		fontRenderer.drawString("Channel 4:", 10, 80, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitor.getTargetheat(3)).toString(), 80, 80, 0x404040);
		fontRenderer.drawString("heat", 140, 80, 0x404040);

		fontRenderer.drawString("Channel 5:", 10, 95, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitor.getTargetheat(4)).toString(), 80, 95, 0x404040);
		fontRenderer.drawString("heat", 140, 95, 0x404040);

		fontRenderer.drawString((new StringBuilder()).append(Monitor.getMontorname()).toString(), 10, 110, 0x404040);

		fontRenderer.drawString("InsertCardblank:", 5, 123, 0x404040);

	}

}
