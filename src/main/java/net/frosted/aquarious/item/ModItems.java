package net.frosted.aquarious.item;

import net.frosted.aquarious.Aquarious;
import net.frosted.aquarious.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems{

    public static final Item STARFISH_ITEM = registerItem("starfish_item",
            settings -> new BlockItem(ModBlocks.STARFISH, settings));


    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(Aquarious.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Aquarious.MOD_ID, name)))));
    }

    public static void registerModItems(){

    }
}
