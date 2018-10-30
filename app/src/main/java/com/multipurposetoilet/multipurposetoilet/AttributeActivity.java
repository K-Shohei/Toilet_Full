package com.multipurposetoilet.multipurposetoilet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by user on 2017/01/26.
 */


public class AttributeActivity extends FragmentActivity {

    private Globals globals;

    String R1_tokusei, R2_syokuji;
    String[] C2_tokubetsusyoku = new String[8];
    String[] C3_Sbihin = new String[3];
    String[] C4_setsubi = new String[5];
    String[] C5_Hbihin = new String[6];
    String[] C6_koui = new String[8];

    String[][] tokubetsusyoku = new String[20][8];  // 避難所ごとに特別食を格納     8個全部埋まるとは限らない
    String[][] Sbihin = new String[20][3];           // 施設として保有する備品を格納
    String[][] setsubi = new String[20][5];          // 避難者が利用できる設備
    String[][] Hbihin = new String[20][6];           // 避難者が利用できる備品
    String[][] koui = new String[20][8];             // 避難者に対応できる行為

    String[] kibou = {"希望する", "希望しない"};
    String dochirademo = "どちらでもよい";

    int[][] attributeSet = new int[20][32];      // 0,1 を格納 （選択されたテキストと同じなら1、違うなら0、「どちらでもよい」が選択されたら1。「1」の合計が大きいほど優先。）

