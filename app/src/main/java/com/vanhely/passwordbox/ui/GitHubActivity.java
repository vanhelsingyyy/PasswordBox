package com.vanhely.passwordbox.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class GitHubActivity extends BaseActivity {

    private String loadUrl;
    private WebView webView;
    private TextView toolName;
    private ProgressBar progressBar;

    @Override
    public int initContentView() {
        return R.layout.activity_github;
    }

    @Override
    public void initViewId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolName = (TextView) findViewById(R.id.tool_name);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolName.setText("Github");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        loadUrl = intent.getStringExtra("loadUrl");
        webView.loadUrl(this.loadUrl);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolName.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

            }
        });

    }

    @Override
    public void initListen() {

    }
}
