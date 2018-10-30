package com.multipurposetoilet.multipurposetoilet;

/**
 * Created by user on 2017/10/23.
 */

public class InputData {
    public static int[] id;                  // 避難所ID
    public static String[] name;        // 避難所名
    public static float[][] coordinate;   // 座標{緯度, 経度}
    public static String[] address;    // 施設所在地（住所）
    public static String[] time;        // 利用可能時間
    public static String[] phone;       // 電話番号
    public static String[] pictureTitle; // 写真タイトル
    public static int[] imageView;          // 写真画像
    public static String[] detail = new String[InformationActivity.item];         // 詳細{属性}
//    public static String[] state = new String[InformationActivity.item_real];         // リアルタイム入力項目
    public static String[] button;      // ボタンに表示するテキスト
    public static int icons = R.drawable.pin;  // リストアイコン ...未使用。現在は ListActivity 内で全て同じアイコンを設定中。
    public static int[] window_image;       // 地図上のウィンドウに表示する画像

    public static String[] fa;             // 施設属性
    public static String[] accepted;       // 受入対象者
    public static String[] meal;           // 食事の提供
    public static String[] special;        // 特別食
    public static String[] fFixtures;      // 施設として保有する備品
    public static String[] eFacility;      // 避難者が利用できる設備
    public static String[] eFixtures;      // 避難者が利用できる備品
    public static String[] eService;       // 避難者に対応できる行為
}
