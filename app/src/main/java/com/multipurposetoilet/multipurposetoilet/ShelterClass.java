package com.multipurposetoilet.multipurposetoilet;

/**
 * Created by user on 2017/07/31.
 */
public class ShelterClass {
    public int id;                  // 避難所ID
    public String name;        // 避難所名
    public float[] coordinate;   // 座標{緯度, 経度}
    public String address;    // 施設所在地（住所）
    public String time;        // 利用可能時間
    public String phone;       // 電話番号
    public String pictureTitle; // 写真タイトル
    public int imageView;          // 写真画像
    public String[] detail = new String[InformationActivity.item];         // 詳細{属性}
//    public  String[] state = new String[InformationActivity.item_real];         // リアルタイム入力項目
    public String button;      // ボタンに表示するテキスト
    public int icons = R.drawable.pin;  // リストアイコン ...未使用。現在は ListActivity 内で全て同じアイコンを設定中。
    public int window_image;       // 地図上のウィンドウに表示する画像

    public String[] fa;             // 施設属性
    public String[] accepted;       // 受入対象者
    public String[] meal;           // 食事の提供
    public String[] special;        // 特別食
    public String[] fFixtures;      // 施設として保有する備品
    public String[] eFacility;      // 避難者が利用できる設備
    public String[] eFixtures;      // 避難者が利用できる備品
    public String[] eService;       // 避難者に対応できる行為

}
