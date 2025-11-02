package dev.hooman.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.hooman.CrystalUtilities;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> CrystalUtilities.configInstance.create(parent, CrystalUtilities.configInstance);
    }
}
