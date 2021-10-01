package com.fabloplatforms.business.store.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.fabloplatforms.business.R;


public class StoreFabloLoading {
    public static StoreFabloLoading storeFabloLoading = null;
    private Dialog mDialog;

    public static StoreFabloLoading getInstance() {
        if (storeFabloLoading == null) {
            storeFabloLoading = new StoreFabloLoading();
        }
        return storeFabloLoading;
    }

    public void showProgress(Context context) {
        mDialog = new Dialog(context);
        context.setTheme(R.style.Theme_FabloTrans);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_loading);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
