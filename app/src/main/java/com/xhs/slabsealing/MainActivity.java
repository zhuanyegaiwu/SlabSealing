package com.xhs.slabsealing;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.xhs.slabsealing.constant.Constant;
import com.xhs.slabsealing.ui.view.LoadingDialog;
import com.xhs.slabsealing.utils.NetWorkUtils;
import com.xhs.slabsealing.utils.SPUtils;

@RequiresApi(api = Build.VERSION_CODES.M)

public class MainActivity extends AppCompatActivity  {

    private Dialog loadingDialog;
    private WebView  webView;
    private SwipeRefreshLayout  swipeLayout;
    private  int sheight;

    public static boolean isNetWorkConnected;
    private boolean isLoad;
    private Button btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        if (NetWorkUtils.isNetworkAvailable(this)) {
            initView();
        } else {
            Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        webView = (WebView ) findViewById(R.id.mwebview);
            settingWebView();

    }
    private void settingWebView() {
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setDefaultFontSize((int)19);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);

        //启用数据库
        webView.getSettings().setDatabaseEnabled(true);


//设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", this.MODE_PRIVATE).getPath();


        webView.getSettings().setGeolocationDatabasePath(dir);
        webView.loadUrl(BaseApp.getInstance().ip);
        //显示加载框
        loadingDialog = LoadingDialog.createLoadingDialog(this, "加载中...");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              //  view.loadUrl(url);
                return false;
            }
            //开始加载的时候 显示 进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }
            //加载完毕的时候 隐藏进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                LoadingDialog.closeDialog(loadingDialog);
                SPUtils.setParam(MainActivity.this, Constant.IP,BaseApp.getInstance().ip);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
                //默认的处理方式，WebView变成空白页
                //handler.cancel();
                // 其他处理
                //handleMessage(Message msg);
            }
        });

        //启用地理定位
        webView.getSettings().setGeolocationEnabled(true);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=loadingDialog){
            LoadingDialog.closeDialog(loadingDialog);
        }
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
