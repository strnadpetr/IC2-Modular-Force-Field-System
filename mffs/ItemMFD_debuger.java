package mffs;

import ic2.common.TileEntityNuclearReactor;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMFD_debuger extends ItemMFD {

	public ItemMFD_debuger(int i) {
		super(i, 3);
		setIconIndex(3);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {

		TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

		if (tileEntity instanceof TileEntityReaktorMonitorClient) {
			info.setLength(0);
			info.append("islink: ").append(((TileEntityReaktorMonitorClient) tileEntity).isLinkMonitor());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("conectet_ID: ").append(((TileEntityReaktorMonitorClient) tileEntity).getLinkMonitor_ID());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("status: ").append(((TileEntityReaktorMonitorClient) tileEntity).getChannel());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("signal: ").append(((TileEntityReaktorMonitorClient) tileEntity).isSignal());
			Functions.ChattoPlayer(entityplayer, info.toString());

		}

		if (tileEntity instanceof TileEntityReaktorMonitor) {
			info.setLength(0);
			info.append("channel: ").append(((TileEntityReaktorMonitor) tileEntity).getChannel(0));
			info.append(((TileEntityReaktorMonitor) tileEntity).getChannel(1));
			info.append(((TileEntityReaktorMonitor) tileEntity).getChannel(2));
			info.append(((TileEntityReaktorMonitor) tileEntity).getChannel(3));
			info.append(((TileEntityReaktorMonitor) tileEntity).getChannel(4));
			info.append(((TileEntityReaktorMonitor) tileEntity).getChannel(5));

			Functions.ChattoPlayer(entityplayer, info.toString());

		}

		if (tileEntity instanceof TileEntityReaktorCooler) {
			info.setLength(0);
			info.append("wrenchrate: ").append(((TileEntityReaktorCooler) tileEntity).getWrenchDropRate());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("active: ").append(((TileEntityReaktorCooler) tileEntity).getActive());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("isreaktor: ").append(((TileEntityReaktorCooler) tileEntity).isIsreaktor());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("conectet_ID: ").append(((TileEntityReaktorCooler) tileEntity).getconectet_ID());
			Functions.ChattoPlayer(entityplayer, info.toString());
		}

		if (tileEntity instanceof TileEntityReaktorField) {
			info.setLength(0);
			info.append("reactorsize: ").append(((TileEntityReaktorField) tileEntity).getReactorsize());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("isreaktor: ").append(((TileEntityReaktorField) tileEntity).isIsreaktor());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.append("reactordist: ").append(((TileEntityReaktorField) tileEntity).getReactordist());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.append("watercool: ").append(((TileEntityReaktorField) tileEntity).isWatercool());
			Functions.ChattoPlayer(entityplayer, info.toString());
		}

		if (tileEntity instanceof TileEntityReaktorConnector) {
			info.setLength(0);
			info.append("redpowert: ").append(((TileEntityReaktorConnector) tileEntity).isredpowert());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("wrenchrate: ").append(((TileEntityReaktorConnector) tileEntity).getWrenchDropRate());
			Functions.ChattoPlayer(entityplayer, info.toString());
		}

		if (tileEntity instanceof TileEntityProjektor) {
			info.setLength(0);
			info.append("linkGenerator_ID: ").append(((TileEntityProjektor) tileEntity).getLinkGenerator_ID());
			Functions.ChattoPlayer(entityplayer, info.toString());

		}

		if (tileEntity instanceof TileEntityNuclearReactor) {
			info.setLength(0);
			info.append("Reaktor Hitze: ").append(((TileEntityNuclearReactor) tileEntity).heat);
			Functions.ChattoPlayer(entityplayer, info.toString());
		}

		if (tileEntity instanceof TileEntityGeneratorCore) {
			info.setLength(0);
			info.append("Generator ID: ").append(((TileEntityGeneratorCore) tileEntity).getGenerator_ID());
			Functions.ChattoPlayer(entityplayer, info.toString());
		}

		if (tileEntity instanceof TileEntityDirectionalExtender) {
			info.setLength(0);
			info.append("is create: ").append(((TileEntityDirectionalExtender) tileEntity).isCreate());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("preactive: ").append(((TileEntityDirectionalExtender) tileEntity).isPreactive());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("active: ").append(((TileEntityDirectionalExtender) tileEntity).getActive());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("con_to_projektor: ").append(((TileEntityDirectionalExtender) tileEntity).isCon_to_projektor());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("remProjektor_ID: ").append(((TileEntityDirectionalExtender) tileEntity).getRemProjektor_ID());
			Functions.ChattoPlayer(entityplayer, info.toString());
			info.setLength(0);
			info.append("remGenerator_ID: ").append(((TileEntityDirectionalExtender) tileEntity).getRemGenerator_ID());
			Functions.ChattoPlayer(entityplayer, info.toString());
			return true;
		}

		//

		if (world.getBlockId(i, j, k) == mod_ModularForceFieldSystem.MFFSFieldblock.blockID) {
			hasher.setLength(0);
			hasher.append(i).append("/").append(j).append("/").append(k);
			ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(world).getFFWM(hasher.toString());
			if (ffworldmap != null) {

				info.setLength(0);
				info.append("sync: ").append(ffworldmap.getsync());
				Functions.ChattoPlayer(entityplayer, info.toString());

				info.setLength(0);
				info.append("blöcke in quee: ").append(ffworldmap.listsize());
				Functions.ChattoPlayer(entityplayer, info.toString());

				info.setLength(0);
				info.append("first block meta: ").append(ffworldmap.ffworld_getfistmode());
				Functions.ChattoPlayer(entityplayer, info.toString());

				info.setLength(0);
				info.append("first Generator_ID: ").append(ffworldmap.ffworld_getfirstGenerator_ID());
				Functions.ChattoPlayer(entityplayer, info.toString());

				info.setLength(0);
				info.append("first Projektor_ID: ").append(ffworldmap.ffworld_getfirstProjektor_ID());
				Functions.ChattoPlayer(entityplayer, info.toString());

				return true;
			}

		}

		return false;
	}

}
