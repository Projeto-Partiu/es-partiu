package br.edu.ufcg.partiu.event;

import dagger.Subcomponent;

/**
 * Created by ordan on 30/07/17.
 */

@CreateEventScope
@Subcomponent(modules = CreateEventModule.class)
public interface CreateEventComponent {

    void inject(CreateEventActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder createEventModule(CreateEventModule module);

        CreateEventComponent build();
    }
}
