package mffs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiReaktorField extends GuiContainer {

	private TileEntityReaktorField inventory;
	private String mode;

	public GuiReaktorField(InventoryPlayer inventoryplayer, TileEntityReaktorField tileEntityReaktorField) {

		super(new ContainerProjektor(inventoryplayer, tileEntityReaktorField));
		inventory = tileEntityReaktorField;

	}

	@SuppressWarnings("unchecked")
	public void initGui() {

		controlList.add(new GuiButton(0, (width / 2) + 35, (height / 2) + 20, 40, 10, "change"));

		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		if (!inventory.getActive()) {
			switch (guibutton.id) {

			case 0:
				if (inventory.getActive() == false) {

					if (inventory.isWatercool() == false) {

						inventory.setWatercool(true);
					} else if (inventory.isWatercool() == true) {
						inventory.setWatercool(false);
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

		fontRenderer.drawString("MFFS Reactor Containment Field", 10, 5, 0x404040);
		fontRenderer.drawString("Force Power", 10, 30, 0x404040);
		fontRenderer.drawString("available", 22, 40, 0x404040);
		fontRenderer.drawString("linked to generator:", 10, 60, 0x404040);
		fontRenderer.drawString("linked to Reaktor: ", 10, 70, 0x404040);
		fontRenderer.drawString("reactorheat: ", 10, 80, 0x404040);
		fontRenderer.drawString("reactorwatercool: ", 10, 90, 0x404040);
		fontRenderer.drawString("Frequency Card:", 10, 123, 0x404040);

		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.getLinkPower()).toString(), 100, 45, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.isLinkGenerator()).toString(), 120, 60, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.isIsreaktor()).toString(), 120, 70, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.getReaktorheat()).toString(), 120, 80, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(inventory.isWatercool()).toString(), 120, 90, 0x404040);

	}

}
