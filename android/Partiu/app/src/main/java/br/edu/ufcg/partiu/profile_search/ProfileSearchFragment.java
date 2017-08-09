package br.edu.ufcg.partiu.profile_search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.edu.ufcg.partiu.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 07/08/17.
 */

public class ProfileSearchFragment extends Fragment implements ProfileSearchContract.View {

    private ProfileSearchContract.Presenter presenter;

    @BindView(R.id.profile_searched_name)
    TextView nameView;

    @BindView(R.id.profile_searched_pic)
    ImageView picView;

    @BindView(R.id.follow_switch)
    Switch followSwitch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_search, container, false);

        ButterKnife.bind(this, view);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        nameView.setText(bundle.getString("USER_NAME"));
        followSwitch.setChecked(bundle.getBoolean("USER_FOLLOWING"));

        Picasso
                .with(getContext())
                .load(bundle.getString("USER_PHOTO"))
                .into(picView);



        return view;
    }

    @Override
    public void setPresenter(ProfileSearchContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
