package com.company.imetlin.fishmarker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import com.company.imetlin.fishmarker.database.DatabaseLoad;
import com.company.imetlin.fishmarker.database.SQLiteHelper;
import com.company.imetlin.fishmarker.pojo.MarkerInformation;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class CardMarkerActivity extends AppCompatActivity {


    private static final String TAG = "CardMarkerActivity";
    private static final AtomicLong counter = new AtomicLong(0);
    private EditText etlatitude;
    private EditText etlongitute;
    private TextView etmDisplayDate;
    private EditText etdepth;
    private EditText etamountoffish;
    private EditText etnote;
    private EditText ettitle;
    private Button ok, cancel;

    private Context context = CardMarkerActivity.this;
    static AtomicInteger atom = new AtomicInteger(0);
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private SQLiteHelper dbHelper;
    private String id_markers_keys;

    public Boolean isnull;

    public Menu menu;
    private AlertDialog alertDialog;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_marker);

        this.id = id;
        this.id_markers_keys = id_markers_keys;

        etlatitude = (EditText) findViewById(R.id.edit_latitude);
        etlongitute = (EditText) findViewById(R.id.edit_longitude);
        ettitle = (EditText) findViewById(R.id.edit_title_marker);
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

                String date = day + "." + month + "." + year;
                etmDisplayDate.setText(date);
            }
        };


        if ((result1 = getIntent().getStringExtra("1")) != null) {


            UpdateMarker();


        } else {

            AddMarker();


        }


    }

    /*
              MENU
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_cardmarker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.recycler:
                //function delete marker
                DeleteMarker();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        String result1;

        if ((result1 = getIntent().getStringExtra("1")) != null) {


        } else {
            MenuItem item = menu.findItem(R.id.recycler);
            item.setVisible(false);

        }
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

        //id
        final String result8 = getIntent().getStringExtra("8");


        etlatitude.setText(result1);
        etlongitute.setText(result2);
        ettitle.setText(result3);
        etmDisplayDate.setText(result4);
        etdepth.setText(result5);
        etamountoffish.setText(result6);
        etnote.setText(result7);

        ok.setText("EDIT");


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                if (isEmpty() == true) {
                    Toast.makeText(context, R.string.fill, Toast.LENGTH_LONG).show();

                } else {

                    String latitude = etlatitude.getText().toString();
                    String longitude = etlongitute.getText().toString();
                    String title = ettitle.getText().toString();
                    String displayDate = etmDisplayDate.getText().toString();
                    String depth = etdepth.getText().toString();
                    String amountoffish = etamountoffish.getText().toString();
                    String note = etnote.getText().toString();


                    contentValues.put(SQLiteHelper.DB_COL_LATITUDE, latitude);
                    contentValues.put(SQLiteHelper.DB_COL_LONGITUDE, longitude);
                    contentValues.put(SQLiteHelper.DB_COL_TITLE, title);
                    contentValues.put(SQLiteHelper.DB_COL_DATE, displayDate);
                    contentValues.put(SQLiteHelper.DB_COL_DEPTH, depth);
                    contentValues.put(SQLiteHelper.DB_COL_AMOUNT, amountoffish);
                    contentValues.put(SQLiteHelper.DB_COL_NOTE, note);

                    //  int update_id = Integer.parseInt(result7);

                    database.update(SQLiteHelper.DB_TABLE_NAME, contentValues, "_id = ?", new String[]{result8});


                    Toast.makeText(context, R.string.update_complete, Toast.LENGTH_LONG).show();

                    ModelClass modelClassupdate = new ModelClass(Integer.parseInt(result8),
                            Double.parseDouble(latitude),
                            Double.parseDouble(longitude),
                            title,
                            displayDate,
                            Double.parseDouble(depth),
                            Integer.parseInt(amountoffish),
                            note);

                    //DatabaseLoad.getInstance().UpdateMarker(modelClassupdate);
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


        String lat_lon = getIntent().getStringExtra("coordinate");
        String[] parts = lat_lon.split("/");
        String lat = parts[0];
        String lon = parts[1];


        etlatitude.setText(lat);
        etlongitute.setText(lon);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isEmpty()) {
                    Toast.makeText(context, R.string.fill, Toast.LENGTH_LONG).show();

                } else {

                    String latitude = etlatitude.getText().toString();
                    String longitude = etlongitute.getText().toString();
                    String title = ettitle.getText().toString();
                    String displayDate = etmDisplayDate.getText().toString();
                    String depth = etdepth.getText().toString();
                    String amountoffish = etamountoffish.getText().toString();
                    String note = etnote.getText().toString();


                    Toast.makeText(context, R.string.add_base, Toast.LENGTH_LONG).show();

                    id_markers_keys = UUID.randomUUID().toString();


                    MarkerInformation markerInformation = new MarkerInformation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            String.valueOf(getID()),
                            Double.valueOf(latitude),
                            Double.valueOf(longitude),
                            title,
                            displayDate,
                            Double.valueOf(depth),
                            Integer.parseInt(amountoffish),
                            note);

                    FirebaseDatabase.getInstance().getReference("Markers").child(String.valueOf(id_markers_keys)).setValue(markerInformation);

                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("id", String.valueOf(markerInformation.getMarker_id()));
                    Intent intent = new Intent();
                    intent.putExtras(bundle);


                    setResult(MapActivity.RESULT_OK, intent);


                    DatabaseLoad.getInstance().AddDataMarker(markerInformation);
                    finish();
                }
            }

        });
    }
    public static long getID() {

          final long LIMIT = 10000000000L;
          long last = 0;
        // 10 digits.
        long id = System.currentTimeMillis() % LIMIT;
        if ( id <= last ) {
            id = (last + 1) % LIMIT;
        }
        return last = id;
    }

    public void DeleteMarker() {


        alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(R.string.you);
        alertDialog.setMessage(context.getResources().getString(R.string.the) + "\n" +
                context.getResources().getString(R.string.from));

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                final String result1 = getIntent().getStringExtra("1");
                final String result2 = getIntent().getStringExtra("2");
                final String result3 = getIntent().getStringExtra("3");
                final String result4 = getIntent().getStringExtra("4");
                final String result5 = getIntent().getStringExtra("5");
                final String result6 = getIntent().getStringExtra("6");
                final String result7 = getIntent().getStringExtra("7");
                //id
                final String result8 = getIntent().getStringExtra("8");

                database.delete(SQLiteHelper.DB_TABLE_NAME, SQLiteHelper.DB_COL_ID_PRIMARY + "=" + result8, null);

                ModelClass modelClassDelete = new ModelClass(Integer.parseInt(result8),
                        Double.parseDouble(result1),
                        Double.parseDouble(result2),
                        result3,
                        result4,
                        Double.parseDouble(result5),
                        Integer.parseInt(result6),
                        result7);

                // DatabaseLoad.getInstance().DeleteMarker(modelClassDelete);


                finish();

                dbHelper.close();

            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, context.getResources().getString(R.string.cancel), Toast.LENGTH_LONG).show();


            }
        });

        alertDialog.show();


    }

    /* FUNCTION OF CHECK EDIT TEXT ON EMPTY */


    public boolean isEmpty() {

        String lat = etlatitude.getText().toString();
        String lon = etlongitute.getText().toString();
        String date = etmDisplayDate.getText().toString();
        String depth = etdepth.getText().toString();
        String amount = etamountoffish.getText().toString();
        String note = etnote.getText().toString();
        String title = ettitle.getText().toString();


        if (TextUtils.isEmpty(lat) ||
                TextUtils.isEmpty(lon) ||
                TextUtils.isEmpty(title) ||
                TextUtils.isEmpty(date) ||
                TextUtils.isEmpty(depth) ||
                TextUtils.isEmpty(amount) ||
                TextUtils.isEmpty(note)) {


            return true;
        } else return false;
    }


}
/*
    public void AddMarker() {




        String lat_lon = getIntent().getStringExtra("coordinate");
        //String coordinate2 = getIntent().getStringExtra("lon");
        //String newcoord = coordinate.replace("lat/lng: (", "").replace(")", "");
        String[] parts = lat_lon.split("/");
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
                    Toast.makeText(context, R.string.fill, Toast.LENGTH_LONG).show();

                } else {

                    String latitude = etlatitude.getText().toString();
                    String longitude = etlongitute.getText().toString();
                    String title = ettitle.getText().toString();
                    String displayDate = etmDisplayDate.getText().toString();
                    String depth = etdepth.getText().toString();
                    String amountoffish = etamountoffish.getText().toString();
                    String note = etnote.getText().toString();

                    contentValues.put(SQLiteHelper.DB_COL_ID_PRIMARY, DatabaseLoad.getInstance().last_id+1);
                    contentValues.put(SQLiteHelper.DB_COL_LATITUDE, latitude);
                    contentValues.put(SQLiteHelper.DB_COL_LONGITUDE, longitude);
                    contentValues.put(SQLiteHelper.DB_COL_TITLE, title);
                    contentValues.put(SQLiteHelper.DB_COL_DATE, displayDate);
                    contentValues.put(SQLiteHelper.DB_COL_DEPTH, depth);
                    contentValues.put(SQLiteHelper.DB_COL_AMOUNT, amountoffish);
                    contentValues.put(SQLiteHelper.DB_COL_NOTE, note);

                    database.insert(SQLiteHelper.DB_TABLE_NAME, null, contentValues);


                    Toast.makeText(context, R.string.add_base, Toast.LENGTH_LONG).show();


                    ModelClass modelClass = new ModelClass(DatabaseLoad.getInstance().last_id+1,
                            Double.valueOf(latitude),
                            Double.valueOf(longitude),
                            title,
                            displayDate,
                            Double.valueOf(depth),
                            Integer.parseInt(amountoffish),
                            note);


                    Bundle bundle = new Bundle();
                    bundle.putString("result", title);
                    bundle.putString("id", String.valueOf(modelClass.getModel_id()));


                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    //returnIntent.putExtra("id",modelClass.getId());

                    setResult(MapActivity.RESULT_OK, intent);



/////////////////////////////////////////////////////////////in hands


*//*

                        FirebaseDatabase.getInstance().getReference().child("Markers").setValue(DatabaseLoad.getInstance().last_id+1);
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("Uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("latitude").setValue(Double.valueOf(latitude));
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("longitude").setValue(Double.valueOf(longitude));
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("title").setValue(title);
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("date").setValue(displayDate);
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("depth").setValue(Double.valueOf(depth));
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("amount_of_fish").setValue(Integer.parseInt(amountoffish));
                        FirebaseDatabase.getInstance().getReference().child("Markers").child("note").setValue(note);
*//*

                    id = UUID.randomUUID().toString();





                    MarkerInformation markerInformation = new MarkerInformation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            id,
                            Double.valueOf(latitude),
                            Double.valueOf(longitude),
                            title,
                            displayDate,
                            Double.valueOf(depth),
                            Integer.parseInt(amountoffish),
                            note);

                    //////////////////////////////Serializing Arrays is not supported, please use Lists instead
                    ////////////////////////////// attend.put("depth", Arrays.asList(depth)) - keys under every point (+)
                        *//*Map<String, Object> attend = new HashMap<>();
                        attend.put("uid",  FirebaseAuth.getInstance().getCurrentUser().getUid()) ;
                        attend.put("latitude", latitude) ;
                        attend.put("longitude",  longitude) ;
                        attend.put("title", title) ;
                        attend.put("displayDate", displayDate) ;
                        attend.put("depth",  depth) ;
                        attend.put("amountoffish", amountoffish) ;
                        attend.put("note", note) ;*//*


                    FirebaseDatabase.getInstance().getReference("Markers").child(id).setValue(markerInformation);







                    // DatabaseLoad.getInstance().AddDataMarker(modelClass);


                    finish();


                }

                *//*} catch (SQLiteConstraintException e){


                    Intent intent = new Intent();
                    setResult(MapActivity.RESULT_CANCELED, intent);
                    finish();

                }*//*
                dbHelper.close();

            }

        });
    }*/





