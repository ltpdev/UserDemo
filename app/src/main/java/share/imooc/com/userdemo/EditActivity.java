package share.imooc.com.userdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private Button btnUpdate;
    private EditText edtName;
    private EditText edtPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        edtName= (EditText) findViewById(R.id.edtName);
        edtPwd= (EditText) findViewById(R.id.edtPwd);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString().trim();
                String pwd=edtPwd.getText().toString().trim();
                String bName=getIntent().getStringExtra("bName");
                String bPwd=getIntent().getStringExtra("bPwd");
                Intent mIntent = new Intent();
                mIntent.putExtra("name",name);
                mIntent.putExtra("pwd",pwd);
                mIntent.putExtra("bName",bName);
                mIntent.putExtra("bPwd",bPwd);
                setResult(1004, mIntent);
                finish();
            }
        });
    }
}
