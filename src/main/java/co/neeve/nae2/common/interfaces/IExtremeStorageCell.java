package co.neeve.nae2.common.interfaces;

import appeng.api.storage.ICellWorkbenchItem;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IExtremeStorageCell<T extends IAEStack<T>> extends ICellWorkbenchItem {
    long getBytes(@Nonnull ItemStack var1);

    int getBytesPerType(@Nonnull ItemStack var1);

    int getTotalTypes(@Nonnull ItemStack var1);

    boolean isBlackListed(@Nonnull ItemStack var1, @Nonnull T var2);

    boolean storableInStorageCell();

    boolean isStorageCell(@Nonnull ItemStack var1);

    double getIdleDrain();

    @Nonnull
    IStorageChannel<T> getChannel();
}
