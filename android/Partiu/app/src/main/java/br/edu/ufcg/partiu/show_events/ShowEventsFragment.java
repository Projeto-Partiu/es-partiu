package br.edu.ufcg.partiu.show_events;

/**
 * Created by ordan on 06/08/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.FilterType;
import br.edu.ufcg.partiu.show_events.view_holder.EventHolder;
import br.edu.ufcg.partiu.show_events.view_holder.EventViewHolder;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowEventsFragment extends Fragment implements ShowEventsContract.View {

    private ShowEventsContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.event_list)
    RecyclerView eventRecyclerView;

    private ItemAdapter<EventHolder> eventAdapter;
    private FilterType filterType = FilterType.BY_DISTANCE;

    @Override
    public void setPresenter(ShowEventsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_events, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity())
                .setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle(R.string.fragment_events_title);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        eventAdapter = new ItemAdapter<EventHolder>()
                .withViewType(new EventViewHolder.Factory(inflater), EventHolder.VIEW_TYPE);

        eventRecyclerView.setAdapter(eventAdapter);
        eventRecyclerView.setLayoutManager(layoutManager);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
        presenter.getEvents(filterType);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.filter_time:
                filterType = FilterType.BY_TIME;
                break;

            case R.id.filter_distance:
                filterType = FilterType.BY_DISTANCE;
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEvents(List<Event> events) {
        List<EventHolder> eventHolderList = new ArrayList<>();

        for (Event event : events) {
            eventHolderList.add(EventHolder.from(event));
        }

        eventAdapter.setItemHolderList(eventHolderList);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
