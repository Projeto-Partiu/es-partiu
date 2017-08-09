package br.edu.ufcg.partiu.settings;

import br.edu.ufcg.partiu.shared.MainScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 05/08/17.
 */

@Module
public class SettingsModule {

    private final SettingsContract.View view;

    public SettingsModule(SettingsContract.View view) {
        this.view = view;
    }

    @MainScope
    @Provides
    public SettingsContract.View providesView() {
        return view;
    }
}
