package br.edu.ufcg.partiu.feed.view_holder;

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
import br.edu.ufcg.partiu.util.ItemAdapter;
import br.edu.ufcg.partiu.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 31/07/17.
 */

public class ActionMarkPresenceViewHolder extends ItemAdapter.ItemViewHolder<ActionHolder> {

    public static final int VIEW_TYPE = ActionType.MARK_PRESENCE.ordinal();

    @BindView(R.id.user_image)
    ImageView userImage;

    @BindView(R.id.action_text)
    TextView actionTextView;

    public ActionMarkPresenceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ActionHolder itemHolder) {
        Action action = itemHolder.getAction();

        String userName = action.getUser().getName();
        String eventName = (String) ((Map<String, Object>) action.getArguments().get("event")).get("name");

        String actionText = itemView.getContext().getString(R.string.action_marked_presence);

        SpannableStringBuilder sb = new SpannableStringBuilder()
                .append(userName)
                .append(" ")
                .append(actionText)
                .append(" ")
                .append(eventName);

        sb.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                userName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        sb.setSpan(
                new StyleSpan(Typeface.BOLD),
                userName.length() + 1 + actionText.length() + 1,
                sb.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        actionTextView.setText(sb);

        Picasso
                .with(itemView.getContext())
                .load(action.getUser().getUrlPhoto())
                .resize(64, 64)
                .placeholder(R.drawable.ic_person_placeholder)
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
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        private final LayoutInflater inflater;

        public Factory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ItemAdapter.ItemViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return new ActionMarkPresenceViewHolder(
                    inflater.inflate(R.layout.view_holder_action_template, parent, false)
            );
        }
    }
}
