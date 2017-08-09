package br.edu.ufcg.partiu.show_events;

/**
 * Created by ordan on 06/08/17.
 */

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event_detail.EventDetailActivity;
import br.edu.ufcg.partiu.feed.view_holder.ActionMarkInterestViewHolder;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.FilterType;
import br.edu.ufcg.partiu.show_events.view_holder.EventHolder;
import br.edu.ufcg.partiu.show_events.view_holder.EventViewHolder;
import br.edu.ufcg.partiu.util.ItemAdapter;
import br.edu.ufcg.partiu.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowEventsFragment extends Fragment implements ShowEventsContract.View {

    private ShowEventsContract.Presenter presenter;

    private FusedLocationProviderClient mFusedLocationClient;

    private AppCompatActivity activity;

    private final int REQUEST_COARSE_LOCATION = 12342;

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

        activity = ((AppCompatActivity) getActivity());

        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setTitle(R.string.fragment_events_title);

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

        setHasOptionsMenu(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        setCallbackLocation();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
        loadEvents();
    }

    private void setCallbackLocation() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION) ;
        } else
            mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Util.saveLastLocation(activity, location);
                        }
                    }
                });

    }

    private void loadEvents() {
        presenter.getEvents(filterType);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setCallbackLocation();
                } else {
                    //Permission denied
                }
                return;
            }
        }
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
                loadEvents();
                showToast("Loading upcoming events");
                break;

            case R.id.filter_distance:
                filterType = FilterType.BY_DISTANCE;
                loadEvents();
                showToast("Loading events near you");
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

    @Override
    public void goToEventDetailActivity(String eventId) {
        Intent intent = new Intent(getContext(), EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EVENT_ID_KEY, eventId);

        startActivity(intent);
    }
}
