package com.haha.contactslist;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
    public List<SelectUser> mUsers;
    private ArrayList<SelectUser> mArraylist;
    private Context mContext;
    private ViewHolder mView;

    public SelectUserAdapter(List<SelectUser> selectUsers, Context context) {
        mUsers = selectUsers;
        mContext = context;
        this.mArraylist = new ArrayList<SelectUser>();
        this.mArraylist.addAll(mUsers);
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return mUsers.get(i);
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
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.row_contacts, null);

        } else {
            view = convertView;
        }

        mView = new ViewHolder();
        mView.mBtnCall = (ImageButton) view.findViewById(R.id.btnCall);
        mView.mBtnSend = (ImageButton) view.findViewById(R.id.btnSend);
        mView.title = (TextView) view.findViewById(R.id.name);
        mView.phone = (TextView) view.findViewById(R.id.no);
        mView.imageView = (ImageView) view.findViewById(R.id.pic);

        final SelectUser data = (SelectUser) mUsers.get(i);
        mView.title.setText(data.getName());
        mView.phone.setText(data.getPhone());
        mView.mBtnCall.setOnClickListener(this);
        mView.mBtnSend.setOnClickListener(this);
        view.setTag(data);
        return view;
    }

    @Override
    public void onClick(View view) {
        //MainActivity mainActivity = new MainActivity();
        switch (view.getId()){
            case R.id.btnCall:
               // mainActivity.setCall();
                break;
            case R.id.btnSend:
                //mainActivity.setSend();
                break;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        ImageButton mBtnSend,mBtnCall;
    }
}
