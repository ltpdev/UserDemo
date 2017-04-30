package share.imooc.com.userdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    private Button btnSub;
    private EditText edtName;
    private EditText edtPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        btnSub= (Button) findViewById(R.id.btnSubmit);
        edtName= (EditText) findViewById(R.id.edtName);
        edtPwd= (EditText) findViewById(R.id.edtPwd);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString().trim();
                String pwd=edtPwd.getText().toString().trim();
                Intent mIntent = new Intent();
                mIntent.putExtra("name",name);
                mIntent.putExtra("pwd",pwd);
                setResult(1002, mIntent);
                finish();
            }
        });
    }
}
