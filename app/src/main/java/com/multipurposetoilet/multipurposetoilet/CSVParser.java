package com.multipurposetoilet.multipurposetoilet;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by user on 2017/10/23.
 */

public class CSVParser {

    private static final String DEFAULT_ENCORDING = "UTF-8";

    static public ArrayList ReadCSV(Context csContext, String strCsvFileName)
    {

        InputStream csInputStream = null;
        BufferedReader csBufReader = null;

        ArrayList<ArrayList<String>> list1 = new ArrayList<ArrayList<String>>();


        try
        {
            AssetManager csAsset = csContext.getResources().getAssets();
            csInputStream = csAsset.open(strCsvFileName);
            csBufReader = new BufferedReader(new InputStreamReader(csInputStream));

            // タイトル処理
            // タイトルは読み捨てる
            //csBufReader.readLine();

            // データ処理
            {
                // 最終行まで読み込む
                int i = 0;
                String line;
                while ((line = csBufReader.readLine()) != null)
                {

                    ArrayList<String> list2 = new ArrayList<String>();
                    // 1行をデータの要素に分割
                    StringTokenizer csStringToken = new StringTokenizer(line, ",");

                    //
                    int j = 0;
                    while (csStringToken.hasMoreTokens()){
                        list2.add(csStringToken.nextToken());
                        j++;
                    }
                    i++;
                    list1.add(list2);
                }
                csBufReader.close();
            }

//            for(int i = 0; i<list1.size(); i++){
//                Log.v("内容", "内容" + i + ": " + list1.get(i));
//                for(int j = 0; j<list2.size(); j++){
//                    Log.v("内容", "内容" + j + ": " + list2.get(j));
//                }
//        }


        } catch (FileNotFoundException e) {
            // Fileオブジェクト生成時の例外捕捉
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedReaderオブジェクトのクローズ時の例外捕捉
            e.printStackTrace();
        }

        return list1;
    }
}
