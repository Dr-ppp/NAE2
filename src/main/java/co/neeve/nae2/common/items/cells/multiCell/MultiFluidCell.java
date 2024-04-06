package co.neeve.nae2.common.items.cells.multiCell;

import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.core.Api;
import co.neeve.nae2.common.items.cells.DenseCell;
import co.neeve.nae2.common.registration.definitions.Materials;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MultiFluidCell extends DenseCell<IAEFluidStack> {

    public MultiFluidCell(Materials.MaterialType whichCell, int kilobytes) {
        super(whichCell, kilobytes);
    }

    @NotNull
    @Override
    public IFluidStorageChannel getChannel() {
        return Api.INSTANCE.storage().getStorageChannel(IFluidStorageChannel.class);
    }

    @Override
    public int getTotalTypes(@NotNull ItemStack cellItem) {
        return 63;
    }
}
