package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.models.Article;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private final Context context;
    private final List<Article> userList;

    public CustomAdapter(Context context, List<Article> userList) {
        this.context = context;
        this.userList = userList;
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
        HolderView holderView;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout_with_cardview, parent,false);
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.custom_list, parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);

        } else {
            holderView = (HolderView) convertView.getTag();
        }

        Article article = userList.get(position);
        holderView.image.setImageResource(R.drawable.no_image);
        holderView.title.setText(article.getTitle());
        holderView.description.setText(article.getDescription());
        Glide.with(context).load(article.getUrlToImage()).into(holderView.image);

        return convertView;
    }

    private static class HolderView{
        private final ImageView image;
        private final TextView title;
        private final TextView description;

        public HolderView(View view){
            image = view.findViewById(R.id.image_item);
            title = view.findViewById(R.id.item_title);
            description = view.findViewById(R.id.item_description);
        }
    }
}
