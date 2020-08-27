package com.basundhara.oneonly;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    private static final String ACTION_SHARE = "share";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String myurl = "https://www.oneonly.in/index.html";
        WebView view = (WebView) this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(myurl);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        WebView myWebView = (WebView) findViewById(R.id.webview);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    public void sharePage(String optionalUrl) {
        String shareUrl;
        WebView myWebView = (WebView) findViewById(R.id.webview);
        String currentUrl = myWebView.getUrl();
        if (optionalUrl == null || optionalUrl.isEmpty()) {
            shareUrl = currentUrl;
        } else {
            try {
                java.net.URI optionalUri = new java.net.URI(optionalUrl);
                if (optionalUri.isAbsolute()) {
                    shareUrl = optionalUrl;
                } else {
                    java.net.URI currentUri = new java.net.URI(currentUrl);
                    shareUrl = currentUri.resolve(optionalUri).toString();
                }
            } catch (URISyntaxException e) {
                shareUrl = optionalUrl;
            }
        }

        if (shareUrl == null || shareUrl.isEmpty()) return;

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, shareUrl);
        //startActivity(Intent.createChooser(share, getString(R.string.ACTION_SHARE)));
    }

    }

