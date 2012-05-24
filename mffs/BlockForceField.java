package mffs;

import java.util.List;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockGlass;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;
import net.minecraft.src.forge.ISpecialResistance;
import net.minecraft.src.forge.ITextureProvider;

public class BlockForceField extends BlockGlass implements ITextureProvider, ISpecialResistance {

	private boolean localFlag;
	private StringBuffer hasher = new StringBuffer();
	private int renderblockpass;

	public BlockForceField(int i) {
		super(i, i, Material.glass, true);
		setHardness(0.5F);
		setResistance(6000F);
		renderblockpass = 0;
		localFlag = false;

	}

	
	
    public int getRenderBlockPass()
    {
    	
        return renderblockpass;
    }

    
	public void onBlockRemoval(World world, int i, int j, int k) {

		if (Functions.isSimulation()) {
			hasher.setLength(0);
			hasher.append(i).append("/").append(j).append("/").append(k);
			ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(world).getFFWM(hasher.toString());
			if (ffworldmap != null && ffworldmap.listsize() >= 1 && ffworldmap.ffworld_getfistactive() == true) {
				ffworldmap.ffworld_setfirstfreeospace(true);
				ffworldmap.setsync(false);

				world.setBlock(i, j, k, mod_ModularForceFieldSystem.MFFSFieldblock.blockID);

				TileEntity tileEntity = Linkgrid.getWorldMap(world).getGenerator().get(ffworldmap.ffworld_getfirstGenerator_ID());
				if (tileEntity instanceof TileEntityGeneratorCore && tileEntity != null) {
					if (ffworldmap.ffworld_getfistmode() == 1) {
						((TileEntityGeneratorCore) tileEntity).Energylost(mod_ModularForceFieldSystem.forcefieldblockcost * mod_ModularForceFieldSystem.forcefieldblockcreatemodifier);
					} else {
						((TileEntityGeneratorCore) tileEntity).Energylost(mod_ModularForceFieldSystem.forcefieldblockcost * mod_ModularForceFieldSystem.forcefieldblockcreatemodifier * mod_ModularForceFieldSystem.forcefieldblockzappermodifier);
					}

				}

			}
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 1) {
			float f = 0.0625F;
			return AxisAlignedBB.getBoundingBoxFromPool((float) i + f, j, (float) k + f, (float) (i + 1) - f, (float) (j + 1) - f, (float) (k + 1) - f);
		}

		return AxisAlignedBB.getBoundingBoxFromPool((float) i, j, (float) k, (float) (i + 1), (float) (j + 1), (float) (k + 1));
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 1) {
			float f = 0.0625F;
			return AxisAlignedBB.getBoundingBoxFromPool((float) i + f, j, (float) k + f, (float) (i + 1) - f, j + 1, (float) (k + 1) - f);
		}
		return AxisAlignedBB.getBoundingBoxFromPool((float) i, j, (float) k, (float) (i + 1), j + 1, (float) (k + 1));
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {

		if (Functions.isSimulation() && world.getBlockMetadata(i, j, k) == 1) {
			if (entity instanceof EntityLiving) {
				entity.attackEntityFrom(DamageSource.outOfWorld, 5);
			}
		}
	}

	public int quantityDropped(Random random) {
		return 0;
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		int i1 = iblockaccess.getBlockId(i, j, k);
		
		if(iblockaccess.getBlockMetadata(i, j, k)==2 && i1== mod_ModularForceFieldSystem.MFFSFieldblock.blockID)
		{
			renderblockpass =1;
			return super.shouldSideBeRendered(iblockaccess, i, j, k, 1 - l);
		}
		
		if (!localFlag && i1 == blockID) {
			return false;
		} else {
			return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
		}
	}
	


	public String getTextureFile() {

		return "/mffs_grafik/blocks.png";
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {

		return false;
	}

	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {

		if (iblockaccess.getBlockMetadata(i, j, k) != 2) {
			return iblockaccess.getBlockMetadata(i, j, k);
		} else {

			Integer temp = TexturworldMap.getTexturMap(ModLoader.getMinecraftInstance().theWorld.worldProvider.worldType).getTextur(i, j, k);

			Integer Blocktextur[] = mod_ModularForceFieldSystem.idtotextur.get(temp);

			if (Blocktextur == null) {
				return iblockaccess.getBlockMetadata(i, j, k);
			}

			else {
				switch (l) {
				case 0:
					return Blocktextur[0];
				case 1:
					return Blocktextur[1];
				case 2:
					return Blocktextur[2];
				case 3:
					return Blocktextur[3];
				case 4:
					return Blocktextur[4];
				case 5:
					return Blocktextur[5];
				default:
					return 3;
				}
			}
		}
	}

	public float getSpecialExplosionResistance(World world, int i, int j, int k, double d, double d1, double d2, Entity entity) {

		if (Functions.isSimulation()) {
			hasher.setLength(0);
			hasher.append(i).append("/").append(j).append("/").append(k);
			ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(world).getFFWM(hasher.toString());
			if (ffworldmap != null && ffworldmap.listsize() >= 1 && ffworldmap.ffworld_getfistactive() == true) {
				TileEntity tileEntity = Linkgrid.getWorldMap(world).getGenerator().get(ffworldmap.ffworld_getfirstGenerator_ID());
				if (tileEntity instanceof TileEntityGeneratorCore && tileEntity != null) {
					((TileEntityGeneratorCore) tileEntity).Energylost(mod_ModularForceFieldSystem.forcefieldblockcost * mod_ModularForceFieldSystem.forcefieldblockcreatemodifier);
				}

			}
		}

		return 60000F;
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random) {

		if (world.getBlockMetadata(i, j, k) == 1) {

			double d = (double) ((float) i + 0.5F);
			double d1 = (double) ((float) j + 0.5F);
			double d2 = (double) ((float) k + 0.5F);

			world.spawnParticle("reddust", d, d1, d2, 0.0D, 0.0D, 0.0D);

		}
	}

	public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir) {
		return false;
	}

}
