package br.edu.ufcg.partiu.profile_search;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 07/08/17.
 */

@Module
public class ProfileSearchModule {

    private final ProfileSearchContract.View view;

    public ProfileSearchModule(ProfileSearchContract.View view) {
        this.view = view;
    }

    @ProfileSearchScope
    @Provides
    public ProfileSearchContract.View providesView() {
        return view;
    }
}
