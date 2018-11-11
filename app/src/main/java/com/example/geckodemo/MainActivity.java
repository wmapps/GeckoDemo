package com.example.geckodemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

public class MainActivity extends AppCompatActivity {

    private GeckoSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GeckoRuntime runtime = GeckoRuntime.create(this);
        mSession = new GeckoSession();
        mSession.open(runtime);

        final GeckoView view = findViewById(R.id.main_gecko_view);
        view.setSession(mSession);

        final EditText editText = findViewById(R.id.main_address_edit_text);
        editText.setText(R.string.test_url);
        editText.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ||
                 actionId == EditorInfo.IME_ACTION_GO)) {
                hideKeyboard();
                mSession.loadUri(v.getText().toString());
            }

            return false;
        });
    }

    public void goBack(View view) {
        mSession.goBack();
    }

    public void goForward(View view) {
        mSession.goForward();
    }

    public void reload(View view) {
        mSession.reload();
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
