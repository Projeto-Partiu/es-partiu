package br.edu.ufcg.partiu.feed;

import br.edu.ufcg.partiu.shared.MainActivity;
import dagger.Subcomponent;

/**
 * Created by lucas on 26/07/17.
 */

@FeedScope
@Subcomponent(modules = FeedModule.class)
public interface FeedComponent {

    void inject(MainActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder feedModule(FeedModule module);

        FeedComponent build();
    }
}
