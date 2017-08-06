package br.edu.ufcg.partiu.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.edu.ufcg.partiu.R;
import butterknife.ButterKnife;

/**
 * Created by lucas on 06/08/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button searchButton = (Button) view.findViewById(R.id.search_profile_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchProfiles();
            }
        });


        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void launchSearchActivity() {
        Intent intent = new Intent(getContext(), ProfileSearchActivity.class);
        startActivity(intent);
    }
}
