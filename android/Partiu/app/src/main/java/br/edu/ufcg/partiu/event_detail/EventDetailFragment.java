package br.edu.ufcg.partiu.event_detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event_detail.view_holder.CommentHolder;
import br.edu.ufcg.partiu.event_detail.view_holder.CommentViewHolder;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 07/08/17.
 */

public class EventDetailFragment extends Fragment implements EventDetailContract.View {

    private EventDetailContract.Presenter presenter;

    @BindView(R.id.comments_list)
    RecyclerView commentRecyclerView;

    @BindView(R.id.event_name)
    TextView nameText;

    @BindView(R.id.event_description)
    TextView descriptionText;

    @BindView(R.id.event_start_date)
    TextView startDateText;

    @BindView(R.id.event_end_date)
    TextView endDateText;

    @BindView(R.id.event_place)
    TextView placeText;

    @BindView(R.id.presence_text)
    TextView presence_text;

    @BindView(R.id.progress_bar)
    ProgressBar loader;

    @BindView(R.id.detail_container_layout)
    ScrollView eventDetailLayout;

    @BindView(R.id.empty_comments_message)
    TextView emptyCommentsMessage;

    @BindView(R.id.comment_edit_text)
    EditText commentEditText;

    @BindView(R.id.add_comment_button)
    ImageButton addCommentButton;

    ItemAdapter<CommentHolder> commentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);

        commentAdapter = new ItemAdapter<CommentHolder>()
                .withViewType(
                        new CommentViewHolder.Factory(inflater),
                        CommentViewHolder.VIEW_TYPE,
                        new ItemAdapter.OnItemClickedListener() {
                            @Override
                            public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder) {
                                Comment comment = commentAdapter
                                        .getItemHolderList()
                                        .get(viewHolder.getAdapterPosition())
                                        .getComment();

                                presenter.onCommentClicked(comment);
                            }
                        }
                );

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(layoutManager);

        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onCommentChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onComment();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.fetchEvent(getArguments().getString(EventDetailActivity.EVENT_ID_KEY));
    }

    @Override
    public void setPresenter(EventDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showDetailLayout() {
        eventDetailLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDetailLayout() {
        eventDetailLayout.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public void setComments(List<Comment> comments) {
        List<CommentHolder> commentHolderList = new ArrayList<>();

        for (Comment comment : comments) {
            commentHolderList.add(CommentHolder.from(comment));
        }

        commentAdapter.setItemHolderList(commentHolderList);
    }

    @Override
    public void setPresence(boolean presenca) {
        if(presenca) {
            presence_text.setText("Eu vou. Partiu!");
        } else {
            presence_text.setText("Eu não vou.");
        }
    }

    @Override
    public void showEmptyCommentsMessage() {
        emptyCommentsMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyCommentsMessage() {
        emptyCommentsMessage.setVisibility(View.GONE);
    }

    @Override
    public void setEventName(String eventName) {
        nameText.setText(eventName);
    }

    @Override
    public void setEventDescription(String eventDescription) {
        descriptionText.setText(eventDescription);
    }

    @Override
    public void setStartDate(String startDate) {
        startDateText.setText(startDate);
    }

    @Override
    public void setPlace(String address) {
        placeText.setText(address);
    }

    @Override
    public void showEndDate() {
        endDateText.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEndDate(String endDate) {
        endDateText.setText(endDate);
    }

    @Override
    public void showDeleteCommentPopup(final Comment comment) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Apagar comentário")
                .setMessage("Você deseja apagar esse comentário?")
                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onDeleteComment(comment);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nao faz nada
                    }
                })
                .create();

        dialog.show();
    }

    @Override
    public void removeCommentFromList(Comment comment) {
        List<CommentHolder> commentHolderList = commentAdapter.getItemHolderList();

        int commentIndex = -1;

        for (int i = 0; i < commentHolderList.size(); i++) {
            if (commentHolderList.get(i).getComment().equals(comment)) {
                commentIndex = i;
                break;
            }
        }

        commentAdapter.removeItem(commentIndex);
    }

    @Override
    public void addCommentToList(Comment comment) {
        commentAdapter.addItem(CommentHolder.from(comment));
    }

    @Override
    public void setCommentInputText(String text) {
        commentEditText.setText(text);
    }
}
