package lol.ryfi.chatabove.client;

import lol.ryfi.chatabove.chat.ChatManager;
import lol.ryfi.chatabove.config.ChatAboveConfig;
import lombok.Getter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Getter
public class ChatAboveClient implements ClientModInitializer {
    @Getter
    private static ChatAboveClient instance;

    ChatManager chatManager = new ChatManager(this);
    ChatAboveConfig config;

    @Override
    public void onInitializeClient() {
        instance = this;
        AutoConfig.register(ChatAboveConfig.class, JanksonConfigSerializer::new);

        config = AutoConfig.getConfigHolder(ChatAboveConfig.class).getConfig();
        chatManager.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(client -> chatManager.tick());
    }
}
