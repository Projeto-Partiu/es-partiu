package br.edu.ufcg.partiu.event.show;

/**
 * Created by ordan on 06/08/17.
 */


import dagger.Subcomponent;

@ShowEventsScope
@Subcomponent(modules = ShowEventsModule.class)
public interface ShowEventsComponent {

    void inject(ShowEventsActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder showEventsModule(ShowEventsModule module);

        ShowEventsComponent build();
    }

}
