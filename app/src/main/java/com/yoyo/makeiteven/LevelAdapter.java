package com.yoyo.makeiteven;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelsViewHolder> {

    private List<Level_item> levelItems;
    Context mContext;

    public LevelAdapter(List<Level_item> levelItems, Context context) {

        this.levelItems = levelItems;
        this.mContext = context;
    }


    @Override
    public LevelsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.level, viewGroup, false);
        return new LevelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LevelsViewHolder levelsViewHolder, final int i) {

        final Level_item levelItem = levelItems.get(i);
        levelsViewHolder.btn.setText(levelItem.getLevelNum() + "");
        levelsViewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT>=21){
//                Explode explode = new Explode();
//                final Rect viewRect = new Rect();
//                v.getGlobalVisibleRect(viewRect);
//                explode.setEpicenterCallback(new Transition.EpicenterCallback() {
//                    @Override
//                    public Rect onGetEpicenter(Transition transition) {
//                        return viewRect;
//                    }
//                });}
                Intent intent = new Intent(mContext, GameActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, null);
                mContext.startActivity(intent,compat.toBundle());
            }
        });

//        levelsViewHolder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View clickedView) {
//                // save rect of view in screen coordinates
//                final Rect viewRect = new Rect();
//                clickedView.getGlobalVisibleRect(viewRect);
//
//                // create Explode transition with epicenter
//                Explode explode = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    explode = new Explode();
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    explode.setEpicenterCallback(new Transition.EpicenterCallback() {
//                        @Override
//                        public Rect onGetEpicenter(Transition transition) {
//                            return viewRect;
//                        }
//                    });
//                }
//                explode.setDuration(1000);
//                RecyclerView recyclerView = ;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    TransitionManager.beginDelayedTransition(recyclerView, explode);
//                }
//
//                // remove all views from Recycler View
//                recyclerView.setAdapter(null);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return levelItems.size();
    }

    public class LevelsViewHolder extends RecyclerView.ViewHolder {

        Button btn;
        LinearLayout layout;


        public LevelsViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.level_btn);
//            layout = itemView.findViewById(R.id.my_level_cube);


        }
    }

    public void setLevelItems(List<Level_item> levelItems) {
        this.levelItems = levelItems;
        notifyDataSetChanged();
    }
}