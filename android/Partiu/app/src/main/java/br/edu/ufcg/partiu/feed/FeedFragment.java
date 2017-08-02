package br.edu.ufcg.partiu.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event.CreateEventActivity;
import br.edu.ufcg.partiu.feed.view_holder.ActionFollowUserViewHolder;
import br.edu.ufcg.partiu.feed.view_holder.ActionHolder;
import br.edu.ufcg.partiu.feed.view_holder.ActionMarkInterestViewHolder;
import br.edu.ufcg.partiu.feed.view_holder.ActionMarkPresenceViewHolder;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 26/07/17.
 */

public class FeedFragment extends Fragment implements FeedContract.View {

    private FeedContract.Presenter presenter;

    @BindView(R.id.add_event_fab)
    public FloatingActionButton fab;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.action_list)
    public RecyclerView actionRecyclerView;

    private ItemAdapter<ActionHolder> actionListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity())
                .setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle(R.string.fragment_feed_title);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateEventActivity.class);

                startActivity(intent);
            }
        });

        setupActionRecyclerView(inflater);

        return view;
    }

    private void setupActionRecyclerView(LayoutInflater inflater) {
        actionListAdapter = new ItemAdapter<ActionHolder>()
                .withViewType(new ActionFollowUserViewHolder.Factory(inflater),
                        ActionFollowUserViewHolder.VIEW_TYPE)
                .withViewType(new ActionMarkInterestViewHolder.Factory(inflater),
                        ActionMarkInterestViewHolder.VIEW_TYPE)
                .withViewType(new ActionMarkPresenceViewHolder.Factory(inflater),
                        ActionMarkPresenceViewHolder.VIEW_TYPE);

        actionRecyclerView.setAdapter(actionListAdapter);
    }

    @Override
    public void setPresenter(FeedContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.start();
    }
}
