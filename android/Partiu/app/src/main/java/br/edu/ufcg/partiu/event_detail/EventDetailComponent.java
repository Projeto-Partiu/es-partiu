package br.edu.ufcg.partiu.event_detail;

import dagger.Subcomponent;

/**
 * Created by lucas on 07/08/17.
 */

@EventDetailScope
@Subcomponent(modules = EventDetailModule.class)
public interface EventDetailComponent {

    void inject(EventDetailActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder eventDetailModule(EventDetailModule module);

        EventDetailComponent build();
    }
}
