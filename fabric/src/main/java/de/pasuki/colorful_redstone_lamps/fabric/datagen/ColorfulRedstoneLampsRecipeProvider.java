package de.pasuki.colorful_redstone_lamps.fabric.datagen;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import de.pasuki.colorful_redstone_lamps.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public final class ColorfulRedstoneLampsRecipeProvider extends FabricRecipeProvider {
    public ColorfulRedstoneLampsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput out) {
        for (DyeColor color : DyeColor.values()) {
            String base = color.getName() + "_redstone_lamp";

            ItemLike normalLamp = ModBlocks.LAMPS.get(color).get();
            ItemLike invertedLamp = ModBlocks.INVERTED_LAMPS.get(color).get();
            Item dye = dyeFor(color);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, normalLamp, 1)
                    .requires(Items.REDSTONE_LAMP)
                    .requires(dye)
                    .unlockedBy("has_redstone_lamp", has(Items.REDSTONE_LAMP))
                    .unlockedBy("has_dye_" + color.getName(), has(dye))
                    .save(out, id("craft/" + base));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, invertedLamp, 1)
                    .requires(normalLamp)
                    .unlockedBy("has_" + base, has(normalLamp))
                    .save(out, id("invert/" + base + "_to_inverted"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, normalLamp, 1)
                    .requires(invertedLamp)
                    .unlockedBy("has_" + base + "_inverted", has(invertedLamp))
                    .save(out, id("invert/" + base + "_to_normal"));
        }
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ColorfulRedstoneLamps.MOD_ID, path);
    }

    private static Item dyeFor(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.WHITE_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case LIME -> Items.LIME_DYE;
            case PINK -> Items.PINK_DYE;
            case GRAY -> Items.GRAY_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case CYAN -> Items.CYAN_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case BLUE -> Items.BLUE_DYE;
            case BROWN -> Items.BROWN_DYE;
            case GREEN -> Items.GREEN_DYE;
            case RED -> Items.RED_DYE;
            case BLACK -> Items.BLACK_DYE;
        };
    }
}