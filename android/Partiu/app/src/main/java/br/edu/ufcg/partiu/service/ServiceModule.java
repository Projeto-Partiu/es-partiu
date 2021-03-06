package br.edu.ufcg.partiu.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.edu.ufcg.partiu.AppScope;
import br.edu.ufcg.partiu.BuildConfig;
import br.edu.ufcg.partiu.service.repository.ActionRepository;
import br.edu.ufcg.partiu.service.repository.CommentRepository;
import br.edu.ufcg.partiu.service.repository.EventRepository;
import br.edu.ufcg.partiu.service.repository.UserRepository;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
    }

    @AppScope
    @Provides
    public Gson providesGson() {
        return new GsonBuilder()
                .setDateFormat(DateFormat.FULL)
                .create();
    }

    @AppScope
    @Provides
    public Retrofit providesRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
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

    @AppScope
    @Provides
    public EventRepository providesEventRepository(Retrofit retrofit) {
        return retrofit.create(EventRepository.class);
    }

    @AppScope
    @Provides
    public EventService providesEventService(Context context, EventRepository eventRepository, UserService userService) {
        return new EventServiceImpl(context, eventRepository, userService);
    }

    @AppScope
    @Provides
    public ActionRepository providesActionRepository(Retrofit retrofit) {
        return retrofit.create(ActionRepository.class);
    }

    @AppScope
    @Provides
    public ActionService providesActionService(ActionRepository repository, UserService userService) {
        return new ActionServiceImpl(repository, userService);
    }

    @AppScope
    @Provides
    public CommentRepository providesCommentRepository(Retrofit retrofit) {
        return retrofit.create(CommentRepository.class);
    }

    @AppScope
    @Provides
    public CommentService providesCommentService(Context context, CommentRepository repository) {
        return new CommentServiceImpl(context, repository);
    }
}
