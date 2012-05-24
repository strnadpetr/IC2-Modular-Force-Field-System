package mffs;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import mffs.Linkgrid.Worldlinknet;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;

public final class TexturworldMap {

	private static Map Textur_Map = new HashMap();
	private static StringBuffer hasher = new StringBuffer();

	static class TexturMap {

		public static Map<String, Integer> TTextur = new Hashtable<String, Integer>();

		public void add(int x, int y, int z, int textur) {
			hasher.setLength(0);
			hasher.append(x).append("/").append(y).append("/").append(z);
			TTextur.put(hasher.toString(), textur);
		}

		public void addfromUpate(String hasher, int textur) {

			TTextur.put(hasher.toString(), textur);
		}

		public int getTextur(int x, int y, int z) {
			hasher.setLength(0);
			hasher.append(x).append("/").append(y).append("/").append(z);

			if (TTextur.get(hasher.toString()) != null) {
				return TTextur.get(hasher.toString());
			}

			return -1;

		}

		public int getSize() {

			return TTextur.size();
		}

	}

	public static TexturMap getTexturMap(Integer typ) {

		if (!Textur_Map.containsKey(typ)) {
			Textur_Map.put(typ, new TexturMap());
		}
		return (TexturMap) Textur_Map.get(typ);

	}

}
