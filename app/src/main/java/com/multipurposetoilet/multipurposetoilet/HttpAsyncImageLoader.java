package com.multipurposetoilet.multipurposetoilet;

/**
 * Created by android1 on 2018/01/24.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpAsyncImageLoader extends AsyncTaskLoader<Bitmap>{

    private String mUrl;

    public HttpAsyncImageLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public Bitmap loadInBackground() {

        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            // 404はここでキャッチする
            e.printStackTrace();
        }

        return null;
    }

}
