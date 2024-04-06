package co.neeve.nae2.common.items.cells.multiCell;

import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.Api;
import co.neeve.nae2.common.items.cells.DenseCell;
import co.neeve.nae2.common.registration.definitions.Materials;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MultiItemCell extends DenseCell<IAEItemStack> {
    public MultiItemCell(Materials.MaterialType whichCell, int kilobytes) {
        super(whichCell, kilobytes);
    }

    @NotNull
    @Override
    public IItemStorageChannel getChannel() {
        return Api.INSTANCE.storage().getStorageChannel(IItemStorageChannel.class);
    }
    @Override
    public int getBytesPerType(@NotNull ItemStack itemStack) {
        return 1;
    }

}
