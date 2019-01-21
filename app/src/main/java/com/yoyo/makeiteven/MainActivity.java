package com.yoyo.makeiteven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView theNumber = findViewById(R.id.the_number);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button start = findViewById(R.id.start_btn);

        final List<Button> btns = new ArrayList<>();
        btns.add(btn1);
        btns.add(btn2);
        btns.add(btn3);
        btns.add(btn4);

        final Game game = new Game(5);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theNumber.setText(String.valueOf(game.gameGenerator(btns))+"  "+game.getHint());
            }
        });

    }

}


