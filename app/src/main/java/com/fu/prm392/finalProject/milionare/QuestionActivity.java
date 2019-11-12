package com.fu.prm392.finalProject.milionare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class QuestionActivity extends AppCompatActivity {
    QuestionService questionService;
    Button a, b, c, d;
    Button witdraw;
    ImageButton yariyariya;
    ImageButton telefonla;
    ImageButton seyircisor;
    TextView soru;
    TextView suregosterici;
    Button[] parabuttonlari = new Button[12];
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen);
        getSupportActionBar().hide();
        gorselata();
        try {
            questionService = new QuestionService(this, a, b, c, d, soru);
            questionService.setParabutton(parabuttonlari);
            questionService.setZamansayaci(suregosterici);
            questionService.setYariyariya(yariyariya);
            questionService.setTelefonla(telefonla);
            questionService.setSeyircisor(seyircisor);
            questionService.setCekilbuttonu(witdraw);
            questionService.setSoruadet(12);
            questionService.oyunOyna();
        } catch (Exception e) {
            Log.e("HATA ALDIM", e.toString());
        }
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.e("TAG", "EROOR REKLAM VS.");
        }
    }

    void gorselata() {
        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        seyircisor = (ImageButton) findViewById(R.id.seyircisor);
        telefonla = (ImageButton) findViewById(R.id.telefonla);
        yariyariya = (ImageButton) findViewById(R.id.yariyariya);
        soru = (TextView) findViewById(R.id.sorucontainer);
        parabuttonlari[0] = (Button) findViewById(R.id.para1);
        parabuttonlari[1] = (Button) findViewById(R.id.para2);
        parabuttonlari[2] = (Button) findViewById(R.id.para3);
        parabuttonlari[3] = (Button) findViewById(R.id.para4);
        parabuttonlari[4] = (Button) findViewById(R.id.para5);
        parabuttonlari[5] = (Button) findViewById(R.id.para6);
        parabuttonlari[6] = (Button) findViewById(R.id.para7);
        parabuttonlari[7] = (Button) findViewById(R.id.para8);
        parabuttonlari[8] = (Button) findViewById(R.id.para9);
        parabuttonlari[9] = (Button) findViewById(R.id.para10);
        parabuttonlari[10] = (Button) findViewById(R.id.para11);
        parabuttonlari[11] = (Button) findViewById(R.id.para12);
        suregosterici = (TextView) findViewById(R.id.suregosterici);
        witdraw = (Button) findViewById(R.id.cekil);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (questionService.mediaPlayer.isPlaying())
            questionService.mediaPlayer.stop();
        try {
            questionService.sureakisi.cancel();
            questionService.countDownTimer.cancel();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.e("TAG", "EROOR REKLAM VS.");
            }
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
            questionService.sureakisi.cancel();
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
            questionService.sureakisi.cancel();
        } catch (Exception e) {
            Log.e("Timer", e.toString());
        }
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.e("TAG", "EROOR REKLAM VS.");
        }

    }




}
