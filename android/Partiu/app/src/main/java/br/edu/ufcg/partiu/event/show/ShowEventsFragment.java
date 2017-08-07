package br.edu.ufcg.partiu.event.show;

/**
 * Created by ordan on 06/08/17.
 */

import android.support.v4.app.Fragment;

public class ShowEventsFragment extends Fragment implements ShowEventsContract.View {

    private ShowEventsContract.Presenter presenter;

    @Override
    public void setPresenter(ShowEventsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
