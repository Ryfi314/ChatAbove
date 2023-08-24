package lol.ryfi.chatabove.config;

import lol.ryfi.chatabove.chat.render.animation.AnimationType;
import lombok.Getter;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Getter
@Config(name = " chatabove")
public class ChatAboveConfig implements ConfigData {

    boolean enabled = true;
    @ConfigEntry.BoundedDiscrete(min = 5, max = 100)
    int maxLineSize = 25;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    double fadeSeconds = 3;

    AnimationType animationType = AnimationType.NONE;
}
