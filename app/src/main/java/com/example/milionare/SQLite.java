package com.example.milionare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "millionare";
    private static final String TABLE_NAME = "score_history";
    private static final String ID = "id";
    private static final String SCORE = "score";
    private static final String TIME = "time";
    private static final String MONEY = "money";
    private static final int DATABASE_VERSION = 1;

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createcmd =
                new StringBuilder().append("CREATE TABLE ")
                        .append(TABLE_NAME).append(" ( ")
                        .append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                        .append(MONEY).append(" TEXT, ")
                        .append(TIME).append(" TEXT, ")
                        .append(SCORE).append(" INTEGER ); ").toString();
        db.execSQL(createcmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addScore(ScoreEntity scoreEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SCORE, scoreEntity.getScore());
        values.put(MONEY, scoreEntity.getMoney());
        values.put(TIME, ""+ scoreEntity.getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteAllScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public ArrayList<ScoreEntity> getAllScores() {
        ArrayList<ScoreEntity> scoreList =new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex(ID));
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            int score = cursor.getInt(cursor.getColumnIndex(SCORE));
            String money = cursor.getString(cursor.getColumnIndex(MONEY));
            ScoreEntity newscores = new ScoreEntity(id,money,Integer.parseInt(time),score);
            scoreList.add(newscores);
        }
        cursor.close();
        db.close();
        return scoreList;
    }

    public ArrayList<ScoreEntity> getAllScoresHighest() {
        ArrayList<ScoreEntity> scoreList =new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME+ " ORDER BY "+SCORE+" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex(ID));
            String money =cursor.getString(cursor.getColumnIndex(MONEY));
            int score =cursor.getInt(cursor.getColumnIndex(SCORE));
            String time =cursor.getString(cursor.getColumnIndex(TIME));
            ScoreEntity newscores = new ScoreEntity(id,money,Integer.parseInt(time),score);
            scoreList.add(newscores);
        }
        cursor.close();
        db.close();
        return scoreList;
    }
}
