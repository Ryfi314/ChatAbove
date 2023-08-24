package lol.ryfi.chatabove.chat.render.animation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum AnimationType {

    INTERPOLATION(InterpolatedAnimation.class),
    NONE(NoAnimation.class),
    SCALING(ScalingAnimation.class);

    Class<? extends Animation> animationClass;

    public Animation createAnimation(long timestamp){
        try {
            return animationClass.getConstructor(long.class).newInstance(timestamp);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
