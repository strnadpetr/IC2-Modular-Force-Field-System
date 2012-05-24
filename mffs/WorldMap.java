package mffs;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import mffs.Linkgrid.Worldlinknet;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;

public final class WorldMap {

	private static Map ForceFieldWorld = new HashMap();
	private static StringBuffer hasher = new StringBuffer();

	static class WorldForceField {
		private static Map<String, ForceFieldWorldMap> FFWorldMap = new Hashtable<String, ForceFieldWorldMap>();

		public ForceFieldWorldMap addandgetffmp(int x, int y, int z) {
			hasher.setLength(0);
			hasher.append(x).append("/").append(y).append("/").append(z);
			if (FFWorldMap.get(hasher.toString()) == null) {
				FFWorldMap.put(hasher.toString(), new ForceFieldWorldMap(x, y, z));
			}
			return FFWorldMap.get(hasher.toString());
		}

		public ForceFieldWorldMap getFFWM(String hash) {
			return FFWorldMap.get(hash);
		}

	}

	public static WorldForceField getForceFieldforWorld(World world) {

		if (world != null) {

			if (!ForceFieldWorld.containsKey(world)) {
				ForceFieldWorld.put(world, new WorldForceField());
			}
			return (WorldForceField) ForceFieldWorld.get(world);
		}

		return null;

	}

}
