package net.frosted.aquarious;

import net.fabricmc.api.ModInitializer;

import net.frosted.aquarious.block.ModBlocks;
import net.frosted.aquarious.item.ModItems;
import net.frosted.aquarious.world.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aquarious implements ModInitializer {
	public static final String MOD_ID = "aquarious";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModCreativeTab.registerCreativeTab();
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModWorldGeneration.generateModWorldGen();
	}
}