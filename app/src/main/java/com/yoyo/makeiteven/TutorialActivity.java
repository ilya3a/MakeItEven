package com.yoyo.makeiteven;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class TutorialActivity extends Activity {
    private ImageView totorialImageView;
    private ImageSwitcher totorialImageSwicher;
    private TextView totorialTextview;
    private Button nextBtn,backBtn;
    private int progresCounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        totorialImageSwicher=findViewById(R.id.totorialImageSwicher);
        totorialTextview=findViewById(R.id.totorial_textview);
        nextBtn=findViewById(R.id.next_btn);
        backBtn=findViewById(R.id.back_btn);

        final Integer[] images={R.drawable.wise_owl,R.drawable.totorial1,R.drawable.totorial2,R.drawable.totorial3,R.drawable.totorial4,
                R.drawable.totorial5,R.drawable.totorial6,R.drawable.totorial7,R.drawable.totorial8,R.drawable.totorial9};
        final String[] strings_for_totorial={"Its easy to learn math with us,you have a simple goal to reach the big black number,press next to continu",
        "now you will chose a number,lets say 6","chose an operator,lets try plus","now after we will chose the second number see what happens,you can try 5","cool we have 11 now",
                "now we also can chose operator first,lets pick minus","now we can chose a number,this time we will take 11","and now the second number to minus from 11,lets go with 5","cool,we are almost there,now will chose both number and operator,we are trying to crate 8-5 to get to 3",
                "minus them up,Good job! you finishd the totorial and got to the target number,hope you will have fun"
        };
        totorialImageSwicher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totorialImageSwicher.setImageResource(images[progresCounter-1]);
                totorialTextview.setText(strings_for_totorial[progresCounter-1]);
                progresCounter--;
                if (progresCounter<9)
                    nextBtn.setVisibility(View.VISIBLE);
                if (progresCounter==0)
                    backBtn.setVisibility(View.GONE);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totorialImageSwicher.setImageResource(images[progresCounter+1]);
                totorialTextview.setText(strings_for_totorial[progresCounter+1]);
                progresCounter++;
                if (progresCounter==9)
                    nextBtn.setVisibility(View.GONE);
                if (progresCounter==1)
                    backBtn.setVisibility(View.VISIBLE);
            }
        });
        totorialImageSwicher.setImageResource(images[progresCounter]);
        totorialTextview.setText(strings_for_totorial[progresCounter]);


//
//        totorialImageView=findViewById(R.id.totorialImageView);
//
//        totorialAnimation = (AnimationDrawable) totorialImageView.getBackground();
//        totorialAnimation.start();
    }
}
