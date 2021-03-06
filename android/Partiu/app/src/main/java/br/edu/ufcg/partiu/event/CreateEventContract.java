package br.edu.ufcg.partiu.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.google.android.gms.location.places.Place;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;

/**
 * Created by ordan on 30/07/17.
 */

public interface CreateEventContract {

    interface Presenter extends BasePresenter {
        void onCreateEvent();

        void onPlacePickerResult(Place place);

        void onEventNameChange(CharSequence name);

        void onEventDescriptionChange(CharSequence description);

        void onStartDateClick();

        void onEndDateClick();
    }

    interface View extends BaseView<Presenter> {
        void showToast(String message);

        void showDatePicker(DatePickerDialog.OnDateSetListener callback, long minDate);

        void showTimePicker(TimePickerDialog.OnTimeSetListener callback);

        void setStartDateText(String text);

        void setEndDateText(String text);

        void setLocationText(String location);

        void goBack();
    }

}
