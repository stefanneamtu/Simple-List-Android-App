package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todo.ListContract.*;

import androidx.annotation.Nullable;

public class ListDBHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "list.db";
  public static final int DATABASE_VERSION = 1;

  public ListDBHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " +
        ListEntry.TABLE_NAME + " (" +
        ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        ListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
        ListEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
        ListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
        ");";

    sqLiteDatabase.execSQL(SQL_CREATE_LIST_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ListEntry.TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
}
