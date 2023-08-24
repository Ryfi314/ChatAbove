package lol.ryfi.chatabove.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.joml.Vector3f;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Line implements Cloneable {
    Vector3f position;
    Vector3f scale;
    long color;
    String text;

    @Override
    public Line clone() {
        try {
            Line clone = (Line) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}