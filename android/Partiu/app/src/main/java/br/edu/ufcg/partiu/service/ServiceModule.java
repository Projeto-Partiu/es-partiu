package br.edu.ufcg.partiu.service;

import android.content.Context;

import br.edu.ufcg.partiu.AppScope;
import br.edu.ufcg.partiu.BuildConfig;
import br.edu.ufcg.partiu.service.repository.UserRepository;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Módulo dos services, todos os services usados no app devem ser
 * declarados aqui para a injeção de dependência
 */
@Module
public class ServiceModule {

    @AppScope
    @Provides
    public Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.SERVER_URL)
                .build();
    }

    @AppScope
    @Provides
    public UserRepository providesUserRepository(Retrofit retrofit) {
        return retrofit.create(UserRepository.class);
    }

    @AppScope
    @Provides
    public UserService providesUserService(Context context, UserRepository repository) {
        return new UserServiceImpl(context, repository);
    }
}
