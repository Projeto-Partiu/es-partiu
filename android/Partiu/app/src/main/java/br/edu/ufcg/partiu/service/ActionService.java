package br.edu.ufcg.partiu.service;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.model.User;

/**
 * Created by lucas on 01/08/17.
 */

public interface ActionService {

    Void findAll(ServiceCallback<List<Action>> callback);
}
