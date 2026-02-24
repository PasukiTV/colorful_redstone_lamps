package de.pasuki.colorful_redstone_lamps.fabric.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ColorfulRedstoneLampsModelProvider implements DataProvider {
    private static final String MOD_ID = "colorful_redstone_lamps";

    private final FabricDataOutput output;

    public ColorfulRedstoneLampsModelProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        PackOutput.PathProvider blockStateProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        PackOutput.PathProvider blockModelProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/block");
        PackOutput.PathProvider itemModelProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (DyeColor color : DyeColor.values()) {
            String base = color.getName() + "_redstone_lamp";

            String offTexture = "block/" + base;
            String onTexture = "block/" + base + "_on";

            futures.add(saveJson(cachedOutput, blockModelProvider.json(id(base + "_off")), cubeAllModel(offTexture)));
            futures.add(saveJson(cachedOutput, blockModelProvider.json(id(base + "_on")), cubeAllModel(onTexture)));

            futures.add(saveJson(cachedOutput, blockStateProvider.json(id(base)), lampBlockStateJson(base + "_off", base + "_on")));
            futures.add(saveJson(cachedOutput, blockStateProvider.json(id(base + "_inverted")), lampBlockStateJson(base + "_off", base + "_on")));

            futures.add(saveJson(cachedOutput, itemModelProvider.json(id(base)), itemParentModel("block/" + base + "_off")));
            futures.add(saveJson(cachedOutput, itemModelProvider.json(id(base + "_inverted")), itemParentModel("block/" + base + "_on")));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Colorful Redstone Lamps Models";
    }

    private static CompletableFuture<?> saveJson(CachedOutput cachedOutput, Path path, JsonObject json) {
        return DataProvider.saveStable(cachedOutput, json, path);
    }

    private static JsonObject cubeAllModel(String texturePath) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_all");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", MOD_ID + ":" + texturePath);
        json.add("textures", textures);

        return json;
    }

    private static JsonObject lampBlockStateJson(String offModelName, String onModelName) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        JsonObject off = new JsonObject();
        off.addProperty("model", MOD_ID + ":block/" + offModelName);
        variants.add("lit=false", off);

        JsonObject on = new JsonObject();
        on.addProperty("model", MOD_ID + ":block/" + onModelName);
        variants.add("lit=true", on);

        root.add("variants", variants);
        return root;
    }

    private static JsonObject itemParentModel(String parentPath) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD_ID + ":" + parentPath);
        return json;
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}