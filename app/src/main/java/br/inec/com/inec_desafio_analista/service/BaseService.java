package br.inec.com.inec_desafio_analista.service;

import br.inec.com.inec_desafio_analista.rest.API;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* configurações padrões das utilização de services(restful)  */

public class BaseService {

    private String BASE_URL = "https://api.github.com/";

    private Retrofit retrofit;

    protected API getApiInstance() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(API.class);
    }
}
