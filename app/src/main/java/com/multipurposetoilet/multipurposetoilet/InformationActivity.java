package com.multipurposetoilet.multipurposetoilet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.content.AsyncTaskLoader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;

import com.multipurposetoilet.multipurposetoilet.AsyncTaskHttpRequest;


public class InformationActivity extends FragmentActivity {

    public static int item = 5;   // 詳細の項目数
//  public static int item_real = 4;    // リアルタイム入力項目の数

    private TextView title,address,time,phone,pictureTitle,u_date,u_time, time_end, a_date, level;
    private ImageView imageView;
    private String image_path;
    //private TextView details[] = new TextView[item];
//    private TextView state[] = new TextView[item_real];
//    private EditText Estate[] = new EditText[item_real];
//    private Button editButton;
    ImageButton mapButton,topButton;
//    private String U_date;
//    private String U_time;

    private LocationManager locationManager;

    int id = 0;
    float ds = 0;

    int edit_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsFlg = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("GPS Enabled", gpsFlg ? "OK" : "NG");
        LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        //LocationManagerの取得
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        // GPSではなくネットワークから現在地の情報を取得
        Location myLocate = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // 緯度の取得
        double myLatitude =  myLocate.getLatitude();
        //経度の取得
        double myLongitude = myLocate.getLongitude();

        //DetailClass DC = new DetailClass();
        //final LinearLayout layout =(LinearLayout)findViewById(R.id.inflate_layout);

        ArrayList<ArrayList<String>> array= new ArrayList<ArrayList<String>>();
        array = CSVParser.ReadCSV(getApplicationContext(), "避難所データ.csv");

        // layoutと関連付ける
        title = (TextView)findViewById(R.id.title);
        address = (TextView)findViewById(R.id.address);
        a_date = (TextView)findViewById(R.id.a_date);
        time = (TextView)findViewById(R.id.time);
        time_end = (TextView)findViewById(R.id.time_end);
        level = (TextView)findViewById(R.id.level);
        phone = (TextView)findViewById(R.id.phone);
        pictureTitle = (TextView)findViewById(R.id.pictureTitle);
//        u_date = (TextView)findViewById(R.id.U_date);
//        u_time = (TextView)findViewById(R.id.U_time);

        //imageView = (ImageView)findViewById(R.id.imageView1);
//        details[0] = (TextView)findViewById(R.id.door1);
//        details[1] = (TextView)findViewById(R.id.key1);
//        details[2] = (TextView)findViewById(R.id.breadth1);
//        details[3] = (TextView)findViewById(R.id.handrail1);
//        details[4] = (TextView)findViewById(R.id.toiletSeat1);

//        TableLayout tableLayout = (TableLayout)findViewById(R.id.info2);
//        getLayoutInflater().inflate(R.layout.table_real, tableLayout);

//        final ViewGroup viewGroup = (ViewGroup)findViewById(R.id.info2);
//        getLayoutInflater().inflate(R.layout.table_real, viewGroup);

//        state[0] = (TextView)findViewById(R.id.Rstate_0);
//        state[1] = (TextView)findViewById(R.id.Rstate_1);
//        state[2] = (TextView)findViewById(R.id.Rstate_2);
//        state[3] = (TextView)findViewById(R.id.Rstate_3);

        mapButton = (ImageButton) findViewById(R.id.mapButton);
        topButton = (ImageButton) findViewById(R.id.topButton);
//        editButton = (Button)findViewById(R.id.editButton);

        // 前の画面で指定した値を取り出す
        Intent intent = getIntent();
        int ID = intent.getIntExtra("Detail", 0);    // MapsActivity と ListActivity から遷移
        id = ID;


        // データを取り出す
        //ShelterClass SL = subDB.DB(ID);
//        DC.title = SL.name;
//        DC.address = SL.address;
//        DC.time = SL.time;
//        DC.phone = SL.phone;
//        DC.pictureTitle = SL.pictureTitle;
//        //for (int i=0; i<item; i++) { DC.details[i] = SL.detail[i]; }
//        for (int i=0; i<item_real; i++) { DC.state[i] = SL.state[i]; }
//        DC.imageView = BitmapFactory.decodeResource(getResources(), SL.imageView);
//
//        // layoutにセット_old
//        title.setText(DC.title);
//        address.append(DC.address);
//        time.append(DC.time);
//        phone.append(DC.phone);
//        pictureTitle.setText(DC.pictureTitle);
//        imageView.setImageBitmap(DC.imageView);
//        //for (int i=0; i<item; i++) details[i].setText(DC.details[i]);
//        for (int i=0; i<item_real; i++) state[i].setText(DC.state[i]);

