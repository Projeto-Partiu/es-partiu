package br.edu.ufcg.partiu.event_detail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 07/08/17.
 */

@Module
public class EventDetailModule {

    private final EventDetailContract.View view;

    public EventDetailModule(EventDetailContract.View view) {
        this.view = view;
    }

    @EventDetailScope
    @Provides
    public EventDetailContract.View providesView() {
        return view;
    }
}
