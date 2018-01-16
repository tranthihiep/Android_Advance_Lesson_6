package com.haha.contactslist;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trant on 16/01/2018.
 */

public class SelectUserAdapter extends BaseAdapter implements View.OnClickListener {
    public List<SelectUser> _data;
    private ArrayList<SelectUser> arraylist;
    Context _c;
    ViewHolder v;

    public SelectUserAdapter(List<SelectUser> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<SelectUser>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.row_contacts, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();
        v.mBtnCall = (ImageButton) view.findViewById(R.id.btnCall);
        v.mBtnSend = (ImageButton) view.findViewById(R.id.btnSend);
        v.title = (TextView) view.findViewById(R.id.name);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final SelectUser data = (SelectUser) _data.get(i);
        v.title.setText(data.getName());
        v.phone.setText(data.getPhone());
        v.mBtnCall.setOnClickListener(this);
        v.mBtnSend.setOnClickListener(this);


        // Set image if exists

        Log.e("Image Thumb", "--------------" + data.getThumb());

        view.setTag(data);
        return view;
    }

    @Override
    public void onClick(View view) {
        MainActivity mainActivity = new MainActivity();
        switch (view.getId()){
            case R.id.btnCall:
                mainActivity.setCall();
                break;
            case R.id.btnSend:
                mainActivity.setSend();
                break;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        ImageButton mBtnSend,mBtnCall;
    }
}
