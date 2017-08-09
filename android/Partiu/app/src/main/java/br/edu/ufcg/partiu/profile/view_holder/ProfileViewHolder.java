package br.edu.ufcg.partiu.profile.view_holder;

import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 07/08/17.
 */

public class ProfileViewHolder extends ItemAdapter.ItemViewHolder<ProfileHolder> {

    @BindView(R.id.profile_list_name)
    TextView nameInList;

    @BindView(R.id.profile_list_pic)
    ImageView picInList;

    @BindView(R.id.root_profile_layout)
    CardView layout;


    public ProfileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ProfileHolder itemHolder) {

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileViewHolder.this.notifyItemClicked();
            }
        });

        final User user = itemHolder.getUser();

        nameInList.setText(user.getName());

        Picasso
                .with(picInList.getContext())
                .load(user.getUrlPhoto())
                .into(picInList);

    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        private final LayoutInflater inflater;

        public Factory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ItemAdapter.ItemViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return new ProfileViewHolder(
                    inflater.inflate(R.layout.view_holder_profile_template, parent, false)
            );
        }
    }
}