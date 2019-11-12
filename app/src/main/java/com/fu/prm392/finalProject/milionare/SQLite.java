package com.fu.prm392.finalProject.milionare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbsonn";
    private static final String TABLE_NAME = "TABLO";
    private static final String KOLON_ISIM_ID = "id";
    private static final String KOLON_ISIM_PUAN= "puan";
    private static final String KOLON_ISIM_PARA = "para";
    private static final String KOLON_ISIM_SURE = "sure";
    private static final int DATABASE_VERSION = 1;
    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createcmd =
                new StringBuilder().append("CREATE TABLE ")
                        .append(TABLE_NAME).append(" ( ")
                        .append(KOLON_ISIM_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                        .append(KOLON_ISIM_PARA).append(" TEXT, ")
                        .append(KOLON_ISIM_SURE).append(" TEXT, ")
                        .append(KOLON_ISIM_PUAN).append(" INTEGER ); ").toString();
        db.execSQL(createcmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addScore(ScoreEntity scoreEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KOLON_ISIM_PARA, scoreEntity.getPara());
        values.put(KOLON_ISIM_PUAN, scoreEntity.getPuan());
        values.put(KOLON_ISIM_SURE, ""+ scoreEntity.getSure());

        Log.e("Eklenenler:",""+ scoreEntity.getPuan()+" "+ scoreEntity.getSure()+" "+ scoreEntity.getPara());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteAllScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public ArrayList<ScoreEntity> getAllScores() {
        ArrayList<ScoreEntity> kisilerList =new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex(KOLON_ISIM_ID));
            String para =cursor.getString(cursor.getColumnIndex(KOLON_ISIM_PARA));
            int puan =cursor.getInt(cursor.getColumnIndex(KOLON_ISIM_PUAN));
            String sure =cursor.getString(cursor.getColumnIndex(KOLON_ISIM_SURE));
            Log.e("Çekilen db:",para+" "+puan+" "+sure);
            ScoreEntity newscores = new ScoreEntity(id,para,Integer.parseInt(sure),puan);
            kisilerList.add(newscores);
        }
        cursor.close();
        db.close();
        return kisilerList;
    }

    public ArrayList<ScoreEntity> getAllScoresHighest() {
        ArrayList<ScoreEntity> kisilerList =new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME+ " ORDER BY "+KOLON_ISIM_PUAN+" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex(KOLON_ISIM_ID));
            String para =cursor.getString(cursor.getColumnIndex(KOLON_ISIM_PARA));
            int puan =cursor.getInt(cursor.getColumnIndex(KOLON_ISIM_PUAN));
            String sure =cursor.getString(cursor.getColumnIndex(KOLON_ISIM_SURE));
            Log.e("Çekilen db:",para+" "+puan+" "+sure);
            ScoreEntity newscores = new ScoreEntity(id,para,Integer.parseInt(sure),puan);
            kisilerList.add(newscores);
        }
        cursor.close();
        db.close();
        return kisilerList;
    }


}



