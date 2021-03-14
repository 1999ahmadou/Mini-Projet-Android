package com.example.gitapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gitapp.R;

import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListViewModel extends ArrayAdapter<GitUser> {

    private int resource;

    public UserListViewModel(@NonNull Context context, int resource, List<GitUser> data) {
        super(context, resource,data);
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem=convertView;
        if (listViewItem==null){
            // recupére la resource User_list_view_layout et on l'instancie
            listViewItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }
        CircleImageView imageViewUser=listViewItem.findViewById(R.id.imageViewUser);
        TextView textViewLogin=listViewItem.findViewById(R.id.textViewLogin);
        TextView textViewScore=(TextView) listViewItem.findViewById(R.id.textViewScore);
        textViewLogin.setText(getItem(position).login);
        textViewScore.setText(String.valueOf(getItem(position).score));
        try {
            URL url=new URL(getItem(position).avatarUrl);
            Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
            imageViewUser.setImageBitmap(bitmap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listViewItem;
    }
}
