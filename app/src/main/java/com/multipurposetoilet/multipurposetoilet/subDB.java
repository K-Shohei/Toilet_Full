package com.multipurposetoilet.multipurposetoilet;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 2017/07/31.
 */
public class subDB {

    public static int Number = 11;   // 空データも含めたデータの数    これ以上の数登録する場合はこの数字を増やすこと




    public static ShelterClass DB(int element){

        ShelterClass[] SL = new ShelterClass[Number];
        for (int i=0; i<SL.length; i++) SL[i] = new ShelterClass(); // 初期化

        //////////////////////////////////////////////////// *** 以下擬似データベース *** ////////////////////////////////////////////////////

        switch (element) {

            case 0:
                SL[element].id = element;
                SL[element].name = "山口大学工学部";
                SL[element].coordinate = new float[]{(float)33.955911, (float)131.272348};
                SL[element].address = "山口県宇部市常盤台2丁目16−1";
                SL[element].time = "月曜日～金曜日　10:00～20:00";
                SL[element].phone = "0836-85-9005";
                SL[element].pictureTitle = "総合研究棟2号館1F";
                SL[element].imageView = R.drawable.yamadai_to_souken1;
                SL[element].detail = new String[]{"詳細１", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.yamadai_gaikan;

                break;

            case 1:
                SL[element].id = element;
                SL[element].name = "有限会社リベルタス興産";
                SL[element].coordinate = new float[]{(float)33.933855, (float)131.256127};
                SL[element].address = "山口県宇部市東見初町526−7";
                SL[element].time = "月曜日～金曜日　10:00～17:00";
                SL[element].phone = "0836-35-78785";
                SL[element].pictureTitle = "社内トイレ";
                SL[element].imageView = R.drawable.libe_to;
                SL[element].detail = new String[]{"詳細１", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.libe_gaikan;

                break;

            case 2:
                SL[element].id = element;
                SL[element].name = "宇部市総合福祉会館";
                SL[element].coordinate = new float[]{(float)33.953069, (float)131.253237};
                SL[element].address = "宇部市琴芝町二丁目4番20号";
                SL[element].time = "月曜日～金曜日　10:00～18:00";
                SL[element].phone = "0836-33-3156";
                SL[element].pictureTitle = "多世代ふれあいセンター2F①";
                SL[element].imageView = R.drawable.fukushi_to1;
                SL[element].detail = new String[]{"詳細１", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.fukushi_gaikan;

                break;

            case 3:
                SL[element].id = element;
                SL[element].name = "障害者支援施設　日の山のぞみ苑";
                SL[element].coordinate = new float[]{(float)33.999406, (float)131.352224};
                SL[element].address = "山口県宇部市大字東岐波字横尾新開1451−1";
                SL[element].time = "月曜日～金曜日　10:00～17:00";
                SL[element].phone = "0836-59-2411";
                SL[element].pictureTitle = "食堂";
                SL[element].imageView = R.drawable.id1;
                SL[element].detail = new String[]{"障害者施設", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.id1;

                break;

            case 4:
                SL[element].id = element;
                SL[element].name = "特別養護老人ホーム　センチュリー２１";
                SL[element].coordinate = new float[]{(float)34.060080, (float)131.333002};
                SL[element].address = "宇部市大字山中126番地1";
                SL[element].time = "月曜日～金曜日　10:00～20:00";
                SL[element].phone = "0836-62-1021";
                SL[element].pictureTitle = "居室・設備";
                SL[element].imageView = R.drawable.pin;
                SL[element].detail = new String[]{"高齢者施設", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.pin;

                break;

            case 5:
                SL[element].id = element;
                SL[element].name = "特別養護老人ホーム　宇部あかり園";
                SL[element].coordinate = new float[]{(float)33.966864, (float)131.301488};
                SL[element].address = "宇部市大字西岐波229番地105";
                SL[element].time = "月曜日～金曜日　10:00～20:00";
                SL[element].phone = "0836-51-1616";
                SL[element].pictureTitle = "施設の様子";
                SL[element].imageView = R.drawable.id1;
                SL[element].detail = new String[]{"高齢者施設", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.id1;

                break;

            case 6:
                SL[element].id = element;
                SL[element].name = "障害者支援施設　セルプ南風";
                SL[element].coordinate = new float[]{(float)33.981840, (float)131.297148};
                SL[element].address = "宇部市あすとぴあ2町目2-15";
                SL[element].time = "月曜日～金曜日・第1、第3土曜日　8：30～17：00";
                SL[element].phone = "0836-43-6211";
                SL[element].pictureTitle = "職場の様子";
                SL[element].imageView = R.drawable.id1;
                SL[element].detail = new String[]{"障害者施設", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.id1;

                break;

            case 7:
                SL[element].id = element;
                SL[element].name = "特別養護老人ホーム 日の山園";
                SL[element].coordinate = new float[]{(float)33.999185, (float)131.352123};
                SL[element].address = "宇部市大字東岐波字道田223番地";
                SL[element].time = "月曜日～金曜日　10:00～20:00";
                SL[element].phone = "0836-58-2202";
                SL[element].pictureTitle = "食堂";
                SL[element].imageView = R.drawable.id1;
                SL[element].detail = new String[]{"高齢者施設", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.id1;

                break;

            case 8:
                SL[element].id = element;
                SL[element].name = InputSaveClass.SaveName;
                SL[element].coordinate = new float[]{InputSaveClass.SaveLongitude, InputSaveClass.SaveLatitude};
                SL[element].address = InputSaveClass.SaveAddress;
                SL[element].time = InputSaveClass.SaveTime;
                SL[element].phone = InputSaveClass.SavePhone;
                SL[element].pictureTitle = InputSaveClass.SavePictureTitle;
                SL[element].imageView = R.drawable.id1;
                SL[element].detail =
                        new String[]{InputSaveClass.SaveAtt[0], InputSaveClass.SaveAtt[1], InputSaveClass.SaveAtt[2], InputSaveClass.SaveAtt[3], InputSaveClass.SaveAtt[4]};
//                SL[element].state =
//                        new String[]{InputSaveClass.SaveState[0], InputSaveClass.SaveState[1], InputSaveClass.SaveState[2], InputSaveClass.SaveState[3]};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.id1;

                break;


            case 9:
                SL[element].id = element;
                SL[element].name = "";
                SL[element].coordinate = new float[]{(float)33.955911, (float)131.272348};
                SL[element].address = "";
                SL[element].time = "月曜日～金曜日　10:00～20:00";
                SL[element].phone = "0836-85-9005";
                SL[element].pictureTitle = "総合研究棟2号館1F";
                SL[element].imageView = R.drawable.yamadai_to_souken1;
                SL[element].detail = new String[]{"詳細１", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.yamadai_gaikan;

                break;

            case 10:
                SL[element].id = element;
                SL[element].name = "";
                SL[element].coordinate = new float[]{(float)33.955911, (float)131.272348};
                SL[element].address = "";
                SL[element].time = "月曜日～金曜日　10:00～20:00";
                SL[element].phone = "0836-85-9005";
                SL[element].pictureTitle = "総合研究棟2号館1F";
                SL[element].imageView = R.drawable.yamadai_to_souken1;
                SL[element].detail = new String[]{"詳細１", "詳細２", "詳細３","詳細４","詳細５"};
//                SL[element].state = new String[]{"状態１", "状態２", "状態３","状態４","状態５"};
                SL[element].button = "詳　細";
                SL[element].window_image = R.drawable.yamadai_gaikan;

                break;

            default:
                break;
        }

        return SL[element];
    }

}
