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

public class GuiReaktorMonitorClient extends GuiContainer {

	private TileEntityReaktorMonitorClient Monitorclient;

	public GuiReaktorMonitorClient(InventoryPlayer inventoryPlayer, TileEntityReaktorMonitorClient tileentity) {

		super(new ContainerReaktorMonitorClient(inventoryPlayer, tileentity));
		Monitorclient = tileentity;

	}

	public void initGui() {

		controlList.add(new GuiButton(0, (width / 2) - 85, (height / 2) - 10, 55, 20, "Channel 0"));
		controlList.add(new GuiButton(1, (width / 2) - 30, (height / 2) - 10, 55, 20, "Channel 1"));
		controlList.add(new GuiButton(2, (width / 2) + 25, (height / 2) - 10, 55, 20, "Channel 2"));
		controlList.add(new GuiButton(3, (width / 2) - 85, (height / 2) + 15, 55, 20, "Channel 3"));
		controlList.add(new GuiButton(4, (width / 2) - 30, (height / 2) + 15, 55, 20, "Channel 4"));
		controlList.add(new GuiButton(5, (width / 2) + 25, (height / 2) + 15, 55, 20, "Channel 5"));

		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		switch (guibutton.id) {

		case 0:
			if (Monitorclient.getUsechannel() != 0) {
				Monitorclient.setUsechannel(0);
			}
			break;
		case 1:
			if (Monitorclient.getUsechannel() != 1) {
				Monitorclient.setUsechannel(1);
			}
			break;
		case 2:
			if (Monitorclient.getUsechannel() != 2) {
				Monitorclient.setUsechannel(2);
			}
			break;
		case 3:
			if (Monitorclient.getUsechannel() != 3) {
				Monitorclient.setUsechannel(3);
			}
			break;
		case 4:
			if (Monitorclient.getUsechannel() != 4) {
				Monitorclient.setUsechannel(4);
			}
			break;
		case 5:
			if (Monitorclient.getUsechannel() != 5) {
				Monitorclient.setUsechannel(5);
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

		fontRenderer.drawString("MFFS Reaktor Monitor Client", 10, 5, 0x404040);

		fontRenderer.drawString("Linkmonitor:", 10, 20, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitorclient.isLinkMonitor()).toString(), 100, 20, 0x404040);

		fontRenderer.drawString((new StringBuilder()).append(" ").append(Monitorclient.getMontorname()).toString(), 10, 35, 0x404040);

		fontRenderer.drawString("current channel:", 10, 50, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(Monitorclient.getUsechannel()).toString(), 100, 50, 0x404040);

		fontRenderer.drawString("---channel switch---", 30, 65, 0x404040);

		fontRenderer.drawString("InsertLinkCard:", 5, 123, 0x404040);

	}

}
