package br.edu.ufcg.partiu.event;

/**
 * Created by ordan on 30/07/17.
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.service.EventService;
import br.edu.ufcg.partiu.service.UserService;
import br.edu.ufcg.partiu.util.Util;
import retrofit2.Response;

public class CreateEventPresenter implements CreateEventContract.Presenter {

    private final CreateEventContract.View view;
    private final EventService eventService;
    private final UserService userService;

    private final DateFormat dateFormat;

    private Event event;

    @Inject
    public CreateEventPresenter(CreateEventContract.View view, EventService eventService, UserService userService) {
        this.view = view;
        this.eventService = eventService;
        this.userService = userService;
        this.event = new Event();
        this.dateFormat = DateFormat.getDateInstance();
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
        try {
            eventService.createEvent(event, new ServiceCallback<Event>() {
                @Override
                public void onResponse(Event object, Response<Event> response) {
                    view.showToast("Evento criado com sucesso");
                    view.goBack();
                }

                @Override
                public void onError(Throwable error) {
                    view.showToast("Ocorreu um erro ao criar o evento");
                }
            });
        } catch (Exception e) {
            view.showToast(e.getMessage());
        }
    }

    @Override
    public void onPlacePickerResult(Place place) {
        String address = place.getAddress().toString();

        double latitude = place.getLatLng().latitude;
        double longitude = place.getLatLng().longitude;

        event.setLatitude(latitude);
        event.setLongitude(longitude);
        event.setAddress(address);

        view.setLocationText(address);
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
        final Calendar startDate = Calendar.getInstance();

        startDate.setTimeInMillis(0);

        view.showDatePicker(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePickerView, int year, int month, int dayOfMonth) {
                startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDate.set(Calendar.MONTH, month);
                startDate.set(Calendar.YEAR, year);

                startDate.set(Calendar.HOUR_OF_DAY, 0);
                startDate.set(Calendar.MINUTE, 0);

                if (event.getEndDate() != null && startDate.compareTo(Util.toCalendar(event.getEndDate())) > 0) {
                    event.setEndDate(null);
                    view.setEndDateText(null);
                }

                // save state
                event.setStartDate(startDate.getTime());

                view.setStartDateText(dateFormat.format(startDate.getTime()));

                view.showTimePicker(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePickerView, int hourOfDay, int minute) {
                        startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startDate.set(Calendar.MINUTE, minute);

                        event.setStartDate(startDate.getTime());

                        view.setStartDateText(
                                dateFormat.format(startDate.getTime()) + " " +
                                        String.format(Locale.getDefault(), "%02d", hourOfDay) + "h" +
                                        String.format(Locale.getDefault(), "%02d", minute)
                        );
                    }
                });
            }
        }, System.currentTimeMillis() - 1000);
    }

    @Override
    public void onEndDateClick() {
        if (event.getStartDate() == null) {
            view.showToast("Selecione uma data de início, por favor");
            return;
        }

        final Calendar endDate = Calendar.getInstance();

        endDate.setTimeInMillis(0);

        view.showDatePicker(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePickerView, int year, int month, int dayOfMonth) {
                endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDate.set(Calendar.MONTH, month);
                endDate.set(Calendar.YEAR, year);

                endDate.set(Calendar.HOUR_OF_DAY, 0);
                endDate.set(Calendar.MINUTE, 0);

                // save state
                event.setEndDate(endDate.getTime());

                view.setEndDateText(dateFormat.format(endDate.getTime()));

                view.showTimePicker(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePickerView, int hourOfDay, int minute) {
                        endDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        endDate.set(Calendar.MINUTE, minute);

                        if (endDate.compareTo(Util.toCalendar(event.getStartDate())) < 0) {
                            event.setEndDate(null);
                            view.setEndDateText(null);
                            view.showToast("Data/hora inválida");
                        } else {
                            event.setEndDate(endDate.getTime());

                            view.setEndDateText(
                                    dateFormat.format(endDate.getTime()) + " " +
                                            String.format(Locale.getDefault(), "%02d", hourOfDay) + "h" +
                                            String.format(Locale.getDefault(), "%02d", minute)
                            );
                        }
                    }
                });
            }
        }, event.getStartDate().getTime());
    }
}
