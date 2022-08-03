package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.fy.baselibrary.download.DownloadUtil;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.hongniu.freight.R;

import java.io.File;

@Route(path = ArouterParamApp.activity_pdf)
public class PDFActivity extends CompanyBaseActivity implements OnLoadCompleteListener, OnRenderListener, PermissionUtils.onApplyPermission, DownloadUtil.OnDownloadListener {
    PDFView pdfView;
    private String pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        setWhitToolBar("查看保单");
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        pdfView = findViewById(R.id.pdfView);
    }

    @Override
    protected void initData() {
        super.initData();
        pdfUrl = getIntent().getStringExtra(Param.TRAN);
        if (!TextUtils.isEmpty(pdfUrl)) {
            PermissionUtils.applyStorage(this, this);
        } else {
            ToastUtils.getInstance().show("文件地址不存在，请返回重试");
        }
    }

    /**
     * Called when the PDF is loaded
     *
     * @param nbPages the number of pages in this PDF file
     */
    @Override
    public void loadComplete(int nbPages) {
    }

    /**
     * Called only once, when document is rendered
     *
     * @param nbPages    number of pages
     * @param pageWidth  width of page
     * @param pageHeight height of page
     */
    @Override
    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
    }


    /**
     * 下载成功之后的文件
     *
     * @param file
     */
    @Override
    public void onDownloadSuccess(File file) {
        hideLoad();
        pdfView.fromFile(file)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onLoad(this) // called after document is loaded and starts to be rendered
                .onRender(this) // called after document is rendered for the first time
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens

                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
//                .linkHandler(DefaultLinkHandler)
                .load();
    }

    /**
     * 下载进度
     *
     * @param progress
     */
    @Override
    public void onDownloading(int progress) {

    }

    /**
     * 下载异常信息
     *
     * @param e
     */
    @Override
    public void onDownloadFailed(Exception e) {
        e.printStackTrace();
        hideLoad();
    }

    @Override
    public void hasPermission() {
        String path = getFilesDir().getAbsolutePath() + "/hongniu/";
        JLog.i(path);
        showLoad();
        DownloadUtil.get().download(pdfUrl, path, SystemClock.currentThreadTimeMillis() + ".pdf", this);

    }

    @Override
    public void noPermission() {

    }
}
