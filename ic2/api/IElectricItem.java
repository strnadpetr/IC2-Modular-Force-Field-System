package ic2.api;

import net.minecraft.src.ItemStack;

public interface IElectricItem
{
    boolean canProvideEnergy();

    int getChargedItemId();

    int getEmptyItemId();

    int getMaxCharge();

    int getTier();

    int getTransferLimit();
}
