package co.neeve.nae2.common.items.cells.ec;

import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.Api;
import co.neeve.nae2.common.items.cells.ec.ExtremeCell;
import co.neeve.nae2.common.registration.definitions.Materials;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Extreme Item Cell
 *
 * @author Magic_Sweepy
 *
 * <p>
 *     This class is used for Singularity Storage Cell.
 * </p>
 */
public class ExtremeItemCell extends ExtremeCell<IAEItemStack> {

    public ExtremeItemCell(Materials.MaterialType whichCell, long bytes) {
        super(whichCell, bytes);
    }

    @NotNull
    @Override
    public IItemStorageChannel getChannel() {
        return Api.INSTANCE.storage().getStorageChannel(IItemStorageChannel.class);
    }

    @Override
    public int getTotalTypes(@NotNull ItemStack cellItem) {
        return 1;
    }
}
