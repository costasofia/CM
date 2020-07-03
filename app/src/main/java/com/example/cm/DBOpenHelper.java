package com.example.cm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "tbl_student";
    public static final String S_ID = "s_id";
    public static final String S_ASSUNTO = "s_assunto";
    public static final String S_LOCAL = "s_local";


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "(" +
            "" + S_ID + " integer primary key autoincrement," +
            "" + S_ASSUNTO + " text not null," +
            "" + S_LOCAL + " text not null);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String s_ASSUNTO, String s_LOCAL) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(S_ASSUNTO, s_ASSUNTO);
        contentValues.put(S_LOCAL, s_LOCAL);

        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        sqLiteDB.insert(TABLE_NAME, null, contentValues);
        sqLiteDB.close();
    }

    public ArrayList<InformModel> getALLInformData() {
        ArrayList<InformModel> list = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                InformModel im = new InformModel();
                im.setId(cursor.getInt(0) + "");
                im.setAssunto(cursor.getString(1));
                im.setLocal(cursor.getString(2));
                list.add(im);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean updateData(int id, String assunto, String local) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(S_ASSUNTO, assunto);
        contentValues.put(S_LOCAL, local);

        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        sqLiteDB.update(TABLE_NAME, contentValues, S_ID + "=" + id, null);
        sqLiteDB.close();
        return true;
    }

    public boolean deleteData(String id) {
        SQLiteDatabase sqLiteDb = this.getWritableDatabase();
        sqLiteDb.delete(TABLE_NAME, S_ID + "=" + id, null);
        sqLiteDb.close();

        return true;
    }
}
