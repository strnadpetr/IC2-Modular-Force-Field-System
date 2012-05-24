package mffs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.src.forge.ISpecialResistance;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.*;

public class BlockMachine extends BlockContainer implements ITextureProvider, ISpecialResistance {

	private int blockid;

	public BlockMachine(int i) {

		super(i, Material.iron);
		blockid = i;
		setHardness(3F);
		setResistance(50F);
		setStepSound(soundMetalFootstep);

	}

	public int getBlockid() {
		return blockid;
	}

	public Integer getGui(World world, int i, int j, int k, EntityPlayer entityplayer) {
		switch (world.getBlockMetadata(i, j, k)) {
		case 0:
			return Integer.valueOf(mod_ModularForceFieldSystem.guiIDGenerator);
		case 1:
			return Integer.valueOf(mod_ModularForceFieldSystem.guiareaproje);
		case 2:
			return Integer.valueOf(mod_ModularForceFieldSystem.guidirectprojektor);
		case 3:
			return Integer.valueOf(mod_ModularForceFieldSystem.guideflectorprojektor);
		case 4:
			return Integer.valueOf(mod_ModularForceFieldSystem.guitubeprojektor);
		case 5:
			return Integer.valueOf(mod_ModularForceFieldSystem.guidirectupgrade);
		case 7:
			return Integer.valueOf(mod_ModularForceFieldSystem.guireaktorfield);
		case 8:
			return Integer.valueOf(mod_ModularForceFieldSystem.guireaktorcooler);
		case 9:
			return Integer.valueOf(mod_ModularForceFieldSystem.guireaktormonitor);
		default:

			return null;
		}
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {

		if (!Functions.isSimulation()) {
			return true;
		} else {

			if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == Block.lever.blockID) {
				return false;
			}
			if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == mod_ModularForceFieldSystem.MFFSUpgrades.blockID) {
				return false;
			}
			if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == mod_ModularForceFieldSystem.MFFSMaschines.blockID) {
				return false;
			}

			if (entityplayer.getCurrentEquippedItem() != null && (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMFD)) {
				return false;
			}

			Integer integer = getGui(world, i, j, k, entityplayer);

			if (integer == null) {
				return false;
			}

