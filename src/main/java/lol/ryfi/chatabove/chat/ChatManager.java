package lol.ryfi.chatabove.chat;

import com.mojang.authlib.GameProfile;
import lol.ryfi.chatabove.chat.render.WorldChatRendering;
import lol.ryfi.chatabove.chat.render.animation.InterpolatedAnimation;
import lol.ryfi.chatabove.chat.render.animation.ScalingAnimation;
import lol.ryfi.chatabove.client.ChatAboveClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChatManager {

    ChatAboveClient modInstance;
    Map<String, Message> messages = new ConcurrentHashMap<>();
    @NonFinal
    ExecutorService executorService;

    ChatParser chatParser = new ChatParser();
    WorldChatRendering chatRendering = new WorldChatRendering();

    public void initialize() {
        executorService = Executors.newFixedThreadPool(4);
    }

    public void tick() {
        invalidateOldMessages();
    }

    void invalidateOldMessages() {
        messages.forEach((sender, msg) -> {
            if ((System.currentTimeMillis() - msg.getTimestamp()) >= (1000 * modInstance.getConfig().getFadeSeconds()))
                messages.remove(sender);
        });
    }


    public void render(
            EntityRenderDispatcher renderDispatcher,
            AbstractClientPlayerEntity abstractClientPlayerEntity,
            float f1,
            float g1,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumerProvider,
            int i1
    ) {
        if(modInstance.getConfig().isEnabled()){
            chatRendering.render(
                    messages.get(abstractClientPlayerEntity.getGameProfile().getName()),
                    renderDispatcher,
                    abstractClientPlayerEntity,
                    f1,
                    g1,
                    matrices,
                    vertexConsumerProvider,
                    i1
            );
        }
    }

    public void processMessage(String text) {
        processMessage(null, text);
    }

    public void processMessage(GameProfile profile, String text) {
        executorService.execute(() -> {
            GameProfile finalGameProfile = profile != null ? profile : retriveGameProfile(text);

            if (finalGameProfile == null) return;

            List<String> parsed = chatParser.parseChat(finalGameProfile, text, modInstance.getConfig().getMaxLineSize());

            Collections.reverse(parsed); // this is used for reverse array because people reading text from top to bottom

            Line[] lines = new Line[parsed.size()];

            for (int i = 0; i < parsed.size(); i++) {

                lines[i] =
                        Line.builder()
                                .text(parsed.get(i))
                                .position(new Vector3f(0, (i * 0.3f), 0))
                                .scale(new Vector3f(-0.025f, -0.025f, 0.025f))
                                .build();


            }

            long timestamp = System.currentTimeMillis();

            messages.put(
                    finalGameProfile.getName(),
                    Message.builder()
                            .sender(finalGameProfile)
                            .message(lines)
                            .animation(modInstance.getConfig().getAnimationType().createAnimation(timestamp))
                            .timestamp(timestamp)
                            .build()
            );
        });
    }

    public GameProfile retriveGameProfile(String input) {
        return Arrays.stream(
                        input.split("(§.)|[^\\w]")
                ).map(word -> {
                    if (word.isEmpty()) return null;
                    PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(word);
                    if (playerListEntry != null) return playerListEntry.getProfile();
                    return null;
                })
                .filter(Objects::nonNull).findFirst().get();
    }

}
