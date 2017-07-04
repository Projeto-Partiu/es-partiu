package br.edu.ufcg.partiu;

import android.app.Application;

public class MainApplication extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        //noinspection deprecation
        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
