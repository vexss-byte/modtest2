package com.lapergods.statue;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Thor statue block. Phase 1: registration only (placeable, no drops, /give only).
 * Interaction, block entity, custom rendering and the multi-block footprint arrive in Phases 3 and 6.
 */
public class ThorStatueBlock extends Block {
	public ThorStatueBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
}
