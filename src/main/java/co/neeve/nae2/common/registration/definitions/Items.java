package co.neeve.nae2.common.registration.definitions;

import appeng.api.AEApi;
import appeng.api.config.Upgrades;
import appeng.api.definitions.IItemDefinition;
import appeng.bootstrap.components.IPostInitComponent;
import appeng.bootstrap.components.IRecipeRegistrationComponent;
import appeng.core.features.ItemDefinition;
import co.neeve.nae2.NAE2;
import co.neeve.nae2.client.gui.PatternMultiToolButtonHandler;
import co.neeve.nae2.common.features.Features;
import co.neeve.nae2.common.items.VirtualPattern;
import co.neeve.nae2.common.items.cells.DenseFluidCell;
import co.neeve.nae2.common.items.cells.DenseItemCell;
import co.neeve.nae2.common.items.cells.ec.ExtremeFluidCell;
import co.neeve.nae2.common.items.cells.ec.ExtremeItemCell;
import co.neeve.nae2.common.items.cells.handlers.ExtremeCellHandler;
import co.neeve.nae2.common.items.cells.handlers.VoidCellHandler;
import co.neeve.nae2.common.items.cells.multiCell.MultiFluidCell;
import co.neeve.nae2.common.items.cells.multiCell.MultiItemCell;
import co.neeve.nae2.common.items.cells.singlecell.DenseSingleFluidCell;
import co.neeve.nae2.common.items.cells.singlecell.DenseSingleItemCell;
import co.neeve.nae2.common.items.cells.vc.VoidFluidCell;
import co.neeve.nae2.common.items.cells.vc.VoidItemCell;
import co.neeve.nae2.common.items.patternmultitool.ToolPatternMultiTool;
import co.neeve.nae2.common.recipes.handlers.VoidCellRecipe;
import co.neeve.nae2.common.registration.registry.Registry;
import co.neeve.nae2.common.registration.registry.interfaces.Definitions;
import co.neeve.nae2.common.registration.registry.rendering.NoItemRendering;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class Items implements Definitions<IItemDefinition> {
	private final Object2ObjectOpenHashMap<String, IItemDefinition> byId = new Object2ObjectOpenHashMap<>();

	private final IItemDefinition patternMultiTool;

	private final IItemDefinition storageCellVoid;
	private final IItemDefinition fluidStorageCellVoid;
	private final IItemDefinition storageCell256K;
	private final IItemDefinition storageCell1024K;
	private final IItemDefinition storageCell4096K;
	private final IItemDefinition storageCell16384K;
	private final IItemDefinition storageCellFluid256K;
	private final IItemDefinition storageCellFluid1024K;
	private final IItemDefinition storageCellFluid4096K;
	private final IItemDefinition storageCellFluid16384K;
	private final IItemDefinition virtualPattern;
	//单种类存储
	private final IItemDefinition singlestorageCell256K;
	private final IItemDefinition singlestorageCell1024K;
	private final IItemDefinition singlestorageCell4096K;
	private final IItemDefinition singlestorageCell16384K;
	private final IItemDefinition singlestorageCellMax;
	private final IItemDefinition singlestorageCellFluid256K;
	private final IItemDefinition singlestorageCellFluid1024K;
	private final IItemDefinition singlestorageCellFluid4096K;
	private final IItemDefinition singlestorageCellFluid16384K;
	private final IItemDefinition singlestorageCellFluidMax;

	//多重硬盘 注册1k-64k
	private final IItemDefinition multistorageCell1K;
	private final IItemDefinition multistorageCell4K;
	private final IItemDefinition multistorageCell16K;
	private final IItemDefinition multistorageCell64K;

	private final IItemDefinition multistorageCell256K;
	private final IItemDefinition multistorageCell1024K;
	private final IItemDefinition multistorageCell4096K;
	private final IItemDefinition multistorageCell16384K;


	private final IItemDefinition multistorageCellFluid1K;
	private final IItemDefinition multistorageCellFluid4K;
	private final IItemDefinition multistorageCellFluid16K;
	private final IItemDefinition multistorageCellFluid64K;

	private final IItemDefinition multistorageCellFluid256K;
	private final IItemDefinition multistorageCellFluid1024K;
	private final IItemDefinition multistorageCellFluid4096K;
	private final IItemDefinition multistorageCellFluid16384K;

	private final IItemDefinition storageCellSingularity;

	private final IItemDefinition storageCellFluidSingularity;
	public Items(Registry registry) {
		this.virtualPattern = this.registerById(registry.item("virtual_pattern", VirtualPattern::new)
			.hide()
			.rendering(new NoItemRendering())
			.build());

		this.patternMultiTool = this.registerById(registry.item("pattern_multiplier", ToolPatternMultiTool::new)
			.features(Features.PATTERN_MULTI_TOOL)
			.bootstrap((item) -> (IPostInitComponent) r -> {
				Upgrades.CAPACITY.registerItem(new ItemStack(item, 1), 3);

				if (r.isClient()) {
					MinecraftForge.EVENT_BUS.register(new PatternMultiToolButtonHandler());
				}

				// Void Cells.

			})
			.build());

		this.storageCellVoid = this.registerById(registry.item("storage_cell_void", VoidItemCell::new)
			.features(Features.VOID_CELLS)
			.build());

		this.fluidStorageCellVoid = this.registerById(
			registry.item("fluid_storage_cell_void", VoidFluidCell::new)
				.features(Features.VOID_CELLS)
				.build());

		registry.addBootstrapComponent((IPostInitComponent) r -> {
			if (this.storageCellVoid.isEnabled() || this.fluidStorageCellVoid.isEnabled()) {
				AEApi.instance().registries().cell().addCellHandler(new VoidCellHandler());
			}
		});

		registry.addBootstrapComponent((IRecipeRegistrationComponent) (side, r) -> NAE2.definitions().materials().cellPartVoid().maybeStack(
			1).ifPresent(voidComponent ->
			AEApi.instance().definitions().materials().emptyStorageCell().maybeStack(1).ifPresent(stack -> {
				this.storageCellVoid.maybeStack(1).ifPresent((itemStack -> r.register(new VoidCellRecipe(
					stack, voidComponent, itemStack).setRegistryName("storage_cell_void"))));

				this.fluidStorageCellVoid.maybeStack(1).ifPresent((itemStack -> r.register(new VoidCellRecipe(
					voidComponent, stack, itemStack).setRegistryName("fluid_storage_cell_void"))));
			})));

		this.storageCell256K = this.registerById(registry.item("storage_cell_256k", () ->
				new DenseItemCell(Materials.MaterialType.CELL_PART_256K,
					(int) Math.pow(2, 8)))
			.features(Features.DENSE_CELLS)
			.build());

		this.storageCell1024K = this.registerById(registry.item("storage_cell_1024k", () ->
				new DenseItemCell(Materials.MaterialType.CELL_PART_1024K,
					(int) Math.pow(2, 10)))
			.features(Features.DENSE_CELLS)
			.build());

		this.storageCell4096K = this.registerById(registry.item("storage_cell_4096k", () ->
				new DenseItemCell(Materials.MaterialType.CELL_PART_4096K,
					(int) Math.pow(2, 12)))
			.features(Features.DENSE_CELLS)
			.build());

		this.storageCell16384K = this.registerById(registry.item("storage_cell_16384k", () ->
				new DenseItemCell(Materials.MaterialType.CELL_PART_16384K,
					(int) Math.pow(2, 14)))
			.features(Features.DENSE_CELLS)
			.build());

		this.storageCellFluid256K = this.registerById(registry.item("storage_cell_fluid_256k", () ->
				new DenseFluidCell(Materials.MaterialType.CELL_FLUID_PART_256K,
					(int) Math.pow(2, 8)))
			.features(Features.DENSE_FLUID_CELLS)
			.build());

		this.storageCellFluid1024K = this.registerById(registry.item("storage_cell_fluid_1024k", () ->
				new DenseFluidCell(Materials.MaterialType.CELL_FLUID_PART_1024K,
					(int) Math.pow(2, 10)))
			.features(Features.DENSE_FLUID_CELLS)
			.build());

		this.storageCellFluid4096K = this.registerById(registry.item("storage_cell_fluid_4096k", () ->
				new DenseFluidCell(Materials.MaterialType.CELL_FLUID_PART_4096K,
					(int) Math.pow(2, 12)))
			.features(Features.DENSE_FLUID_CELLS)
			.build());

		this.storageCellFluid16384K = this.registerById(registry.item("storage_cell_fluid_16384k", () ->
				new DenseFluidCell(Materials.MaterialType.CELL_FLUID_PART_16384K,
					(int) Math.pow(2, 14)))
			.features(Features.DENSE_FLUID_CELLS)
			.build());


		this.singlestorageCell256K = this.registerById(registry.item("single_storage_cell_256k", () ->
						new DenseSingleItemCell(Materials.MaterialType.CELL_PART_256K,
								(int) Math.pow(2, 8)))
				.features(Features.DENSE_CELLS)
				.build());

		this.singlestorageCell1024K = this.registerById(registry.item("single_storage_cell_1024k", () ->
						new DenseSingleItemCell(Materials.MaterialType.CELL_PART_1024K,
								(int) Math.pow(2, 10)))
				.features(Features.DENSE_CELLS)
				.build());

		this.singlestorageCell4096K = this.registerById(registry.item("single_storage_cell_4096k", () ->
						new DenseSingleItemCell(Materials.MaterialType.CELL_PART_4096K,
								(int) Math.pow(2, 12)))
				.features(Features.DENSE_CELLS)
				.build());

		this.singlestorageCell16384K = this.registerById(registry.item("single_storage_cell_16384k", () ->
						new DenseSingleItemCell(Materials.MaterialType.CELL_PART_16384K,
								(int) Math.pow(2, 14)))
				.features(Features.DENSE_CELLS)
				.build());
		this.singlestorageCellMax = this.registerById(registry.item("single_storage_cell_max", () ->
						new DenseSingleItemCell(Materials.MaterialType.CELL_PART_MAX,
								2097151))
				.features(Features.DENSE_CELLS)
				.build());


		this.singlestorageCellFluid256K = this.registerById(registry.item("single_storage_cell_fluid_256k", () ->
						new DenseSingleFluidCell(Materials.MaterialType.CELL_FLUID_PART_256K,
								(int) Math.pow(2, 8)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.singlestorageCellFluid1024K = this.registerById(registry.item("single_storage_cell_fluid_1024k", () ->
						new DenseSingleFluidCell(Materials.MaterialType.CELL_FLUID_PART_1024K,
								(int) Math.pow(2, 10)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.singlestorageCellFluid4096K = this.registerById(registry.item("single_storage_cell_fluid_4096k", () ->
						new DenseSingleFluidCell(Materials.MaterialType.CELL_FLUID_PART_4096K,
								(int) Math.pow(2, 12)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.singlestorageCellFluid16384K = this.registerById(registry.item("single_storage_cell_fluid_16384k", () ->
						new DenseSingleFluidCell(Materials.MaterialType.CELL_FLUID_PART_16384K,
								(int) Math.pow(2, 14)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());
		this.singlestorageCellFluidMax = this.registerById(registry.item("single_storage_cell_fluid_max", () ->
						new DenseSingleFluidCell(Materials.MaterialType.CELL_FLUID_PART_MAX,
								2097151))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		//多重硬盘
		this.multistorageCell1K = this.registerById(registry.item("multi_storage_cell_1k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_1K,
								(int) Math.pow(2, 0)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell4K = this.registerById(registry.item("multi_storage_cell_4k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_4K,
								(int) Math.pow(2, 2)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell16K = this.registerById(registry.item("multi_storage_cell_16k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_16K,
								(int) Math.pow(2, 4)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell64K = this.registerById(registry.item("multi_storage_cell_64k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_64K,
								(int) Math.pow(2, 6)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell256K = this.registerById(registry.item("multi_storage_cell_256k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_256K,
								(int) Math.pow(2, 8)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell1024K = this.registerById(registry.item("multi_storage_cell_1024k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_1024K,
								(int) Math.pow(2, 10)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell4096K = this.registerById(registry.item("multi_storage_cell_4096k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_4096K,
								(int) Math.pow(2, 12)))
				.features(Features.DENSE_CELLS)
				.build());

		this.multistorageCell16384K = this.registerById(registry.item("multi_storage_cell_16384k", () ->
						new MultiItemCell(Materials.MaterialType.MULTI_CELL_PART_16384K,
								(int) Math.pow(2, 14)))
				.features(Features.DENSE_CELLS)
				.build());




		this.multistorageCellFluid1K = this.registerById(registry.item("multi_storage_cell_fluid_1k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_1K,
								(int) Math.pow(2, 0)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid4K = this.registerById(registry.item("multi_storage_cell_fluid_4k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_4K,
								(int) Math.pow(2, 2)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid16K = this.registerById(registry.item("multi_storage_cell_fluid_16k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_16K,
								(int) Math.pow(2, 4)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid64K = this.registerById(registry.item("multi_storage_cell_fluid_64k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_64K,
								(int) Math.pow(2, 6)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid256K = this.registerById(registry.item("multi_storage_cell_fluid_256k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_256K,
								(int) Math.pow(2, 8)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid1024K = this.registerById(registry.item("multi_storage_cell_fluid_1024k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_1024K,
								(int) Math.pow(2, 10)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid4096K = this.registerById(registry.item("multi_storage_cell_fluid_4096k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_4096K,
								(int) Math.pow(2, 12)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.multistorageCellFluid16384K = this.registerById(registry.item("multi_storage_cell_fluid_16384k", () ->
						new MultiFluidCell(Materials.MaterialType.MULTI_CELL_FLUID_PART_16384K,
								(int) Math.pow(2, 14)))
				.features(Features.DENSE_FLUID_CELLS)
				.build());

		this.storageCellSingularity = this.registerById(registry.item("storage_cell_singularity", () ->
						new ExtremeItemCell(Materials.MaterialType.CELL_PART_SINGULARITY,
								Long.MAX_VALUE / 8))
				.features(Features.EXTREME_CELLS)
				.build());

		this.storageCellFluidSingularity = this.registerById(registry.item("storage_cell_fluid_singularity", () ->
						new ExtremeFluidCell(Materials.MaterialType.CELL_FLUID_PART_SINGULARITY,
								Long.MAX_VALUE / 8000))
				.features(Features.EXTREME_FLUID_CELLS)
				.build());


		registry.addBootstrapComponent((IPostInitComponent) r -> {
			var items = AEApi.instance().definitions().items();
			var cellDef = items.cell1k();
			if (Features.DENSE_CELLS.isEnabled() && cellDef.isEnabled()) {
				mirrorCellUpgrades(cellDef, new IItemDefinition[]{
					this.storageCell256K,
					this.storageCell1024K,
					this.storageCell4096K,
					this.storageCell16384K,
					this.storageCellVoid,
						this.singlestorageCell256K,
						this.singlestorageCell1024K,
						this.singlestorageCell4096K,
						this.singlestorageCell16384K,
						this.singlestorageCellMax,
						this.multistorageCell1K,
						this.multistorageCell4K,
						this.multistorageCell16K,
						this.multistorageCell64K,
						this.multistorageCell256K,
						this.multistorageCell1024K,
						this.multistorageCell4096K,
						this.multistorageCell16384K
				});
			}
			if (Features.EXTREME_CELLS.isEnabled() && cellDef.isEnabled()) {
				mirrorCellUpgrades(cellDef, new IItemDefinition[] {
						this.storageCellSingularity,
						this.storageCellFluidSingularity
				});

				AEApi.instance().registries().cell().addCellHandler(new ExtremeCellHandler());
			}

			var fluidCellDef = items.fluidCell1k();
			if (Features.DENSE_FLUID_CELLS.isEnabled() && fluidCellDef.isEnabled()) {
				mirrorCellUpgrades(fluidCellDef, new IItemDefinition[]{
					this.storageCellFluid256K,
					this.storageCellFluid1024K,
					this.storageCellFluid4096K,
					this.storageCellFluid16384K,
					this.fluidStorageCellVoid,
						this.singlestorageCellFluid256K,
						this.singlestorageCellFluid1024K,
						this.singlestorageCellFluid4096K,
						this.singlestorageCellFluid16384K,
						this.singlestorageCellFluidMax,
						this.multistorageCellFluid1K,
						this.multistorageCellFluid4K,
						this.multistorageCellFluid16K,
						this.multistorageCellFluid64K,
						this.multistorageCellFluid256K,
						this.multistorageCellFluid1024K,
						this.multistorageCellFluid4096K,
						this.multistorageCellFluid16384K
				});
			}
		});
	}

	private static void mirrorCellUpgrades(IItemDefinition cellDef, IItemDefinition[] cells) {
		var supported = new java.util.HashMap<Upgrades, Integer>();
		Arrays.stream(Upgrades.values())
			.forEach(upgrade ->
				upgrade.getSupported().entrySet().stream()
					.filter(x -> cellDef.isSameAs(x.getKey()))
					.map(Map.Entry::getValue)
					.findFirst()
					.ifPresent(value -> supported.put(upgrade, value)));

		Arrays.stream(cells).forEach(iItemDefinition ->
			supported.forEach((key, value) ->
				key.registerItem(iItemDefinition, value)));
	}

	private IItemDefinition registerById(ItemDefinition item) {
		this.byId.put(item.identifier(), item);
		return item;
	}

	@Override
	public Optional<IItemDefinition> getById(String id) {
		return Optional.ofNullable(this.byId.getOrDefault(id, null));
	}

	public IItemDefinition patternMultiTool() {
		return this.patternMultiTool;
	}

	public IItemDefinition storageCellVoid() {
		return this.storageCellVoid;
	}

	public IItemDefinition fluidStorageCellVoid() {
		return this.fluidStorageCellVoid;
	}

	public IItemDefinition virtualPattern() {
		return this.virtualPattern;
	}

	public IItemDefinition storageCell256K() {
		return this.storageCell256K;
	}

	public IItemDefinition storageCell1024K() {
		return this.storageCell1024K;
	}

	public IItemDefinition storageCell4096K() {
		return this.storageCell4096K;
	}

	public IItemDefinition storageCell16384K() {
		return this.storageCell16384K;
	}

	public IItemDefinition storageCellFluid256K() {
		return this.storageCellFluid256K;
	}

	public IItemDefinition storageCellFluid1024K() {
		return this.storageCellFluid1024K;
	}

	public IItemDefinition storageCellFluid4096K() {
		return this.storageCellFluid4096K;
	}

	public IItemDefinition storageCellFluid16384K() {return this.storageCellFluid16384K;}

	public IItemDefinition singlestorageCell256K() {
		return this.singlestorageCell256K;
	}

	public IItemDefinition singlestorageCell1024K() {
		return this.singlestorageCell1024K;
	}

	public IItemDefinition singlestorageCell4096K() {
		return this.singlestorageCell4096K;
	}

	public IItemDefinition singlestorageCell16384K() {
		return this.singlestorageCell16384K;
	}
	public IItemDefinition singlestorageCellMax() {
		return this.singlestorageCellMax;
	}

	public IItemDefinition singlestorageCellFluid256K() {
		return this.singlestorageCellFluid256K;
	}

	public IItemDefinition singlestorageCellFluid1024K() {
		return this.singlestorageCellFluid1024K;
	}

	public IItemDefinition singlestorageCellFluid4096K() {
		return this.singlestorageCellFluid4096K;
	}

	public IItemDefinition singlestorageCellFluid16384K() {
		return this.singlestorageCellFluid16384K;
	}
	public IItemDefinition singlestorageCellFluidMax() {
		return this.singlestorageCellFluidMax;
	}

	public IItemDefinition multistorageCell1K() {
		return this.multistorageCell1K;
	}
	public IItemDefinition multistorageCell4K() {
		return this.multistorageCell4K;
	}
	public IItemDefinition multistorageCell16K() {
		return this.multistorageCell16K;
	}
	public IItemDefinition multistorageCell64K() {
		return this.multistorageCell64K;
	}
	public IItemDefinition multistorageCell256K() {
		return this.multistorageCell256K;
	}
	public IItemDefinition multistorageCell1024K() {
		return this.multistorageCell1024K;
	}
	public IItemDefinition multistorageCell4096K() {
		return this.multistorageCell4096K;
	}
	public IItemDefinition multistorageCell16384K() {
		return this.multistorageCell16384K;
	}


	public IItemDefinition multistorageCellFluid1K() {
		return this.multistorageCellFluid1K;
	}
	public IItemDefinition multistorageCellFluid4K() {
		return this.multistorageCellFluid4K;
	}
	public IItemDefinition multistorageCellFluid16K() {
		return this.multistorageCellFluid16K;
	}
	public IItemDefinition multistorageCellFluid64K() {
		return this.multistorageCellFluid64K;
	}
	public IItemDefinition multistorageCellFluid256K() {
		return this.multistorageCellFluid256K;
	}
	public IItemDefinition multistorageCellFluid1024K() {
		return this.multistorageCellFluid1024K;
	}
	public IItemDefinition multistorageCellFluid4096K() {
		return this.multistorageCellFluid4096K;
	}
	public IItemDefinition multistorageCellFluid16384K() {
		return this.multistorageCellFluid16384K;
	}

	public IItemDefinition storageCellSingularity() {
		return this.storageCellSingularity;
	}
	public IItemDefinition storageCellFluidSingularity() {
		return this.storageCellFluidSingularity;
	}

}
