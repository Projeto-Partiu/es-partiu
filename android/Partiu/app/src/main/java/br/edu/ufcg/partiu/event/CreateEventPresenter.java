package br.edu.ufcg.partiu.event;

/**
 * Created by ordan on 30/07/17.
 */

import com.google.android.gms.location.places.Place;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.service.EventService;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

public class CreateEventPresenter implements CreateEventContract.Presenter {

    private final CreateEventContract.View view;
    private final EventService eventService;
    private final UserService userService;

    private Event event;

    @Inject
    public CreateEventPresenter(CreateEventContract.View view, EventService eventService, UserService userService) {
        this.view = view;
        this.eventService = eventService;
        this.userService = userService;
        this.event = new Event();
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onCreateEvent() {
        eventService.createEvent(event, new ServiceCallback<Event>() {
            @Override
            public void onResponse(Event object, Response<Event> response) {
                view.showSuccessCreateEventToast("Evento criado com sucesso para " + userService.loggedUser().getName());
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    @Override
    public void onPlacePickerResult(Place place) {
        String address = place.getAddress().toString();

        double latitude = place.getLatLng().latitude;
        double longitude = place.getLatLng().longitude;

        event.setLatitude(latitude);
        event.setLongitude(longitude);
        event.setAddress(address);
    }

    @Override
    public void onEventNameChange(CharSequence name) {
        event.setName(name.toString());
    }

    @Override
    public void onEventDescriptionChange(CharSequence description) {
        event.setDescription(description.toString());
    }

    @Override
    public void onStartDateClick() {

    }

    @Override
    public void onEndDateClick() {

    }
}
