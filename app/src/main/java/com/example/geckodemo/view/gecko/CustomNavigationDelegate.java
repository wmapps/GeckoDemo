package com.example.geckodemo.view.gecko;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.geckodemo.listener.OnDebugMessageListener;

import org.mozilla.geckoview.AllowOrDeny;
import org.mozilla.geckoview.GeckoResult;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.WebRequestError;

/**
 * Copyright (c) 2019, Adversign Media GmbH
 * <p>
 * Created on 17.04.19.
 */
public class CustomNavigationDelegate implements GeckoSession.NavigationDelegate {
    private static final String TAG = CustomProgressDelegate.class.getSimpleName();
    @Nullable private OnDebugMessageListener mOnDebugMessageListener;

    @Override
    public void onLocationChange(@NonNull GeckoSession session, @Nullable String url) {
        Log.d(TAG, "onLocationChange() called with: session = [" + session + "], url = [" + url + "]");
        postMessage("onLocationChange() called with: session = [" + session + "], url = [" + url + "]");
    }

    @Override
    public void onCanGoBack(@NonNull GeckoSession session, boolean canGoBack) {
        Log.d(TAG, "onCanGoBack() called with: session = [" + session + "], canGoBack = [" + canGoBack + "]");
        postMessage("onCanGoBack() called with: session = [" + session + "], canGoBack = [" + canGoBack + "]");
    }

    @Override
    public void onCanGoForward(@NonNull GeckoSession session, boolean canGoForward) {
        Log.d(TAG, "onCanGoForward() called with: session = [" + session + "], canGoForward = [" + canGoForward + "]");
        postMessage("onCanGoForward() called with: session = [" + session + "], canGoForward = [" + canGoForward + "]");
    }

    @Nullable
    @Override
    public GeckoResult<AllowOrDeny> onLoadRequest(@NonNull GeckoSession session, @NonNull LoadRequest request) {
        Log.d(TAG, "onLoadRequest() called with: session = [" + session + "], request = [" + request + "]");
        postMessage("onLoadRequest() called with: session = [" + session + "], request = [" + request + "]");
        return null;
    }

    @Nullable
    @Override
    public GeckoResult<GeckoSession> onNewSession(@NonNull GeckoSession session, @NonNull String uri) {
        Log.d(TAG, "onNewSession() called with: session = [" + session + "], uri = [" + uri + "]");
        postMessage("onNewSession() called with: session = [" + session + "], uri = [" + uri + "]");
        return null;
    }

    @Nullable
    @Override
    public GeckoResult<String> onLoadError(@NonNull GeckoSession session, @Nullable String uri, @NonNull WebRequestError error) {
        Log.d(TAG, "onLoadError() called with: session = [" + session + "], uri = [" + uri + "], error = [" + error + "]");
        postMessage("onLoadError() called with: session = [" + session + "], uri = [" + uri + "], error = [" + error + "]");
        return null;
    }

    public void setOnDebugMessageListener(@Nullable OnDebugMessageListener listener) {
        mOnDebugMessageListener = listener;
    }

    private void postMessage(@Nullable String message) {
        if (mOnDebugMessageListener != null) {
            mOnDebugMessageListener.onMessage(message);
        }
    }
}
