package com.example.milionare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreListActivity extends AppCompatActivity {
    ListView scorelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score_list);
        getSupportActionBar().hide();
        setScorelist();
    }

    void setScorelist() {
        scorelist = (ListView) findViewById(R.id.score_list);
        SQLite sqLite = new SQLite(this);
        ScoreAdapter adapter = new ScoreAdapter(this, sqLite.getAllScoresHighest());
        scorelist.setAdapter(adapter);
    }

    public void filterScore(View view) {
        SQLite sqLite = new SQLite(this);
        ScoreAdapter adapter = new ScoreAdapter(this, sqLite.getAllScoresHighest());
        scorelist.setAdapter(adapter);
    }

    public void filterDate(View view) {
        SQLite sqLite = new SQLite(this);
        ArrayList<ScoreEntity> list= sqLite.getAllScores();
        Collections.reverse(list);
        ScoreAdapter adapter = new ScoreAdapter(this,list);
        scorelist.setAdapter(adapter);
    }

    public void clear(View view) {
        SQLite sqLite = new SQLite(this);
        sqLite.deleteAllScores();
        ScoreAdapter adapter = new ScoreAdapter(this, sqLite.getAllScoresHighest());
        scorelist.setAdapter(adapter);
    }
}
