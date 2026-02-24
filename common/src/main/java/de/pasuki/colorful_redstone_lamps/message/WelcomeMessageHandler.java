package de.pasuki.colorful_redstone_lamps.message;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public final class WelcomeMessageHandler {
    private static final String CURSEFORGE_URL = "https://www.curseforge.com/minecraft/mc-mods/colorful-redstone-lamps/comments";
    private static final String MODRINTH_URL = "https://modrinth.com/mod/colorful-redstone-lamps";
    private static final String DISCORD_URL = "https://discord.gg/";

    private WelcomeMessageHandler() {
    }

    public static void onPlayerJoined(ServerPlayer player) {
        ServerLevel overworld = player.server.overworld();
        WelcomeMessageData data = WelcomeMessageData.get(overworld);
        var playerId = player.getUUID();

        if (!data.wasShown(playerId)) {
            player.sendSystemMessage(
                    Component.translatable("colorful_redstone_lamps.welcome.title")
                            .withStyle(ChatFormatting.GRAY)
            );

            player.sendSystemMessage(
                    Component.translatable("colorful_redstone_lamps.welcome.feedback_intro")
                            .withStyle(ChatFormatting.DARK_GRAY)
                            .append(Component.literal(" "))
                            .append(link("colorful_redstone_lamps.welcome.feedback.curseforge", CURSEFORGE_URL))
                            .append(Component.literal(" | ").withStyle(ChatFormatting.DARK_GRAY))
                            .append(link("colorful_redstone_lamps.welcome.feedback.modrinth", MODRINTH_URL))
                            .append(Component.literal(" | ").withStyle(ChatFormatting.DARK_GRAY))
                            .append(link("colorful_redstone_lamps.welcome.feedback.discord", DISCORD_URL))
            );

            data.setShown(playerId);
        }
    }

    private static Component link(String key, String url) {
        return Component.translatable(key)
                .withStyle(style -> style
                        .withColor(ChatFormatting.AQUA)
                        .withUnderlined(true)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                );
    }
}