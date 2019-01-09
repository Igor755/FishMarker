package com.company.imetlin.fishmarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.company.imetlin.fishmarker.adapters.AdapterRecycler;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WaterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);

        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        final List<ModelClass> image_details = getListData();


        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.separator));
        mRecyclerView.addItemDecoration(divider);

        /*
        This is standart divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), 1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        */


        mAdapter = new AdapterRecycler(image_details, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(WaterActivity.this, MapActivity.class);
                ModelClass m = image_details.get(position);

                intent.putExtra("coordinates", m.getCoordinates());
                intent.putExtra("zoom", m.getZoom());
                startActivity(intent);


            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    private List<ModelClass> getListData() {

        Intent intent = getIntent();
        String txtName = getIntent().getStringExtra("name");
        ArrayList<ModelClass> list = new ArrayList<ModelClass>();

        switch (txtName) {

            case "Ocean":
            case "Океан":

                ModelClass NorthPacific = new ModelClass(getBaseContext().getResources().getString(R.string.NorthPacific), 39.63953756, -177.890625, 3);
                ModelClass SouthPacific = new ModelClass(getBaseContext().getResources().getString(R.string.SouthPacific), -31.05293399, -128.671875, 3);
                ModelClass Southern = new ModelClass(getBaseContext().getResources().getString(R.string.Southern), -67.87554135, -164.1796875, 0);
                ModelClass NorthAtlantic = new ModelClass(getBaseContext().getResources().getString(R.string.NorthAtlantic), 41.77131168, -393.92578125, 3);
                ModelClass SouthAtlantic = new ModelClass(getBaseContext().getResources().getString(R.string.SouthAtlantic), -24.52713482, -372.12890625, 3);
                ModelClass Indian = new ModelClass(getBaseContext().getResources().getString(R.string.Indian), -14.94478488, -282.12890625, 1);
                ModelClass Arctic = new ModelClass(getBaseContext().getResources().getString(R.string.Arctic), 82.11838361, -169.27734375, 0);


                list.add(NorthPacific);
                list.add(SouthPacific);
                list.add(Southern);
                list.add(NorthAtlantic);
                list.add(SouthAtlantic);
                list.add(Indian);
                list.add(Arctic);

                Collections.sort(list);

                break;

            case "Sea":
            case "Море":

                ModelClass Black = new ModelClass(getBaseContext().getResources().getString(R.string.Black), 43.19716728, -326.00830078, 6);
                ModelClass SeaOfAzov = new ModelClass(getBaseContext().getResources().getString(R.string.SeaOfAzov), 45.96642454, -323.32763672, 7);
                ModelClass Baltic = new ModelClass(getBaseContext().getResources().getString(R.string.Baltic), 57.6571576, -340.08178711, 6);
                ModelClass Red = new ModelClass(getBaseContext().getResources().getString(R.string.Red), 21.28937436, -321.97631836, 6);
                ModelClass Caspian = new ModelClass(getBaseContext().getResources().getString(R.string.Caspian), 41.9349765, -309.56176758, 6);
                ModelClass Mediterranean = new ModelClass(getBaseContext().getResources().getString(R.string.Mediterranean), 35.3532161, -341.43310547, 5);
                ModelClass Arabian = new ModelClass(getBaseContext().getResources().getString(R.string.Arabian), 14.17918614, -295.53222656, 5);
                ModelClass SeaofOkhotsk = new ModelClass(getBaseContext().getResources().getString(R.string.SeaofOkhotsk), 54.826008, -210.36621094, 5);
                ModelClass SeaofJapan = new ModelClass(getBaseContext().getResources().getString(R.string.SeaofJapan), 40.41349605, -225.28564453, 5);
                ModelClass BeringSea = new ModelClass(getBaseContext().getResources().getString(R.string.BeringSea), 57.86813176, -177.78076172, 5);
                ModelClass NorthSea = new ModelClass(getBaseContext().getResources().getString(R.string.NorthSea), 56.31653672, -356.46240234, 5);
                ModelClass CaribeanSea = new ModelClass(getBaseContext().getResources().getString(R.string.CaribeanSea), 15.19938605, -433.19091797, 5);

                list.add(Black);
                list.add(Baltic);
                list.add(Red);
                list.add(SeaOfAzov);
                list.add(Caspian);
                list.add(Mediterranean);
                list.add(Arabian);
                list.add(SeaofOkhotsk);
                list.add(SeaofJapan);
                list.add(BeringSea);
                list.add(NorthSea);
                list.add(CaribeanSea);

                Collections.sort(list);

                break;

            case "Lake":
            case "Озеро":

                ModelClass Baikal = new ModelClass(getBaseContext().getResources().getString(R.string.Baikal), 53.18212452, 107.98451206, 7);
                ModelClass Victoria = new ModelClass(getBaseContext().getResources().getString(R.string.Victoria), -1.14207713, 32.9604879, 8);
                ModelClass Titicaca = new ModelClass(getBaseContext().getResources().getString(R.string.Titicaca), -15.85597851, -69.33705471, 9);
                ModelClass Superior = new ModelClass(getBaseContext().getResources().getString(R.string.Superior), 47.60616304, -447.31933594, 7);
                ModelClass Michigan = new ModelClass(getBaseContext().getResources().getString(R.string.Michigan), 43.86621801, -447.09960938, 7);
                ModelClass Huron = new ModelClass(getBaseContext().getResources().getString(R.string.Huron), 44.65302416, -442.33154297, 7);
                ModelClass Ladoga = new ModelClass(getBaseContext().getResources().getString(R.string.Ladoga), 60.83955786, -328.40881348, 8);
                ModelClass Onega = new ModelClass(getBaseContext().getResources().getString(R.string.Onega), 61.63250678, -324.33288574, 8);
                ModelClass White = new ModelClass(getBaseContext().getResources().getString(R.string.White), 60.16337606, -322.30316162, 9);

                list.add(Baikal);
                list.add(Victoria);
                list.add(Titicaca);
                list.add(Superior);
                list.add(Michigan);
                list.add(Huron);
                list.add(Ladoga);
                list.add(Onega);
                list.add(White);

                Collections.sort(list);

                break;

            case "River":
            case "Река":

                ModelClass Dnieper = new ModelClass(getBaseContext().getResources().getString(R.string.Dnieper), 50.88137667, -329.50401306, 10);
                ModelClass Dniester = new ModelClass(getBaseContext().getResources().getString(R.string.Dniester), 46.4113515, -329.73867416, 13);
                ModelClass Southern_Bug = new ModelClass(getBaseContext().getResources().getString(R.string.Southern_Bug), 48.71731705, 29.14673289, 13);
                ModelClass Turunchuk = new ModelClass(getBaseContext().getResources().getString(R.string.Turunchuk), 46.46760094, -329.80901241, 14);
                ModelClass Volga = new ModelClass(getBaseContext().getResources().getString(R.string.Volga), 50.28231945, -314.11560059, 8);
                ModelClass Yenisei = new ModelClass(getBaseContext().getResources().getString(R.string.Yenisei), 67.22105297, -273.40026855, 8);
                ModelClass Lena = new ModelClass(getBaseContext().getResources().getString(R.string.Lena), 69.22499685, -235.4864502, 8);
                ModelClass Ob = new ModelClass(getBaseContext().getResources().getString(R.string.Ob), 65.45712014, -294.33883667, 8);
                ModelClass Angara = new ModelClass(getBaseContext().getResources().getString(R.string.Angara), 54.39015388, -256.73126221, 8);
                ModelClass Amur = new ModelClass(getBaseContext().getResources().getString(R.string.Amur), 47.65058757, -228.37623596, 8);
                ModelClass Amazonka = new ModelClass(getBaseContext().getResources().getString(R.string.Amazonka), -2.27357302, -416.20056152, 8);

                list.add(Dnieper);
                list.add(Dniester);
                list.add(Southern_Bug);
                list.add(Turunchuk);
                list.add(Volga);
                list.add(Yenisei);
                list.add(Lena);
                list.add(Ob);
                list.add(Angara);
                list.add(Amur);
                list.add(Amazonka);

                Collections.sort(list);

                break;

            case "Gulf":
            case "Залив":

                ModelClass Persian = new ModelClass(getBaseContext().getResources().getString(R.string.Persian), 26.82407078, -307.97973633, 7);
                ModelClass Taharonzka = new ModelClass(getBaseContext().getResources().getString(R.string.Taharonzka), 47.00835282, -321.50253296, 9);
                ModelClass Finland = new ModelClass(getBaseContext().getResources().getString(R.string.Finland), 59.9660097, -333.46252441, 7);
                ModelClass GulfofThailand = new ModelClass(getBaseContext().getResources().getString(R.string.GulfofThailand), 9.51407926, -257.9864502, 6);
                ModelClass Bengal = new ModelClass(getBaseContext().getResources().getString(R.string.Bengal), 13.9234039, -272.48291016, 6);


                list.add(Persian);
                list.add(Taharonzka);
                list.add(Finland);
                list.add(GulfofThailand);
                list.add(Bengal);

                Collections.sort(list);

                break;

            case "Another":
            case "Другое":

                ModelClass ReservoirHAES = new ModelClass(getBaseContext().getResources().getString(R.string.ReservoirHAES), 50.2992091, -333.41557503, 13);
                ModelClass RibinskiyReservoir = new ModelClass(getBaseContext().getResources().getString(R.string.RibinskiyReservoir), 58.42185332, -321.54373169, 9);
                ModelClass KuibishivskiyReservoir = new ModelClass(getBaseContext().getResources().getString(R.string.KuibishivskiyReservoir), 54.5083265, -311.37176514, 10);

                list.add(ReservoirHAES);
                list.add(RibinskiyReservoir);
                list.add(KuibishivskiyReservoir);

                Collections.sort(list);

                break;


            default:
                break;

        }
        return list;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_water, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.back:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}