package com.company.imetlin.fishmarker;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.database.DatabaseLoad;
import com.company.imetlin.fishmarker.database.SQLiteHelper;
import com.company.imetlin.fishmarker.pojo.ModelClass;

import java.util.ArrayList;
import java.util.Calendar;

import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID_PRIMARY;

public class CardMarkerActivity extends AppCompatActivity {


    private static final String TAG = "CardMarkerActivity";


    private EditText etlongitute;
    private EditText etlatitude;
    private TextView etmDisplayDate;
    private EditText etdepth;
    private EditText etamountoffish;
    private EditText etnote;
    private Button ok, cancel;

    private Context context = CardMarkerActivity.this;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private SQLiteHelper dbHelper;

    private ArrayList<ModelClass> id_markers;
    public DatabaseLoad databaseLoad;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_marker);
        etlongitute = (EditText) findViewById(R.id.edit_longitude);
        etlatitude = (EditText) findViewById(R.id.edit_latitude);
        etmDisplayDate = (TextView) findViewById(R.id.tvDate);
        etdepth = (EditText) findViewById(R.id.edit_dept);
        etamountoffish = (EditText) findViewById(R.id.edit_number_of_fish);
        etnote = (EditText) findViewById(R.id.edit_note);
        ok = (Button) findViewById(R.id.btnOk);
        cancel = (Button) findViewById(R.id.btnCancel);
        etlongitute.setEnabled(false);
        etlatitude.setEnabled(false);



        this.databaseLoad = DatabaseLoad.getInstance(context);









        dbHelper = new SQLiteHelper(this);


        etmDisplayDate.setOnClickListener(new View.OnClickListener() {
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
                etmDisplayDate.setText(date);
            }
        };

        String coordinate = getIntent().getStringExtra("coord");
        String newcoord = coordinate.replace("lat/lng: (","").replace(")","");
        String[] parts = newcoord.split(",");
        String lat = parts[0];
        String lon = parts[1];

        etlongitute.setText(lon);
        etlatitude.setText(lat);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                if (etlongitute.getText().toString().equals("") ||
                        etlatitude.getText().toString().equals("") ||
                        etmDisplayDate.getText().toString().equals("") ||
                        etdepth.getText().toString().equals("") ||
                        etamountoffish.getText().toString().equals("") ||
                        etnote.getText().toString().equals("")) {
                    Toast.makeText(context, "Fill in all the fields", Toast.LENGTH_LONG).show();

                } else {
                    String longitude = etlongitute.getText().toString();
                    String latitude = etlatitude.getText().toString();
                    String displayDate = etmDisplayDate.getText().toString();
                    String depth = etdepth.getText().toString();
                    String amountoffish = etamountoffish.getText().toString();
                    String note = etnote.getText().toString();


                    contentValues.put(SQLiteHelper.DB_COL_LONGITUDE, longitude);
                    contentValues.put(SQLiteHelper.DB_COL_LATITUDE, latitude);
                    contentValues.put(SQLiteHelper.DB_COL_DATE, displayDate);
                    contentValues.put(SQLiteHelper.DB_COL_DEPTH, depth);
                    contentValues.put(SQLiteHelper.DB_COL_AMOUNT, amountoffish);
                    contentValues.put(SQLiteHelper.DB_COL_NOTE, note);

                    database.insert(SQLiteHelper.DB_TABLE_NAME, null, contentValues);


                    Toast.makeText(context, "ADD to BASE, SELEBRATION", Toast.LENGTH_LONG).show();



                    ModelClass modelClass = new ModelClass( databaseLoad.last_id + 1  ,
                            Double.valueOf(longitude),
                            Double.valueOf(latitude),
                            displayDate,
                            Double.valueOf(depth),
                            Integer.parseInt(amountoffish),
                            note);


                    databaseLoad.alldatamarkers.add(modelClass);


                    Bundle bundle = new Bundle();
                    bundle.putString("result",displayDate);
                    bundle.putString("id", String.valueOf(modelClass.getId()));


                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    //returnIntent.putExtra("id",modelClass.getId());

                    setResult(MapActivity.RESULT_OK,intent);
                    finish();



                }
                dbHelper.close();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(MapActivity.RESULT_CANCELED, returnIntent);
                finish();

            }
        });


    }
    public void Update(){



    }
}





