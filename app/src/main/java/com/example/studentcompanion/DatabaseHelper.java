package com.example.studentcompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE attendance(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, date TEXT, status TEXT)");

        db.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, subject TEXT, note TEXT)");

        db.execSQL("CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, task TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS notes");
        db.execSQL("DROP TABLE IF EXISTS todo");

        onCreate(db);
    }



    public void insertAttendance(String user, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user", user);
        cv.put("date", date);
        cv.put("status", status);
        db.insert("attendance", null, cv);
    }

    public boolean isDateExists(String user, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM attendance WHERE user=? AND date=?", new String[]{user, date});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public Cursor getAttendance(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM attendance WHERE user=?", new String[]{user});
    }


    public void addNote(String user, String subject, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user", user);
        cv.put("subject", subject);
        cv.put("note", note);
        db.insert("notes", null, cv);
    }

    public Cursor getNotes(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM notes WHERE user=?", new String[]{user});
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", "id=?", new String[]{String.valueOf(id)});
    }


    public void addTask(String user, String task, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user", user);
        cv.put("task", task);
        cv.put("date", date);
        db.insert("todo", null, cv);
    }

    public Cursor getTasks(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM todo WHERE user=?", new String[]{user});
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("todo", "id=?", new String[]{String.valueOf(id)});
    }
}