			return mod_ModularForceFieldSystem.launchGUI(entityplayer, world.getBlockTileEntity(i, j, k), integer);
		}
	}

	public TileEntityMaschines getBlockEntity() {
		return null;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
		if (!Functions.isSimulation()) {
			return;
		}
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0) {
			TileEntityGeneratorCore tileentityblock = (TileEntityGeneratorCore) world.getBlockTileEntity(i, j, k);
			tileentityblock.addtogrid();
		}
		if (meta == 1) {
			TileEntityAreaProjektor tileentityblock = (TileEntityAreaProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.addtogrid();
		}
		if (meta == 2) {
			TileEntityDirectionalProjektor tileentityblock = (TileEntityDirectionalProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.addtogrid();
		}
		if (meta == 3) {
			TileEntityDeflectorProjektor tileentityblock = (TileEntityDeflectorProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.addtogrid();
		}
		if (meta == 4) {
			TileEntityTubeProjektor tileentityblock = (TileEntityTubeProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.addtogrid();
		}
		if (meta == 7) {
			TileEntityReaktorField tileentityblock = (TileEntityReaktorField) world.getBlockTileEntity(i, j, k);
			tileentityblock.addtogrid();
		}

	}

	public void onBlockRemoval(World world, int i, int j, int k) {
		if (!Functions.isSimulation()) {
			return;
		}
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0) {
			TileEntityGeneratorCore tileentityblock = (TileEntityGeneratorCore) world.getBlockTileEntity(i, j, k);
			tileentityblock.removefromgrid();
		}
		if (meta == 1) {
			TileEntityAreaProjektor tileentityblock = (TileEntityAreaProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.destroyField();
			tileentityblock.removefromgrid();
		}
		if (meta == 2) {
			TileEntityDirectionalProjektor tileentityblock = (TileEntityDirectionalProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.destroyField();
			tileentityblock.removefromgrid();
		}
		if (meta == 3) {
			TileEntityDeflectorProjektor tileentityblock = (TileEntityDeflectorProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.destroyField();
			tileentityblock.removefromgrid();
		}
		if (meta == 4) {
			TileEntityTubeProjektor tileentityblock = (TileEntityTubeProjektor) world.getBlockTileEntity(i, j, k);
			tileentityblock.destroyField();
			tileentityblock.removefromgrid();
		}
		if (meta == 5) {
			TileEntityDirectionalExtender tileentityblock = (TileEntityDirectionalExtender) world.getBlockTileEntity(i, j, k);
			tileentityblock.destroyField();
		}
		if (meta == 7) {
			TileEntityReaktorField tileentityblock = (TileEntityReaktorField) world.getBlockTileEntity(i, j, k);
			tileentityblock.destroyField();
			tileentityblock.removefromgrid();
		}
		if (meta == 9) {
			TileEntityReaktorMonitor tileentityblock = (TileEntityReaktorMonitor) world.getBlockTileEntity(i, j, k);
			tileentityblock.removefromgrid();
		}

		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityProjektor) {
			ItemStack itemstack = ((TileEntityProjektor) tileentity).getStackInSlot(0);
			if (itemstack != null) {
				if (itemstack.getItem() instanceof ItemFrequenzCard) {
					EntityItem entityitem = new EntityItem(world, (float) i, (float) j, (float) k, new ItemStack(mod_ModularForceFieldSystem.MFFSitemcardempty, 1));
					world.spawnEntityInWorld(entityitem);
				}
			}
		}

		world.removeBlockTileEntity(i, j, k);

	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		if (!Functions.isSimulation()) {
			return;
		}

		TileEntityMaschines tileentityblock = (TileEntityMaschines) world.getBlockTileEntity(i, j, k);

		int l = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		int i1 = Math.round(entityliving.rotationPitch);
		if (i1 >= 65) {
			tileentityblock.setFacing((short) 1);
		} else if (i1 <= -65) {
			tileentityblock.setFacing((short) 0);
		} else if (l == 0) {
			tileentityblock.setFacing((short) 2);
		} else if (l == 1) {
			tileentityblock.setFacing((short) 5);
		} else if (l == 2) {
			tileentityblock.setFacing((short) 3);
		} else if (l == 3) {
			tileentityblock.setFacing((short) 4);
		}
	}

	protected int damageDropped(int i) {
		return i;

	}

	public TileEntityMaschines getBlockEntity(int i) {
		switch (i) {
		case 0:
			return new TileEntityGeneratorCore();
		case 1:
			return new TileEntityAreaProjektor();
		case 2:
			return new TileEntityDirectionalProjektor();
		case 3:
			return new TileEntityDeflectorProjektor();
		case 4:
			return new TileEntityTubeProjektor();
		case 5:
			return new TileEntityDirectionalExtender();
		case 6:
			return new TileEntityGeneratorEUInjector();
		case 7:
			return new TileEntityReaktorField();
		case 8:
			return new TileEntityReaktorCooler();
		case 9:
			return new TileEntityReaktorMonitor();
		}
		return null;
	}

	public String getTextureFile() {

		return "/mffs_grafik/machines.png";
	}

	public int idDropped(int i, Random random) {
		switch (i) {
		default:
			return blockID;
		}
	}

	public static boolean isActive(IBlockAccess iblockaccess, int i, int j, int k) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityMaschines) {
			return ((TileEntityMaschines) tileentity).getActive();
		} else {
			return false;
		}
	}

	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);
		short facing = (tileentity instanceof TileEntityMaschines) ? ((TileEntityMaschines) tileentity).getFacing() : 0;
		int meta = iblockaccess.getBlockMetadata(i, j, k);

		if (isActive(iblockaccess, i, j, k)) {
			if (facing == l) {
				return (meta * 16) + 7;
			}
			if (facing != 0 && l == 0) {
				return (meta * 16) + 7 + 1;
			}
			return (meta * 16) + 7 + l;

		} else {
			if (facing == l) {
				return (meta * 16);
			}
			if (facing != 0 && l == 0) {
				return (meta * 16) + 1;
			}
			return (meta * 16) + l;
		}
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j) {

		return (j * 16) + i - 1;
	}

	public float getSpecialExplosionResistance(World world, int i, int j, int k, double d, double d1, double d2, Entity entity) {

		if (world.getBlockTileEntity(i, j, k) instanceof TileEntityMaschines) {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (((TileEntityMaschines) tileentity).getActive()) {
				return 60000F;
			} else {
				return 50F;
			}
		}
		return 50F;
	}

}
