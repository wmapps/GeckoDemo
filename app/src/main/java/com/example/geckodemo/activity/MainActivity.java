package com.example.geckodemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geckodemo.R;
import com.example.geckodemo.listener.OnDebugMessageListener;
import com.example.geckodemo.listener.OnUrlChangedListener;
import com.example.geckodemo.view.CustomGeckoView;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoRuntimeSettings;
import org.mozilla.geckoview.GeckoSession;

/**
 * Copyright (c) 2017, WM-Apps
 * <p>
 * Created on 31.01.17.
 */
public class MainActivity extends AppCompatActivity implements OnDebugMessageListener, OnUrlChangedListener {

    private SharedPreferences mPreferences;
    private EditText mAddressInput;
    private TextView mDebugOutput;

    private CustomGeckoView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final GeckoRuntimeSettings.Builder builder = new GeckoRuntimeSettings.Builder();
        builder.aboutConfigEnabled(true);
        builder.consoleOutput(true);
        builder.remoteDebuggingEnabled(true);
        builder.javaScriptEnabled(true);
        builder.allowInsecureConnections(GeckoRuntimeSettings.ALLOW_ALL);

        final GeckoRuntime runtime = GeckoRuntime.create(this, builder.build());
        final GeckoSession session = new GeckoSession();
        session.open(runtime);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mAddressInput = findViewById(R.id.main_address_edit_text);
        mDebugOutput = findViewById(R.id.main_debug_text);
        mWebView = findViewById(R.id.main_gecko_webview);
        mWebView.setSession(session);

        mAddressInput.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ||
                 actionId == EditorInfo.IME_ACTION_GO)) {
                hideKeyboard();
                mWebView.loadUrl(v.getText().toString());
            }

            return false;
        });
        mDebugOutput.setMovementMethod(new ScrollingMovementMethod());
        mWebView.setOnDebugMessageListener(this);
        mWebView.setOnUrlChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        home(null);
        mWebView.setUserAgent(mPreferences.getString(SettingsActivity.PREF_USER_AGENT, null));
        mDebugOutput.setVisibility(mPreferences.getBoolean("show_debug", false) ? View.VISIBLE : View.GONE);

        mWebView.loadUrl("https://static.vg.no/vgtv/public-display/ferrule/category/3");
    }

    public void goBack(@NonNull View view) {
        mWebView.goBack();
    }

    public void goForward(@NonNull View view) {
        mWebView.goForward();
    }

    public void reload(@NonNull View view) {
        mWebView.reload();
    }

    public void home(@Nullable View view) {
        String homePage = mPreferences.getString(SettingsActivity.PREF_HOME_PAGE, null);
        if (homePage != null && Patterns.WEB_URL.matcher(homePage).matches()) {
            mWebView.loadUrl(homePage);
        } else {
            homePage = "about:buildconfig";
            mPreferences.edit().putString(SettingsActivity.PREF_HOME_PAGE, homePage).apply();
            Toast.makeText(this, homePage + " is not valid", Toast.LENGTH_SHORT).show();
        }

        mAddressInput.setText(homePage);
        mAddressInput.setSelection(mAddressInput.getText().length());
    }

    public void settings(@NonNull View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onMessage(@Nullable String message) {
        if (!TextUtils.isEmpty(message)) {
            mDebugOutput.append(message);
            mDebugOutput.append("\n");
        }
    }

    @Override
    public void onChanged(@Nullable String url) {
        if (url != null) {
            mAddressInput.setText(url);
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus
        final View view = this.getCurrentFocus();
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