        // layoutにセット_new
        title.setText(array.get(ID).get(1));
//      address.append(array.get(ID).get(2));
        address.append(array.get(ID).get(6));
        //time.append(DC.time);
//      phone.append(array.get(ID).get(5));
        phone.append(array.get(ID).get(7));
//        pictureTitle.setText(DC.pictureTitle);
        a_date.append(array.get(ID).get(8));
        time.append(array.get(ID).get(9));
        time_end.append(array.get(ID).get(10));
        level.append(array.get(ID).get(11));
        image_path = (array.get(ID).get(48));

//        int resId = getResources().getIdentifier("imageView1", "id", getPackageName());
//        ImageView iv = (ImageView)findViewById(resId);
//        int imageId = getResources().getIdentifier("id" + ID, "drawable", getPackageName());
//        iv.setImageResource(imageId);



        ImageView imageView = (ImageView)findViewById(R.id.customview);
        String stringUri = image_path;
        Uri uri = Uri.parse(stringUri);
        Uri.Builder builder = uri.buildUpon();
        AsyncTaskHttpRequest task = new AsyncTaskHttpRequest(imageView);
        task.execute(builder);

        //for (int i=0; i<item; i++) details[i].setText(DC.details[i]);
        //for (int i=0; i<array.get(ID).size()-6; i++) state[i].setText(DC.state[i]);
        //for (int i=0; i<item_real; i++) state[i].setText(DC.state[i]);

        float[] distance = getDistance(myLatitude, myLongitude, Float.parseFloat(array.get(ID).get(3)),Float.parseFloat(array.get(ID).get(4)));
        ds = distance[0];
//
//
//        id = SL.id;


