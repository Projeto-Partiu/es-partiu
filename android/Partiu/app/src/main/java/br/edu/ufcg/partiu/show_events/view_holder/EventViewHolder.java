package br.edu.ufcg.partiu.show_events.view_holder;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.util.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 07/08/17.
 */

public class EventViewHolder extends ItemAdapter.ItemViewHolder<EventHolder> {

    @BindView(R.id.layout)
    ConstraintLayout viewLayout;

    @BindView(R.id.day_text_view)
    TextView dayText;

    @BindView(R.id.month_text_view)
    TextView monthText;

    @BindView(R.id.name_text_view)
    TextView nameText;

    @BindView(R.id.address_text_view)
    TextView addressText;

    @BindView(R.id.date_text_view)
    TextView dateText;

    public EventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(EventHolder itemHolder) {
        Event event = itemHolder.getEvent();

        String day = (String) DateFormat.format("dd", event.getStartDate());
        dayText.setText(day);

        String month = (String) DateFormat.format("MMM", event.getStartDate());
        monthText.setText(month);

        nameText.setText(event.getName());

        addressText.setText(event.getFriendlyAddress());

        dateText.setText(event.getFriendlyDate());

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventViewHolder.super.notifyItemClicked();
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
            return new EventViewHolder(
                    inflater.inflate(R.layout.view_holder_event, parent, false)
            );
        }
    }
}
