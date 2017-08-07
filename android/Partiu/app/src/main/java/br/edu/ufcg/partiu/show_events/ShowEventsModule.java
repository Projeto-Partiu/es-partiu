package br.edu.ufcg.partiu.show_events;


import br.edu.ufcg.partiu.shared.MainScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ordan on 06/08/17.
 */

@Module
public class ShowEventsModule {
    private final ShowEventsContract.View view;

    public ShowEventsModule(ShowEventsContract.View view) {
        this.view = view;
    }

    @MainScope
    @Provides
    public ShowEventsContract.View providesCreateEventView() {
        return view;
    }
}
