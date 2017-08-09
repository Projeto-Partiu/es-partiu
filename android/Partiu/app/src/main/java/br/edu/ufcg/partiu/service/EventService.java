package br.edu.ufcg.partiu.service;

import java.util.List;

import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.base.ServiceCallback;

/**
 * Created by ordan on 29/07/17.
 */

public interface EventService {

    Void createEvent(Event event, ServiceCallback<Event> callback);

    Void getEvents(ServiceCallback<List<Event>> callback);

    Void find(String eventId, ServiceCallback<Event> callback);

    Void disconfirmPresence(Event event, ServiceCallback<Void> callback);

    Void confirmPresence(Event event, ServiceCallback<Void> callback);
}
