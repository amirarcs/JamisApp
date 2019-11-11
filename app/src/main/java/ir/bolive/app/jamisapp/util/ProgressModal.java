package ir.bolive.app.jamisapp.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;

public class ProgressModal extends Dialog {
    Dialog dialog;
    @BindView(R.id.loading_text)
    TextView txtLoading;
    public ProgressModal(Context context,String text){
        super(context);
        View view= LayoutInflater.from(context).inflate(R.layout.loading,null);
        dialog=new Dialog(context);
        dialog.setCancelable(false); // if you want user to wait for some process to finish,
        dialog.addContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this,dialog);
        txtLoading.setText(text);
    }
    public Dialog getProgress(){
        return dialog;
    }
}
