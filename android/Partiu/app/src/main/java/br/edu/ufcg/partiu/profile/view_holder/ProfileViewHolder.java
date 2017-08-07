package br.edu.ufcg.partiu.profile.view_holder;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.model.ActionType;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.util.ItemAdapter;
import br.edu.ufcg.partiu.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by filipe on 06/08/17.
 */

public class ProfileViewHolder extends ItemAdapter.ItemViewHolder<ProfileHolder> {

    @BindView(R.id.profile_list_pic)
    ImageView profilePic;

    @BindView(R.id.profile_list_name)
    TextView profileName;

    public ProfileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ProfileHolder itemHolder) {
        User user = itemHolder.getUser();
        String userName = user.getName();

        Picasso
                .with(itemView.getContext())
                .load(user.getUrlPhoto())
                .placeholder(R.drawable.ic_person_placeholder)
                .resize(64, 64)
                .into(profilePic, new Callback() {
                    @Override
                    public void onSuccess() {
                        profilePic.setImageDrawable(
                                Util.toRoundBitmap(
                                        itemView.getContext(),
                                        ((BitmapDrawable) profilePic.getDrawable()).getBitmap()
                                )
                        );
                    }

                    @Override
                    public void onError() {

                    }
                });

        profileName.setText(userName);
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        private final LayoutInflater inflater;

        public Factory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ItemAdapter.ItemViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return new ProfileViewHolder(
                    inflater.inflate(R.layout.view_holder_action_template, parent, false)
            );
        }
    }
}
