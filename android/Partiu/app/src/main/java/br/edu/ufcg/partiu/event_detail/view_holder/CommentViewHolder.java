package br.edu.ufcg.partiu.event_detail.view_holder;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.util.ItemAdapter;
import br.edu.ufcg.partiu.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 08/08/17.
 */

public class CommentViewHolder extends ItemAdapter.ItemViewHolder<CommentHolder> {

    public static final int VIEW_TYPE = 1;

    @BindView(R.id.user_image)
    ImageView userImage;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.comment)
    TextView commentText;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(CommentHolder itemHolder) {
        Comment comment = itemHolder.getComment();

        userName.setText(comment.getUser().getName());
        commentText.setText(comment.getContent());

        Picasso
                .with(itemView.getContext())
                .load(comment.getUser().getUrlPhoto())
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
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        private final LayoutInflater inflater;

        public Factory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ItemAdapter.ItemViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return new CommentViewHolder(
                    inflater.inflate(R.layout.view_holder_comment, parent, false)
            );
        }
    }
}
