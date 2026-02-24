package de.pasuki.colorful_redstone_lamps.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class ColorfulRedstoneLampsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ColorfulRedstoneLampsLanguageProvider::new);
        pack.addProvider(ColorfulRedstoneLampsGermanLanguageProvider::new);
        pack.addProvider(ColorfulRedstoneLampsModelProvider::new);
        pack.addProvider(ColorfulRedstoneLampsLootTableProvider::new);
        pack.addProvider(ColorfulRedstoneLampsRecipeProvider::new);
        pack.addProvider(ColorfulRedstoneLampsItemTagProvider::new);
        pack.addProvider(ColorfulRedstoneLampsBlockTagProvider::new);
    }
}