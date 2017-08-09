package br.edu.ufcg.partiu.service;

import com.facebook.Profile;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.User;

/**
 * Created by Lucas on 17/07/2017.
 */

public interface UserService {

    Void createUser(User user, ServiceCallback<User> callback);

    User loggedUser();

    Void logout(ServiceCallback<Void> callback);

    Void findUsers(String query, ServiceCallback<List<User>> callback);
}
