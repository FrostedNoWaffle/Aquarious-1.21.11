package net.frosted.aquarious.block;

import net.frosted.aquarious.Aquarious;
import net.frosted.aquarious.block.custom.Starfish;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static final Block PALM = registerBlock("palm", properties ->
            new Block(properties
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(1f)));

    public static final Block STARFISH = registerBlock("starfish",properties ->
            new Starfish(properties
                    .sounds(BlockSoundGroup.CORAL)
                    .breakInstantly()
                    .nonOpaque()
                    .dynamicBounds()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .noCollision()
            ));

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Aquarious.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(Aquarious.MOD_ID, name), toRegister);
    }
    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, Identifier.of(Aquarious.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Aquarious.MOD_ID, name)))));
    }
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Aquarious.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Aquarious.MOD_ID, name)))));
    }

    public static void registerModBlocks(){}
}
