package com.yoyo.makeiteven;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelsViewHolder> {

    private List<Level> levelItems;

    private Context mContext;

    public LevelAdapter(List<Level> levelItems, Context context) {

        this.levelItems = levelItems;
        this.mContext = context;
    }

    @Override
    public LevelsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.level_cell, viewGroup, false );
        return new LevelsViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull LevelsViewHolder levelsViewHolder, final int i) {

        int currentStage = DataStore.getInstance( mContext ).getCurrentStage();

        final Level levelItem = levelItems.get( i );
        levelsViewHolder.stageNumberTv.setText( levelItem.getLevelNum() + "" );

        if (levelItem.getLevelNum() <= currentStage) {
            levelsViewHolder.stageNumberLayout.setVisibility( View.VISIBLE );
            levelsViewHolder.lockStage.setVisibility( View.INVISIBLE );
        } else {
            levelsViewHolder.lockStage.setVisibility( View.VISIBLE );
            levelsViewHolder.stageNumberLayout.setVisibility( View.INVISIBLE );

        }

        TiltEffectAttacher.attach( levelsViewHolder.btn );
        if (levelsViewHolder.lockStage.getVisibility() == View.INVISIBLE) {

            levelsViewHolder.btn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = StageGameMode.TYPE;
                    GameActivity.startGameActivity( mContext, type, levelItem.getLevelNum() );


                }
            } );
        }
    }

    @Override
    public int getItemCount() {
        return levelItems.size();
    }

    public class LevelsViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        View stageNumberLayout;
        View lockStage;
        TextView stageNumberTv;


        public LevelsViewHolder(View itemView) {
            super( itemView );
            btn = itemView.findViewById( R.id.level_btn );
            stageNumberLayout = itemView.findViewById( R.id.stage_number_layout );
            lockStage = itemView.findViewById( R.id.lock_iv );
            stageNumberTv = itemView.findViewById( R.id.stage_number_tv );
        }
    }

    public void setLevelItems(List<Level> levelItems) {
        this.levelItems = levelItems;
        notifyDataSetChanged();
    }
}