package com.example.milionare;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionService {
    int delayTime = 2500;
    static Activity activity;
    ArrayList<QuestionEntity> eazyQuest = new ArrayList<>();
    ArrayList<QuestionEntity> mediumQuest = new ArrayList<>();
    ArrayList<QuestionEntity> hardQuest = new ArrayList<>();
    TextView questionContainer;
    Button a;
    Button b;
    Button c;
    Button d;
    TextView timeCounter;
    QuestionEntity quest;
    boolean help50percent = true;
    ImageButton halfHelp;
    ImageButton callHelp;
    ImageButton audianHelp;
    static String supporter;
    static String audianAnswer;
    int level = 0;
    static int specialLevel = 0;
    static String moneyEarn;
    Button[] buttonScoreList;

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHalfHelp(ImageButton halfHelp) {
        this.halfHelp = halfHelp;
    }

    public void setCallHelp(ImageButton callHelp) {
        this.callHelp = callHelp;
    }

    public void setAudianHelp(ImageButton audianHelp) {
        this.audianHelp = audianHelp;
    }

    public void setTimeCounter(TextView timeCounter) {
        this.timeCounter = timeCounter;
    }

    public void setButtonScoreList(Button[] buttonScoreList) {
        this.buttonScoreList = buttonScoreList;
    }

    public void setWithdraw(Button withdraw) {
        this.withdraw = withdraw;
    }

    static String moneyInLevel = "0";
    public static int questionPass = 0;
    public static MediaPlayer mediaPlayer;
    public static CountDownTimer countDownTimer;
    public static Timer timer = new Timer();
    Button withdraw;
    int index;

    public QuestionService(final Activity activity, Button a, Button b, Button c, Button d, TextView questionContainer) {
        this.activity = activity;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        eazyQuest = getEazyQuest();
        mediumQuest = getMediumQuest();
        hardQuest = getHardQuest();
        this.questionContainer = questionContainer;
        mediaPlayer = MediaPlayer.create(activity, R.raw.oyunbaslangic);
        SharedPreferences sharedPreferences=activity.getSharedPreferences("ses", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("ses","").equals("ok")) {
            mediaPlayer.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(activity, R.raw.soruekranigenel);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setVolume(0.3f, 0.3f);
                }
            }, 2500);
        }
    }


    public void startGame() {
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmWithdraw();
            }
        });
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                questionPass++;
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(t, 1000, 1000);
        takeQuest(0, new Answer() {
            @Override
            public void correctAnswer() {
            }
            @Override
            public void wrongAnswer() {
                gameOver();
            }
        });
    }

    public ArrayList<QuestionEntity> getEazyQuest() {
        ArrayList<QuestionEntity> questList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream in_s = activity.getApplicationContext().getAssets().open("easyquestions.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            questList = parseQuestion(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questList);
        return questList;
    }

    private ArrayList<QuestionEntity> parseQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
        String text = "";
        ArrayList<QuestionEntity> questList = new ArrayList<>();
        int eventType = parser.getEventType();
        QuestionEntity quest = null;
        Map<String, String> answer = new HashMap<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagname = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("questcontainer")) {
                        quest = new QuestionEntity();
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("questcontainer")) {
                        quest.setAnswer(answer);
                        answer = new HashMap<>();
                        questList.add(quest);
                    } else if (tagname.equalsIgnoreCase("quest")) {
                        quest.setQuestion(text);
                    } else if (tagname.equalsIgnoreCase("a")) {
                        answer.put("1", text);
                    } else if (tagname.equalsIgnoreCase("b")) {
                        answer.put("2", text);
                    } else if (tagname.equalsIgnoreCase("c")) {
                        answer.put("3", text);
                    } else if (tagname.equalsIgnoreCase("d")) {
                        answer.put("4", text);
                    } else if (tagname.equalsIgnoreCase("answer")) {
                        quest.setCorrectAnswer(text);
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return questList;
    }

    public ArrayList<QuestionEntity> getMediumQuest() {
        ArrayList<QuestionEntity> questList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream in_s = activity.getApplicationContext().getAssets().open("normalquestions.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            questList = parseQuestion(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questList);
        return questList;
    }

    public ArrayList<QuestionEntity> getHardQuest() {
        ArrayList<QuestionEntity> questList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream in_s = activity.getApplicationContext().getAssets().open("hardquestions.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            questList = parseQuestion(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questList);
        return questList;
    }

    void takeQuest(int questNo, final Answer answer) {
        index = questNo;
        boolean res = false;
        if (index < 4) quest = eazyQuest.get(index);
        else if (index > 3 && index < 8) quest = mediumQuest.get(index - 4);
        else quest = hardQuest.get(index - 8);
        Map<String, String> ans = new HashMap<>();
        ans = quest.getAnswer();
        HalfHelp(halfHelp, quest.getCorrectAnswer());
        CallHelp(quest.getCorrectAnswer(), callHelp);
        AskAudian(audianHelp, quest.getCorrectAnswer());
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        a.setText("A:  " + ans.get("1"));
        b.setText("B:  " + ans.get("2"));
        c.setText("C:  " + ans.get("3"));
        d.setText("D:  " + ans.get("4"));
        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        d.setEnabled(true);
        final CountDownTimer ct = CountDown();
        ct.start();
        moneyEarn = setMoney(index);
        a.setBackground(activity.getDrawable(R.drawable.sorunormalcevap));
        a.setTextColor(Color.WHITE);
        b.setBackground(activity.getDrawable(R.drawable.sorunormalcevap));
        b.setTextColor(Color.WHITE);
        c.setBackground(activity.getDrawable(R.drawable.sorunormalcevap));
        c.setTextColor(Color.WHITE);
        d.setBackground(activity.getDrawable(R.drawable.sorunormalcevap));
        d.setTextColor(Color.WHITE);
        questionContainer.setText(quest.getQuestion());
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWaitDelay(R.raw.sikisaretleme);
                ChangeColorAnswer(quest.getCorrectAnswer());
                ct.cancel();
                a.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
                d.setEnabled(false);
                a.setBackground(activity.getDrawable(R.drawable.soruaktifsari));
                a.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (a.getTag().equals(quest.getCorrectAnswer())) {
                            SetWaitDelay(R.raw.dogrucevap);
                            a.setBackground(activity.getDrawable(R.drawable.sorudogrucevap));
                            a.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.correctAnswer();
                                    if (index < (level - 1)) {
                                        index++;
                                        takeQuest(index, new Answer() {
                                            @Override
                                            public void correctAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                gameOver();
                                            }
                                        });
                                    } else {
                                        gameOverFinal();
                                    }
                                }
                            }, delayTime);
                        } else {
                            SetWait(R.raw.yanliscevap);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.wrongAnswer();

                                }
                            }, delayTime);
                            a.setBackground(activity.getDrawable(R.drawable.yanliscevap));
                            a.setTextColor(Color.WHITE);
                        }
                    }
                }, delayTime);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWaitDelay(R.raw.sikisaretleme);
                ChangeColorAnswer(quest.getCorrectAnswer());
                ct.cancel();
                b.setEnabled(false);
                a.setEnabled(false);
                c.setEnabled(false);
                d.setEnabled(false);
                b.setBackground(activity.getDrawable(R.drawable.soruaktifsari));
                b.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (b.getTag().equals(quest.getCorrectAnswer())) {
                            SetWaitDelay(R.raw.dogrucevap);
                            b.setBackground(activity.getDrawable(R.drawable.sorudogrucevap));
                            b.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.correctAnswer();
                                    if (index < (level - 1)) {
                                        index++;
                                        takeQuest(index, new Answer() {
                                            @Override
                                            public void correctAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                gameOver();
                                            }
                                        });
                                    } else {
                                        gameOverFinal();
                                    }
                                }
                            }, delayTime);
                        } else {
                            SetWait(R.raw.yanliscevap);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.wrongAnswer();
                                }
                            }, delayTime);
                            b.setBackground(activity.getDrawable(R.drawable.yanliscevap));
                            b.setTextColor(Color.WHITE);
                        }
                    }
                }, delayTime);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWaitDelay(R.raw.sikisaretleme);

                ChangeColorAnswer(quest.getCorrectAnswer());
                ct.cancel();
                c.setEnabled(false);
                b.setEnabled(false);
                a.setEnabled(false);
                d.setEnabled(false);
                c.setBackground(activity.getDrawable(R.drawable.soruaktifsari));
                c.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (c.getTag().equals(quest.getCorrectAnswer())) {
                            SetWaitDelay(R.raw.dogrucevap);
                            c.setBackground(activity.getDrawable(R.drawable.sorudogrucevap));
                            c.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.correctAnswer();
                                    if (index < (level - 1)) {
                                        index++;
                                        takeQuest(index, new Answer() {
                                            @Override
                                            public void correctAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                gameOver();
                                            }
                                        });
                                    } else {
                                        gameOverFinal();
                                    }
                                }
                            }, delayTime);
                        } else {
                            SetWait(R.raw.yanliscevap);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.wrongAnswer();
                                }
                            }, delayTime);
                            c.setBackground(activity.getDrawable(R.drawable.yanliscevap));
                            c.setTextColor(Color.WHITE);
                        }
                    }
                }, delayTime);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWaitDelay(R.raw.sikisaretleme);
                ChangeColorAnswer(quest.getCorrectAnswer());
                ct.cancel();
                d.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
                a.setEnabled(false);
                d.setBackground(activity.getDrawable(R.drawable.soruaktifsari));
                d.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (d.getTag().equals(quest.getCorrectAnswer())) {
                            SetWaitDelay(R.raw.dogrucevap);
                            d.setBackground(activity.getDrawable(R.drawable.sorudogrucevap));
                            d.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            answer.correctAnswer();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (index < (level - 1)) {
                                        index++;
                                        takeQuest(index, new Answer() {
                                            @Override
                                            public void correctAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                gameOver();
                                            }
                                        });
                                    } else {
                                        gameOverFinal();
                                    }
                                }
                            }, delayTime);
                        } else {
                            SetWait(R.raw.yanliscevap);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answer.wrongAnswer();
                                }
                            }, delayTime);
                            d.setBackground(activity.getDrawable(R.drawable.yanliscevap));
                            d.setTextColor(Color.WHITE);
                        }
                    }
                }, delayTime);
            }
        });
    }

    public CountDownTimer CountDown() {
        countDownTimer = new CountDownTimer(24000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeCounter.setText("" + millisUntilFinished / 1000);
                if (timeCounter.getText().equals("1")) {
                    gameOver();
                }
            }
            public void onFinish() {
            }
        };
        return countDownTimer;
    }

    public static void gameOver() {
        FragmentManager fm = activity.getFragmentManager();
        EndGame dialogFragment = new EndGame();
        try{dialogFragment.show(fm, "Sample Fragment");
        }
        catch (Exception e){

        }
    }

    public void confirmWithdraw() {
        FragmentManager fm = activity.getFragmentManager();
        WithdrawDialog dialogFragment = new WithdrawDialog();
        dialogFragment.show(fm, "Sample Fragment");
    }

    public static class WithdrawDialog extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.confirm_withdraw, container, false);
            getDialog().setTitle("Confirm dialog");
            Button yes = (Button) rootView.findViewById(R.id.btnYes);
            Button no = (Button) rootView.findViewById(R.id.btnNo);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    specialLevel = 1;
                    gameOver();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });


            return rootView;
        }
    }

    public static void gameOverFinal() {
        FragmentManager fm = activity.getFragmentManager();
//        mediaPlayer.stop();
        EndGameFinal dialogFragment = new EndGameFinal();
        dialogFragment.show(fm, "Sample Fragment");
    }

    public String setMoney(int QQ) {
        String money = "0/0";
        Log.e("Kazanılan tutar çarpmı:", "" + QQ);
        for (int i = 0; i < buttonScoreList.length; i++) {

            if (i == QQ) {
                buttonScoreList[i].setBackground(activity.getDrawable(R.drawable.para_aktifsira));
            } else if ((i == 1 || i == 6 || i == 10) && i != QQ) {
                buttonScoreList[i].setBackground(activity.getDrawable(R.drawable.para_barajsira));
            } else {
                buttonScoreList[i].setBackground(activity.getDrawable(R.drawable.para_normalsira));
            }

        }
        switch (QQ) {
            case 0:
                money = "0";
                moneyInLevel = "0";
                break;
            case 1:
                money = "500";
                moneyInLevel = "0";
                break;
            case 2:
                money = "1.000";
                moneyInLevel = "1.000";
                break;
            case 3:
                money = "2.000";
                moneyInLevel = "1.000";
                break;
            case 4:
                moneyInLevel = "1.000";
                money = "3.000";
                break;
            case 5:
                moneyInLevel = "1.000";
                money = "5.000";
                break;
            case 6:
                moneyInLevel = "1.000";
                money = "7.500";
                break;
            case 7:
                moneyInLevel = "15.000";
                money = "15.000";
                break;
            case 8:
                moneyInLevel = "15.000";
                money = "30.000";
                break;
            case 9:
                moneyInLevel = "15.000";
                money = "60.000";
                break;
            case 10:
                moneyInLevel = "15.000";
                money = "125.000";
                break;
            case 11:
                moneyInLevel = "250.000";
                money = "250.000";
                break;

        }

        return money;
    }

    public static class EndGame extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.score, container, false);
            getDialog().setTitle("GameOver");
            TextView money = (TextView) rootView.findViewById(R.id.money);
            String earn = "";
            if (specialLevel == 1)
                earn = moneyEarn;
            else if (specialLevel == 0)
                earn = moneyInLevel;
            countDownTimer.cancel();
            money.setText(earn + " " + "$");

            mediaPlayer.stop();
            timer.cancel();
            ScoreEntity scoreEntity = new ScoreEntity(earn, questionPass);
            TextView noti = (TextView) rootView.findViewById(R.id.noti);
            noti.setText(getActivity().getResources().getString(R.string.game_over));
            this.setCancelable(false);
            SQLite sqLite = new SQLite(activity);
            sqLite.addScore(scoreEntity);
            Button menu = (Button) rootView.findViewById(R.id.menuBtn);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            return rootView;
        }
    }

    public static class EndGameFinal extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.score, container, false);
            getDialog().setTitle("Game Over");
            TextView money = (TextView) rootView.findViewById(R.id.money);
            String earn = "1.000.000";
            countDownTimer.cancel();
            money.setText(earn + " " + getActivity().getResources().getString(R.string.currency));
            mediaPlayer.stop();
            timer.cancel();
            ScoreEntity scoreEntity = new ScoreEntity(earn, questionPass);
            TextView noti = (TextView) rootView.findViewById(R.id.noti);
            noti.setText(getActivity().getResources().getString(R.string.game_over_final));
            this.setCancelable(false);
            SQLite sqLite = new SQLite(activity);
            sqLite.addScore(scoreEntity);
            Button menu = (Button) rootView.findViewById(R.id.menuBtn);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            return rootView;
        }
    }

    public interface Answer {
        void correctAnswer();

        void wrongAnswer();
    }

    public void HalfHelp(final ImageButton btnHalf, final String correctAnswer) {

        btnHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (help50percent) {
                    help50percent = false;
                    btnHalf.setEnabled(false);
                    btnHalf.setImageDrawable(activity.getResources().getDrawable(R.drawable.yokelli));
                    ArrayList<Button> buttonList = new ArrayList<Button>();
                    buttonList.add(a);
                    buttonList.add(b);
                    buttonList.add(c);
                    buttonList.add(d);
                    ArrayList<Button> randomList = new ArrayList<Button>();
                    for (int i = 0; i < buttonList.size(); i++) {
                        if (!buttonList.get(i).getTag().equals(correctAnswer)) {
                            randomList.add(buttonList.get(i));
                        }
                    }
                    Collections.shuffle(randomList);
                    randomList.get(0).setText("");
                    randomList.get(1).setText("");
                }
            }
        });

    }

    public void CallHelp(final String ans, final ImageButton callBtn) {
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String helper = "....";
                String helperAnswer;
                if (ans.equals("1")) helperAnswer = "A";
                else if (ans.equals("2")) helperAnswer = "B";
                else if (ans.equals("3")) helperAnswer = "C";
                else helperAnswer = "D";
                Random random = new Random();
                int i = random.nextInt(5);
                switch (i) {

                    case 0:
                        helper = "I dont know the answer sorry :(. Good luck.";
                        break;

                    case 1:
                        helper = "I searched it on Google ^^ definetly " + helperAnswer;
                        break;

                    case 2:
                        helper = "I am not sure but seems to be " + helperAnswer + ".";
                        break;

                    case 3:
                        helper = "If I were you i would say " + helperAnswer + ".";
                        break;

                    case 4:
                        helper = "I will say " + helperAnswer + ".";
                        break;
                }
                supporter = helper;
                FragmentManager fm = activity.getFragmentManager();
                CallHelper dialogFragment = new CallHelper();
                dialogFragment.show(fm, "caller");
                callBtn.setEnabled(false);
                callBtn.setImageDrawable(activity.getResources().getDrawable(R.drawable.yoktelefon));
            }
        });

    }

    public static class CallHelper extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.call, container, false);
            getDialog().setTitle("Calling supporter...");
            Button thank = (Button) rootView.findViewById(R.id.thankbtn);
            TextView answer = (TextView) rootView.findViewById(R.id.answerLayout);
            answer.setText(supporter);
            thank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });
            return rootView;
        }
    }

    public void AskAudian(final ImageButton btnAudian, String cevap) {
        audianAnswer = cevap;
        btnAudian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = activity.getFragmentManager();
                Audian dialogFragment = new Audian();
                dialogFragment.show(fm, "Audian");
                btnAudian.setEnabled(false);
                btnAudian.setImageDrawable(activity.getResources().getDrawable(R.drawable.yokseyirci));
            }
        });
    }

    public static class Audian extends DialogFragment {
        ImageView ia, ib, ic, id;
        ImageView answerCol;
        Button dismissss;
        int total = 10;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.advicer, container, false);
            getDialog().setTitle("Asking audian...");
            gorselata(rootView);

            ArrayList<ImageView> listView = new ArrayList<ImageView>();
            listView.add(ia);
            listView.add(ib);
            listView.add(ic);
            listView.add(id);
            ArrayList<ImageView> xd = new ArrayList<ImageView>();
            for (int i = 0; i < listView.size(); i++) {
                if (listView.get(i).getTag().equals(audianAnswer)) {
                    answerCol = listView.get(i);
                    Random r = new Random();
                    int Low = 4;
                    int High = 8;
                    int Result = r.nextInt(High - Low) + Low;
                    total = total - Result;
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                            answerCol.getLayoutParams();
                    params.weight = (float) Result;
                    answerCol.setLayoutParams(params);
                } else {
                    xd.add(listView.get(i));
                }
            }
            int[] gg = randomCol(total, 3);
            for (int i = 0; i < xd.size(); i++) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        xd.get(i).getLayoutParams();
                params.weight = (float) gg[i];
                xd.get(i).setLayoutParams(params);
            }

            dismissss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            return rootView;
        }

        public static int[] randomCol(int number, int number_of_parts) {
            Random r = new Random();
            HashSet<Integer> uniqueInts = new HashSet<Integer>();
            uniqueInts.add(0);
            uniqueInts.add(number);
            int array_size = number_of_parts + 1;
            while (uniqueInts.size() < array_size) {
                uniqueInts.add(1 + r.nextInt(number - 1));
            }
            Integer[] dividers = uniqueInts.toArray(new Integer[array_size]);
            Arrays.sort(dividers);
            int[] results = new int[number_of_parts];
            for (int i = 1, j = 0; i < dividers.length; ++i, ++j) {
                results[j] = dividers[i] - dividers[j];
            }
            return results;
        }

        void gorselata(View view) {
            dismissss = (Button) view.findViewById(R.id.dismissss);
            ia = (ImageView) view.findViewById(R.id.ia);
            ib = (ImageView) view.findViewById(R.id.ib);
            ic = (ImageView) view.findViewById(R.id.ic);
            id = (ImageView) view.findViewById(R.id.id);
        }
    }

    public void SetWait(int i) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ses", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("ses", "").equals("ok")) {
            final MediaPlayer mp = MediaPlayer.create(activity, i);
            mp.start();
        }
    }

    public void SetWaitDelay(int i) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ses", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("ses", "").equals("ok")) {
            final MediaPlayer mp = MediaPlayer.create(activity, i);
            mp.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mp.stop();
                }
            }, 2200);
        }
    }

    public void ChangeColorAnswer(final String correctAnswer) {
        final Button correctBtn;

        if (a.getTag().equals(correctAnswer)) {
            correctBtn = a;
        } else if (b.getTag().equals(correctAnswer)) {
            correctBtn = b;
        } else if (c.getTag().equals(correctAnswer)) {
            correctBtn = c;
        } else {
            correctBtn = d;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                correctBtn.setBackground(activity.getDrawable(R.drawable.sorudogrucevap));
            }
        }, delayTime);

    }

}
