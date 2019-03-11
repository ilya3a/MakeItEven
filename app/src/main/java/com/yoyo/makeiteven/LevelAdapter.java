package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.jinatonic.confetti.CommonConfetti;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelsViewHolder> {

    private List<Level> levelItems;
    private int mCurrentStage;
    private Context mContext;

    public LevelAdapter(List<Level> levelItems, Context context, int currentStage) {

        this.levelItems = levelItems;
        this.mContext = context;
        this.mCurrentStage = currentStage;
    }

    @Override
    public LevelsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.level_cell, viewGroup, false);
        return new LevelsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LevelsViewHolder levelsViewHolder, final int i) {
        final Level levelItem = levelItems.get(i);
        levelsViewHolder.stageNumberTv.setText(levelItem.getLevelNum() + "");
        if (levelItem.getLevelNum() == 0) {
            levelsViewHolder.stageNumberLayout.setVisibility(View.VISIBLE);
            levelsViewHolder.lockStage.setVisibility(View.INVISIBLE);
            levelsViewHolder.stageNumberTv.setText(R.string.tutorial);
            levelsViewHolder.stageNumberTv.setTextSize(21);
        } else if (levelItem.getLevelNum() <= mCurrentStage) {
            levelsViewHolder.stageNumberLayout.setVisibility(View.VISIBLE);
            levelsViewHolder.lockStage.setVisibility(View.INVISIBLE);
            // anim

        } else {
            levelsViewHolder.lockStage.setVisibility(View.VISIBLE);
            levelsViewHolder.stageNumberLayout.setVisibility(View.INVISIBLE);
        }

        final Animation btn_press = AnimationUtils.loadAnimation(mContext, R.anim.btn_pressed);
        final Animation btn_release = AnimationUtils.loadAnimation(mContext, R.anim.btn_realeas);
        final View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(btn_release);
                }
                return false;
            }
        };
        if (levelsViewHolder.lockStage.getVisibility() == View.INVISIBLE) {
            levelsViewHolder.btn.setOnTouchListener(btn_animation);
            levelsViewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String type = StageGameMode.TYPE;
                    GameActivity.startGameActivity(mContext, type, levelItem.getLevelNum());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return levelItems.size();
    }

    public class LevelsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rootLayout;
        Button btn;
        View stageNumberLayout;
        View lockStage;
        TextView stageNumberTv;


        public LevelsViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.level_btn);
            stageNumberLayout = itemView.findViewById(R.id.stage_number_layout);
            lockStage = itemView.findViewById(R.id.lock_iv);
            stageNumberTv = itemView.findViewById(R.id.stage_number_tv);
            rootLayout = itemView.findViewById(R.id.root_layout_level_cell);
        }
    }

    public void setLevelItems(List<Level> levelItems) {
        this.levelItems = levelItems;
        notifyDataSetChanged();
    }
}