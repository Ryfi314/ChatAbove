package lol.ryfi.chatabove.chat.render.animation;

import lol.ryfi.chatabove.chat.Line;
import lol.ryfi.chatabove.client.ChatAboveClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.joml.Vector3f;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

public class ScalingAnimation extends Animation {
    @NonFinal
    Line[] interpolatedLines;

    public ScalingAnimation(long timestamp) {
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
                cloned.setScale(new Vector3f(-0.00025f, -0.00025f, 0.00025f));
                interpolatedLines[index] = cloned;
            }

            Line interpolatedLine = interpolatedLines[index];
            if ((System.currentTimeMillis() - timestamp) <= (1000 * ChatAboveClient.getInstance().getConfig().getFadeSeconds()) - 50)
                interpolatedLine.setScale(interpolatedLine.getScale().lerp(line.getScale(), delta / 6));
            else {
                interpolatedLine.setScale(line.getScale().lerp(new Vector3f(-0.00025f, -0.00025f, 0.00025f), delta / 6));

            }


            index++;
        }

        return interpolatedLines;
    }
}
