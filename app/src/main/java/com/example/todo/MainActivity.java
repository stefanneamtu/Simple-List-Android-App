package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private SQLiteDatabase database;

  private ListAdapter adapter;

  private EditText editTextName;
  private TextView textViewAmount;
  private int amount = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // create a new database
    ListDBHelper dbHelper = new ListDBHelper(this);

    // get the writable database to add items
    database = dbHelper.getWritableDatabase();

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    adapter = new ListAdapter(this, getAllItems());
    recyclerView.setAdapter(adapter);

    // implement the swipe functionality
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        removeItem((long) viewHolder.itemView.getTag());
      }
    }).attachToRecyclerView(recyclerView);

    // initialise the views
    editTextName = findViewById(R.id.edit_text_name);
    textViewAmount = findViewById(R.id.text_view_amount);

    Button buttonIncrease = findViewById(R.id.button_increase);
    Button buttonDecrease = findViewById(R.id.button_decrease);
    Button buttonAdd = findViewById(R.id.button_add);

    // set the on click listeners of the buttons
    buttonIncrease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        increase();
      }
    });

    buttonDecrease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        decrease();
      }
    });

    buttonAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addItem();
      }
    });
  }

  private void increase() {
    ++amount;
    textViewAmount.setText(String.valueOf(amount));
  }

  private void decrease() {
    if (amount > 0) {
      --amount;
      textViewAmount.setText(String.valueOf(amount));
    }
  }

  // add an item to the database
  private void addItem() {
    if (editTextName.getText().toString().trim().length() == 0 || amount == 0) {
      return;
    }

    String name = editTextName.getText().toString();
    ContentValues contentValues = new ContentValues();

    contentValues.put(ListContract.ListEntry.COLUMN_NAME, name);
    contentValues.put(ListContract.ListEntry.COLUMN_AMOUNT, amount);

    database.insert(ListContract.ListEntry.TABLE_NAME, null, contentValues);
    adapter.swapCursor(getAllItems());

    editTextName.getText().clear();
  }

  // removes an item from the list
  private void removeItem(long id) {
    database.delete(ListContract.ListEntry.TABLE_NAME,
        ListContract.ListEntry._ID + "=" + id, null);
    adapter.swapCursor(getAllItems());
  }

  private Cursor getAllItems() {
    return database.query(
        ListContract.ListEntry.TABLE_NAME,
        null,
        null,
        null,
        null,
        null,
        ListContract.ListEntry.COLUMN_TIMESTAMP + " DESC"
    );
  }
}