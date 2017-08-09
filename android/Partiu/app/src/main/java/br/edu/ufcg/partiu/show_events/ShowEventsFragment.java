package br.edu.ufcg.partiu.show_events;

/**
 * Created by ordan on 06/08/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event_detail.EventDetailActivity;
import br.edu.ufcg.partiu.feed.view_holder.ActionMarkInterestViewHolder;
import br.edu.ufcg.partiu.model.Event;
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
                .withViewType(
                        new EventViewHolder.Factory(inflater),
                        EventHolder.VIEW_TYPE,
                        new ItemAdapter.OnItemClickedListener() {
                            @Override
                            public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder) {
                                EventHolder eventHolder = eventAdapter.getItemHolderList().get(viewHolder.getAdapterPosition());

                                presenter.onEventClicked(eventHolder.getEvent());
                            }
                        }
                );

        eventRecyclerView.setAdapter(eventAdapter);
        eventRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
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

    @Override
    public void goToEventDetailActivity(String eventId) {
        Intent intent = new Intent(getContext(), EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EVENT_ID_KEY, eventId);

        startActivity(intent);
    }
}
