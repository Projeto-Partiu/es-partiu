package br.edu.ufcg.partiu.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event.CreateEventActivity;
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

        return view;
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
