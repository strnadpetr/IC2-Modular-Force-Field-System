package mffs;

public class ThreadReaktorLinkRemote implements Runnable {
	protected GuiReaktorLinkRemote guireaktorlinkremote;
	public boolean working;

	public ThreadReaktorLinkRemote(GuiReaktorLinkRemote gui) {
		this.guireaktorlinkremote = gui;
	}

	@Override
	public void run() {
		working = true;
		while (working) {

			if (guireaktorlinkremote.getMonitorID() != 0) {
				guireaktorlinkremote.importData();
				guireaktorlinkremote.initGui();
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				working = false;
				guireaktorlinkremote.close();
			}
		}

	}
}
