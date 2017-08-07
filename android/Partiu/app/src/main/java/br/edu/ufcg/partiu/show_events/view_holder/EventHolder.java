package br.edu.ufcg.partiu.show_events.view_holder;

import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.util.ItemAdapter;

/**
 * Created by lucas on 07/08/17.
 */

public class EventHolder implements ItemAdapter.ItemHolder {

    public static final int VIEW_TYPE = 0;

    private final Event event;

    private EventHolder(Event event) {
        this.event = event;
    }

    public static EventHolder from(Event event) {
        return new EventHolder(event);
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public int getItemViewType() {
        return VIEW_TYPE;
    }
}
