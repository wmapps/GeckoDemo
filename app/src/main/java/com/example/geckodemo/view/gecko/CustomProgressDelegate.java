package com.example.geckodemo.view.gecko;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.geckodemo.listener.OnDebugMessageListener;

import org.mozilla.geckoview.GeckoSession;

/**
 * Copyright (c) 2019, Adversign Media GmbH
 * <p>
 * Created on 17.04.19.
 */
public class CustomProgressDelegate implements GeckoSession.ProgressDelegate {
    private static final String TAG = CustomProgressDelegate.class.getSimpleName();
    private OnDebugMessageListener mOnDebugMessageListener;

    @Override
    public void onPageStart(@NonNull GeckoSession session, @NonNull String url) {
        Log.d(TAG, "onPageStart() called with: session = [" + session + "], url = [" + url + "]");
        postMessage("onPageStart() called with: session = [" + session + "], url = [" + url + "]");
    }

    @Override
    public void onPageStop(@NonNull GeckoSession session, boolean success) {
        Log.d(TAG, "onPageStop() called with: session = [" + session + "], success = [" + success + "]");
        postMessage("onPageStop() called with: session = [" + session + "], success = [" + success + "]");
    }

    @Override
    public void onProgressChange(@NonNull GeckoSession session, int progress) {
        Log.d(TAG, "onProgressChange() called with: session = [" + session + "], progress = [" + progress + "]");
        postMessage("onProgressChange() called with: session = [" + session + "], progress = [" + progress + "]");
    }

    @Override
    public void onSecurityChange(@NonNull GeckoSession session, @NonNull SecurityInformation securityInfo) {
        Log.d(TAG, "onSecurityChange() called with: session = [" + session + "], securityInfo = [" + securityInfo + "]");
        postMessage("onSecurityChange() called with: session = [" + session + "], securityInfo = [" + securityInfo + "]");
    }

    public void setOnDebugMessageListener(OnDebugMessageListener listener) {
        mOnDebugMessageListener = listener;
    }

    private void postMessage(@Nullable String message) {
        if (mOnDebugMessageListener != null) {
            mOnDebugMessageListener.onMessage(message);
        }
    }
}
