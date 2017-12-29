package br.inec.com.inec_desafio_analista.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.inec.com.inec_desafio_analista.R;
import br.inec.com.inec_desafio_analista.model.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Context context;
    private List<User> users;

    public UsersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_view_user, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        final User user = users.get(position);

        holder.txtName.setText(user.getLogin());
        Picasso.with(context).load(user.getAvatarURL()).into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName;

        UserViewHolder(View itemView) {

            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }
}
