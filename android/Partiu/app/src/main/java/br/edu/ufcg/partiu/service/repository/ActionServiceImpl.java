package br.edu.ufcg.partiu.service.repository;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.ActionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucas on 01/08/17.
 */

public class ActionServiceImpl implements ActionService {
    private final ActionRepository repository;

    public ActionServiceImpl(ActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void findAll(User user, final ServiceCallback<List<Action>> callback) {
        repository.findAll(user.getId()).enqueue(new Callback<List<Action>>() {
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
