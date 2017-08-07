package br.edu.ufcg.partiu.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.profile_search.ProfileSearchActivity;
import butterknife.ButterKnife;

/**
 * Created by lucas on 06/08/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter presenter;
    private Button searchButton;
    private TextView searchTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        searchButton = (Button) view.findViewById(R.id.search_profile_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchProfiles();
            }
        });

        searchTextView = (TextView) view.findViewById(R.id.search_profile_field);

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
        intent.putExtra("SEARCH_STRING", searchTextView.getText().toString());

        startActivity(intent);
    }
}
