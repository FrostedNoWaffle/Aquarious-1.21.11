package net.frosted.aquarious;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.frosted.aquarious.block.ModBlocks;
import net.frosted.aquarious.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModCreativeTab {

    public static final ItemGroup AQUARIOUS_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Aquarious.MOD_ID, "aquarious_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.STARFISH_ITEM)).displayName(Text.translatable("aquarious_tab"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.STARFISH_ITEM);






                    }).build());

    public static void registerCreativeTab(){
    }
}
