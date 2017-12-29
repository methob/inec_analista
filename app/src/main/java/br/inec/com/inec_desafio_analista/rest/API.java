package br.inec.com.inec_desafio_analista.rest;

import java.util.List;

import br.inec.com.inec_desafio_analista.model.Repositories;
import br.inec.com.inec_desafio_analista.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/* Restful APIs */

public interface API {

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String user);

    @GET("users/{username}/repos")
    Call<List<Repositories>> getUserRepositories(@Path("username") String user);

    @GET("repos/{username}/{repository}/languages")
    Call<ResponseBody> getUserRepositoriesLanguages(@Path("username") String user, @Path("repository") String repository);

    @GET("repos/{username}/{repository}/contributors")
    Call<List<User>> getUserRepositoriesContributors(@Path("username") String user, @Path("repository") String repository);
}
