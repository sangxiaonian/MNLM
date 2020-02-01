package com.fy.androidlibrary.widget.load;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.androidlibrary.widget.dialog.BaseFragmentDialog;


/**
 * 作者： ${PING} on 2017/10/18.
 */

public class LoadDialogFragment extends BaseFragmentDialog {

    private int res;//loading图片

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LoadDialog dialog = new LoadDialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setImageLoad(res);
        return dialog;
    }


    public void setLoadRes(int res) {
        this.res = res;
    }


}
