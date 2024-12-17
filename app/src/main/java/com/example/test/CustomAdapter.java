package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.models.Article;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    public Context context;
    private final List <Article> userList;
    private final LayoutInflater inflater;

    public CustomAdapter(Context context, List<Article> userList) {
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
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_item);
        TextView title = convertView.findViewById(R.id.item_title);
        TextView description = convertView.findViewById(R.id.item_description);

        Article article = userList.get(position);
        imageView.setImageResource(R.drawable.no_image2);
        title.setText(article.getTitle());
        description.setText(article.getDescription());
        return convertView;
    }
}
