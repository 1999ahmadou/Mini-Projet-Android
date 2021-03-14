package com.example.gitapp.service;

import com.example.gitapp.model.GitUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitRepoServiceAPI {
    /**
     * J'envoie une requette de type Get vers search/users et avec un parametre q= query
     * Retoune des objets de type Call qui est un type generique
     * @param query
     * @return
     */
    @GET("search/users")
    public Call<GitUsersResponse> searchUsers(@Query("q") String query);
}
