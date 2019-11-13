package com.example.milionare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    QuestionService questionService;
    Button a, b, c, d;
    Button withdraw;
    ImageButton haflHelp;
    ImageButton audian;
    ImageButton callHelp;
    TextView countTime;
    TextView question;
    Button[] level = new Button[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        setObject();
        try {
            questionService = new QuestionService(this, a, b, c, d, question);
            questionService.setButtonScoreList(level);
            questionService.setTimeCounter(countTime);
            questionService.setHalfHelp(haflHelp);
            questionService.setCallHelp(callHelp);
            questionService.setAudianHelp(audian);
            questionService.setWithdraw(withdraw);
            questionService.setLevel(12);
            questionService.startGame();
        } catch (Exception e) {
        }
    }

    void setObject() {
        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        haflHelp = (ImageButton) findViewById(R.id.half_help);
        audian = (ImageButton) findViewById(R.id.audian_help);
        callHelp = (ImageButton) findViewById(R.id.call_help);
        question = (TextView) findViewById(R.id.quest_container);
        level[0] = (Button) findViewById(R.id.lev1);
        level[1] = (Button) findViewById(R.id.lev2);
        level[2] = (Button) findViewById(R.id.lev3);
        level[3] = (Button) findViewById(R.id.lev4);
        level[4] = (Button) findViewById(R.id.lev5);
        level[5] = (Button) findViewById(R.id.lev6);
        level[6] = (Button) findViewById(R.id.lev7);
        level[7] = (Button) findViewById(R.id.lev8);
        level[8] = (Button) findViewById(R.id.lev9);
        level[9] = (Button) findViewById(R.id.lev10);
        level[10] = (Button) findViewById(R.id.lev11);
        level[11] = (Button) findViewById(R.id.lev12);
        countTime = (TextView) findViewById(R.id.count_time);
        withdraw = (Button) findViewById(R.id.withdraw_btn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (questionService.mediaPlayer.isPlaying())
            questionService.mediaPlayer.stop();
        try {
            questionService.timer.cancel();
            questionService.countDownTimer.cancel();
        } catch (Exception e) {
            Log.e("Timer", e.toString());
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (questionService.mediaPlayer.isPlaying())
            questionService.mediaPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (questionService.mediaPlayer.isPlaying())
            questionService.mediaPlayer.stop();
        try {
            questionService.timer.cancel();
        } catch (Exception e) {
            Log.e("Timer", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (questionService.mediaPlayer.isPlaying())
            questionService.mediaPlayer.stop();
        try {
            questionService.timer.cancel();
        } catch (Exception e) {
            Log.e("Timer", e.toString());
        }
    }
}