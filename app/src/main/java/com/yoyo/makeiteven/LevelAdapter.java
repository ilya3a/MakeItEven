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
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.level, viewGroup, false );
        return new LevelsViewHolder( view );
    }

    @Override
    public void onBindViewHolder(LevelsViewHolder levelsViewHolder, final int i) {

        if (levelItems.get( i ).isFinished()){

        }


        final Level_item levelItem = levelItems.get( i );
        levelsViewHolder.btn.setText( levelItem.getLevelNum() + "" );
        TiltEffectAttacher.attach( levelsViewHolder.btn );


        levelsViewHolder.btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( mContext, GameActivity.class );
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation( (Activity) mContext, null );
                mContext.startActivity( intent, compat.toBundle() );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return levelItems.size();
    }

    public class LevelsViewHolder extends RecyclerView.ViewHolder {

        Button btn;


        public LevelsViewHolder(View itemView) {
            super( itemView );
            btn = itemView.findViewById( R.id.level_btn );
        }
    }

    public void setLevelItems(List<Level_item> levelItems) {
        this.levelItems = levelItems;
        notifyDataSetChanged();
    }
}