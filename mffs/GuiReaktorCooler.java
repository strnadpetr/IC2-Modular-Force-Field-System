package mffs;

import mffs.ContainerReactorCooler;
import mffs.TileEntityReaktorCooler;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GuiReaktorCooler extends GuiContainer {

	private TileEntityReaktorCooler inventory;

	public GuiReaktorCooler(InventoryPlayer inventoryplayer, TileEntityReaktorCooler tileEntityReaktorControl) {

		super(new ContainerReactorCooler(inventoryplayer, tileEntityReaktorControl));
		inventory = tileEntityReaktorControl;

	}

	@SuppressWarnings("unchecked")
	public void initGui() {

		controlList.add(new GuiButton(1, (width / 2) + 70, (height / 2) - 20, 10, 10, "+"));
		controlList.add(new GuiButton(2, (width / 2) + 10, (height / 2) - 20, 10, 10, "-"));

		controlList.add(new GuiButton(3, (width / 2) - 82, (height / 2) + 8, 40, 20, "offline"));
		controlList.add(new GuiButton(4, (width / 2) - 42, (height / 2) + 8, 40, 20, "cooler"));
		controlList.add(new GuiButton(5, (width / 2) - 2, (height / 2) + 8, 40, 20, "heater"));
		controlList.add(new GuiButton(6, (width / 2) + 38, (height / 2) + 8, 42, 20, "balance"));

		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		switch (guibutton.id) {

		case 1:
			if (inventory.getTargetheat() <= inventory.getMaxheat()) {
				inventory.setTargetheat(inventory.getTargetheat() + 100);
				if (inventory.getTargetheat() > inventory.getMaxheat()) {
					inventory.setTargetheat(inventory.getMaxheat());
				}
			}
			break;
		case 2:
			if (inventory.getTargetheat() >= 0) {
				inventory.setTargetheat(inventory.getTargetheat() - 100);
				if (inventory.getTargetheat() < 0) {
					inventory.setTargetheat(0);
				}
			}

			break;

		case 3:
			inventory.setMode(0);
			break;

		case 4:
			inventory.setMode(1);
			break;
		case 5:
			inventory.setMode(2);
			break;
		case 6:
			inventory.setMode(3);
			break;

		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/reaktorcooler.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);

	}

	protected void drawGuiContainerForegroundLayer() {

		fontRenderer.drawString("MFFD reactor heat control", 20, 8, 0x404040);

		fontRenderer.drawString("heat:", 10, 30, 0x404040);
		fontRenderer.drawString("maxheat:", 10, 45, 0x404040);
		fontRenderer.drawString("targetheat:", 10, 60, 0x404040);
		fontRenderer.drawString("control mode:", 10, 75, 0x404040);

		fontRenderer.drawString((new StringBuilder()).append(inventory.getHeat()).toString(), 120, 30, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(inventory.getMaxheat()).toString(), 120, 45, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(inventory.getTargetheat()).toString(), 120, 60, 0x404040);

		switch (inventory.getMode()) {
		case 0:
			fontRenderer.drawString((new StringBuilder()).append("offline").toString(), 120, 75, 0x404040);
			break;
		case 1:
			fontRenderer.drawString((new StringBuilder()).append("cooler").toString(), 120, 75, 0x404040);
			break;
		case 2:
			fontRenderer.drawString((new StringBuilder()).append("heater").toString(), 120, 75, 0x404040);
			break;
		case 3:
			fontRenderer.drawString((new StringBuilder()).append("balance").toString(), 120, 75, 0x404040);
			break;

		}

	}

}