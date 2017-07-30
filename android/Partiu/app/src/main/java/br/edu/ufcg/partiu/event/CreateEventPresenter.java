package br.edu.ufcg.partiu.event;

/**
 * Created by ordan on 30/07/17.
 */

import javax.inject.Inject;

import br.edu.ufcg.partiu.login.LoginContract;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.UserService;


public class CreateEventPresenter implements CreateEventContract.Presenter {


    private final UserService userService;

    @Inject
    public CreateEventPresenter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void start() {

    }

    @Override
    public void onCreateEvent() {

    }

    public User getUser (){
        return userService.loggedUser();
    }
}
