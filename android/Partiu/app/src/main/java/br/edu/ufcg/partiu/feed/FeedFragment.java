package br.edu.ufcg.partiu.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufcg.partiu.R;
import butterknife.ButterKnife;

/**
 * Created by lucas on 26/07/17.
 */

public class FeedFragment extends Fragment implements FeedContract.View {

    private FeedContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        ButterKnife.bind(this, view);

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
