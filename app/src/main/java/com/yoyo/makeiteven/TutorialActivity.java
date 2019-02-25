package com.yoyo.makeiteven;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class TutorialActivity extends Activity {
    private ImageView totorialImageView;
    private ImageSwitcher totorialImageSwicher;
    private TextView totorialTextview;
    private Button nextBtn, backBtn;
    private int progresCounter = 0, exitflag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        totorialImageSwicher = findViewById(R.id.totorialImageSwicher);
        totorialTextview = findViewById(R.id.totorial_textview);
        nextBtn = findViewById(R.id.next_btn_ib);
        backBtn = findViewById(R.id.back_btn);
        totorialImageSwicher.setClickable(true);
        totorialImageSwicher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exitflag == 1) {
                    onBackPressed();
                } else
                    nextBtn.callOnClick();
            }
        });
        final Integer[] images = {R.drawable.wise_owl, R.drawable.tutorial_1, R.drawable.tutorial_2, R.drawable.tutorial_3, R.drawable.tutorial_4,
                R.drawable.tutorial_5, R.drawable.tutorial_6, R.drawable.tutorial_7, R.drawable.tutorial_8, R.drawable.tutorial_9,
                R.drawable.tutorial_10, R.drawable.tutorial_11, R.drawable.tutorial_12};

        final Resources resources = getResources();
        final String[] strings_for_totorial = resources.getStringArray(R.array.tutorial_string_array);

        totorialImageSwicher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totorialImageSwicher.setImageResource(images[progresCounter - 1]);
                totorialTextview.setText(strings_for_totorial[progresCounter - 1]);
                progresCounter--;
                if (progresCounter < 12) {
                    nextBtn.setText(getResources().getString(R.string.next));
                    exitflag = 0;
                }
                if (progresCounter == 0)
                    backBtn.setVisibility(View.GONE);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exitflag == 1)
                    onBackPressed();
                else {
                    totorialImageSwicher.setImageResource(images[progresCounter + 1]);
                    totorialTextview.setText(strings_for_totorial[progresCounter + 1]);
                    progresCounter++;
                    if (progresCounter == 12) {
                        nextBtn.setText(getResources().getString(R.string.finish));
                        exitflag = 1;
                    }
                    if (progresCounter == 1)
                        backBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        totorialImageSwicher.setImageResource(images[progresCounter]);
        totorialTextview.setText(strings_for_totorial[progresCounter]);

    }
}
