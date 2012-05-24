package ic2.api;

import net.minecraft.src.ItemStack;

public interface IBoxable
{
    public abstract boolean canBeStoredInToolbox(ItemStack itemstack);
}
