package ir.bolive.app.jamisapp.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import ir.bolive.app.jamisapp.R;

public class ProgressView {
    Dialog dialog;
    TextView txtLoading;
    Context context;
    String text;
    public ProgressView(Context context, String text){
        this.context=context;
        this.text=text;
    }
    public void showProgress(){
        dialog=new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading);
        txtLoading=dialog.findViewById(R.id.loading_text);
        txtLoading.setText(text);
        dialog.show();
    }
    public void hideProgress(){
        dialog.dismiss();
    }
}
