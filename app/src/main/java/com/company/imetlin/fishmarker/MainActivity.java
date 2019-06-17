package com.company.imetlin.fishmarker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.company.imetlin.fishmarker.adapters.AdapterGrid;
import com.company.imetlin.fishmarker.firebaseAuth.SignInActivity;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.company.imetlin.fishmarker.userplaces.PlacesUserActivity;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public Menu menu;
    //private Context context = MainActivity.this;

    private FirebaseAuth mAuth;
    FrameLayout frameLayout;
    private int mGridViewBGColor = Color.parseColor("#cce6ff");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);





        List<ModelClass> image_details = getListData();

        final GridView gridView = (GridView) findViewById(R.id.gridView);

        mAuth = FirebaseAuth.getInstance();

        gridView.setAdapter(new AdapterGrid(this, image_details));

       // gridView.setBackgroundResource(R.drawable.fon4);
        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                ModelClass _modelClass = (ModelClass) o;

                frameLayout = (FrameLayout) findViewById(R.id.frame);
               // gridView.setBackgroundColor(mGridViewBGColor);


                String selectedItem = a.getItemAtPosition(position).toString();

                Snackbar snackbar = Snackbar.make(
                        frameLayout,
                        "Selected : " + selectedItem,
                        Snackbar.LENGTH_LONG
                );
                snackbar.getView().setBackgroundColor(Color.parseColor("#FF66729B"));
                snackbar.show();

                // Initialize a new color drawable array
                ColorDrawable[] colors = {
                        new ColorDrawable(Color.RED), // Animation starting color
                        new ColorDrawable(mGridViewBGColor) // Animation ending color
                };

                TransitionDrawable transitionDrawable = new TransitionDrawable(colors);

                // Set the clicked item background
                v.setBackground(transitionDrawable);

                // Finally, Run the item background color animation
                // This is the grid view item click effect
                transitionDrawable.startTransition(600);


                Intent intent = new Intent(getBaseContext(), PlacesUserActivity.class);
                intent.putExtra("name", _modelClass.getName_fbase());
                startActivity(intent);


            }
        });
    }


    private List<ModelClass> getListData() {

        List<ModelClass> list = new ArrayList<ModelClass>();
        ModelClass ocean = new ModelClass(getApplicationContext().getResources().getString(R.string.ocean), "ocean","ocean");
        ModelClass sea = new ModelClass(getApplicationContext().getResources().getString(R.string.sea), "sea_n","sea");
        ModelClass river = new ModelClass(getApplicationContext().getResources().getString(R.string.river), "river_n", "river");
        ModelClass lake = new ModelClass(getApplicationContext().getResources().getString(R.string.lake), "lake_n","lake");

        ////zaliv
        ModelClass Gulf = new ModelClass(getApplicationContext().getResources().getString(R.string.gulf), "gulf_n","gulf");
        ModelClass Another = new ModelClass(getApplicationContext().getResources().getString(R.string.another), "another_n", "another");


        list.add(ocean);
        list.add(sea);
        list.add(river);
        list.add(lake);

        list.add(Gulf);
        list.add(Another);


        return list;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.information:
                //function information
                InformationWindow();
                return true;
         /*   case R.id.exit:
                //function information
                signOut();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void InformationWindow() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Developer information")
                .setMessage("Developer: i.metlin" + "\n" +
                        "                    Ukraine" + "\n" +
                        "                    Odessa" + "\n" +
                        "                    metlin.igor@gmail.com" + "\n" +
                        "                    Metlin Igor")
                .setIcon(R.drawable.information)
                .setCancelable(false)
                .setNegativeButton(getApplicationContext().getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
