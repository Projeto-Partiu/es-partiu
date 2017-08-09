package br.edu.ufcg.partiu.profile_search;

import dagger.Subcomponent;

/**
 * Created by lucas on 07/08/17.
 */

@ProfileSearchScope
@Subcomponent(modules = ProfileSearchModule.class)
public interface ProfileSearchComponent {

    void inject(ProfileSearchActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder profileSearchModule(ProfileSearchModule module);

        ProfileSearchComponent build();
    }
}
