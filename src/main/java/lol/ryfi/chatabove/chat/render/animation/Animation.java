package lol.ryfi.chatabove.chat.render.animation;

import lol.ryfi.chatabove.chat.Line;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class Animation {

    long timestamp;
    public abstract Line[] transform(Line[] lines, float delta);

}
