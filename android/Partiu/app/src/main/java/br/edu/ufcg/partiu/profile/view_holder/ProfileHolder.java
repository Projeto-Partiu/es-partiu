package br.edu.ufcg.partiu.profile.view_holder;

import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.util.ItemAdapter;

/**
 * Created by filipe on 06/08/17.
 */

public class ProfileHolder implements ItemAdapter.ItemHolder {
    private final User user;
    public static final int ITEM_TYPE = 1;
    private ProfileHolder(User user) {
        this.user = user;
    }

    @Override
    public int getItemViewType() {
        return ITEM_TYPE;
    }

    public User getUser() {
        return user;
    }

    public static ProfileHolder from(User user) {
        return new ProfileHolder(user);
    }
}
