package mffs;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public final class Linkgrid {

	private static Map WorldpowernetMap = new HashMap();

	static class Worldlinknet {

		private Map<Integer, TileEntityProjektor> Projektor = new Hashtable<Integer, TileEntityProjektor>();
		private Map<Integer, TileEntityGeneratorCore> Generator = new Hashtable<Integer, TileEntityGeneratorCore>();
		private Map<Integer, TileEntityReaktorMonitor> RMonitor = new Hashtable<Integer, TileEntityReaktorMonitor>();

		public Map<Integer, String> RMonitorCount = new Hashtable<Integer, String>();

		public Map<Integer, TileEntityProjektor> getProjektor() {
			return Projektor;
		}

		public Map<Integer, TileEntityGeneratorCore> getGenerator() {
			return Generator;
		}

		public Map<Integer, TileEntityReaktorMonitor> getRMonitor() {
			return RMonitor;
		}

		public int newGenerator_ID(TileEntityGeneratorCore tileEntityGeneratorCore) {

			Random random = new Random();
			int tempGenerator_ID = random.nextInt();

			while (Generator.get(tempGenerator_ID) != null) {
				tempGenerator_ID = random.nextInt();
			}
			Generator.put(tempGenerator_ID, tileEntityGeneratorCore);
			return tempGenerator_ID;
		}

		public int newRMonitor_ID(TileEntityReaktorMonitor tileEntityReaktorMonitor) {

			Random random2 = new Random();
			int tempRMonitor_ID = random2.nextInt();

			while (RMonitor.get(tempRMonitor_ID) != null) {
				tempRMonitor_ID = random2.nextInt();
			}
			RMonitor.put(tempRMonitor_ID, tileEntityReaktorMonitor);
			return tempRMonitor_ID;
		}

		public int newRMonitor_Name() {

			StringBuffer name = new StringBuffer();

			int ID = myRandom(0, 999);

			while (RMonitor.get(ID) != null) {
				ID = myRandom(0, 999);
			}

			name.append(ModLoader.getMinecraftInstance().thePlayer.username);
			name.append(ID);

			RMonitorCount.put(ID, name.toString());

			return ID;
		}

		public static int myRandom(int low, int high) {
			return (int) (Math.random() * (high - low) + low);
		}

		public int conProjektors(int Generator_ID, int xCoordr, int yCoordr, int zCoordr, short Transmitrange) {
			int counter = 0;
			for (TileEntityProjektor tileentity : Projektor.values()) {
				if (tileentity.getLinkGenerator_ID() == Generator_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (Transmitrange >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}

				}
			}
			return counter;
		}

	}

	public static Worldlinknet getWorldMap(World world) {
		if (world != null) {

			if (!WorldpowernetMap.containsKey(world)) {
				WorldpowernetMap.put(world, new Worldlinknet());
			}
			return (Worldlinknet) WorldpowernetMap.get(world);
		}

		return null;
	}

}