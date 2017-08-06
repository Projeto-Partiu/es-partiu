package br.edu.ufcg.partiu.feed.view_holder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.model.ActionType;
import br.edu.ufcg.partiu.util.ItemAdapter;
import br.edu.ufcg.partiu.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 31/07/17.
 */

public class ActionFollowUserViewHolder extends ItemAdapter.ItemViewHolder<ActionHolder> {

    public static final int VIEW_TYPE = ActionType.FOLLOW_USER.ordinal();

    @BindView(R.id.user_name)
    TextView userNameText;

    @BindView(R.id.second_user_name)
    TextView secondUserNameText;

    @BindView(R.id.user_image)
    ImageView userImage;

    public ActionFollowUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ActionHolder itemHolder) {
        Action action = itemHolder.getAction();

        userNameText.setText(action.getUser().getName());

        Picasso
                .with(itemView.getContext())
                .load(action.getUser().getUrlPhoto())
                .placeholder(R.drawable.ic_person_placeholder)
                .resize(64, 64)
                .into(userImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        userImage.setImageDrawable(
                                Util.toRoundBitmap(
                                        itemView.getContext(),
                                        ((BitmapDrawable) userImage.getDrawable()).getBitmap()
                                )
                        );
                    }

                    @Override
                    public void onError() {

                    }
                });

        secondUserNameText.setText((String) ((Map<String, Object>) action.getArguments().get("user")).get("name"));
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        private final LayoutInflater inflater;

        public Factory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ItemAdapter.ItemViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return new ActionFollowUserViewHolder(
                    inflater.inflate(R.layout.view_holder_action_follow_user, parent, false)
            );
        }
    }
}
