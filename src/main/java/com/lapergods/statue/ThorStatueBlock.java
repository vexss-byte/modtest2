package com.lapergods.statue;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Thor statue block. Phase 1: registration only (placeable, no drops, /give only).
 * Interaction, block entity, custom rendering and the multi-block footprint arrive in Phases 3 and 6.
 */
public class ThorStatueBlock extends Block {
	public ThorStatueBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	/**
	 * Blocks creative pick-block (middle click): the server ignores an empty stack in
	 * ServerGamePacketListenerImpl#handlePickItemFromBlock, so the statue cannot be copied into an inventory.
	 * Signature checked against the 26.3 sources (BlockBehaviour#getCloneItemStack).
	 */
	@Override
	protected ItemStack getCloneItemStack(final LevelReader level, final BlockPos pos, final BlockState state,
										  final boolean includeData) {
		return ItemStack.EMPTY;
	}
}