    Button attribute_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_shelter);

        globals = (Globals)this.getApplication();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 31; j++) {
                attributeSet[i][j] = 0;
            }
        }

        // csvファイルを読み込み
        ArrayList<ArrayList<String>> array= new ArrayList<ArrayList<String>>();
        array = CSVParser.ReadCSV(getApplicationContext(), "避難所データ.csv");

        // 各項目を「、」ごとに分割して格納
        for(int i = 0; i<array.size()-1; i++) {
            StringTokenizer ts9 = new StringTokenizer(array.get(i+1).get(9), "、");     // 「、」を区切り文字として分割
            StringTokenizer ts10 = new StringTokenizer(array.get(i+1).get(10), "、");
            StringTokenizer ts11 = new StringTokenizer(array.get(i+1).get(11), "、");
            StringTokenizer ts12 = new StringTokenizer(array.get(i+1).get(12), "、");
            StringTokenizer ts13 = new StringTokenizer(array.get(i+1).get(13), "、");
            int cnt = 0;
            while (ts9.hasMoreTokens()) {
                tokubetsusyoku[i][cnt] = ts9.nextToken().toString();        // tokubetsusyoku[i][特別食の数]
                cnt++;
            }
            cnt = 0;
            while (ts10.hasMoreTokens()) {
                Sbihin[i][cnt] = ts10.nextToken().toString();
                cnt++;
            }
            cnt = 0;
            while (ts11.hasMoreTokens()) {
                setsubi[i][cnt] = ts11.nextToken().toString();
                cnt++;
            }
            cnt = 0;
            while (ts12.hasMoreTokens()) {
                Hbihin[i][cnt] = ts12.nextToken().toString();
                cnt++;
            }
            cnt = 0;
            while (ts13.hasMoreTokens()) {
                koui[i][cnt] = ts13.nextToken().toString();
                cnt++;
            }
        }

        // 食事の提供を「希望する」の場合、特別食を表示する
        final CheckBox checkBox1 = (CheckBox)findViewById(R.id.C2_1);
        final CheckBox checkBox2 = (CheckBox)findViewById(R.id.C2_2);
        final CheckBox checkBox3 = (CheckBox)findViewById(R.id.C2_3);
        final CheckBox checkBox4 = (CheckBox)findViewById(R.id.C2_4);
        final CheckBox checkBox5 = (CheckBox)findViewById(R.id.C2_5);
        final CheckBox checkBox6 = (CheckBox)findViewById(R.id.C2_6);
        final CheckBox checkBox7 = (CheckBox)findViewById(R.id.C2_7);
        final CheckBox checkBox8 = (CheckBox)findViewById(R.id.C2_8);
        RadioGroup rg = (RadioGroup)findViewById(R.id.R2);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton)findViewById(checkedId);
                if(radioButton.getText().toString().equals(kibou[0])){
                    checkBox1.setEnabled(true);
                    checkBox2.setEnabled(true);
                    checkBox3.setEnabled(true);
                    checkBox4.setEnabled(true);
                    checkBox5.setEnabled(true);
                    checkBox6.setEnabled(true);
                    checkBox7.setEnabled(true);
                    checkBox8.setEnabled(true);
                }else {
                    checkBox1.setChecked(false);
                    checkBox1.setEnabled(false);
                    checkBox2.setChecked(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setChecked(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setChecked(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setChecked(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setChecked(false);
                    checkBox6.setEnabled(false);
                    checkBox7.setChecked(false);
                    checkBox7.setEnabled(false);
                    checkBox8.setChecked(false);
                    checkBox8.setEnabled(false);
                }
            }
        });

        // 「入力完了」クリック時の処理
        attribute_button = (Button)findViewById(R.id.attribute_button);
        attribute_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                globals.AttInit();       // 属性合計値を初期化

                // csvファイルを読み込み
                ArrayList<ArrayList<String>> array= new ArrayList<ArrayList<String>>();
                array = CSVParser.ReadCSV(getApplicationContext(), "避難所データ.csv");

                // 障害者特性
                // ラジオグループ指定→ラジオボタンのテキストを取得
                RadioGroup rg = (RadioGroup)findViewById(R.id.R1);
                int id = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)findViewById(id);
                R1_tokusei = radioButton.getText().toString();

                // csvと比較
                for(int i=0; i<array.size()-1; i++){      // attributeSet[i][0]まで
                    //Log.v("内容" + i, "array.get(i+1).get(7): " + array.get(i+1).get(7));
                    //Log.v("内容" +1, "R1_tokusei : " + R1_tokusei);
                    if(array.get(i+1).get(7).toString().equals(R1_tokusei)){
                        attributeSet[i][0] = 1;
                    }else {
                        attributeSet[i][0] = 0;
                    }
                }

                // 食事の提供
                rg = (RadioGroup)findViewById(R.id.R2);
                id = rg.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(id);
                R2_syokuji = radioButton.getText().toString();
                if(R2_syokuji.equals(kibou[0])){
                    R2_syokuji = "有";
                }else if(R2_syokuji.equals(kibou[1])){
                    R2_syokuji = "無";
                }

                for(int i=0; i<array.size()-1; i++){      // attributeSet[i][1]まで
                    if(R2_syokuji.equals(dochirademo) == false  && array.get(i+1).get(8).equals(R2_syokuji)){
                        attributeSet[i][1] = 1;
                    }else if(R2_syokuji.equals(dochirademo) == false && array.get(i+1).get(8).equals(R2_syokuji) == false){
                        attributeSet[i][1] = 0;
                    }else {
                        attributeSet[i][1] = 1;
                    }
                }

                // チェックボックスを指定→テキストを取得
                // 特別食
                CheckBox checkBox = (CheckBox)findViewById(R.id.C2_1);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[0] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[0] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_2);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[1] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[1] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_3);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[2] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[2] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_4);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[3] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[3] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_5);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[4] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[4] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_6);
                if(checkBox.isChecked() == true) { C2_tokubetsusyoku[5] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[5] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_7);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[6] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[6] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C2_8);
                if(checkBox.isChecked() == true) {
                    C2_tokubetsusyoku[7] = checkBox.getText().toString();
                } else {
                    C2_tokubetsusyoku[7] = "";
                }
                // 特別食ごとにマッチング
                    for (int i=0; i<array.size()-1; i++){       // attributeSet[0][2]～[9]まで
                        for (int j = 0; j<8; j++){
                            if(C2_tokubetsusyoku[j].equals(tokubetsusyoku[i][j])){
                                attributeSet[i][j+2] = 1;
                            } else {
                                attributeSet[i][j+2] = 0;
                            }
                        }
                    }


                // 施設として保有する備品
                checkBox = (CheckBox)findViewById(R.id.C3_1);
                if(checkBox.isChecked() == true) {
                    C3_Sbihin[0] = checkBox.getText().toString();
                } else {
                    C3_Sbihin[0] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C3_2);
                if(checkBox.isChecked() == true) {
                    C3_Sbihin[1] = checkBox.getText().toString();
                } else {
                    C3_Sbihin[1] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C3_3);
                if(checkBox.isChecked() == true) {
                    C3_Sbihin[2] = checkBox.getText().toString();
                } else {
                    C3_Sbihin[2] = "";
                }
                // 備品ごとにマッチング
                for (int i=0; i<array.size()-1; i++){       // attributeSet[i][10]～[12]まで
                    for (int j=0; j<3; j++){
                        if(C3_Sbihin[j].equals(Sbihin[i][j])){
                            attributeSet[i][j+10] = 1;
                        } else {
                            attributeSet[i][j+10] = 0;
                        }
                    }
                }


                // 避難者が利用できる設備
                checkBox = (CheckBox)findViewById(R.id.C4_1);
                if(checkBox.isChecked() == true) {
                    C4_setsubi[0] = checkBox.getText().toString();
                } else {
                    C4_setsubi[0] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C4_2);
                if(checkBox.isChecked() == true) {
                    C4_setsubi[1] = checkBox.getText().toString();
                } else {
                    C4_setsubi[1] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C4_3);
                if(checkBox.isChecked() == true) {
                    C4_setsubi[2] = checkBox.getText().toString();
                } else {
                    C4_setsubi[2] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C4_4);
                if(checkBox.isChecked() == true) {
                    C4_setsubi[3] = checkBox.getText().toString();
                } else {
                    C4_setsubi[3] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C4_5);
                if(checkBox.isChecked() == true) {
                    C4_setsubi[4] = checkBox.getText().toString();
                } else {
                    C4_setsubi[4] = "";
                }
                // 設備ごとにマッチング
                for (int i=0; i<array.size()-1; i++){       // attributeSet[i][13]～[17]まで
                    for (int j=0; j<5; j++){
                        if(C4_setsubi[j].equals(setsubi[i][j])){
                            attributeSet[i][j+13] = 1;
                        } else {
                            attributeSet[i][j+13] = 0;
                        }
                    }
                }


                // 避難者が利用できる備品
                checkBox = (CheckBox)findViewById(R.id.C5_1);
                if(checkBox.isChecked() == true) {
                    C5_Hbihin[0] = checkBox.getText().toString();
                } else {
                    C5_Hbihin[0] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C5_2);
                if(checkBox.isChecked() == true) {
                    C5_Hbihin[1] = checkBox.getText().toString();
                } else {
                    C5_Hbihin[1] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C5_3);
                if(checkBox.isChecked() == true) {
                    C5_Hbihin[2] = checkBox.getText().toString();
                } else {
                    C5_Hbihin[2] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C5_4);
                if(checkBox.isChecked() == true) {
                    C5_Hbihin[3] = checkBox.getText().toString();
                } else {
                    C5_Hbihin[3] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C5_5);
                if(checkBox.isChecked() == true) {
                    C5_Hbihin[4] = checkBox.getText().toString();
                } else {
                    C5_Hbihin[4] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C5_6);
                if(checkBox.isChecked() == true) {
                    C5_Hbihin[5] = checkBox.getText().toString();
                } else {
                    C5_Hbihin[5] = "";
                }
                // 設備ごとにマッチング
                for (int i=0; i<array.size()-1; i++){       // attributeSet[i][18]～[23]まで
                    for (int j=0; j<6; j++){
                        if(C5_Hbihin[j].equals(Hbihin[i][j])){
                            attributeSet[i][j+18] = 1;
                        } else {
                            attributeSet[i][j+18] = 0;
                        }
                    }
                }


                // 避難者に対応できる行為
                checkBox = (CheckBox)findViewById(R.id.C6_1);
                if(checkBox.isChecked() == true) {
                    C6_koui[0] = checkBox.getText().toString();
                } else {
                    C6_koui[0] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_2);
                if(checkBox.isChecked() == true) {
                    C6_koui[1] = checkBox.getText().toString();
                } else {
                    C6_koui[1] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_3);
                if(checkBox.isChecked() == true) {
                    C6_koui[2] = checkBox.getText().toString();
                } else {
                    C6_koui[2] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_4);
                if(checkBox.isChecked() == true) {
                    C6_koui[3] = checkBox.getText().toString();
                } else {
                    C6_koui[3] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_5);
                if(checkBox.isChecked() == true) {
                    C6_koui[4] = checkBox.getText().toString();
                } else {
                    C6_koui[4] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_6);
                if(checkBox.isChecked() == true) {
                    C6_koui[5] = checkBox.getText().toString();
                } else {
                    C6_koui[5] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_7);
                if(checkBox.isChecked() == true) {
                    C6_koui[6] = checkBox.getText().toString();
                } else {
                    C6_koui[6] = "";
                }
                checkBox = (CheckBox)findViewById(R.id.C6_8);
                if(checkBox.isChecked() == true) {
                    C6_koui[7] = checkBox.getText().toString();
                } else {
                    C6_koui[7] = "";
                }

                // 設備ごとにマッチング
                for (int i=0; i<array.size()-1; i++){       // attributeSet[i][24]～[31]まで
                    for (int j=0; j<8; j++){
                        if(C6_koui[j].equals(koui[i][j])){
                            attributeSet[i][j+24] = 1;
                        } else {
                            attributeSet[i][j+24] = 0;
                        }
                    }
                }

                // グローバル変数に属性合計値を格納
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 31; j++) {
                        globals.attSum[i] += attributeSet[i][j];
                    }
                }


////////////////////////////////////// 表示テスト //////////////////////////////////////
//                for (int i = 0; i < 20; i++) {
//                    Log.v("内容" + i, "合計: " + globals.attSum[i]);
//                }

//                for(int i = 0; i<array.size()-1; i++){
//                    //Log.v("内容", "内容" + i + ": " + attributeSet[][]);
//                    for(int j = 0; j<32; j++){
//                        Log.v("内容[" + i + "]", "内容[" + j + "]: " + attributeSet[i][j]);
//                    }
//                }
                ////////////////////////////////////// 表示テスト //////////////////////////////////////



                // 画面遷移
                Intent intent = new Intent(AttributeActivity.this, MainSearchActivity.class);
                //intent.putExtra("attSum", attSum);
                startActivity(intent);

            }

        });


    }

}



