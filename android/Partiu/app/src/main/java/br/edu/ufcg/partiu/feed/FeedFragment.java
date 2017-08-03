package br.edu.ufcg.partiu.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event.CreateEventActivity;
import br.edu.ufcg.partiu.feed.view_holder.ActionFollowUserViewHolder;
import br.edu.ufcg.partiu.feed.view_holder.ActionHolder;
import br.edu.ufcg.partiu.feed.view_holder.ActionMarkInterestViewHolder;
import br.edu.ufcg.partiu.feed.view_holder.ActionMarkPresenceViewHolder;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 26/07/17.
 */

public class FeedFragment extends Fragment implements FeedContract.View {

    private FeedContract.Presenter presenter;

    @BindView(R.id.add_event_fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.action_list)
    RecyclerView actionRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.empty_message)
    TextView emptyMessageTextView;

    @BindView(R.id.action_list_layout)
    SwipeRefreshLayout actionListLayout;

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

        actionListLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefreshList();
            }
        });

        setupActionRecyclerView(inflater);

        return view;
    }

    private void setupActionRecyclerView(LayoutInflater inflater) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        actionListAdapter = new ItemAdapter<ActionHolder>()
                .withViewType(new ActionFollowUserViewHolder.Factory(inflater),
                        ActionFollowUserViewHolder.VIEW_TYPE)
                .withViewType(new ActionMarkInterestViewHolder.Factory(inflater),
                        ActionMarkInterestViewHolder.VIEW_TYPE)
                .withViewType(new ActionMarkPresenceViewHolder.Factory(inflater),
                        ActionMarkPresenceViewHolder.VIEW_TYPE);

        actionRecyclerView.setAdapter(actionListAdapter);
        actionRecyclerView.setLayoutManager(layoutManager);
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

    @Override
    public void setActionList(List<Action> actionList) {
        List<ActionHolder> actionHolderList = new ArrayList<>();

        for (Action action: actionList) {
            actionHolderList.add(ActionHolder.from(action));
        }

        actionListAdapter.setItemHolderList(actionHolderList);
    }

    @Override
    public void showLoadingSpinner() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingSpinner() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyMessage() {
        emptyMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyMessage() {
        emptyMessageTextView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        actionListLayout.setRefreshing(refreshing);
    }
}
