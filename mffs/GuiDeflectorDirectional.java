package mffs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiDeflectorDirectional extends GuiContainer {

	private TileEntityDeflectorProjektor inventory;

	public GuiDeflectorDirectional(InventoryPlayer inventoryplayer, TileEntityDeflectorProjektor tileEntity) {

		super(new ContainerProjektor(inventoryplayer, tileEntity));
		inventory = tileEntity;

	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		controlList.add(new GuiButton(0, (width / 2) + 50, (height / 2) - 10, 10, 10, "+"));
		controlList.add(new GuiButton(1, (width / 2) + 10, (height / 2) - 10, 10, 10, "-"));
		controlList.add(new GuiButton(2, (width / 2) + 50, (height / 2) - 0, 10, 10, "+"));
		controlList.add(new GuiButton(3, (width / 2) + 10, (height / 2) - 0, 10, 10, "-"));
		controlList.add(new GuiButton(4, (width / 2) + 50, (height / 2) + 10, 10, 10, "+"));
		controlList.add(new GuiButton(5, (width / 2) + 10, (height / 2) + 10, 10, 10, "-"));

		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		if (!inventory.getActive()) {
			switch (guibutton.id) {

			case 0:
				if (inventory.getActive() == false) {

					if (inventory.getlengthx() < inventory.getMaxlengthx()) {

						inventory.setlengthx(inventory.getlengthx() + 1);
					}
				}
				break;
			case 1:
				if (inventory.getActive() == false) {

					if (inventory.getlengthx() > 0) {
						inventory.setlengthx(inventory.getlengthx() - 1);
					}

				}
				break;

			case 2:
				if (inventory.getActive() == false) {

					if (inventory.getlengthz() < inventory.getMaxlengthz()) {

						inventory.setlengthz(inventory.getlengthz() + 1);
					}
				}
				break;
			case 3:
				if (inventory.getActive() == false) {

					if (inventory.getlengthz() > 0) {
						inventory.setlengthz(inventory.getlengthz() - 1);
					}

				}
				break;

			case 4:
				if (inventory.getActive() == false) {

					if (inventory.getDistance() < inventory.getMaxdistance()) {
						inventory.setDistance(inventory.getDistance() + 1);
					}
				}
				break;
			case 5:
				if (inventory.getActive() == false) {

					if (inventory.getDistance() > 0) {
						inventory.setDistance(inventory.getDistance() - 1);
					}

				}
				break;

			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/GUIid.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
		int i1 = (69 * inventory.getLinkPower()) / inventory.getMaxlinkPower();
		drawTexturedModalRect(w + 93, k + 30, 176, 0, i1 + 1, 69);

	}

	protected void drawGuiContainerForegroundLayer() {

		fontRenderer.drawString("MFFS Deflector", 40, 5, 0x404040);
		fontRenderer.drawString("Force Power", 10, 30, 0x404040);
		fontRenderer.drawString("available", 22, 40, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.getLinkPower()).toString(), 100, 45, 0x404040);
		fontRenderer.drawString("linked to generator:", 10, 60, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.isLinkGenerator()).toString(), 120, 60, 0x404040);
		fontRenderer.drawString("Frequency Card:", 10, 123, 0x404040);

		fontRenderer.drawString("wide x:", 10, 75, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(inventory.getlengthx()).toString(), 120, 75, 0x404040);
		fontRenderer.drawString("wide z:", 10, 85, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(inventory.getlengthz()).toString(), 120, 85, 0x404040);
		fontRenderer.drawString("distance:", 10, 95, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(inventory.getDistance()).toString(), 120, 95, 0x404040);
	}

}
