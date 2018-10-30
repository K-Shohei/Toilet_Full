package com.multipurposetoilet.multipurposetoilet;

        import android.graphics.Bitmap;

/**
 * Created by user on 2017/09/24.
 */
public class DetailClass {

    public String title = "";        // 避難所名
    public String address = "";      // 住所
    public String time = "";         // 利用可能時間
    public String phone = "";        // 電話番号
    public String pictureTitle = ""; // 写真タイトル
    public Bitmap imageView;           // = R.drawable.yamadai_to_souken1; 写真
    public String[] details = new String[InformationActivity.item];         // 詳細{属性} (手動、レバー式、○○ｘ○○ｍ、可動式、安定)
    // / public String[] state = new String[InformationActivity.item_real];         // リアルタイム入力項目

}
