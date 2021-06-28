package com.example.forgeutils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class Beijing extends AppCompatActivity {
    public  static ClipboardManager clipboard = null;
    EditText editTextInput;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beijing);
        editTextInput = findViewById(R.id.editTextInput);
        textView2 = findViewById(R.id.textView2);
    }

    public void processMessage(View view) {
        String origin = editTextInput.getText().toString();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<origin.length(); i++) {
            char word = origin.charAt(i);
            stringBuilder.append(word);
            if(isChinese(word) && word != '儿') {
                stringBuilder.append('儿');
            }
        }
        textView2.setText(stringBuilder.toString());
    }
    public boolean isChinese(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
            return true;
        }

        return false;
    }

    public class ClipboardTools {
        public void copyTextToClipboard(final Context activity, final String str) {
            if (Looper.myLooper() == null){
                Looper.prepare();
            }
            clipboard = (ClipboardManager) activity.getSystemService(Activity.CLIPBOARD_SERVICE);

            ClipData textCd = ClipData.newPlainText("data", str);
            clipboard.setPrimaryClip(textCd);
        }
    }

    public void clickToCopy(View view) {
        ClipboardTools clipboardTools = new ClipboardTools();
        clipboardTools.copyTextToClipboard(view.getContext(),textView2.getText().toString());
    }
}