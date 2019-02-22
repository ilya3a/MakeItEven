package com.yoyo.makeiteven;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreBoardAdapter extends ArrayAdapter<ScoreBoard> {

    Context mContext;

    public ScoreBoardAdapter(Context context, int resource, List<ScoreBoard> items) {
        super(context, resource, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.score_cell, null);
        }
        ScoreBoard item = getItem(position);

        TextView tvName = v.findViewById(R.id.name_txt);
        TextView tvScore = v.findViewById(R.id.score_txt);

        if (position == 0) {

            tvName.setTextColor(mContext.getResources().getColor(R.color.blue));
            tvScore.setTextColor(mContext.getResources().getColor(R.color.blue));
            if (Build.VERSION.SDK_INT >= 23) {
                tvName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                tvScore.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            }
        } else {
            tvName.setTextColor(mContext.getResources().getColor(R.color.primaryTextColor));
            tvScore.setTextColor(mContext.getResources().getColor(R.color.primaryTextColor));
        }

        tvName.setText(position + 1 + "   " + item.getmNickName());

        tvScore.setText(item.getmFinalScore() + " ");
        return v;
    }
}

