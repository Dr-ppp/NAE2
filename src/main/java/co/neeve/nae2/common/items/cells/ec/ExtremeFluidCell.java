package co.neeve.nae2.common.items.cells.ec;

import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.core.Api;
import co.neeve.nae2.common.registration.definitions.Materials;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExtremeFluidCell extends ExtremeCell<IAEFluidStack> {

    public ExtremeFluidCell(Materials.MaterialType whichCell, long bytes) {
        super(whichCell, bytes);
    }

    @NotNull
    @Override
    public IFluidStorageChannel getChannel() {
        return Api.INSTANCE.storage().getStorageChannel(IFluidStorageChannel.class);
    }

    @Override
    public int getTotalTypes(@NotNull ItemStack cellItem) {
        return 1;
    }
}

