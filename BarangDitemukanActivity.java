package com.project.baranghilang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.project.baranghilang.ModulAPI;
import com.project.baranghilang.R;
import com.project.baranghilang.adapter.PostingBarangDitemukanAdapterListview;
import com.project.baranghilang.model.CDataPosting;
import com.project.baranghilang.model.DataPosting;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BarangDitemukanActivity extends AppCompatActivity {

    ImageView imgbaranghilang;
    ImageView imgbarangditemukan;
    ImageView imgmyposting;
    ImageView imgsearch;
    EditText etcari;

    ListView lvposting;

    String stremail;
    String strusername;
    String strnohp;
    String struserurl;

    public static final String ROOT_URL = "https://barang-hilang.com/";
    private List<DataPosting> lstdataposting;


    String[] vpostingid;
    String[] vtanggal;
    String[] vemailposting;
    String[] vusernameposting;
    String[] vnohpposting;
    String[] vjudulposting;
    String[] vdeskripsi;
    String[] vlokasi;
    String[] vjenis;
    String[] vstatus;
    String[] vphotourl;

    String strpostingid;
    String strtanggal;

    String stremailposting;
    String strusernmaeposting;
    String strnohpposting;

    String strjudulposting;
    String strdeskripsi;
    String strlokasi;
    String strjenis;
    String strstatus;
    String strphotourl;

    String strquery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_barang_ditemukan);

        Intent i=getIntent();
        stremail= i.getStringExtra("kirimemail");
        strusername= i.getStringExtra("kirimusername");
        strnohp= i.getStringExtra("kirimnohp");
        struserurl= i.getStringExtra("kirimuserurl");

        lvposting=(ListView) findViewById(R.id.lvposting);
        imgbaranghilang=(ImageView) findViewById(R.id.imgBaranghilang);
        imgbarangditemukan=(ImageView) findViewById(R.id.imgBarangditemukan);
        imgmyposting=(ImageView) findViewById(R.id.imgMyposting);
        imgsearch = (ImageView) findViewById(R.id.imgsearch);
        etcari = (EditText) findViewById(R.id.etcari);

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strquery=etcari.getText().toString();

                subambildataposting();
            }
        });

        imgbaranghilang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BarangHilangActivity.class);
                intent.putExtra("kirimemail", stremail.toString());
                intent.putExtra("kirimusername", strusername.toString());
                intent.putExtra("kirimnohp", strnohp.toString());
                intent.putExtra("kirimuserurl", struserurl.toString());
                startActivity(intent);
                finish();
            }
        });

        imgbarangditemukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BarangDitemukanActivity.class);
                intent.putExtra("kirimemail", stremail.toString());
                intent.putExtra("kirimusername", strusername.toString());
                intent.putExtra("kirimnohp", strnohp.toString());
                intent.putExtra("kirimuserurl", struserurl.toString());
                startActivity(intent);
                finish();
            }
        });

        imgmyposting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPostingActivity.class);
                intent.putExtra("kirimemail", stremail.toString());
                intent.putExtra("kirimusername", strusername.toString());
                intent.putExtra("kirimnohp", strnohp.toString());
                intent.putExtra("kirimuserurl", struserurl.toString());
                startActivity(intent);
                finish();
            }
        });


        lvposting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strpostingid=vpostingid[position];
                strtanggal=vtanggal[position];
                stremailposting=vemailposting[position];
                strusernmaeposting=vusernameposting[position];
                strnohpposting=vnohpposting[position];
                strjudulposting=vjudulposting[position];
                strdeskripsi=vdeskripsi[position];
                strlokasi=vlokasi[position];
                strjenis=vjenis[position];
                strstatus=vstatus[position];
                strphotourl=vphotourl[position];

