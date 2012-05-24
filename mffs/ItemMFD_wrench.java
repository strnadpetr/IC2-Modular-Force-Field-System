package mffs;

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

public class ItemMFD_wrench extends ItemMFD {

	public ItemMFD_wrench(int i) {
		super(i, 0);
		setIconIndex(0);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {

		if (Functions.isSimulation()) {
			if (world.getBlockTileEntity(i, j, k) instanceof TileEntityMaschines) {

				TileEntityMaschines maschine = (TileEntityMaschines) world.getBlockTileEntity(i, j, k);

				if (maschine.getWrenchDropRate() > 0) {
					if (maschine.getFacing() != l && maschine.getFacing() != -1) {
						maschine.setFacing((short) l);
						world.markBlockAsNeedsUpdate(i, j, k);

					} else {

						ItemStack itemstacks = new ItemStack(world.getBlockId(i, j, k), 1, world.getBlockMetadata(i, j, k));
						EntityItem entityitem = new EntityItem(world, i, j, k, itemstacks);
						world.setBlockWithNotify(i, j, k, 0);
						world.spawnEntityInWorld(entityitem);

					}
					return true;
				}
			}
		}
		return false;
	}
}
