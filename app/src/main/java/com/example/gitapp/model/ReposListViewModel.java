package com.example.gitapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gitapp.R;

import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReposListViewModel extends ArrayAdapter<GitRepos> {
    int resource;
    public ReposListViewModel(@NonNull Context context, int resource, List<GitRepos> data) {
        super(context, resource);
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
        CircleImageView imageViewUser=listViewItem.findViewById(R.id.imageView);
        TextView textViewName=listViewItem.findViewById(R.id.textViewName);
        TextView textViewLanguage=listViewItem.findViewById(R.id.textViewLanguage);
        TextView textViewIdentifiant=listViewItem.findViewById(R.id.textViewIdentifiant);
        TextView textViewSize=listViewItem.findViewById(R.id.textViewSize);

        textViewName.setText(getItem(position).name);
        textViewLanguage.setText(getItem(position).language);
        textViewIdentifiant.setText(String.valueOf(getItem(position).id));
        textViewSize.setText(String.valueOf(getItem(position).size));
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
