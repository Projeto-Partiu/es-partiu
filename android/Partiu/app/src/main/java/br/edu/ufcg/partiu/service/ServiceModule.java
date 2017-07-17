package br.edu.ufcg.partiu.service;

import android.content.Context;

import br.edu.ufcg.partiu.AppScope;
import dagger.Module;
import dagger.Provides;

/**
 * Módulo dos services, todos os services usados no app devem ser
 * declarados aqui para a injeção de dependência
 */
@Module
public class ServiceModule {

    @AppScope
    @Provides
    public UserService providesUserService(Context context) {
        return new UserServiceImpl(context);
    }
}
