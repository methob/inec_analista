package br.inec.com.inec_desafio_analista.service;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.util.List;

import br.inec.com.inec_desafio_analista.event.RequestFailedEvent;
import br.inec.com.inec_desafio_analista.model.Repositories;
import br.inec.com.inec_desafio_analista.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* chamada ao webservices ligados aos repositórios do usuário */

public class RepositoriesService extends BaseService {

    public void getRepositoriesByUserName(String userName) {

        Call<List<Repositories>> call = getApiInstance().getUserRepositories(userName);

        call.enqueue(new Callback<List<Repositories>>() {
            @Override
            public void onResponse(Call<List<Repositories>> call, Response<List<Repositories>> response) {

                if (response.isSuccessful())
                    EventBus.getDefault().post(response.body());
                else
                    EventBus.getDefault().post(new RequestFailedEvent(response.message(), response.code()));
            }

            @Override
            public void onFailure(Call<List<Repositories>> call, Throwable t) {

                EventBus.getDefault().post(new RequestFailedEvent("Falha ao fazer requisição.", -1));
            }
        });
    }

    public void getRepositoriesLanguage(String username, final String repository) {

        Call<ResponseBody> call = getApiInstance().getUserRepositoriesLanguages(username, repository);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    if (response.isSuccessful()) {

                        JSONObject object = new JSONObject(response.body().string());
                        EventBus.getDefault().post(object);

                    } else {

                        EventBus.getDefault().post(new RequestFailedEvent(response.message(), response.code()));
                    }

                } catch (Exception ignored) {}

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                EventBus.getDefault().post(new RequestFailedEvent("Falha ao fazer requisição.", -1));
            }
        });

    }

    public void getRepositoriesContributors(String username, final String repository) {

        Call<List<User>> call = getApiInstance().getUserRepositoriesContributors(username, repository);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null)
                        EventBus.getDefault().post(response.body());

                } else {
                    EventBus.getDefault().post(new RequestFailedEvent(response.message(), response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                EventBus.getDefault().post(new RequestFailedEvent("Falha ao fazer requisição.", -1));
            }
        });
    }
}
