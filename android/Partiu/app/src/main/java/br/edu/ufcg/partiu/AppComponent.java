package br.edu.ufcg.partiu;

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
}