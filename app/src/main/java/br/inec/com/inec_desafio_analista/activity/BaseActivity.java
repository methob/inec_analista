package br.inec.com.inec_desafio_analista.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.inec.com.inec_desafio_analista.R;
import butterknife.ButterKnife;

/* Configurações padrões que ocorrem nas activities da aplicação. */

public class BaseActivity extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressDialog(Activity activity) {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.show();
    }

    public void showAlertDialog(Context context, String message, String title)  {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setTitle(title);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)) // Registra evento
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (EventBus.getDefault().isRegistered(this)) // desregistra evento
            EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void defaultSubscribe(String sample) {}

    public void dismissProgressDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
