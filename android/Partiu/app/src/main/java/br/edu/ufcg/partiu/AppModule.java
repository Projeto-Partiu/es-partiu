package br.edu.ufcg.partiu;

import android.content.Context;

import br.edu.ufcg.partiu.event.CreateEventComponent;
import br.edu.ufcg.partiu.event.show.ShowEventsComponent;
import br.edu.ufcg.partiu.login.LoginComponent;
import br.edu.ufcg.partiu.shared.MainComponent;
import dagger.Module;
import dagger.Provides;

@Module(
        subcomponents = {
                LoginComponent.class,
                CreateEventComponent.class,
                MainComponent.class,
                ShowEventsComponent.class
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
