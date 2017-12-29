package br.inec.com.inec_desafio_analista.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.inec.com.inec_desafio_analista.R;
import br.inec.com.inec_desafio_analista.adapter.RepositoriesAdapter;
import br.inec.com.inec_desafio_analista.event.RequestFailedEvent;
import br.inec.com.inec_desafio_analista.model.Repositories;
import br.inec.com.inec_desafio_analista.model.User;
import br.inec.com.inec_desafio_analista.service.RepositoriesService;
import br.inec.com.inec_desafio_analista.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

/* Activity que mostra o perfil do usuário */

public class ProfileUserActivity extends BaseActivity {

    @BindView(R.id.img_user)
    ImageView imgUser;

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.txt_email)
    TextView txtEmail;

    @BindView(R.id.txt_empty_repositories)
    TextView txtEmptyRepositories;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
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

        User user = (User) getIntent().getSerializableExtra(Constants.PARAM);

        Picasso.with(this).load(user.getAvatarURL()).into(imgUser);
        txtName.setText(user.getLogin());
        txtEmail.setText(user.getEmail());

        RepositoriesService service = new RepositoriesService();
        service.getRepositoriesByUserName(user.getLogin()); // obtem repositórios do usuário.

        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider_recycler_view));
        recyclerView.addItemDecoration(myDivider);
    }



    @Subscribe
    public void recyclerViewItemClick(Repositories repository) {
        Intent intent = new Intent(this, RepositoriesDetailActivity.class);
        intent.putExtra(Constants.PARAM, repository);
        startActivity(intent);
    }

    @Subscribe
    public void requestRepositoriesUserSuccessfully(List<Repositories> repositories) {

        progressBar.setVisibility(View.INVISIBLE);

        RepositoriesAdapter repositoriesAdapter = new RepositoriesAdapter();
        repositoriesAdapter.bindList(repositories, this);
        recyclerView.setAdapter(repositoriesAdapter);

        if (repositories.isEmpty())
            txtEmptyRepositories.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void requestUserInformationFailed(RequestFailedEvent response) {

        dismissProgressDialog();
        showAlertDialog(this, response.getMessage(), getString(R.string.err_txt_failure));
    }

}
