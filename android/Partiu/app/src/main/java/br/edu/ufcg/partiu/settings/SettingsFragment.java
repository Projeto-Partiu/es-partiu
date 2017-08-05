package br.edu.ufcg.partiu.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 05/08/17.
 */

public class SettingsFragment extends Fragment implements SettingsContract.View {

    private SettingsContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.logout_button)
    Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity())
                .setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle(R.string.fragment_settings_title);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogoutButtonClick();
            }
        });

        return view;
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void goToLoginScreen() {
        Intent intent = new Intent(getContext(), LoginActivity.class);

        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
