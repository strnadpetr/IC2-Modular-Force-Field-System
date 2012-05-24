package ic2.api;

import net.minecraft.src.ItemStack;

public final class Items
{
    public static ItemStack getItem(String name)
    {
        try
        {
            Object ret = Class.forName(getPackage() + ".common.Ic2Items").getField(name).get(null);

            if (ret instanceof ItemStack)
            {
                return (ItemStack) ret;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println("IC2 API: Call getItem failed for " + name);
            return null;
        }
    }

    private static String getPackage()
    {
        Package pkg = Items.class.getPackage();

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
