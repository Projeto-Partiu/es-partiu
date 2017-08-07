package br.edu.ufcg.partiu.show_events;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.event.CreateEventActivity;
import br.edu.ufcg.partiu.model.Event;

/**
 * Created by ordan on 07/08/17.
 */

public class EventAdapter extends BaseAdapter implements ListAdapter{

    private final List<Event> events;
    private final Context context;

    public EventAdapter (List<Event> events, Context context){
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = convertView;

        if (view == null){
            LayoutInflater inflater =  LayoutInflater.from(context);
            view = inflater.inflate(R.layout.view_holder_event, parent, false);
        }

        Event event = (Event) getItem(position);

        // LinearLayout dayMonthLayout = (LinearLayout) view.findViewById(R.id.day_month_layout);

        // dayMonthLayout.setBackgroundColor(Color.BLUE);

        TextView dayText = (TextView) view.findViewById(R.id.day_text_view);
        String day  = (String) DateFormat.format("dd",   event.getStartDate());
        dayText.setText(day);

        TextView monthText = (TextView) view.findViewById(R.id.month_text_view);
        String month = (String) DateFormat.format("MMM",  event.getStartDate());
        monthText.setText(month);

        TextView nameText = (TextView) view.findViewById(R.id.name_text_view);
        nameText.setText(event.getName());

        TextView addressText = (TextView) view.findViewById(R.id.address_text_view);
        addressText.setText(event.getFriendlyAddress());

        TextView dateText = (TextView) view.findViewById(R.id.date_text_view);
        dateText.setText(event.getFriendlyDate());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.edit_event_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateEventActivity.class);

                parent.getContext().startActivity(intent);
            }
        });

        return view;

    }
}
