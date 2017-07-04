package br.edu.ufcg.partiu;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module(
        subcomponents = {
        }
)
public class AppModule {
    public Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    public Context providesContext() {
        return context;
    }
}
