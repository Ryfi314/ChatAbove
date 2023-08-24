package lol.ryfi.chatabove.chat;

import com.mojang.authlib.GameProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2(topic = "Parser")
public class ChatParser {


    public List<String> parseChat(@NotNull GameProfile gameProfile, @NotNull String message, int maxLineSize) {

        int index = 0;
        String[] words = message.split(" ");
        for (var word : words) {
            if (word.equals(gameProfile.getName())) break;
            index++;
        }


        List<String> strings = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        for (var str : Arrays.copyOf(words, index)) {
            if (
                    (
                            buffer.isEmpty() ?
                                    buffer.append(str) :
                                    buffer.append(" ").append(str)
                    )
                            .length() > maxLineSize
            ) {
                strings.add(buffer.toString());
                buffer.delete(0, buffer.length());
            }
        }
        if (!buffer.isEmpty()) strings.add(buffer.toString());

        return strings;
    }


}
