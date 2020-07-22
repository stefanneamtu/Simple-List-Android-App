package com.example.todo;

import android.provider.BaseColumns;

public class ListContract {

  private ListContract() {}

  public static final class ListEntry implements BaseColumns {
    public static final String TABLE_NAME = "todoList";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TIMESTAMP = "timestamp";
  }
}
