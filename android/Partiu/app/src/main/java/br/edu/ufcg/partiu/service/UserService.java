package br.edu.ufcg.partiu.service;

import com.facebook.Profile;

import br.edu.ufcg.partiu.base.ServiceCallback;

/**
 * Created by Lucas on 17/07/2017.
 */

public interface UserService {

    void createUser(Profile profile, ServiceCallback callback);
}
