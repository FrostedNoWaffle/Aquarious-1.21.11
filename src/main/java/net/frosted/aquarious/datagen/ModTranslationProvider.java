package net.frosted.aquarious.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.frosted.aquarious.Aquarious;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModTranslationProvider extends FabricLanguageProvider {
    public ModTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us",registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("aquarious_tab", "Aquarious");

        translationBuilder.add("block.aquarious.palm", "Palm");
        translationBuilder.add("block.aquarious.starfish", "Starfish");

        translationBuilder.add("item.aquarious.starfish_item", "Starfish");
    }
}
