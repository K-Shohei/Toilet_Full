package com.multipurposetoilet.multipurposetoilet;

import android.graphics.Bitmap;

/**
 * Created by user on 2017/10/13.
 */

public class InputSaveClass {

    public static String SaveName = "";        // 入力避難所名
    public static float SaveLongitude;        // 入力緯度
    public static float SaveLatitude;         // 入力経度
    public static String SaveAddress = "";     // 入力住所
    public static String SavePhone = "";       // 入力電話番号
    public static String SaveTime = "";         // 入力利用可能時間
    public static String SavePictureTitle = ""; // 入力写真タイトル
    public static String[] SaveAtt = new String[InformationActivity.item];         // 入力詳細{属性} (避難所の種類、設備、食料など)
//    public static String[] SaveState = new String[InformationActivity.item_real];   // リアルタイム入力項目（現在の収容人数など）

    public Bitmap SaveImageView;           // = R.drawable.yamadai_to_souken1; 入力写真　未完成

}
