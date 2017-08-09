package br.edu.ufcg.partiu.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.profile.view_holder.ProfileHolder;
import br.edu.ufcg.partiu.profile.view_holder.ProfileViewHolder;
import br.edu.ufcg.partiu.profile_search.ProfileSearchActivity;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 06/08/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter presenter;
    private Button searchButton;
    private TextView searchTextView;

    RecyclerView profileRecyclerView;

    @BindView(R.id.profile_layout)
    LinearLayout profileLayout;

    private ItemAdapter<ProfileHolder> profileAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileRecyclerView = (RecyclerView) view.findViewById(R.id.user_list);

        searchButton = (Button) view.findViewById(R.id.search_profile_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchTextView.getText().toString();


                profileLayout.setVisibility(View.GONE);
                profileRecyclerView.setVisibility(View.VISIBLE);

                presenter.searchProfiles(query);
            }
        });


        searchTextView = (TextView) view.findViewById(R.id.search_profile_field);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        profileAdapter = new ItemAdapter<ProfileHolder>()
                .withViewType(new ProfileViewHolder.Factory(inflater),
                        ProfileHolder.VIEW_TYPE,
                        new ItemAdapter.OnItemClickedListener() {
                            @Override
                            public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder) {

                                User user = profileAdapter.getItemHolderList().get(viewHolder.getAdapterPosition()).getUser();

                                Intent intent = new Intent(getContext(), ProfileSearchActivity.class);
                                intent.putExtras(presenter.provideBundle(user));
                                startActivity(intent);

                            }
                        });


        profileRecyclerView.setAdapter(profileAdapter);
        profileRecyclerView.setLayoutManager(layoutManager);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showUsers(List<User> users) {
        List<ProfileHolder> profileHolderList = new ArrayList<>();

        for (User user : users) {
            profileHolderList.add(ProfileHolder.from(user));
        }

        profileAdapter.setItemHolderList(profileHolderList);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
