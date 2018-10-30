package com.multipurposetoilet.multipurposetoilet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Double2;
import android.renderscript.Float2;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Created by user on 2017/09/29.
 */
public class InputFormActivity extends FragmentActivity {

    public static int cnt = 0;
    EditText name, lon, lat, address, phone, time, naisou_title, att_0, att_1, att_2, att_3, att_4;
//    EditText[]  state = new EditText[InformationActivity.item_real];
    Button gaikan_button, naisou_button, input_button;
    ImageView gaikan_image;
    ImageView naisou_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_form);

        name = (EditText)findViewById(R.id.name);
        lon = (EditText)findViewById(R.id.lon);
        lat = (EditText)findViewById(R.id.lat);
        address = (EditText)findViewById(R.id.address);
        phone = (EditText)findViewById(R.id.phone);
        time = (EditText)findViewById(R.id.time);
        naisou_title = (EditText)findViewById(R.id.naisou_title);
        att_0 = (EditText)findViewById(R.id.att_0);
        att_1 = (EditText)findViewById(R.id.att_1);
        att_2 = (EditText)findViewById(R.id.att_2);
        att_3 = (EditText)findViewById(R.id.att_3);
        att_4 = (EditText)findViewById(R.id.att_4);
//        state[0] = (EditText)findViewById(R.id.state_0);
//        state[1] = (EditText)findViewById(R.id.state_1);
//        state[2] = (EditText)findViewById(R.id.state_2);
//        state[3] = (EditText)findViewById(R.id.state_3);

        gaikan_image = (ImageView)findViewById(R.id.gaikan_image);
        naisou_image = (ImageView)findViewById(R.id.naisou_image);

        gaikan_button = (Button)findViewById(R.id.gaikan_button);
        naisou_button = (Button)findViewById(R.id.naisou_button);
        input_button = (Button)findViewById(R.id.input_button);

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // エディットテキストのテキスト、およびイメージ画像を取得
                //String Name = name.getText().toString();
                //Double D_lon = Double.parseDouble(lon.getText().toString());
                //Double D_lat = Double.parseDouble(lat.getText().toString());
                //String Address = address.getText().toString();
                //String Phone = phone.getText().toString();
                //String Time = time.getText().toString();
                //String NaisouTitel = naisou_title.getText().toString();
                //String Att1 = att_1.getText().toString();           // for文にしたい
                //String Att2 = att_2.getText().toString();
                //String Att3 = att_3.getText().toString();
                //String Att4 = att_4.getText().toString();

                ImageView Gaikan = gaikan_image;
                ImageView Naisou = naisou_image;

                // 取得したテキストと画像をデータベースに格納
                for(int i=0; i<=subDB.Number; i++){
                    ShelterClass SL = subDB.DB(i);
                    if(SL.name == "" && cnt == 0){
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(InputFormActivity.this);

                        sp.edit().putString("SaveName", name.getText().toString()).commit();        // + i でいける？

                        sp.edit().putFloat("SaveLongitude", Float.valueOf(lon.getText().toString())).commit();

                        sp.edit().putFloat("SaveLatitude", Float.valueOf(lat.getText().toString())).commit();

                        sp.edit().putString("SaveAddress", address.getText().toString()).commit();

                        sp.edit().putString("SavePhone", phone.getText().toString()).commit();

                        sp.edit().putString("SaveTime", time.getText().toString()).commit();

                        sp.edit().putString("SavePictureTitle", naisou_title.getText().toString()).commit();
                        //SL.detail[0] = Att1;                        // for文にしたい
                        sp.edit().putString("SaveAtt0", att_0.getText().toString()).commit();
                        sp.edit().putString("SaveAtt1", att_1.getText().toString()).commit();
                        //SL.detail[1] = Att2;
                        sp.edit().putString("SaveAtt2", att_2.getText().toString()).commit();
                        //SL.detail[2] = Att3;
                        sp.edit().putString("SaveAtt3", att_3.getText().toString()).commit();
                        //SL.detail[3] = Att4;
                        sp.edit().putString("SaveAtt4", att_4.getText().toString()).commit();

//                        sp.edit().putString("SaveState0", state[0].getText().toString()).commit();
//                        sp.edit().putString("SaveState1", state[1].getText().toString()).commit();
//                        sp.edit().putString("SaveState2", state[2].getText().toString()).commit();
//                        sp.edit().putString("SaveState3", state[3].getText().toString()).commit();
                       // SL.window_image =  BitmapFactory.decodeResource(getResources(), Gaikan);      // 画像の読み込み未完成


                        InputSaveClass.SaveName = sp.getString("SaveName", null);
                        InputSaveClass.SaveLongitude = sp.getFloat("SaveLongitude", 0);
                        InputSaveClass.SaveLatitude = sp.getFloat("SaveLatitude", 0);
                        InputSaveClass.SaveAddress = sp.getString("SaveAddress", null);
                        InputSaveClass.SavePhone = sp.getString("SavePhone", null);
                        InputSaveClass.SaveTime = sp.getString("SaveTime", null);
                        InputSaveClass.SavePictureTitle = sp.getString("SavePictureTitle", null);
                        InputSaveClass.SaveAtt[0] = sp.getString("SaveAtt0", null);
                        InputSaveClass.SaveAtt[1] = sp.getString("SaveAtt1", null);
                        InputSaveClass.SaveAtt[2] = sp.getString("SaveAtt2", null);
                        InputSaveClass.SaveAtt[3] = sp.getString("SaveAtt3", null);
                        InputSaveClass.SaveAtt[4] = sp.getString("SaveAtt4", null);
//                        InputSaveClass.SaveState[0] = sp.getString("SaveState0", null);
//                        InputSaveClass.SaveState[1] = sp.getString("SaveState1", null);
//                        InputSaveClass.SaveState[2] = sp.getString("SaveState2", null);
//                        InputSaveClass.SaveState[3] = sp.getString("SaveState3", null);





                        cnt++;

                        Intent Intent = new Intent(InputFormActivity.this, MainSearchActivity.class);
                        startActivity(Intent);

                        break;

                    }
                    else if(i == subDB.Number && cnt == 0){
                        Toast.makeText(InputFormActivity.this, "データベースに空きがありません", Toast.LENGTH_SHORT).show();
                    }
//                    else if(cnt == 1){
//                        Intent Intent = new Intent(InputFormActivity.this, MainSearchActivity.class);
//                        startActivity(Intent);
//                    }
                }
            }
        });
    }
}
