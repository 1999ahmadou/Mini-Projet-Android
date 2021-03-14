package com.example.gitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.gitapp.model.GitUser;
import com.example.gitapp.model.GitUsersResponse;
import com.example.gitapp.model.UserListViewModel;
import com.example.gitapp.service.GitRepoServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<GitUser> data=new ArrayList<>();
    public static final String USER_LOGIN_PARAM="user.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Récuperation de nos champs à partir de leur identifiant
        EditText editTextQuery=findViewById(R.id.editTextQuery);
        Button buttonSearch=findViewById(R.id.buttonSearch);
        ListView listViewUsers=findViewById(R.id.listeViewUsers);

        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        UserListViewModel listViewModel=new UserListViewModel(this,R.layout.users_list_view_layout,data);
        listViewUsers.setAdapter(listViewModel);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()) // when we get json data we can tell which library we use to deserialise data
                .build();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=editTextQuery.getText().toString();
                //The Retrofit class generates an implementation of the GitRepoServiceAPI interface.
                GitRepoServiceAPI gitRepoServiceAPI = retrofit.create(GitRepoServiceAPI.class);
                //Each Call from the created GitHubService can make a synchronous or asynchronous HTTP request to the remote webserver.
                Call<GitUsersResponse> callGitUsers = gitRepoServiceAPI.searchUsers(query);
                callGitUsers.enqueue(new Callback<GitUsersResponse>() {
                    @Override
                    public void onResponse(Call<GitUsersResponse> call, Response<GitUsersResponse> response) {
                        if (!response.isSuccessful()){
                            Log.i("info",String.valueOf(response.code()));
                            return;
                        }
                        GitUsersResponse gitUsersResponse=response.body();
                        for (GitUser user:gitUsersResponse.Users){
                            data.add(user);
                        }
                        listViewModel.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<GitUsersResponse> call, Throwable t) {
                        Log.i("error","Il y'a une erreur");
                    }
                });
            }
        });
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String login=data.get(position).login;
                Log.i("loginn",login);
                Intent intent=new Intent(getApplicationContext(),RepositoryActivity.class);
                intent.putExtra(USER_LOGIN_PARAM,login);
                startActivity(intent);
            }
        });
    }
}