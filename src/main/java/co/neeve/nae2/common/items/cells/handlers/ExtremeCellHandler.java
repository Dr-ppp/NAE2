package co.neeve.nae2.common.items.cells.handlers;

import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.ISaveProvider;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.me.storage.BasicCellInventoryHandler;
import co.neeve.nae2.common.interfaces.IExtremeCellHandler;
import co.neeve.nae2.common.items.cells.ec.*;
import net.minecraft.item.ItemStack;

public class ExtremeCellHandler implements IExtremeCellHandler {
    @Override
    public boolean isCell(ItemStack itemStack) {
        return itemStack.getItem() instanceof ExtremeCell;
    }

    @Override
    public <T extends IAEStack<T>> ICellInventoryHandler<T> getCellInventory(ItemStack itemStack, ISaveProvider iSaveProvider, IStorageChannel<T> iStorageChannel) {
        return !itemStack.isEmpty()
                && (
                (itemStack.getItem() instanceof ExtremeItemCell && iStorageChannel instanceof IItemStorageChannel)
                        || (itemStack.getItem() instanceof ExtremeFluidCell && iStorageChannel instanceof IFluidStorageChannel)
        ) ? new BasicCellInventoryHandler<>(ExtremeCellInventory.createInventory(itemStack, iSaveProvider), iStorageChannel) : null;
    }
}
