package share.imooc.com.userdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements CallBackListener{
    private ListView listview;
    private List<User> datalist;
    private MyAdapter myAdapter;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.lisview);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,1001);
            }
        });
        datalist = new ArrayList<User>();
        myAdapter = new MyAdapter(datalist, this);
        myAdapter.setCallBackListener(this);
        listview.setAdapter(myAdapter);
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder().url("http://192.168.191.7:8080/AndroidTest/Login?type=query");
                Request request = builder.build();
                try {
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    Gson gson = new Gson();

                    final List<User> res = gson.fromJson(data, new TypeToken<List<User>>() {
                    }.getType());
                    for (int i = 0; i < res.size(); i++) {
                        User user = res.get(i);
                        datalist.add(user);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void del(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name=user.getName();
                String pwd=user.getPassword();
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder().url("http://192.168.191.7:8080/AndroidTest/Login?type=delete&" +
                        "name="+name+"&pwd="+pwd);
                Request request = builder.build();
                try {
                    client.newCall(request).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            datalist.clear();
                            initData();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void update(User user) {
        Intent intent=new Intent(MainActivity.this,EditActivity.class);
        intent.putExtra("bName",user.getName());
        intent.putExtra("bPwd",user.getPassword());
        startActivityForResult(intent,1003);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
         if (resultCode==1002){
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     String name=data.getStringExtra("name");
                     String pwd=data.getStringExtra("pwd");
                     OkHttpClient client = new OkHttpClient();
                     Request.Builder builder = new Request.Builder().url("http://192.168.191.7:8080/AndroidTest/Login?type=insert&" +
                             "name="+name+"&pwd="+pwd);
                     Request request = builder.build();

                     try {
                         client.newCall(request).execute();
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 datalist.clear();
                                 initData();
                             }
                         });
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }).start();

         }
        if (resultCode==1004){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name=data.getStringExtra("name");
                    String pwd=data.getStringExtra("pwd");
                    String bName=data.getStringExtra("bName");
                    String bPwd=data.getStringExtra("bPwd");
                    OkHttpClient client = new OkHttpClient();
                    Request.Builder builder = new Request.Builder().url("http://192.168.191.7:8080/AndroidTest/Login?type=update&" +
                            "name="+name+"&pwd="+pwd+
                            "&bname="+bName+"&bpwd="+bPwd);
                    Request request = builder.build();

                    try {
                        client.newCall(request).execute();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                datalist.clear();
                                initData();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
