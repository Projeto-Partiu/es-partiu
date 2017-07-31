package br.edu.ufcg.partiu.event;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ordan on 30/07/17.
 */

@Module
public class CreateEventModule {

    private final CreateEventContract.View view;

    public CreateEventModule(CreateEventContract.View view) {
        this.view = view;
    }

    @CreateEventScope
    @Provides
    public CreateEventContract.View providesCreateEventView() {
        return view;
    }
}
