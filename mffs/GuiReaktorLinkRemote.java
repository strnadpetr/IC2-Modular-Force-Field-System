package mffs;

import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Slot;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;

import org.lwjgl.opengl.GL11;

public class GuiReaktorLinkRemote extends GuiScreen {

	private EntityPlayer player;
	private ItemStack itemstack;
	private World world;
	private int xSize;
	private int ySize;
	private Thread trlr;
	private int MonitorID = 0;
	private String Montorname = null;
	private boolean channel[];
	private int heat = 0;
	private boolean linkreaktor;

	public GuiReaktorLinkRemote(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		player = entityplayer;
		this.itemstack = itemstack;
		this.world = world;
		this.xSize = 176;
		this.ySize = 166;
		trlr = new Thread(new ThreadReaktorLinkRemote(this));
		channel = new boolean[] { false, false, false, false, false, false };

		if (Functions.getTAGfromItemstack(itemstack).hasKey("RMonitorID")) {
			MonitorID = Functions.getTAGfromItemstack(itemstack).getInteger("RMonitorID");
			if (Linkgrid.getWorldMap(world).getRMonitor().get(MonitorID) != null) {
				importData();
			} else {
				MonitorID = 0;
			}
		}

		trlr.start();
	}

	public void initGui() {

		controlList.add(new GuiButton(0, (width / 2) - 80, (height / 2) + 50, 120, 20, "Import LinkCard Data"));
		controlList.add(new GuiButton(1, (width / 2) + 40, (height / 2) + 50, 40, 20, "Close"));
		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton) {

		switch (guibutton.id) {

		case 0:
			importMonitorLink();
			break;
		case 1:

			trlr.interrupt();
			break;

		}
	}

	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch (Exception e) {

		}
	}

	public void importData() {
		if (MonitorID != 0) {
			channel = Linkgrid.getWorldMap(world).getRMonitor().get(MonitorID).getChannel();
			Montorname = Linkgrid.getWorldMap(world).getRMonitor().get(MonitorID).getMontorname();
			heat = Linkgrid.getWorldMap(world).getRMonitor().get(MonitorID).getHeat();
			linkreaktor = Linkgrid.getWorldMap(world).getRMonitor().get(MonitorID).isIsreaktor();

			if (!linkreaktor) {
				heat = 0;
			}
		}
	}

	public int getMonitorID() {
		return MonitorID;
	}

	private boolean importMonitorLink() {
		List<Slot> slots = player.inventorySlots.inventorySlots;

		for (Slot slot : slots) {
			if (slot.getStack() != null) {
				if (slot.getStack().getItem() == mod_ModularForceFieldSystem.MFFSitemsclc) {

					if (Functions.getTAGfromItemstack(slot.getStack()).hasKey("RMonitorID")) {

						int tempMonitorID = Functions.getTAGfromItemstack(slot.getStack()).getInteger("RMonitorID");

						if (tempMonitorID != MonitorID) {
							Functions.getTAGfromItemstack(itemstack).setInteger("RMonitorID", tempMonitorID);
							MonitorID = tempMonitorID;
							return true;
						}
						break;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void drawScreen(int i, int j, float f) {

		drawDefaultBackground();
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;

		drawGuiContainerBackgroundLayer(f);
		GL11.glPushMatrix();
		GL11.glTranslatef(k, l, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(2896 /* GL_LIGHTING */);
		GL11.glDisable(2929 /* GL_DEPTH_TEST */);
		drawGuiContainerForegroundLayer();
		GL11.glPopMatrix();

		super.drawScreen(i, j, f);
		GL11.glEnable(2896 /* GL_LIGHTING */);
		GL11.glEnable(2929 /* GL_DEPTH_TEST */);
		GL11.glDisable(2896 /* GL_LIGHTING */);
		GL11.glEnable(2896 /* GL_LIGHTING */);

	}

	protected void drawGuiContainerBackgroundLayer(float f) {

		int textur = mc.renderEngine.getTexture("/mffs_grafik/GUIreaktorlink.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);

	}

	protected void drawGuiContainerForegroundLayer() {

		boolean link = false;
		if (MonitorID != 0) {
			link = true;
		}

		fontRenderer.drawString("MFFS Remote Reaktor Link", 20, 10, 0x404040);

		fontRenderer.drawString("Link to Reaktor:", 10, 45, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(linkreaktor).toString(), 100, 45, 0x404040);

		fontRenderer.drawString("Link to Monitor:", 10, 30, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(link).toString(), 100, 30, 0x404040);

		fontRenderer.drawString("Monitor name:", 10, 95, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(Montorname).toString(), 10, 110, 0x404040);

		fontRenderer.drawString("Reaktor Working:", 10, 60, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(channel[0]).toString(), 100, 60, 0x404040);

		fontRenderer.drawString("Reaktor Heat:", 10, 75, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append(" ").append(heat).toString(), 100, 75, 0x404040);
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

}
