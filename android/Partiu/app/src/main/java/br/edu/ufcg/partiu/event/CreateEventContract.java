package br.edu.ufcg.partiu.event;

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
        void showSuccessCreateEventToast(String message);
    }

}
