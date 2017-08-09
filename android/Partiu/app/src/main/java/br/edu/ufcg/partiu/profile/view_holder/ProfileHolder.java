package br.edu.ufcg.partiu.profile.view_holder;

import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.util.ItemAdapter;

/**
 * Created by filipe on 06/08/17.
 */

public class ProfileHolder implements ItemAdapter.ItemHolder {

    public static final int VIEW_TYPE = 1;

    private final User user;

    private ProfileHolder(User user) {
        this.user = user;
    }

    public static ProfileHolder from(User user) {
        return new ProfileHolder(user);
    }

    public User getUser() {
        return user;
    }

    @Override
    public int getItemViewType() {
        return VIEW_TYPE;
    }
}