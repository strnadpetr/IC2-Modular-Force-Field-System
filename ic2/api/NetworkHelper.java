package ic2.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public final class NetworkHelper
{
    public static void updateTileEntityField(TileEntity te, String field)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("updateTileEntityField", TileEntity.class, String.class).invoke(null, te, field);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("initiateTileEntityEvent", TileEntity.class, Integer.TYPE, Boolean.TYPE).invoke(null, te, event, limitRange);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("initiateItemEvent", EntityPlayer.class, ItemStack.class, Integer.TYPE, Boolean.TYPE).invoke(null, player, itemStack, event, limitRange);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void announceBlockUpdate(World world, int x, int y, int z)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("announceBlockUpdate", World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE).invoke(null, world, x, y, z);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void requestInitialData(INetworkDataProvider dataProvider)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("requestInitialData", INetworkDataProvider.class).invoke(null, dataProvider);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateClientTileEntityEvent(TileEntity te, int event)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("initiateClientTileEntityEvent", TileEntity.class, Integer.TYPE).invoke(null, te, event);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateClientItemEvent(ItemStack itemStack, int event)
    {
        try
        {
            Class.forName(getPackage() + ".platform.NetworkManager").getMethod("initiateClientItemEvent", ItemStack.class, Integer.TYPE).invoke(null, itemStack, event);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getPackage()
    {
        Package pkg = NetworkHelper.class.getPackage();

        if (pkg != null)
        {
            return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
        }
        else
        {
            return "ic2";
        }
    }
}
