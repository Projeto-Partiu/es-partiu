package br.edu.ufcg.partiu.profile_search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufcg.partiu.R;
import butterknife.ButterKnife;

/**
 * Created by lucas on 07/08/17.
 */

public class ProfileSearchFragment extends Fragment implements ProfileSearchContract.View {

    private ProfileSearchContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_search, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setPresenter(ProfileSearchContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
