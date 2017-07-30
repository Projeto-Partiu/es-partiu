package br.edu.ufcg.partiu.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import br.edu.ufcg.partiu.BuildConfig;
import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.service.EventService;
import br.edu.ufcg.partiu.service.EventServiceImpl;
import br.edu.ufcg.partiu.service.repository.EventRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateEventActivity extends AppCompatActivity {

    private static int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        EditText localizationEditText = (EditText) findViewById(R.id.localizationEditText);
        localizationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event event = new Event();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EventRepository eventRepository = retrofit.create(EventRepository.class);

                final Call<Event> call = eventRepository.createEvent(event);

                call.enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        System.out.println(response);
                        System.out.println(call);

                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        System.out.println(call);
                        System.out.println(t);
                    }
                });


            }

        });

        Calendar cal = Calendar.getInstance();
        Formatter timeString = new Formatter();
        timeString.format("%02d:%02d", Calendar.HOUR_OF_DAY, cal.get(Calendar.MINUTE));

        EditText startTimeEditText = (EditText) findViewById(R.id.startTimeEditText);
        startTimeEditText.setText(timeString.toString());

        EditText endTimeEditText = (EditText) findViewById(R.id.endTimeEditText);
        endTimeEditText.setText(timeString.toString());


        SimpleDateFormat monthDate = new SimpleDateFormat("MMM");
        Calendar month = Calendar.getInstance();
        month.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        String monthName = monthDate.format(month.getTime());


        EditText endDateEditText = (EditText) findViewById(R.id.endDateEditText);
        endDateEditText.setText( cal.get(Calendar.DAY_OF_MONTH) + " " + monthName + ", " +  cal.get(Calendar.YEAR));

        final EditText startDateEditText = (EditText) findViewById(R.id.startDateEditText);
        startDateEditText.setText( cal.get(Calendar.DAY_OF_MONTH) + " " + monthName + ", " +  cal.get(Calendar.YEAR));

        startTimeEditText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            EditText startTimeEditText = (EditText) findViewById(R.id.startTimeEditText);

                            Formatter formatter = new Formatter();

                            formatter.format("%02d:%02d", selectedHour, selectedMinute);

                            startTimeEditText.setText(formatter.toString());

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecione o horário de início");
                mTimePicker.show();

            }

        });

        endTimeEditText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        EditText endTimeEditText = (EditText) findViewById(R.id.endTimeEditText);

                        Formatter formatter = new Formatter();

                        formatter.format("%02d:%02d", selectedHour, selectedMinute);

                        endTimeEditText.setText(formatter.toString());

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecione o horário de fim");
                mTimePicker.show();

            }

        });


        endDateEditText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;

                mDatePicker = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        EditText endDateEditText = (EditText) findViewById(R.id.endDateEditText);
                        SimpleDateFormat monthDate = new SimpleDateFormat("MMM");

                        mcurrentDate.set(Calendar.MONTH, monthOfYear);

                        String monthName = monthDate.format(mcurrentDate.getTime());

                        endDateEditText.setText( dayOfMonth + " " + monthName + ", " + year);
                    }
                }, year, month, day);//Yes 24 hour time
                mDatePicker.setTitle("Selecione a data de fim");
                mDatePicker.show();

            }

        });

        startDateEditText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;

                mDatePicker = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        EditText startDateEditText = (EditText) findViewById(R.id.startDateEditText );

                        SimpleDateFormat monthDate = new SimpleDateFormat("MMM");
                        mcurrentDate.set(Calendar.MONTH, monthOfYear);

                        String monthName = monthDate.format(mcurrentDate.getTime());
                        startDateEditText.setText( dayOfMonth + " " + monthName + ", " + year);
                    }
                }, year, month, day);//Yes 24 hour time
                mDatePicker.setTitle("Selecione a data de início");
                mDatePicker.show();

            }

        });

    }

    protected void onActivityResult( int requestCode , int resultCode , Intent data ){
        if( requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {

                final Place place = PlacePicker.getPlace(this, data);

                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();

                final double latitude = place.getLatLng().latitude;
                final double longitude = place.getLatLng().longitude;

                EditText localizationEditText = (EditText) findViewById(R.id.localizationEditText);
                localizationEditText.setText(address);

                EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
                nameEditText.setText(name);
            }
        }
    }

}
