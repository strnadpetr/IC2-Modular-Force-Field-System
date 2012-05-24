package ic2.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface IMetalArmor
{
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player);
}
