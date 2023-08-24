package lol.ryfi.chatabove.chat;

import com.mojang.authlib.GameProfile;
import lol.ryfi.chatabove.chat.render.animation.Animation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Message {
    GameProfile sender;
    Line[] message;
    Animation animation;
    long timestamp;
}
