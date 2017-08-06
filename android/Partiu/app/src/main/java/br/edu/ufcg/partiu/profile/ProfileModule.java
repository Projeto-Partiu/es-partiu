package br.edu.ufcg.partiu.profile;

import br.edu.ufcg.partiu.shared.MainScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 06/08/17.
 */

@Module
public class ProfileModule {

    private final ProfileContract.View view;

    public ProfileModule(ProfileContract.View view) {
        this.view = view;
    }

    @MainScope
    @Provides
    public ProfileContract.View providesView() {
        return view;
    }
}
