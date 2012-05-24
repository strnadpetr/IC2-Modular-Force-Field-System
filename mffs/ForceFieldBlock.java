package mffs;

public class ForceFieldBlock {

	private short mode;
	private int Projektor_ID;
	private int Generator_Id;
	private boolean freespace;
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getGenerator_Id() {
		return Generator_Id;
	}

	public void setFreespace(boolean freespace) {
		this.freespace = freespace;
	}

	public int getProjektor_ID() {
		return Projektor_ID;
	}

	public boolean isFreespace() {
		return freespace;
	}

	public short getMode() {
		return mode;
	}

	public ForceFieldBlock(int Generator_Id, int Projektor_ID, boolean freespace, boolean active, short mode) {
		this.active = active;
		this.freespace = freespace;
		this.Projektor_ID = Projektor_ID;
		this.Generator_Id = Generator_Id;
		this.mode = mode;

	}

}
