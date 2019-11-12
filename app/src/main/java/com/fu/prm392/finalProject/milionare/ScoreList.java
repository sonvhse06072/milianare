package com.fu.prm392.finalProject.milionare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.fu.prm392.finalProject.milionare.Adapter.ScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreList extends AppCompatActivity {

    ListView scorelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score_list);
        getSupportActionBar().hide();
        gorselata();
    }


    void gorselata() {

        scorelist = (ListView) findViewById(R.id.skorlistview);

        SQLite sqLite = new SQLite(this);
        ScoreAdapter adapter = new ScoreAdapter(this, sqLite.getAllScoresHighest());
        scorelist.setAdapter(adapter);

    }

    public void skoragore(View view) {
        SQLite sqLite = new SQLite(this);
        ScoreAdapter adapter = new ScoreAdapter(this, sqLite.getAllScoresHighest());
        scorelist.setAdapter(adapter);
    }

    public void tarihegore(View view) {
        SQLite sqLite = new SQLite(this);
        ArrayList <ScoreEntity> list= sqLite.getAllScores();
        Collections.reverse(list);
        ScoreAdapter adapter = new ScoreAdapter(this,list);
        scorelist.setAdapter(adapter);
    }

    public void temizle(View view) {
        SQLite sqLite = new SQLite(this);
        sqLite.deleteAllScores();
        ScoreAdapter adapter = new ScoreAdapter(this, sqLite.getAllScoresHighest());
        scorelist.setAdapter(adapter);
    }
}
