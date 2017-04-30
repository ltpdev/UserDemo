package share.imooc.com.userdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus- on 2017/4/27.
 */

public class MyAdapter extends BaseAdapter{
    private CallBackListener callBackListener;
    private Context context;
    private List<User>dataList;

    public MyAdapter(List<User> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public void setCallBackListener(CallBackListener callBackListener){
        this.callBackListener=callBackListener;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        final User user=dataList.get(position);
        viewHolder.txtName.setText(user.getName());
        viewHolder.txtPwd.setText(user.getPassword());
        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackListener.del(user);
            }
        });
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackListener.update(user);
            }
        });
        return convertView;
    }

    class  ViewHolder{
        TextView txtName;
        TextView txtPwd;
        Button btnDel;
        Button btnUpdate;
        public ViewHolder(View view) {
            txtName= (TextView) view.findViewById(R.id.txt_name);
            txtPwd= (TextView) view.findViewById(R.id.txt_pwd);
            btnDel= (Button) view.findViewById(R.id.btnDel);
            btnUpdate= (Button) view.findViewById(R.id.btnUpdate);
        }
    }
}
