package br.inec.com.inec_desafio_analista.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import br.inec.com.inec_desafio_analista.R;
import br.inec.com.inec_desafio_analista.adapter.UsersAdapter;
import br.inec.com.inec_desafio_analista.event.RequestFailedEvent;
import br.inec.com.inec_desafio_analista.model.Repositories;
import br.inec.com.inec_desafio_analista.model.User;
import br.inec.com.inec_desafio_analista.service.RepositoriesService;
import br.inec.com.inec_desafio_analista.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_languages)
    TextView txtLanguages;

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.txt_description)
    TextView txtDescription;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.txt_empty_contributors)
    TextView txtEmptyContributors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Repositories repositories = (Repositories) getIntent().getSerializableExtra(Constants.PARAM);

        RepositoriesService repositoriesService = new RepositoriesService();

        // request lista de linguagens e contribuidores
        repositoriesService.getRepositoriesLanguage(repositories.getOwner().getLogin(), repositories.getName());
        repositoriesService.getRepositoriesContributors(repositories.getOwner().getLogin(), repositories.getName());

        txtName.setText(repositories.getName());
        txtDescription.setText(repositories.getDescription());

        showProgressDialog(this);
    }

    @Subscribe
    public void requestUserInformationFailed(RequestFailedEvent response) {

        dismissProgressDialog();
        showAlertDialog(this, response.getMessage(), getString(R.string.err_txt_failure));
    }

    @Subscribe
    public void requestRepositoriesLanguageSuccessfully(JSONObject jsonObject) {

        fillLanguagesString(jsonObject);
    }

    @Subscribe
    public void requestRepositoriesContributors(List<User> users) {

        dismissProgressDialog();

        if (users.isEmpty()) {

            txtEmptyContributors.setVisibility(View.VISIBLE);

        } else {

            UsersAdapter usersAdapter = new UsersAdapter(this, users);
            recyclerView.setAdapter(usersAdapter);
        }
    }

    // cria a string das linguagens do usuário.
    private void fillLanguagesString(JSONObject obj) {

        String languages = "";

        JSONArray array = obj.names();

        for (int i = 0; i < array.length();i++) {

            try {

                String language = array.get(i) + " / ";

                languages = languages.concat(language);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        if (!languages.isEmpty())
             languages = languages.substring(0, languages.length() - 2);


        txtLanguages.setText(languages);
    }
}
