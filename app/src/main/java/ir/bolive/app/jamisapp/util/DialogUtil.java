package ir.bolive.app.jamisapp.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class DialogUtil {
    Context context;
    Boolean canelable;
    int themeResId;
    public DialogUtil(Context context) {
        this.context=context;
    }

    public DialogUtil(Context context, int themeResId) {
        this.context=context;
        this.themeResId=themeResId;
    }
    public AlertDialog.Builder createAlert(String message, String positiveText, String negativeText, final CallbackAlertDialog callback){
        AlertDialog.Builder builder=new AlertDialog.Builder(context,themeResId);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.OnAlertPositiveClick(builder);
            }
        });
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.OnAlertNegativeClick(builder);
            }
        });
        return builder;
    }
    public interface CallbackAlertDialog {
        void OnAlertPositiveClick(AlertDialog.Builder builder);
        void OnAlertNegativeClick(AlertDialog.Builder builder);
    }

}
