package br.edu.ufcg.partiu;

import br.edu.ufcg.partiu.event.CreateEventComponent;
import br.edu.ufcg.partiu.event_detail.EventDetailComponent;
import br.edu.ufcg.partiu.login.LoginComponent;
import br.edu.ufcg.partiu.profile_search.ProfileSearchComponent;
import br.edu.ufcg.partiu.service.ServiceModule;
import br.edu.ufcg.partiu.shared.MainComponent;
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

    CreateEventComponent.Builder newCreateEventComponent();

    MainComponent.Builder newMainComponent();

    ProfileSearchComponent.Builder newProfileSearchComponent();

    EventDetailComponent.Builder newEventDetailComponent();
}
