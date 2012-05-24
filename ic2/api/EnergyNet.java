package ic2.api;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public final class EnergyNet
{
    public static EnergyNet getForWorld(World world)
    {
        try
        {
            return new EnergyNet(Class.forName(getPackage() + ".common.EnergyNet").getMethod("getForWorld", World.class).invoke(null, world));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private EnergyNet(Object energyNetInstance)
    {
        this.energyNetInstance = energyNetInstance;
    }

    public void addTileEntity(TileEntity addedTileEntity)
    {
        try
        {
            Class.forName(getPackage() + ".common.EnergyNet").getMethod("addTileEntity", TileEntity.class).invoke(energyNetInstance, addedTileEntity);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void removeTileEntity(TileEntity removedTileEntity)
    {
        try
        {
            Class.forName(getPackage() + ".common.EnergyNet").getMethod("removeTileEntity", TileEntity.class).invoke(energyNetInstance, removedTileEntity);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public int emitEnergyFrom(IEnergySource energySource, int amount)
    {
        try
        {
            return ((Integer) Class.forName(getPackage() + ".common.EnergyNet").getMethod("emitEnergyFrom", IEnergySource.class, Integer.TYPE).invoke(energyNetInstance, energySource, amount)).intValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public long getTotalEnergyConducted(TileEntity tileEntity)
    {
        try
        {
            return ((Long) Class.forName(getPackage() + ".common.EnergyNet").getMethod("getTotalEnergyConducted", TileEntity.class).invoke(energyNetInstance, tileEntity)).longValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getPackage()
    {
        Package pkg = EnergyNet.class.getPackage();

        if (pkg != null)
        {
            return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
        }
        else
        {
            return "ic2";
        }
    }

    Object energyNetInstance;
}
