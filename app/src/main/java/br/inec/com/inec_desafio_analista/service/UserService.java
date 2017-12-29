package br.inec.com.inec_desafio_analista.service;

import org.greenrobot.eventbus.EventBus;

import br.inec.com.inec_desafio_analista.event.RequestFailedEvent;
import br.inec.com.inec_desafio_analista.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* chamada ao webservices ligados ao usuário em si. */

public class UserService extends BaseService {

    public void getUserByUsername(String username) {

        Call<User> call = getApiInstance().getUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful())
                    EventBus.getDefault().post(response.body());

                else
                    EventBus.getDefault().post(new RequestFailedEvent(response.message(), response.code()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                EventBus.getDefault().post(new RequestFailedEvent("Falha ao fazer requisição", -1));
            }
        });
    }
}
