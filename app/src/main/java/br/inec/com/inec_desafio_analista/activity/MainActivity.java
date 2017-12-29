package br.inec.com.inec_desafio_analista.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import br.inec.com.inec_desafio_analista.R;
import br.inec.com.inec_desafio_analista.event.RequestFailedEvent;
import br.inec.com.inec_desafio_analista.model.User;
import br.inec.com.inec_desafio_analista.service.UserService;
import br.inec.com.inec_desafio_analista.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/* Activity que pesquisa por um usuário específico do github */

public class MainActivity extends BaseActivity {

    @BindView(R.id.edt_user_name)
    EditText edtUsername;

    @BindView(R.id.btn_search_user)
    Button button;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        userService = new UserService();
    }

    @OnClick(R.id.btn_search_user)
    public void search() {

        String username = edtUsername.getText().toString();

        if (!username.isEmpty()) {

            showProgressDialog(this);
            userService.getUserByUsername(username);

        } else {

            edtUsername.setError(getString(R.string.err_txt_empty_username));
        }
    }

    @Subscribe
    public void requestUserInformationSuccessefully(User user) {

        dismissProgressDialog();

        Intent intent = new Intent(this, ProfileUserActivity.class);
        intent.putExtra(Constants.PARAM, user);
        startActivity(intent);
    }

    @Subscribe
    public void requestUserInformationFailed(RequestFailedEvent response) {

        dismissProgressDialog();
        showAlertDialog(this, response.getMessage(), getString(R.string.err_txt_failure));
    }
}
