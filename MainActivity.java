package com.example.individualproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spVehicles;
    Button btnTime;
    int hour,minute;
    double rate=0 , totalCharge=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTime= findViewById(R.id.btnTime);

        //initialize spinner
        spVehicles = findViewById(R.id.spVehicles);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.vehicles,R.layout.spinner_item);
        spVehicles.setAdapter(adapter);
    }
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour=selectedHour;
                minute=selectedMinute;
                btnTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                LocalTime time = LocalTime.parse(btnTime.getText().toString());
                LocalTime startTime = LocalTime.parse("00:00");
                LocalTime endTime = LocalTime.parse("05:59");

                if(time.isAfter(startTime) && time.isAfter(endTime));
                rate = 1.3;
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }

    public void calculateDelivery(View view)
    {

        //read delivery distance,
        EditText etDelDistance = findViewById(R.id.etDelDistance);
        int DelDistance = Integer.parseInt(etDelDistance.getText().toString());

        EditText etaddStop = findViewById(R.id.etaddStop);
        int addStop= Integer.parseInt(etaddStop.getText().toString());

        int selectedIDx = spVehicles.getSelectedItemPosition();
        switch (selectedIDx)
        {
            case 0:
                if(DelDistance>10)
                {
                    totalCharge=((DelDistance-10)*0.8)+14;
                }
                else if ((DelDistance>5) &&(DelDistance<=10))
                {
                    totalCharge=((DelDistance-5)*1)+5;
                }
                else
                    totalCharge=5;

                totalCharge=totalCharge + (addStop*1);
                break;
            case 1:
                if(DelDistance>15)
                {
                    totalCharge=((DelDistance-15)*0.8)+17;
                }
                else if ((DelDistance>5) &&(DelDistance<=15))
                {
                    totalCharge=((DelDistance-5)*1)+8;
                }
                else
                    totalCharge=8;
                totalCharge=totalCharge + (addStop*2);
                break;
            case 2:
                if(DelDistance>100)
                {
                    totalCharge=((DelDistance-100)*1.65)+217.8;
                }
                else if ((DelDistance>10) &&(DelDistance<=100))
                {
                    totalCharge=((DelDistance-10)*2.2)+22;
                }
                else
                    totalCharge=22;
                totalCharge=totalCharge + (addStop*5);
                break;
        }

        TextView tvResult = findViewById(R.id.tvResult);

        tvResult.setText("Total charge for your delivery : RM" +totalCharge);

    }
    }




