package lol.ryfi.chatabove.mixin;

import com.mojang.authlib.GameProfile;
import lol.ryfi.chatabove.client.ChatAboveClient;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageHandler.class)
public abstract class MessageHandlerMixin {

    @Inject(method = "onProfilelessMessage", at = @At("HEAD"))
    public void onProfilelessMessageInjected(Text content, MessageType.Parameters params, CallbackInfo ci) {
        ChatAboveClient.getInstance().getChatManager().processMessage(content.getString());
    }

    @Inject(method = "onChatMessage", at = @At("HEAD"))
    public void onChatMessageInjected(SignedMessage message, GameProfile sender, MessageType.Parameters params, CallbackInfo ci) {
        ChatAboveClient.getInstance().getChatManager().processMessage(sender, message.getContent().getString());
    }

    @Inject(method = "onGameMessage", at = @At("HEAD"))
    public void onGameMessageInjected(Text message, boolean overlay, CallbackInfo ci) {
        ChatAboveClient.getInstance().getChatManager().processMessage(message.getString());
    }

}
