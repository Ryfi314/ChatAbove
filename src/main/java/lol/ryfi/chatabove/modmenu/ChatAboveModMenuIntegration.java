package lol.ryfi.chatabove.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import lol.ryfi.chatabove.config.ChatAboveConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ChatAboveModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ChatAboveConfig.class, parent).get();
    }
}
