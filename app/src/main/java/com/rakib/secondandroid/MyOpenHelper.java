package com.rakib.secondandroid;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyOpenHelper extends SQLiteOpenHelper {

    SQLiteDatabase database;
    private String tname = "student";

    private  List<Student> list;

    Student s;

    public MyOpenHelper(@Nullable Context context) {
        super(context,"stdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE student(id INTEGER PRIMARY KEY, name TEXT, email TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS student");
        onCreate(sqLiteDatabase);

    }

    public void doSave(Student s){
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", s.getName());
        cv.put("email", s.getEmail());
        database.update(tname,cv,"id=?", new String[]{String.valueOf(s.getId())});
        database.close();

    }

    public void dpDelete(Student s){
        database = this.getWritableDatabase();
        database.delete(tname, "id=?",  new String[]{String.valueOf(s.getId())});
        database.close();

    }

    public List<Student> allStudent(){
        list = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+tname, null);
        while (cursor.moveToNext()){
            s=new Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            list.add(s);
        }
        database.close();
        return list;
    }


}
