package br.edu.ufcg.partiu.event_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Event;
import butterknife.ButterKnife;

/**
 * Created by lucas on 07/08/17.
 */

public class EventDetailFragment extends Fragment implements EventDetailContract.View {

    private EventDetailContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.fetchEvent(getArguments().getString(EventDetailActivity.EVENT_ID_KEY));
    }

    @Override
    public void setPresenter(EventDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void setEventFields(Event event) {

    }
}
