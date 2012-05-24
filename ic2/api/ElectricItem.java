package ic2.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public final class ElectricItem
{
    public static int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
    {
        try
        {
            return (Integer) Class.forName(getPackage() + ".common.ElectricItem").getMethod("charge", ItemStack.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE).invoke(null, itemStack, amount, tier, ignoreTransferLimit, simulate);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
    {
        try
        {
            return (Integer) Class.forName(getPackage() + ".common.ElectricItem").getMethod("discharge", ItemStack.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE).invoke(null, itemStack, amount, tier, ignoreTransferLimit, simulate);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean canUse(ItemStack itemStack, int amount)
    {
        try
        {
            return (Boolean) Class.forName(getPackage() + ".common.ElectricItem").getMethod("canUse", ItemStack.class, Integer.TYPE).invoke(null, itemStack, amount);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean use(ItemStack itemStack, int amount, EntityPlayer player)
    {
        try
        {
            return (Boolean) Class.forName(getPackage() + ".common.ElectricItem").getMethod("use", ItemStack.class, Integer.TYPE, EntityPlayer.class).invoke(null, itemStack, amount, player);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void chargeFromArmor(ItemStack itemStack, EntityPlayer player)
    {
        try
        {
            Class.forName(getPackage() + ".common.ElectricItem").getMethod("chargeFromArmor", ItemStack.class, EntityPlayer.class).invoke(null, itemStack, player);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getPackage()
    {
        Package pkg = ElectricItem.class.getPackage();

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
