package br.inec.com.inec_desafio_analista.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.inec.com.inec_desafio_analista.R;
import br.inec.com.inec_desafio_analista.model.Repositories;

/**
 * Created by jonathan on 27/12/2017.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder> {


    private List<Repositories> repositories;
    private Context context;

    public void bindList(List<Repositories> repositoriesList, Context context) {

        this.context = context;
        this.repositories = repositoriesList;
    }

    @Override
    public RepositoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_view_user_repositories, parent, false);

        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoriesViewHolder holder, int position) {

        try {

            final Repositories repository = repositories.get(position);

            holder.txtName.setText(repository.getName());
            holder.txtDescription.setText(repository.getDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(repository);
                }
            });

        } catch (Exception ignored) {}
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public class RepositoriesViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtDescription;

        RepositoriesViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
        }
    }

}
