package br.edu.ufcg.partiu.event_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event_detail.view_holder.CommentHolder;
import br.edu.ufcg.partiu.event_detail.view_holder.CommentViewHolder;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;
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

    ItemAdapter<CommentHolder> commentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);

        commentAdapter = new ItemAdapter<CommentHolder>()
                .withViewType(new CommentViewHolder.Factory(inflater), CommentViewHolder.VIEW_TYPE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(layoutManager);

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

        for (Comment comment: comments) {
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
}
