package com.multipurposetoilet.multipurposetoilet;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements LocationListener{

//tesr testtest

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private String DI;  // 詳細情報を見るための変数 = selectedName
    private String image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsFlg = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("GPS Enabled", gpsFlg ? "OK" : "NG");

        LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        //LocationManagerの取得
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        // GPSではなくネットワークから現在地の情報を取得
        Location myLocate = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // 前の画面で指定した値を取り出す
        Intent intent = getIntent();
        final int selectedID = intent.getIntExtra("ID", 0);        // ID  データがない場合0が返る
        final float selectedDS = intent.getFloatExtra("DS", 0);

        mMap.getUiSettings().setZoomControlsEnabled(true);      // ズームボタンを配置


        if(myLocate != null){ // 現在地取得に成功

            // 緯度の取得
            double myLatitude =  myLocate.getLatitude();
            //経度の取得
            double myLongitude = myLocate.getLongitude();
            // 緯度・経度を指定 ズームレベルを指定
            CameraUpdate cUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), ZoomLevel(selectedDS));
            //Toast.makeText(this, "緯度: " + latitude + "経度: " + longitude, Toast.LENGTH_SHORT).show();
            // 現在地まで移動
            mMap.moveCamera(cUpdate);
            // 現在地ボタン
            mMap.setMyLocationEnabled(true);
            // 現在地に星形のアイコンを配置
            //setIcon(myLatitude, myLongitude);




            // 指定の座標にマーカーを設置
            //ShelterClass SL = subDB.DB(selectedID);  // データベースに接続
            ArrayList<ArrayList<String>> array= new ArrayList<ArrayList<String>>();     // CSVファイルの読み込み
            array = CSVParser.ReadCSV(getApplicationContext(), "避難所データ.csv");
            DI = array.get(selectedID).get(1);   //selectedName;
            image_path = (array.get(selectedID).get(47));
            System.out.println("パス名ですよう＝ " + image_path);
            //final int window_image = SL.window_image;
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(array.get(selectedID).get(3).toString()), Double.parseDouble(array.get(selectedID).get(4).toString()))).title(DI));   // LatLng(緯度, 経度).タイトル　を設定

            // マップ上にウィンドウを表示
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {

                    View view = getLayoutInflater().inflate(R.layout.info_window, null);
                    // タイトル設定
                    TextView title = (TextView) view.findViewById(R.id.info_title);
                    title.setText(marker.getTitle());
                    // 画像設定
                    ImageView img = (ImageView) view.findViewById(R.id.info_image);
//                    img.setImageBitmap(BitmapFactory.decodeResource(getResources(), window_image));
                    img.setImageResource(R.drawable.gaikan_alternative3);
//
//                    int resId = getResources().getIdentifier("info_image", "id", getPackageName());
//                    ImageView iv = (ImageView)findViewById(resId);
//                    int imageId = getResources().getIdentifier("id" + selectedID + "_gaikan", "drawable", getPackageName());
//                    img.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageId));

//                    ImageView imageView = (ImageView)findViewById(R.id.info_image);
//                    String stringUri = image_path;
//                    Uri uri = Uri.parse(stringUri);
//                    Uri.Builder builder = uri.buildUpon();
//                    AsyncTaskHttpRequest task = new AsyncTaskHttpRequest(imageView);
//                    task.execute(builder);




                    // クリック時処理
                    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                    mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            goDetailedInformation(selectedID);
                        }
                    });
                    return view;
                }

                @Override
                public View getInfoContents(Marker marker) {
//                    boolean isRedraw = (marker != null && marker.isInfoWindowShown());
//                    if(isRedraw) marker.showInfoWindow();
                    return null;
                }
            });

        }else // 現在地取得に失敗
            Toast.makeText(this, "現在地を取得できません", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void setIcon(double latitude, double longitude){

        // マップに貼り付ける BitmapDescriptor生成
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.hoshi);

        // 貼り付設定
        GroundOverlayOptions overlayOptions = new GroundOverlayOptions();
        overlayOptions.image(descriptor);

        //　public GroundOverlayOptions anchor (float u, float v)
        // (0,0):top-left, (0,1):bottom-left, (1,0):top-right, (1,1):bottom-right
        overlayOptions.anchor(0.5f, 0.5f);

        // 張り付け画像の大きさ メートル単位
        // public GroundOverlayOptions	position(LatLng location, float width, float height)
        overlayOptions.position(new LatLng(latitude, longitude), 300f, 300f);

        // マップに貼り付け・アルファを設定
        GroundOverlay overlay = mMap.addGroundOverlay(overlayOptions);
        // ズーム
        //zoomMap(latitude, longitude);

        // 透明度
        //overlay.setTransparency(0.0F);

    }


    public void goDetailedInformation(int DetailInformation){
        //Toast.makeText(this, "ここは、" + DetailInformation , Toast.LENGTH_SHORT).show();

        // インテントにアクティビティをセット
        Intent intent = new Intent(this, InformationActivity.class);

        // インテントに値をセット
        intent.putExtra("Detail", DetailInformation);

        // 次の画面へ
        startActivity(intent);
    }
    public int ZoomLevel(float ds) {
        if (ds <= 5) {
            return 21;
        } else if (ds <= 10) {
            return 20;
        } else if (ds <= 20) {
            return 19;
        } else if (ds <= 50) {
            return 18;
        } else if (ds <= 100) {
            return 17;
        } else if (ds <= 200) {
            return 16;
        } else if (ds <= 400) {
            return 16;
        } else if (ds <= 500) {
            return 15;
        } else if (ds <= 1000) {
            return 15;
        } else if (ds <= 2000) {
            return 14;
        } else if (ds <= 5000) {
            return 13;
        } else if (ds <= 10000) {
            return 12;
        } else if (ds <= 20000) {
            return 11;
        } else if (ds <= 50000) {
            return 11;
        } else if (ds <= 100000) {
            return 11;
        } else if (ds <= 200000) {
            return 10;
        } else if (ds <= 500000) {
            return 9;
        } else if (ds <= 1000000) {
            return 8;
        } else if (ds <= 200000) {
            return 7;
        } else if (ds <= 400000) {
            return 6;
        } else  {
            return 1;
        }
    }


}
