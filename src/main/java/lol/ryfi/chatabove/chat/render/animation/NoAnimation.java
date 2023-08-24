package lol.ryfi.chatabove.chat.render.animation;

import lol.ryfi.chatabove.chat.Line;

public class NoAnimation extends Animation {
    public NoAnimation(long timestamp) {
        super(timestamp);
    }

    @Override
    public Line[] transform(Line[] lines, float delta) {
        return lines;
    }
}
