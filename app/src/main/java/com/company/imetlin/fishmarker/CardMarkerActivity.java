package com.company.imetlin.fishmarker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CardMarkerActivity extends AppCompatActivity {


    private static final String TAG = "CardMarkerActivity";


    private EditText longitute;
    private EditText latitude;
    private TextView mDisplayDate;
    private EditText depth;
    private EditText amountoffish;
    private EditText note;
    private Button ok, cancel;

    private Context context = CardMarkerActivity.this;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_marker);
        longitute = (EditText) findViewById(R.id.edit_longitude);
        latitude = (EditText) findViewById(R.id.edit_latitude);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        depth = (EditText) findViewById(R.id.edit_dept);
        amountoffish = (EditText) findViewById(R.id.edit_number_of_fish);
        note = (EditText) findViewById(R.id.edit_note);
        ok = (Button) findViewById(R.id.btnOk);
        cancel = (Button) findViewById(R.id.btnCancel);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CardMarkerActivity.this,
                        android.R.style.Theme_Material_Dialog,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (longitute.getText().toString().equals("") ||
                        latitude.getText().toString().equals("") ||
                        mDisplayDate.getText().toString().equals("") ||
                        depth.getText().toString().equals("") ||
                        amountoffish.getText().toString().equals("") ||
                        note.getText().toString().equals("")) {
                    Toast.makeText(context, "Fill in all the fields", Toast.LENGTH_LONG).show();

                } else {















                   //если есть текст, то здесь другой код


                    Toast.makeText(context, "All fields are filled, SELEBRATION", Toast.LENGTH_LONG).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}





