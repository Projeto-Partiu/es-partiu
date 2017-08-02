package br.edu.ufcg.partiu.feed.view_holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.ActionType;
import br.edu.ufcg.partiu.util.ItemAdapter;

/**
 * Created by lucas on 31/07/17.
 */

public class ActionMarkPresenceViewHolder extends ItemAdapter.ItemViewHolder<ActionHolder> {

    public static final int VIEW_TYPE = ActionType.MARK_PRESENCE.ordinal();

    public ActionMarkPresenceViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(ActionHolder itemHolder) {

    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        private final LayoutInflater inflater;

        public Factory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ItemAdapter.ItemViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return new ActionMarkPresenceViewHolder(
                    inflater.inflate(R.layout.view_holder_action_mark_presence, parent, false)
            );
        }
    }
}
