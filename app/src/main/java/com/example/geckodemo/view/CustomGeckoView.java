package com.example.geckodemo.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.geckodemo.listener.OnDebugMessageListener;
import com.example.geckodemo.listener.OnUrlChangedListener;
import com.example.geckodemo.view.gecko.CustomNavigationDelegate;
import com.example.geckodemo.view.gecko.CustomProgressDelegate;

import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

/**
 * Copyright (c) 2019, Adversign Media GmbH
 * <p>
 * Created on 17.04.19.
 */
public class CustomGeckoView extends GeckoView {
    private static final String TAG = CustomGeckoView.class.getSimpleName();
    private CustomNavigationDelegate mNavigationDelegate;
    private CustomProgressDelegate mProgressDelegate;

    public CustomGeckoView(@NonNull Context context) {
        super(context);
    }

    public CustomGeckoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSession(@NonNull GeckoSession session) {
        super.setSession(session);

        mNavigationDelegate = new CustomNavigationDelegate();
        mProgressDelegate = new CustomProgressDelegate();

        if (getSession() != null) {
            getSession().setNavigationDelegate(mNavigationDelegate);
            getSession().setProgressDelegate(mProgressDelegate);
        }
    }

    public void setOnDebugMessageListener(@Nullable OnDebugMessageListener listener) {
        mNavigationDelegate.setOnDebugMessageListener(listener);
        mProgressDelegate.setOnDebugMessageListener(listener);
    }

    public void setOnUrlChangedListener(@Nullable OnUrlChangedListener listener) {
        //                mUIClient.setOnUrlChangedListener(listener);
    }

    /**
     * Goes to previous page.
     */
    public void goBack() {
        if (getSession() != null) {
            getSession().goBack();
        }
    }

    /**
     * Goes to next page.
     */
    public void goForward() {
        if (getSession() != null) {
            getSession().goForward();
        }
    }

    /**
     * Reloads the website in normal mode.
     */
    public void reload() {
        if (getSession() != null) {
            getSession().reload();
        }
    }

    public void loadUrl(@NonNull String url) {
        // Add http:// at first if the url does not contains
        if (!url.isEmpty() && !url.startsWith("about:") && !url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        if (getSession() != null) {
            getSession().loadUri(url);
        }
    }

    public void setUserAgent(String userAgent) {
    }
}
