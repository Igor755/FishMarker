package com.company.imetlin.fishmarker;

import android.app.AlertDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
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

    private EditText etlatitude;
    private EditText etlongitute;
    private TextView etmDisplayDate;
    private EditText etdepth;
    private EditText etamountoffish;
    private EditText etnote;
    private Button ok, cancel;

    private Context context = CardMarkerActivity.this;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private SQLiteHelper dbHelper;

   //public String lon,lat,date,depth,amount,note;

    public Boolean isnull;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_marker);

        etlatitude = (EditText) findViewById(R.id.edit_latitude);
        etlongitute = (EditText) findViewById(R.id.edit_longitude);
        etmDisplayDate = (TextView) findViewById(R.id.tvDate);
        etdepth = (EditText) findViewById(R.id.edit_dept);
        etamountoffish = (EditText) findViewById(R.id.edit_number_of_fish);
        etnote = (EditText) findViewById(R.id.edit_note);
        ok = (Button) findViewById(R.id.btnOk);
        cancel = (Button) findViewById(R.id.btnCancel);
        etlongitute.setEnabled(false);
        etlatitude.setEnabled(false);
        ok.setTextColor(Color.parseColor("#FFFFFF"));
        cancel.setTextColor(Color.parseColor("#FFFFFF"));



        String result1;
        dbHelper = new SQLiteHelper(this);


        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(MapActivity.RESULT_CANCELED, returnIntent);
                finish();

            }
        });

        etmDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(
                        CardMarkerActivity.this,
                        R.style.AppCompatDialogStyle,  /*AlertDialog.THEME_HOLO_DARK*/
                        mDateSetListener,
                        year, month, day);
               // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.green(2)));
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


        if ((result1 = getIntent().getStringExtra("1")) != null) {


            UpdateMarker();


        } else {

            AddMarker();


        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_cardmarker,menu);
        return true;
    }

/*
    FUNCTION UPDATE DATA MARKER IN BASE
*/
    public void UpdateMarker() {


        final String result1 = getIntent().getStringExtra("1");
        final String result2 = getIntent().getStringExtra("2");
        final String result3 = getIntent().getStringExtra("3");
        final String result4 = getIntent().getStringExtra("4");
        final String result5 = getIntent().getStringExtra("5");
        final String result6 = getIntent().getStringExtra("6");
        final String result7 = getIntent().getStringExtra("7");



        etlatitude.setText(result1);
        etlongitute.setText(result2);
        etmDisplayDate.setText(result3);
        etdepth.setText(result4);
        etamountoffish.setText(result5);
        etnote.setText(result6);

        ok.setText("EDIT");


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                if (isEmpty() == true){
                    Toast.makeText(context, "Fill in all the fields", Toast.LENGTH_LONG).show();

                } else {

                    String latitude = etlatitude.getText().toString();
                    String longitude = etlongitute.getText().toString();
                    String displayDate = etmDisplayDate.getText().toString();
                    String depth = etdepth.getText().toString();
                    String amountoffish = etamountoffish.getText().toString();
                    String note = etnote.getText().toString();


                    contentValues.put(SQLiteHelper.DB_COL_LATITUDE, latitude);
                    contentValues.put(SQLiteHelper.DB_COL_LONGITUDE, longitude);
                    contentValues.put(SQLiteHelper.DB_COL_DATE, displayDate);
                    contentValues.put(SQLiteHelper.DB_COL_DEPTH, depth);
                    contentValues.put(SQLiteHelper.DB_COL_AMOUNT, amountoffish);
                    contentValues.put(SQLiteHelper.DB_COL_NOTE, note);

                  //  int update_id = Integer.parseInt(result7);

                    database.update(SQLiteHelper.DB_TABLE_NAME,contentValues,"_id = ?",new String[] {result7});


                    Toast.makeText(context, "UPDATE COMPLETE", Toast.LENGTH_LONG).show();

                    ModelClass modelClassupdate = new ModelClass(Integer.parseInt(result7),
                            Double.parseDouble(latitude),
                            Double.parseDouble(longitude),
                            displayDate,
                            Integer.parseInt(depth),
                            Integer.parseInt(amountoffish),
                            note);

                    //DatabaseLoad.getInstance(context).AddDataMarker(modelClassupdate);
                    DatabaseLoad.getInstance(context).UpdateMarker(modelClassupdate);
                    finish();

                }

                dbHelper.close();
            }
        });

    }
/*
    FUNCTION ADD MARKER ON MAP (IN BASE)
*/
    public void AddMarker() {

        String coordinate = getIntent().getStringExtra("coord");
        String newcoord = coordinate.replace("lat/lng: (", "").replace(")", "");
        String[] parts = newcoord.split(",");
        String lat = parts[0];
        String lon = parts[1];


        etlatitude.setText(lat);
        etlongitute.setText(lon);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                if (isEmpty() == true) {
                    Toast.makeText(context, "Fill in all the fields", Toast.LENGTH_LONG).show();

                } else {

                    String latitude = etlatitude.getText().toString();
                    String longitude = etlongitute.getText().toString();
                    String displayDate = etmDisplayDate.getText().toString();
                    String depth = etdepth.getText().toString();
                    String amountoffish = etamountoffish.getText().toString();
                    String note = etnote.getText().toString();



                    contentValues.put(SQLiteHelper.DB_COL_LATITUDE, latitude);
                    contentValues.put(SQLiteHelper.DB_COL_LONGITUDE, longitude);
                    contentValues.put(SQLiteHelper.DB_COL_DATE, displayDate);
                    contentValues.put(SQLiteHelper.DB_COL_DEPTH, depth);
                    contentValues.put(SQLiteHelper.DB_COL_AMOUNT, amountoffish);
                    contentValues.put(SQLiteHelper.DB_COL_NOTE, note);

                    database.insert(SQLiteHelper.DB_TABLE_NAME, null, contentValues);


                    Toast.makeText(context, "ADD to BASE, SELEBRATION", Toast.LENGTH_LONG).show();


                    ModelClass modelClass = new ModelClass(DatabaseLoad.getInstance(context).last_id + 1,
                            Double.valueOf(latitude),
                            Double.valueOf(longitude),
                            displayDate,
                            Integer.parseInt(depth),
                            Integer.parseInt(amountoffish),
                            note);

                    DatabaseLoad.getInstance(context).AddDataMarker(modelClass);


                    Bundle bundle = new Bundle();
                    bundle.putString("result", displayDate);
                    bundle.putString("id", String.valueOf(modelClass.getId()));


                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    //returnIntent.putExtra("id",modelClass.getId());

                    setResult(MapActivity.RESULT_OK, intent);
                    finish();


                }
                dbHelper.close();

            }

        });
    }

   /* FUNCTION OF CHECK EDIT TEXT ON EMPTY */


    public boolean isEmpty(){

        String lat = etlatitude.getText().toString();
        String lon = etlongitute.getText().toString();
        String date = etmDisplayDate.getText().toString();
        String depth = etdepth.getText().toString();
        String amount = etamountoffish.getText().toString();
        String note = etnote.getText().toString();


          if  (TextUtils.isEmpty(lat) ||
                  TextUtils.isEmpty(lon) ||
                  TextUtils.isEmpty(date) ||
                  TextUtils.isEmpty(depth)||
                  TextUtils.isEmpty(amount) ||
                  TextUtils.isEmpty(note)) {


              return true;
          }
          else return false;
    }




}





