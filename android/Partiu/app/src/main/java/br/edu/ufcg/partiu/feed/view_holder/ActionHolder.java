package br.edu.ufcg.partiu.feed.view_holder;

import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.util.ItemAdapter;

/**
 * Created by lucas on 31/07/17.
 */

public class ActionHolder implements ItemAdapter.ItemHolder {
    private final Action action;

    private ActionHolder(Action action) {
        this.action = action;
    }

    @Override
    public int getItemViewType() {
        return action.getType().ordinal();
    }

    public Action getAction() {
        return action;
    }

    public static ActionHolder from(Action action) {
        return new ActionHolder(action);
    }
}
