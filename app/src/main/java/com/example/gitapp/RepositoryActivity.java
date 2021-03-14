package com.example.gitapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gitapp.model.GitRepos;
import com.example.gitapp.model.GitUser;
import com.example.gitapp.model.GitUsersResponse;
import com.example.gitapp.model.ReposListViewModel;
import com.example.gitapp.service.GitRepoServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryActivity extends AppCompatActivity {
    List<String> data=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_layout);
        Intent intent=getIntent();
        String login=intent.getStringExtra(MainActivity.USER_LOGIN_PARAM);
        setTitle("Repositories");
        TextView textViewLogin=findViewById(R.id.textViewUserLogin);
        ListView listViewrepository=findViewById(R.id.listeViewRepository);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
       // ReposListViewModel listViewModel=new ReposListViewModel(this,R.layout.repos_list_view_layout,data);
        listViewrepository.setAdapter(arrayAdapter);
        textViewLogin.setText(login);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()) // when we get json data we can tell which library we use to deserialise data
                .build();
        GitRepoServiceAPI gitRepoServiceAPI = retrofit.create(GitRepoServiceAPI.class);
        Call<List<GitRepos>> reposCall = gitRepoServiceAPI.userRepositories(login);
        reposCall.enqueue(new Callback<List<GitRepos>>() {
            @Override
            public void onResponse(Call<List<GitRepos>> call, Response<List<GitRepos>> response) {
                if (!response.isSuccessful()){
                    Log.i("info",String.valueOf(response.code()));
                    return;
                }
                List<GitRepos> gitRepos=response.body();
                Log.i("corppp",gitRepos.toString());
                for (GitRepos repos:gitRepos){
                    String content="";
                    content+=repos.name+"\n";
                    content+=repos.language+"\n";
                    content+=repos.id+"\n";
                    content+=repos.size+"\n";
                    data.add(content);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitRepos>> call, Throwable t) {

            }
        });
    }
}
