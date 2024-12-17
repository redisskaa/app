package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private final List<UserModel> userList;
    private LayoutInflater inflater;

    public UserAdapter(Context context, List<UserModel> userList) {
        this.userList = userList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //convertView = inflater.inflate(R.layout.list_row, parent, false);
        }

        //ImageView imageView = convertView.findViewById(R.id.avatar);

        UserModel user = userList.get(position);
        //imageView.setImageResource(user.getImage());

        return convertView;
    }
}
