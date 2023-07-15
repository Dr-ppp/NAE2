package co.neeve.nae2.mixin;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.container.implementations.ContainerPatternEncoder;
import appeng.container.slot.SlotRestrictedInput;
import co.neeve.nae2.common.helpers.ItemHandlerHelper;
import co.neeve.nae2.common.interfaces.IPatternMultiToolHost;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("SameReturnValue")
@Mixin(ContainerPatternEncoder.class)
public class MixinContainerPatternEncoder extends MixinContainerMEMonitorable {
	@Shadow
	protected SlotRestrictedInput patternSlotIN;

	/**
	 * Injects into the Pattern Encoder to try and substitute empty blank patterns.
	 */
	@Inject(method = "encode", at = @At(value = "INVOKE", target = "Lappeng/container/slot/SlotRestrictedInput;" +
		"getStack()Lnet/minecraft/item/ItemStack;", ordinal = 1))
	public void injectBlanks(CallbackInfo ci, @Local ItemStack pattern) {
		if (pattern.isEmpty() && this instanceof IPatternMultiToolHost pmh) {
			// Try search for blanks in our inventory.
			final IItemHandler pmhInv = pmh.getPatternMultiToolInventory();
			if (pmhInv == null) return;

			final IDefinitions definitions = AEApi.instance().definitions();

			for (int i = 0; i < pmhInv.getSlots(); i++) {
				ItemStack is = pmhInv.getStackInSlot(i);
				if (!is.isEmpty() && definitions.materials().blankPattern().isSameAs(is)) {
					ItemStack newPattern = is.copy();
					newPattern.setCount(1);
					is.shrink(1);
					this.patternSlotIN.putStack(newPattern);
					return;
				}
			}
		}
	}

	/**
	 * Injects into the Pattern Encoder to ensure that newly created patterns are first inserted into the PMT.
	 */
	@Redirect(method = "encodeAndMoveToInventory", at = @At(value = "INVOKE", target =
		"Lnet/minecraft/entity/player" + "/InventoryPlayer;addItemStackToInventory(Lnet/minecraft/item/ItemStack;)Z"))
	public boolean injectPMTOutput(InventoryPlayer ip, ItemStack itemStackIn) {
		if (this instanceof IPatternMultiToolHost pmh) {
			final IItemHandler pmhInv = pmh.getPatternMultiToolInventory();
			if (pmhInv != null) {
				itemStackIn = ItemHandlerHelper.insertIntoHandler(pmhInv, itemStackIn);
				if (itemStackIn.isEmpty()) {
					return true;
				}
			}
		}

		return ip.add(-1, itemStackIn);
	}
}
