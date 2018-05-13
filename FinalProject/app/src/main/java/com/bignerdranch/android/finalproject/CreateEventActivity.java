package com.bignerdranch.android.finalproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button timePickerBtn;
    TextView eventTitle;

    Button locationPickerBtn;
    Button createButton;
    EditText descriptionText;

    int PLACE_PICKER_REQUEST =1;

    int day, month, year, hour, minutes;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    String monthName, dayFinalStr,monthFinalStr, yearFinalStr, hourFinalStr, minuteFinalStr;
    String username;

    Event event = new Event();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Intent receivingIntent = getIntent();
        username = receivingIntent.getStringExtra("Hostname");
        createButton = (Button) findViewById(R.id.createbtn);

        timePickerBtn = (Button) findViewById(R.id.timepickerBtn);
        eventTitle = (TextView) findViewById(R.id.title);

        locationPickerBtn = (Button) findViewById(R.id.locationpickerBtn);

        descriptionText = (EditText) findViewById(R.id.descText);


        eventTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() !=0){
                    event.setName(eventTitle.getText().toString());
                    //Log.i("->->->->>>",eventTitle.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        descriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() !=0){
                    event.setDescription(descriptionText.getText().toString());
                    //Log.i("->->->->>>",eventTitle.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        timePickerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);

                month = calendar.get(Calendar.MONTH);

                day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this,CreateEventActivity.this,year,month,day);
                datePickerDialog.show();


            }
        });

        locationPickerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setHostname(username);
                Intent intent = new Intent(CreateEventActivity.this, userInvite.class);
                intent.putExtra("newEvent", event);
                startActivity(intent);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                Log.d("Place Name", name.toString());
                Log.d("Place Address",address.toString());
                String location = name.toString()+"\n"+address.toString();
                event.setLocation(location);
                locationPickerBtn.setText(location);
                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        yearFinal = year;
        yearFinalStr = Integer.toString(yearFinal);
        monthFinal = month + 1;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        calendar.set(Calendar.MONTH,month);
        monthName = month_date.format(calendar.getTime());
        dayFinal = dayOfMonth;
        dayFinalStr = Integer.toString(dayFinal);


        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this,CreateEventActivity.this, hour, minutes, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;

        hourFinalStr = Integer.toString(hourFinal);
        minuteFinalStr = Integer.toString(minuteFinal);

        String AmOrPm;
        if(hourFinal >= 12)
            AmOrPm = "PM";
        else
            AmOrPm = "AM";


        String timeDisplay = monthName+" "+dayFinalStr+", "+yearFinalStr+" @ " + hourFinalStr+":"+minuteFinalStr+" "+AmOrPm;
        timePickerBtn.setText(timeDisplay);
        event.setDate(timeDisplay);


    }



}
