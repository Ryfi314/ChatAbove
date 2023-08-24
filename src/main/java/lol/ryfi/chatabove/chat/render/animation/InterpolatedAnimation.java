package lol.ryfi.chatabove.chat.render.animation;

import lol.ryfi.chatabove.chat.Line;
import lol.ryfi.chatabove.client.ChatAboveClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.joml.Vector3f;

public class InterpolatedAnimation extends Animation {

    @NonFinal
    Line[] interpolatedLines;

    public InterpolatedAnimation(long timestamp) {
        super(timestamp);
    }


    @Override
    public Line[] transform(Line[] lines, float delta) {
        if (interpolatedLines == null || interpolatedLines.length != lines.length)
            interpolatedLines = new Line[lines.length]; // resetting or creating new

        int index = 0;


        for (Line line : lines) {
            if (interpolatedLines[index] == null) {
                Line cloned = line.clone();
                cloned.setPosition(new Vector3f(0, -0.5f, 0));
                interpolatedLines[index] = cloned;
            }

            Line interpolatedLine = interpolatedLines[index];
            if ((System.currentTimeMillis() - timestamp) <= (1000 * ChatAboveClient.getInstance().getConfig().getFadeSeconds()) - 5)
                interpolatedLine.setPosition(interpolatedLine.getPosition().lerp(line.getPosition(), delta / 6));
            else {
                interpolatedLine.setPosition(line.getPosition().lerp(new Vector3f(0, -0.5f, 0), delta / 8));
            }


            index++;
        }

        return interpolatedLines;
    }
}
