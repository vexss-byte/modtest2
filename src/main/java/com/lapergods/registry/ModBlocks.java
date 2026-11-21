package com.lapergods.registry;

import com.lapergods.LaperGods;
import com.lapergods.statue.ThorStatueBlock;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Block + block item registration. The Thor statue is deliberately NOT added to any creative tab,
 * has no recipe and an empty loot table: it can only be obtained with /give @p lapergods:thor_statue.
 */
public final class ModBlocks {
	public static final Block THOR_STATUE = register(
			"thor_statue",
			ThorStatueBlock::new,
			BlockBehaviour.Properties.of().strength(3.0F, 1200.0F).sound(SoundType.STONE).noOcclusion(),
			true
	);

	private ModBlocks() {
	}

	/** Forces class loading so the registrations above run during mod initialisation. */
	public static void init() {
	}

	private static Block register(String name, Function<BlockBehaviour.Properties, Block> factory,
								  BlockBehaviour.Properties properties, boolean registerItem) {
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, LaperGods.id(name));
		Block block = factory.apply(properties.setId(blockKey));

		if (registerItem) {
			ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, LaperGods.id(name));
			BlockItem item = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
			Registry.register(BuiltInRegistries.ITEM, itemKey, item);
		}

		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}
}