//                Toast.makeText(getApplicationContext(),"masuk " + stridtoko, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), KomentarBarangHilangActivity.class);
                intent.putExtra("kirimemail", stremail.toString());
                intent.putExtra("kirimusername", strusername.toString());
                intent.putExtra("kirimnohp", strnohp.toString());
                intent.putExtra("kirimuserurl", struserurl.toString());

                intent.putExtra("kirimpostingid", strpostingid.toString());
                intent.putExtra("kirimtanggal", strtanggal.toString());
                intent.putExtra("kirimemailposting", stremailposting.toString());
                intent.putExtra("kirimusernameposting", strusernmaeposting.toString());
                intent.putExtra("kirimnohposting", strnohpposting.toString());
                intent.putExtra("kirimjudulposting", strjudulposting.toString());
                intent.putExtra("kirimdeskripsi", strdeskripsi.toString());
                intent.putExtra("kirimlokasi", strlokasi.toString());
                intent.putExtra("kirimphotourl", strphotourl.toString());

                startActivity(intent);
                finish();

            }
        });

        subambildataposting();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginUserActivity.class);
        startActivity(intent);
        finish();

    }



    private void subambildataposting() {
//        //Here we will handle the http request to insert user to mysql db
//        //Creating a RestAdapter
//
////        Toast.makeText(UtamaUserActivity.this, stridcategory, Toast.LENGTH_LONG).show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ModulAPI api = adapter.create(ModulAPI.class);


        //Calling method to get whether report
        api.apiambildataposting(
                "Barang Ditemukan",
                strquery,


                new Callback<CDataPosting>() {
                    @Override
                    public void success(CDataPosting cdataposting, Response response) {
                        lstdataposting = new ArrayList<DataPosting>();
                        lstdataposting = cdataposting.getDataPosting();

                        vpostingid = new String[lstdataposting.size()];
                        vtanggal = new String[lstdataposting.size()];
                        vemailposting = new String[lstdataposting.size()];
                        vusernameposting = new String[lstdataposting.size()];
                        vnohpposting = new String[lstdataposting.size()];
                        vjudulposting = new String[lstdataposting.size()];
                        vdeskripsi = new String[lstdataposting.size()];
                        vlokasi= new String[lstdataposting.size()];
                        vstatus= new String[lstdataposting.size()];
                        vjenis= new String[lstdataposting.size()];
                        vphotourl= new String[lstdataposting.size()];

//                        Toast.makeText(UtamaUserActivity.this, String.valueOf(lstdatatoko.size()),Toast.LENGTH_LONG).show();

                        for (int i = 0; i < lstdataposting.size(); i++) {
                            //Storing names to string array
                            vpostingid[i] = lstdataposting.get(i).getpostingid().toString();
                            vtanggal[i] = lstdataposting.get(i).getpostingdate().toString();
                            vemailposting[i] = lstdataposting.get(i).getemail().toString();
                            vusernameposting[i]= lstdataposting.get(i).getusername().toString();
                            vnohpposting[i] =lstdataposting.get(i).getusername().toString();
                            vjudulposting[i] = lstdataposting.get(i).getpostingjudul().toString();
                            vdeskripsi[i] =lstdataposting.get(i).getpostingdeskripsi().toString();
                            vlokasi[i] = lstdataposting.get(i).getlokasi().toString();
                            vstatus[i] =lstdataposting.get(i).getstatus().toString();
                            vjenis[i] =lstdataposting.get(i).getjenis().toString();

                            vphotourl[i] = lstdataposting.get(i).getpostingurl().toString();


                        }



                        PostingBarangDitemukanAdapterListview vadapter = new PostingBarangDitemukanAdapterListview(BarangDitemukanActivity.this, vpostingid, vtanggal, vemailposting, vusernameposting,vnohpposting, vjudulposting, vdeskripsi, vlokasi, vjenis, vstatus, vphotourl);
                        lvposting.setAdapter(vadapter);







                    }

                    @Override
                    public void failure(RetrofitError error) {

                        String merror = error.getMessage();

                        Toast.makeText(BarangDitemukanActivity.this, " Terjadi Kesalahan Kooneksi Category " + merror, Toast.LENGTH_LONG).show();
                    }
                }
        );

    }
}
