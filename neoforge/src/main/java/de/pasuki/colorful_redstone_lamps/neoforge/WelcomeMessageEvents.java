package de.pasuki.colorful_redstone_lamps.neoforge;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import de.pasuki.colorful_redstone_lamps.message.WelcomeMessageHandler;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ColorfulRedstoneLamps.MOD_ID)
public final class WelcomeMessageEvents {
    private WelcomeMessageEvents() {
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            WelcomeMessageHandler.onPlayerJoined(player);
        }
    }
}