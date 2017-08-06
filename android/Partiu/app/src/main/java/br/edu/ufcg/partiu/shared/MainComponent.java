package br.edu.ufcg.partiu.shared;

import br.edu.ufcg.partiu.feed.FeedModule;
import br.edu.ufcg.partiu.settings.SettingsModule;
import dagger.Subcomponent;

/**
 * Created by lucas on 05/08/17.
 */

@MainScope
@Subcomponent(modules = {
        FeedModule.class,
        SettingsModule.class
})
public interface MainComponent {

    void inject(MainActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder feedModule(FeedModule module);

        Builder settingsModule(SettingsModule module);

        MainComponent build();
    }
}