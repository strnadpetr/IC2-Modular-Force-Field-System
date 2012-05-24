package mffs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiDirectionalUpgrade extends GuiContainer {

	private TileEntityDirectionalExtender inventory;

	public GuiDirectionalUpgrade(InventoryPlayer inventoryplayer, TileEntityDirectionalExtender tileEntity_Directional_Extender) {

		super(new Containerdummy(inventoryplayer, tileEntity_Directional_Extender));
		inventory = tileEntity_Directional_Extender;

	}

	@SuppressWarnings("unchecked")
	public void initGui() {

		controlList.add(new GuiButton(1, (width / 2) + 50, (height / 2) - 55, 10, 10, "+"));
		controlList.add(new GuiButton(2, (width / 2) + 10, (height / 2) - 55, 10, 10, "-"));

		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		if (!inventory.getActive()) {
			switch (guibutton.id) {

			case 1:
				if (inventory.getActive() == false) {

					if (inventory.getWide() < inventory.getMaxwide()) {

						inventory.setWide(inventory.getWide() + 1);
					}
				}
				break;
			case 2:
				if (inventory.getActive() == false) {

					if (inventory.getWide() > 0) {
						inventory.setWide(inventory.getWide() - 1);
					}

				}
				break;

			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/gui-upgrade.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
		int i1 = (69 * inventory.getLinkPower()) / inventory.getMaxlinkPower();
		drawTexturedModalRect(w + 93, k + 30, 176, 0, i1 + 1, 69);

	}

	protected void drawGuiContainerForegroundLayer() {

		fontRenderer.drawString("MFFD directional extender", 20, 5, 0x404040);

		fontRenderer.drawString("wide :", 20, 30, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(inventory.getWide()).toString(), 120, 30, 0x404040);

	}

}
