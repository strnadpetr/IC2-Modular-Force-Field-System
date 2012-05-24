package mffs;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import net.minecraft.src.mod_ModularForceFieldSystem;

public class ForceFieldWorldMap {

	private boolean sync;
	private int x;
	private int y;
	private int z;
	private LinkedList<ForceFieldBlock> ffworld = new LinkedList<ForceFieldBlock>();

	public ForceFieldWorldMap(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		sync = true;

	}

	public boolean getsync() {
		return sync;
	}

	public void setsync(boolean sync) {
		this.sync = sync;
	}

	public void ffworld_setfistactive(boolean f) {
		ffworld.getFirst().setActive(f);
	}

	public boolean ffworld_getfistactive() {
		return ffworld.getFirst().isActive();
	}

	public short ffworld_getfistmode() {
		return ffworld.getFirst().getMode();
	}

	public void ffworld_setfirstfreeospace(boolean f) {
		ffworld.getFirst().setFreespace(f);
	}

	public boolean ffworld_getfirstfreespace() {

		return ffworld.getFirst().isFreespace();

	}

	public int ffworld_getfirstGenerator_ID() {
		return ffworld.getFirst().getGenerator_Id();
	}

	public int ffworld_getfirstProjektor_ID() {
		try {
			return ffworld.getFirst().getProjektor_ID();
		} catch (NoSuchElementException ex) {
			return 0;
		}
	}

	public void ffworld_addFirst(ForceFieldBlock e) {

		ffworld.addFirst(e);
	}

	public void ffworld_addLast(ForceFieldBlock e) {

		ffworld.addLast(e);
	}

	public ForceFieldBlock ffworld_getfirst() {
		return ffworld.getFirst();

	}

	public ForceFieldBlock ffworld_getLast() {
		return ffworld.getLast();

	}

	public void ffworld_removefirst() {
		ffworld.removeFirst();
	}

	public void ffworld_remove(int Projektor_ID) {

		for (int i = 0; i < ffworld.size(); i++) {
			ForceFieldBlock ffb = ffworld.get(i);
			if (ffb.getProjektor_ID() == Projektor_ID) {
				ffworld.remove(i);
			}

		}
	}

	public int listsize() {

		return ffworld.size();

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}