package com.multipurposetoilet.multipurposetoilet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by user on 2017/01/25.
 */
public class MatchingActivity {

    public static void MatchingAttribute(int[] userAttribute, int[][] toiletAttribute){         // ユーザー属性とトイレ属性の配列を引数とする

        int xorMatch[][] = new int[toiletAttribute.length][userAttribute.length];    // XOR によるマッチング結果

        for(int i = 0; i < toiletAttribute.length; i++) {                     // トイレ属性の数　くり返し
            for(int j = 0; j < userAttribute.length; j++) {                   // ユーザー属性の長さ　くり返し
                if (userAttribute.length == toiletAttribute[i].length){       // ユーザー属性の長さとi番目のトイレ属性の長さが同じ場合
                    xorMatch[i][j] = userAttribute[j] ^ toiletAttribute[i][j];  // マッチング結果を格納
                }
            }
        }

        for(int i = 0; i < toiletAttribute.length; i++) {             // トイレ属性の数　くり返し
            Log.d(i + "Attribute1", "トイレ" + (i+1) + "とのマッチング結果：");

            for (int j = 0; j < userAttribute.length; j++) {              // ユーザー属性の長さ　くり返し
                Log.d("Attribute2", "属性「" + j + "」：　" + xorMatch[i][j]);
            }

        }

    }

}
