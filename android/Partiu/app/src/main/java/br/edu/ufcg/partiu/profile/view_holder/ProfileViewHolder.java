package br.edu.ufcg.partiu.profile.view_holder;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Event;
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



    public ProfileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ProfileHolder itemHolder) {
        User user = itemHolder.getUser();

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