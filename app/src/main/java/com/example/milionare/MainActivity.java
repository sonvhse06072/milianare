package com.example.milionare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    ImageView logo;
    Switch voice;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        voice = (Switch) findViewById(R.id.openvoice);
        getSupportActionBar().hide();
        mp = MediaPlayer.create(this, R.raw.anaekran);
        RotateNow();
        sharedPreferences = getSharedPreferences("ses", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edit.putString("ses", "ok");
                    mp.start();
                    edit.commit();
                } else if (!b) {
                    edit.putString("ses", "no");
                    mp.stop();
                    edit.commit();
                }
            }
        });
        if (sharedPreferences.getString("ses", "").equals("ok")) {
            mp.start();
            voice.setChecked(true);
        } else voice.setChecked(false);
    }

    public void startGame(View view) {
        mp.stop();
        startActivity(new Intent(MainActivity.this, GameActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mp.isPlaying()) {
            mp.stop();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mp.isPlaying()) {
            mp.stop();
        }
    }

    public void highScore(View view) {
        startActivity(new Intent(MainActivity.this, ScoreListActivity.class));
    }

    public void RotateNow() {
        logo = (ImageView) findViewById(R.id.logoOnload);
        RotateAnimation rotate = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(15000);
        rotate.setInterpolator(new LinearInterpolator());
        logo.startAnimation(rotate);
    }

    public void quit(View view) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
