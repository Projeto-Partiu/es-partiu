package br.edu.ufcg.partiu.feed;

import br.edu.ufcg.partiu.shared.MainScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 26/07/17.
 */

@Module
public class FeedModule {

    private final FeedContract.View view;

    public FeedModule(FeedContract.View view) {
        this.view = view;
    }

    @MainScope
    @Provides
    public FeedContract.View providesView() {
        return view;
    }
}
