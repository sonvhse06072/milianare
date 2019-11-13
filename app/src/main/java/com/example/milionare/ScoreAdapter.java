package com.example.milionare;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    ArrayList<ScoreEntity> scoreList;
    public ScoreAdapter(Activity activity, ArrayList<ScoreEntity> scoreList) {
        this.activity = activity;
        this.scoreList = scoreList;
        inflater = LayoutInflater.from(activity.getApplicationContext());
    }
    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int i) {
        return scoreList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =inflater.inflate(R.layout.rowscore,null);
        TextView money=(TextView)v.findViewById(R.id.money);
        TextView time=(TextView)v.findViewById(R.id.time);
        TextView point=(TextView)v.findViewById(R.id.point);

        money.setText(""+scoreList.get(i).getMoney()+" "+activity.getString(R.string.currency));
        time.setText(""+scoreList.get(i).getTime()+" "+activity.getString(R.string.second));
        point.setText(""+scoreList.get(i).getScore()+" "+activity.getString(R.string.point));
        return v;
    }
}
