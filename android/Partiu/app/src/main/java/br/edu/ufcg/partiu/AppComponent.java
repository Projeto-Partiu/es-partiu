package br.edu.ufcg.partiu;

import br.edu.ufcg.partiu.event.CreateEventComponent;
import br.edu.ufcg.partiu.feed.FeedComponent;
import br.edu.ufcg.partiu.login.LoginComponent;
import br.edu.ufcg.partiu.service.ServiceModule;
import dagger.Component;

@AppScope
@Component(
        modules = {
                AppModule.class,
                ServiceModule.class
        }
)
public interface AppComponent {

    LoginComponent.Builder newLoginComponent();

    FeedComponent.Builder newFeedComponent();

    CreateEventComponent.Builder newCreateEventComponent();
}
