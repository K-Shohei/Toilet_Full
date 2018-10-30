package com.multipurposetoilet.multipurposetoilet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.logging.Handler;


public class ListActivity extends FragmentActivity {
    
    Globals globals;
    int cnt = 0;


    // データを準備
    ArrayList<User> users;
    // adapter -ArrayAdapter -UserAdapter
    UserAdapter adapter;
    ListView myListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        android.app.ActionBar actionBar = getActionBar();
        globals = (Globals)this.getApplication();

        switch (item.getItemId()){
            case android.R.id.home: // 戻るボタン
                finish();
                return true;
            case R.id.Button1:  // 近い順
                Collections.sort(users, itemComparator_distance);
                actionBar.setSubtitle("（近い順）");
                globals.searchCnt = 1;
                adapter.notifyDataSetChanged();
                break;
            case R.id.Button2:  // 属性マッチング
                Collections.sort(users, itemComparator_attribute);
                actionBar.setSubtitle("（属性マッチング）");
                globals.searchCnt = 1;
                adapter.notifyDataSetChanged();
                break;
            case R.id.Button3:  // ホーム
                Intent intent_home = new Intent(this, MainSearchActivity.class);
                intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_home);
                break;
            case R.id.Button4:  // ユーザー設定
                Intent intent_att = new Intent(this, AttributeActivity.class);
                intent_att.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_att);
                break;
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        globals = (Globals)this.getApplication();

        Intent intent = getIntent();
        double myLongitude = intent.getDoubleExtra("myLongitude", 0);
        double myLatitude = intent.getDoubleExtra("myLatitude", 0);

        // アクションバー設定

        android.app.ActionBar actionBar = getActionBar();
        int color = R.color.common_action_bar_splitter;
        Drawable backgroundDrawable = getApplicationContext().getResources().getDrawable(color);
        actionBar.setBackgroundDrawable(backgroundDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("検索結果");
//        if(globals.searchCnt == 0){
//            actionBar.setSubtitle("                                                                      （近い順）");
//        } else if (globals.searchCnt == 1){
//            actionBar.setSubtitle("                                                              （属性マッチング）");
//        }
        actionBar.setSubtitle("（近い順）");




        // CSVファイルの読み込み
        ArrayList<ArrayList<String>> array= new ArrayList<ArrayList<String>>();
        array = CSVParser.ReadCSV(getApplicationContext(), "避難所データ.csv");

        System.out.println("「println」メソッドは出力後改行します。array.size = " + array.size());

        int ID[] = new int[array.size()];              // 避難所idを格納
        String names[] = new String[array.size()];      // 避難所名を格納
        String Button[] = new String [array.size()];    //  ボタンに表示するテキストを格納
        int icons[] = new int[array.size()];           // アイコンを格納
        String miniDistance[] = new String[array.size()];   // アイコン下に表示する避難所までの距離
        float coordinate[][] = new float[array.size()][2];    // 座標を格納
        float ds[] = new float[array.size()];           //２点間の距離

        for(int i = 1; i < array.size(); i++){
            ID[i] = Integer.parseInt(array.get(i).get(0));
            names[i] = array.get(i).get(1);

            icons[i] = R.drawable.pin;
            coordinate[i][0] = Float.parseFloat(array.get(i).get(3));
            coordinate[i][1] = Float.parseFloat(array.get(i).get(4));
            cnt++;
            float[] distance = getDistance(myLatitude, myLongitude, coordinate[i][0], coordinate[i][1]);
            ds[i] = distance[0];
//            if(globals.searchCnt == 1){
//                Button[i] = "(距離: " + (int)distance[0] + ", 属性値: " + globals.attSum[ID[i]-1] +")";
//            }else {
                Button[i] = "詳　細";
          //  }
            miniDistance[i] = String.format("%.1f km ", (double)distance[0]/1000);

        }

        users = new ArrayList<>();
        adapter = new UserAdapter(this, 0, users);
        myListView = (ListView) findViewById(R.id.myListView);

        for (int i = 1; i < cnt+1; i++){
                User user = new User();
                user.setIcon(BitmapFactory.decodeResource(getResources(), icons[i]));
                user.setID(ID[i]);
                user.setName(names[i]);
                user.setButton(Button[i]);
                user.setCd(coordinate[i]);
                user.setds(ds[i]);
//                user.setAtt(globals.attSum[ID[i]-1]);zz
                user.setMds(miniDistance[i]);
                users.add(user);
        }


        // ListViewに表示
        myListView.setEmptyView(findViewById(R.id.emptyView));  // データがない場合に空のビューを表示
        myListView.setAdapter(adapter);                          // データをセット

//        if(globals.searchCnt == 0){            // 検索オプションに従ってソート
//            Collections.sort(users, itemComparator_distance);
//        } else if (globals.searchCnt == 1){
//            Collections.sort(users, itemComparator_attribute);
//        }
        Collections.sort(users, itemComparator_distance);

        adapter.notifyDataSetChanged();
        myListView.invalidateViews();

        // ClickEvent
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(

                    AdapterView<?> adapterView,
                    View view, // タップされたView
                    int position,    // 何番目？
                    long l    // View id
            ) {

                User user = (User)myListView.getItemAtPosition(position);
                int selectedId = user.getID();
                float selectedDS = user.getds();
//              float[] selectedCd = user.getCd();
//              String selectedName = user.getName();
                getMap(selectedId, selectedDS);

                //GoogleMapアプリ用
//              getMap(selectedId, selectedDS, selectedCd, selectedName);

            }
        });
    }


    public class UserAdapter extends ArrayAdapter<User>{

        private LayoutInflater layoutInflater;
        public UserAdapter(Context c, int id, ArrayList<User> users){
            super(c, id, users);
            this.layoutInflater = (LayoutInflater) c.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = layoutInflater.inflate(
                        R.layout.list_item,
                        parent,
                        false
                );
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.button = (TextView) convertView.findViewById(R.id.dButton);
                holder.miniDistance = (TextView)convertView.findViewById(R.id.distance);
                convertView.setTag(holder);

            } else{
                holder = (ViewHolder) convertView.getTag();
            }

            Button btn = (Button)convertView.findViewById(R.id.dButton);// 詳細ボタン追加部分
            btn.setTag(pos);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListActivity.this, InformationActivity.class);
                    User user = (User)myListView.getItemAtPosition(pos);
                    String selectedName = user.getName();
                    Integer o = user.getID();
                    intent.putExtra("Detail", o);  // IDを渡す
                    startActivity(intent);
                }
            });

            User user = (User) getItem(pos);

            holder.icon.setImageBitmap(user.getIcon());
            holder.name.setText(user.getName());
            holder.button.setText(user.getDETAIL());
            holder.miniDistance.setText(user.getMds());

            return convertView;
        }

    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView button;
        TextView miniDistance;
    }

    public class User {
        private Bitmap icon;
        private int id;
        private String name;
        private String button;
        private float[] cd;
        private float ds;
        private int att;
        private String mds;

        public Bitmap getIcon() { return icon; }

        public void setIcon(Bitmap icon) { this.icon = icon; }

        public float[] getCd() { return cd; }

        public void setCd(float[] cd) {
            this.cd = cd;
        }

        public int getID() { return id; }

        public void setID(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDETAIL() {
            return button;
        }

        public void setButton(String Button) {
            this.button = Button;
        }

        public float getds() {return ds;}

        public void setds(float ds){this.ds = ds;}

        public int getAtt() { return att; }

        public void setAtt(int att) {this.att = att;}

        public String getMds() {
            return mds;
        }

        public void setMds(String mds) {
            this.mds = mds;
        }

    }

    public void getMap_old(String sname, String sloc, double[] scoordinate){      // 引数を変化
        // インテントにアクティビティをセット
        Intent MapsIntent = new Intent(this, MapsActivity.class);
        // インテントに値をセット
        MapsIntent.putExtra("Name", sname);
        MapsIntent.putExtra("Loc", sloc);
        MapsIntent.putExtra("Coordinate" , scoordinate);
        // 次の画面へ
        startActivity(MapsIntent);
    }

    public void getMap(int ID, float DS){
//  public void getMap(int ID, float DS, float[] Cd, String name){
        Intent MapsIntent = new Intent(this, MapsActivity.class);
        MapsIntent.putExtra("ID", ID);
        MapsIntent.putExtra("DS", DS);
        startActivity(MapsIntent);


        //Google Mapアプリに遷移
//        Intent i = new Intent();
//        i.setAction(Intent.ACTION_VIEW);
//        i.setClassName("com.google.android.apps.maps", "com.google.android.maps.driveabout.app.NavigationActivity");
//        Uri uri = Uri.parse("google.navigation:///?ll="+ Cd[0] + "," + Cd[1] + "&q=" + name + "mode=W");
//        i.setData(uri);
//        startActivity(i);
    }


    public void goDetailedInformation(View view){            // 詳細の画面遷移
        //Toast.makeText(this, "ここは、" + DetailInformation , Toast.LENGTH_SHORT).show();

        // 次の画面へ
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    public float[] getDistance(double x, double y, double x2, double y2) {
        // 結果を格納するための配列を生成
        float[] results = new float[3];

        // 距離計算
        Location.distanceBetween(x, y, x2, y2, results);

        return results;
    }


    // comparator（距離検索）を定義
    Comparator<User> itemComparator_distance = new Comparator<User>() {
        @Override
        public int compare(User lhs, User rhs) {
            return Integer.valueOf((int)(lhs.getds() * 10000)).compareTo(Integer.valueOf((int)((rhs.getds() * 10000))));
        }
    };

    // comparator（属性検索）を定義
    Comparator<User> itemComparator_attribute = new Comparator<User>() {
        @Override
        public int compare(User lhs, User rhs) {
            return -Integer.valueOf(lhs.getAtt()).compareTo(rhs.getAtt());
        }
    };

}
