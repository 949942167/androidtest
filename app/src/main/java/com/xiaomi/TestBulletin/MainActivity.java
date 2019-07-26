package com.xiaomi.TestBulletin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    private TextView tv1;//item.xml里的TextView：Textviewname
    private TextView tv2;//item.xml里的TextView：Textviewage
    private Button bt;//activity_main.xml里的Button
    private ListView lv;//activity_main.xml里的ListView
    private BaseAdapter adapter;//要实现的类
    private EditText etHttp;//要实现的类
    private EditText etIp;//要实现的类
    private EditText etPort;//要实现的类
    private EditText etPath;//要实现的类
    private ListLRU listLRU;//实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listLRU = new ListLRU(this,20);
        setContentView(R.layout.activity_main1);
        bt = (Button) findViewById(R.id.Button);
        lv = (ListView) findViewById(R.id.listView1);
        etHttp = (EditText) findViewById(R.id.et_http);
        etIp = (EditText) findViewById(R.id.et_ip);
        etPort = (EditText) findViewById(R.id.et_port);
        etPath = (EditText) findViewById(R.id.et_html);
        bt.requestFocus();
        //模拟数据库

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String http = etHttp.getText().toString().trim();
                String ip = etIp.getText().toString().trim();
                String port = etPort.getText().toString().trim();
                String path = etPath.getText().toString().trim();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String url = http + "://" + ip + ":" + port + "/" + path;
                listLRU.put(new Item(df.format(new Date()), url));
                adapter.notifyDataSetChanged();

                ComponentName componetName = new ComponentName("com.xm.webcontent", "com.xiaomi.webview.WebViewActivity");
                Intent intent = new Intent();
                intent.putExtra("data", "{\"opentype\":\"2\", \"url\":\"" + url + "?t=" + System.currentTimeMillis() + "\"}");
                intent.setComponent(componetName);
                startActivity(intent);
            }
        });

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listLRU.size();//数目
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view;

                if (convertView == null) {
                    view = inflater.inflate(R.layout.item, null);
                } else {
                    view = convertView;
                }
                tv1 = (TextView) view.findViewById(R.id.tv_time);//找到Textviewname
                tv1.setText(((Item) listLRU.get(position)).getTime());//设置参数

                tv2 = (TextView) view.findViewById(R.id.tv_http);//找到Textviewage
                tv2.setText(((Item) listLRU.get(position)).getUrl());//设置参数
                return view;
            }

            @Override
            public long getItemId(int position) {//取在列表中与指定索引对应的行id
                return 0;
            }

            @Override
            public Object getItem(int position) {//获取数据集中与指定索引对应的数据项
                return null;
            }
        };
        lv.setAdapter(adapter);
        //获取当前ListView点击的行数，并且得到该数据
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1 = (TextView) view.findViewById(R.id.tv_http);//找到Textviewname
                String url = tv1.getText().toString();//得到数据
                Toast.makeText(MainActivity.this, "" + url, Toast.LENGTH_SHORT).show();//显示数据
                //点击后跳转到列表首页
                listLRU.put(listLRU.get(position));
                adapter.notifyDataSetChanged();

                ComponentName componetName = new ComponentName("com.xm.webcontent", "com.xiaomi.webview.WebViewActivity");
                Intent intent = new Intent();
                intent.putExtra("data", "{\"opentype\":\"2\", \"url\":\"" + url + "?t=" + System.currentTimeMillis() + "\"}");
                intent.setComponent(componetName);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        listLRU.save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listLRU.save();
    }


}
