package com.example.daquexian.flexiblerichtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daquexian.flexiblerichtextview.Attachment;
import com.daquexian.flexiblerichtextview.FlexibleRichTextView;
import com.daquexian.flexiblerichtextview.Tokenizer;

import org.scilab.forge.jlatexmath.core.AjLatexMath;

import java.util.ArrayList;
import java.util.List;

import io.github.kbiakov.codeview.classifier.CodeProcessor;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "yxx-MainActivity";
    private FlexibleRichTextView mLatexTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // train classifier on app start
        CodeProcessor.init(this);
        AjLatexMath.init(this); // init library: load fonts, create paint, etc.

        mLatexTextView = (FlexibleRichTextView) findViewById(R.id.frtv);
        mLatexTextView.post(new Runnable() {
            @Override
            public void run() {
                int measuredWidth = mLatexTextView.getMeasuredWidth();
                int measuredHeight = mLatexTextView.getMeasuredHeight();
                mLatexTextView.setTextSize(25);
                Log.d(TAG, "onResume: measuredWidth is: " + measuredWidth + "  measuredHeight is: " + measuredHeight);
                mLatexTextView.setTextLocation(measuredWidth /2, measuredHeight /2);
                mLatexTextView.setText("$\\dfrac {-b^{2}\\pm \\sqrt {b^{-}-4aL}} {4a_{L}-2}$");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}