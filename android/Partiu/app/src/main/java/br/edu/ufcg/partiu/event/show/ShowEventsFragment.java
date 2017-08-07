package br.edu.ufcg.partiu.event.show;

/**
 * Created by ordan on 06/08/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Event;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowEventsFragment extends Fragment implements ShowEventsContract.View {

    private ShowEventsContract.Presenter presenter;

    @BindView(R.id.list_view)
    ListView eventList;

    @Override
    public void setPresenter(ShowEventsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_events, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void showEvents(List<Event> events) {
        EventAdapter adapter = new EventAdapter(events, getActivity());
        eventList.setAdapter(adapter);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.loadEvents();
    }
}
