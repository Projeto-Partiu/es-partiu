package br.edu.ufcg.partiu.service;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.ActionService;
import br.edu.ufcg.partiu.service.repository.ActionRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucas on 01/08/17.
 */

public class ActionServiceImpl implements ActionService {
    private final ActionRepository repository;
    private final UserService userService;

    public ActionServiceImpl(ActionRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Void findAll(final ServiceCallback<List<Action>> callback) {
        User user = userService.loggedUser();

        repository.findAll(user.getToken()).enqueue(new Callback<List<Action>>() {
            @Override
            public void onResponse(Call<List<Action>> call, Response<List<Action>> response) {
                callback.onResponse(response.body(), response);
            }

            @Override
            public void onFailure(Call<List<Action>> call, Throwable t) {
                callback.onError(t);
            }
        });

        return null;
    }
}
