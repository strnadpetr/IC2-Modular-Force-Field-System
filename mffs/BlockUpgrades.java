package mffs;


import ic2.common.TileEntityReactorChamber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.src.forge.ISpecialResistance;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.*;

public class BlockUpgrades extends BlockContainer implements ITextureProvider, ISpecialResistance {

	private int blockid;

	public BlockUpgrades(int i) {

		super(i, Material.iron);
		blockid = i;
		setHardness(3F);
		setResistance(50F);
		setStepSound(soundMetalFootstep);

	}

	public int getBlockid() {
		return blockid;
	}

	public Integer getGuiPro(World world, int i, int j, int k, EntityPlayer entityplayer) {
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
		default:

			return null;
		}
	}

	public Integer getGuiUp(World world, int i, int j, int k, EntityPlayer entityplayer) {
		switch (world.getBlockMetadata(i, j, k)) {

		case 0:
			return Integer.valueOf(mod_ModularForceFieldSystem.guireaktormonitorclient);
		case 7:
			return Integer.valueOf(mod_ModularForceFieldSystem.guicamouflageupgrade);
		default:

			return null;
		}
	}

	public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {

		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityReaktorConnector) {

			if (((TileEntityReaktorConnector) tileentity).isredpowert()) {

				switch (l) {

				case 0:
					if (world.getBlockTileEntity(i, j + 1, k) instanceof TileEntityReactorChamber) {
						return true;
					}

					break;
				case 1:

					if (world.getBlockTileEntity(i, j - 1, k) instanceof TileEntityReactorChamber) {
						return true;
					}

					break;
				case 2:
					if (world.getBlockTileEntity(i, j, k + 1) instanceof TileEntityReactorChamber) {
						return true;
					}
					break;
				case 3:
					if (world.getBlockTileEntity(i, j, k - 1) instanceof TileEntityReactorChamber) {
						return true;
					}

					break;
				case 4:
					if (world.getBlockTileEntity(i + 1, j, k) instanceof TileEntityReactorChamber) {
						return true;
					}
					break;
				case 5:
					if (world.getBlockTileEntity(i - 1, j, k) instanceof TileEntityReactorChamber) {
						return true;
					}
					break;
				default:
					return false;

				}

			}

		}

		if (tileentity instanceof TileEntityReaktorMonitorClient) {
			if (((TileEntityReaktorMonitorClient) tileentity).isSignal()) {
				switch (l) {

				case 0:
					if (world.getBlockTileEntity(i, j + 1, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}

					break;
				case 1:

					if (world.getBlockTileEntity(i, j - 1, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}

					break;
				case 2:
					if (world.getBlockTileEntity(i, j, k + 1) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}
					break;
				case 3:
					if (world.getBlockTileEntity(i, j, k - 1) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}

					break;
				case 4:
					if (world.getBlockTileEntity(i + 1, j, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}
					break;
				case 5:
					if (world.getBlockTileEntity(i - 1, j, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}
					break;
				}

				return false;
			}
		}

		return false;
	}

	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityReaktorConnector) {

			if (((TileEntityReaktorConnector) tileentity).isredpowert()) {

				World world = ModLoader.getMinecraftInstance().theWorld;
				switch (l) {

				case 0:
					if (world.getBlockTileEntity(i, j + 1, k) instanceof TileEntityReactorChamber) {
						return true;
					}

					break;
				case 1:

					if (world.getBlockTileEntity(i, j - 1, k) instanceof TileEntityReactorChamber) {
						return true;
					}

					break;
				case 2:
					if (world.getBlockTileEntity(i, j, k + 1) instanceof TileEntityReactorChamber) {
						return true;
					}
					break;
				case 3:
					if (world.getBlockTileEntity(i, j, k - 1) instanceof TileEntityReactorChamber) {
						return true;
					}

					break;
				case 4:
					if (world.getBlockTileEntity(i + 1, j, k) instanceof TileEntityReactorChamber) {
						return true;
					}
					break;
				case 5:
					if (world.getBlockTileEntity(i - 1, j, k) instanceof TileEntityReactorChamber) {
						return true;
					}
					break;
				default:
					return false;

				}
			}

			return false;

		}

		if (tileentity instanceof TileEntityReaktorMonitorClient) {
			if (((TileEntityReaktorMonitorClient) tileentity).isSignal()) {

				World world = ModLoader.getMinecraftInstance().theWorld;

				switch (l) {

				case 0:
					if (world.getBlockTileEntity(i, j + 1, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}

					break;
				case 1:

					if (world.getBlockTileEntity(i, j - 1, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}

					break;
				case 2:
					if (world.getBlockTileEntity(i, j, k + 1) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}
					break;
				case 3:
					if (world.getBlockTileEntity(i, j, k - 1) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}

					break;
				case 4:
					if (world.getBlockTileEntity(i + 1, j, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}
					break;
				case 5:
					if (world.getBlockTileEntity(i - 1, j, k) instanceof TileEntityReaktorMonitorClient) {
						return false;
					}
					break;
				}
				return true;
			}
		}

		return false;
	}

	public boolean canProvidePower() {

		return true;
	}

	public void onBlockRemoval(World world, int i, int j, int k) {
		if (!Functions.isSimulation()) {
			return;
		}

		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityReaktorMonitorClient) {
			ItemStack itemstack = ((TileEntityReaktorMonitorClient) tileentity).getStackInSlot(0);
			if (itemstack != null) {
				if (itemstack.getItem() instanceof ItemSecLinkCard) {
					EntityItem entityitem = new EntityItem(world, (float) i, (float) j, (float) k, new ItemStack(mod_ModularForceFieldSystem.MFFSitemcardempty, 1));
					world.spawnEntityInWorld(entityitem);
				}
			}
		}

		world.removeBlockTileEntity(i, j, k);
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {

		if (!Functions.isSimulation()) {
			return true;
		} else {

			Integer integer = getGuiUp(world, i, j, k, entityplayer);
			Integer meta = world.getBlockMetadata(i, j, k);

			if (integer == null && meta != 4 && meta != 5) {
				return false;
			}

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

			if (integer != null) {
				return mod_ModularForceFieldSystem.launchGUI(entityplayer, world.getBlockTileEntity(i, j, k), integer);
			}

			if (meta == 4 || meta == 5) {
				TileEntityPassivUpgrade tileentity = (TileEntityPassivUpgrade) world.getBlockTileEntity(i, j, k);
				if (tileentity.getconectet_ID() == 0) {
					return false;
				} else {
					TileEntityGeneratorCore tileentitygen = Linkgrid.getWorldMap(world).getGenerator().get(tileentity.getconectet_ID());
					if (tileentitygen != null) {
						Integer guiid = getGuiPro(world, tileentitygen.xCoord, tileentitygen.yCoord, tileentitygen.zCoord, entityplayer);
						return mod_ModularForceFieldSystem.launchGUI(entityplayer, world.getBlockTileEntity(tileentitygen.xCoord, tileentitygen.yCoord, tileentitygen.zCoord), guiid);
					}

				}
			}
			return false;
		}

	}

	public TileEntityMaschines getBlockEntity() {
		return null;
	}

	protected int damageDropped(int i) {
		return i;

	}

	public TileEntityMaschines getBlockEntity(int i) {
		switch (i) {
		case 0:
			return new TileEntityReaktorMonitorClient();
		case 1: // '\\ Projektor Subwater'
		case 2: // '\\ Projektor Dome'
		case 3: // '\\ Projektor Hardner'
		case 4: // '\\ Generator Array'
		case 5: // '\\ Generator Storage'
		case 6: // '\\ Projektor Zapper'
			return new TileEntityPassivUpgrade();
		case 7: // '\\ Projektor camouflage'
			return new TileEntityCamoflageUpgrade();
		case 8: // '\\ Reaktor Connector'
			return new TileEntityReaktorConnector();
		}
		return null;
	}

	public String getTextureFile() {

		return "/mffs_grafik/upgrades.png";
	}

	public static boolean isActive(IBlockAccess iblockaccess, int i, int j, int k) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);

		if (tileentity instanceof TileEntityReaktorMonitorClient) {
			if (((TileEntityReaktorMonitorClient) tileentity).isSignal()) {
				return true;
			} else {
				return false;
			}

		}

		if (tileentity instanceof TileEntityPassivUpgrade) {
			return ((TileEntityPassivUpgrade) tileentity).getconectet_ID() != 0;
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

			return (meta * 16) + 7 + l;

		} else {
			if (facing == l) {
				return (meta * 16);
			}

			return (meta * 16) + l;
		}
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j) {

		return (j * 16) + i - 1;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityPassivUpgrade) {
			((TileEntityPassivUpgrade) tileentity).updatecheck();
		}
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