        // テーブルを動的に生成
        ViewGroup vg = (ViewGroup)findViewById(R.id.info1);
     //   for(int i = 7; i < array.get(0).size(); i++){
        for(int i = 12; i < 30; i++){    //トイレ情報の数
            // 行を追加
            getLayoutInflater().inflate(R.layout.table_row, vg);
            // 文字設定
            TableRow tr = (TableRow)vg.getChildAt(i-12);
            //String str = String.format(Locale.getDefault(), "テスト%d", i+1);
            ((TextView)(tr.getChildAt(0))).setText(array.get(0).get(i));
            ((TextView)(tr.getChildAt(1))).setText(array.get(ID).get(i));
            if(i%2 ==0){
                ((TextView)(tr.getChildAt(0))).setBackgroundColor(Color.parseColor("#b3daff"));
                ((TextView)(tr.getChildAt(1))).setBackgroundColor(Color.parseColor("#819FF7"));
            }else {
                //((TextView)(tr.getChildAt(0))).setBackgroundColor(Color.parseColor("#E6E6E6"));
                ((TextView)(tr.getChildAt(1))).setBackgroundColor(Color.parseColor("#E6E6E6"));

            }
        }
        ViewGroup vg2 = (ViewGroup)findViewById(R.id.info2);
        for(int i = 32; i < 41; i++){    //周辺の情報
            // 行を追加
            getLayoutInflater().inflate(R.layout.table_row, vg2);
            // 文字設定
            TableRow tr = (TableRow)vg2.getChildAt(i-32);
            //String str = String.format(Locale.getDefault(), "テスト%d", i+1);
            ((TextView)(tr.getChildAt(0))).setText(array.get(0).get(i));
            ((TextView)(tr.getChildAt(1))).setText(array.get(ID).get(i));
            if(i%2 ==0){
                ((TextView)(tr.getChildAt(0))).setBackgroundColor(Color.parseColor("#ed7d31"));
                ((TextView)(tr.getChildAt(1))).setBackgroundColor(Color.parseColor("#f8d7cd"));
            }else {
                //((TextView)(tr.getChildAt(0))).setBackgroundColor(Color.parseColor("#E6E6E6"));
                ((TextView)(tr.getChildAt(1))).setBackgroundColor(Color.parseColor("#fcece8"));

            }
        }
        ViewGroup vg3 = (ViewGroup)findViewById(R.id.info3);
        for(int i = 41; i < 46; i++){    //付加情報
            // 行を追加
            getLayoutInflater().inflate(R.layout.table_row, vg3);
            // 文字設定
            TableRow tr = (TableRow)vg3.getChildAt(i-41);
            //String str = String.format(Locale.getDefault(), "テスト%d", i+1);
            ((TextView)(tr.getChildAt(0))).setText(array.get(0).get(i));
            //csv テーブルのタイトルと内容のセルの位置が違うため i - 1
            ((TextView)(tr.getChildAt(1))).setText(array.get(ID).get(i-1));
            if(i%2 ==0){
                ((TextView)(tr.getChildAt(0))).setBackgroundColor(Color.parseColor("#70ad47"));
                ((TextView)(tr.getChildAt(1))).setBackgroundColor(Color.parseColor("#d5e3cf"));
            }else {
                //((TextView)(tr.getChildAt(0))).setBackgroundColor(Color.parseColor("#E6E6E6"));
                ((TextView)(tr.getChildAt(1))).setBackgroundColor(Color.parseColor("#ebf1e9"));

            }
        }


//        editButton.setOnClickListener(new  View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                if(edit_counter == 0){
//
//                    // 変更したいレイアウトを取得
//                    //TableLayout tableLayout = (TableLayout)findViewById(R.id.info2);
//                    // レイアウトのビューを全て削除する
//                    viewGroup.removeAllViews();
//                    // 別のレイアウトに変更
//                    getLayoutInflater().inflate(R.layout.table_real_editor, viewGroup);
//
//                    Estate[0] = (EditText)findViewById(R.id.Estate_0);
//                    Estate[1] = (EditText)findViewById(R.id.Estate_1);
//                    Estate[2] = (EditText)findViewById(R.id.Estate_2);
//                    Estate[3] = (EditText)findViewById(R.id.Estate_3);
//
//                    Estate[0].setText(state[0].getText());
//                    Estate[1].setText(state[1].getText());
//                    Estate[2].setText(state[2].getText());
//                    Estate[3].setText(state[3].getText());
//
//                    editButton.setText("更新する");
//                    editButton.setTextColor(Color.RED);
//
//                    edit_counter = 1;
//
//                } else {
//
//                    Estate[0] = (EditText)findViewById(R.id.Estate_0);
//                    Estate[1] = (EditText)findViewById(R.id.Estate_1);
//                    Estate[2] = (EditText)findViewById(R.id.Estate_2);
//                    Estate[3] = (EditText)findViewById(R.id.Estate_3);
//
//                    // 変更したいレイアウトを取得
//                    TableLayout tableLayout = (TableLayout)findViewById(R.id.Einfo2);
//                    // レイアウトのビューを全て削除する
//                    tableLayout.removeAllViews();
//                    // 別のレイアウトに変更
//                    getLayoutInflater().inflate(R.layout.table_real, tableLayout);
//
//                    state[0] = (TextView)findViewById(R.id.Rstate_0);
//                    state[1] = (TextView)findViewById(R.id.Rstate_1);
//                    state[2] = (TextView)findViewById(R.id.Rstate_2);
//                    state[3] = (TextView)findViewById(R.id.Rstate_3);
//
//                    state[0].setText(Estate[0].getText());
//                    state[1].setText(Estate[1].getText());
//                    state[2].setText(Estate[2].getText());
//                    state[3].setText(Estate[3].getText());
//
//                    // 日時の取得
//                    Calendar cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//                    int hour = cal.get(Calendar.HOUR_OF_DAY);
//                    int minute = cal.get(Calendar.MINUTE);
//                    int second = cal.get(Calendar.SECOND);
//                    U_date = year + "年" + (month + 1) + "月" + day + "日";
//                    U_time = hour + "時" + minute + "分" + second + "秒";
//                    u_date.setText(U_date);
//                    u_time.setText(U_time);
//
//                    editButton.setText("情報を更新");
//                    editButton.setTextColor(Color.BLACK);
//
//                    edit_counter = 0;
//                }
//            }
//        });


        mapButton.setOnClickListener(new View.OnClickListener() {       // 地図へ
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InformationActivity.this, MapsActivity.class);
                Intent.putExtra("ID", id);
                Intent.putExtra("DS", ds);
                startActivity(Intent);
            }
        });
        topButton.setOnClickListener(new View.OnClickListener() {       // 地図へ
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InformationActivity.this, MainSearchActivity.class);
                Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent);
            }
        });
    }


    // Bundleへ値を保存
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

//        u_date = (TextView)findViewById(R.id.U_date);
//        String value = u_date.getText().toString();
//        outState.putString("u_date", value);
    }

    // Bundleから値を取り出す
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

//        String value = savedInstanceState.getString("u_date");
//        u_date = (TextView)findViewById(R.id.U_date);
//        u_date.setText(value);
    }


    public float[] getDistance(double x, double y, double x2, double y2) {
        // 結果を格納するための配列を生成
        float[] results = new float[3];

        // 距離計算
        Location.distanceBetween(x, y, x2, y2, results);

        return results;
    }

}