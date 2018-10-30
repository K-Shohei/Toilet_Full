package com.multipurposetoilet.multipurposetoilet;

import android.app.Application;

/**
 * Created by user on 2017/11/06.
 */

public class Globals extends Application {

    // グローバル変数を定義
    int[] attSum = new int[20];
    int searchCnt = 0;

    // 属性合計値を初期化するメソッド
    public void AttInit(){
        for (int i = 0; i < 20; i++) {
            attSum[i] = 0;
        }
    }
}
