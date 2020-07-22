package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

  private Context context;
  private Cursor cursor;

  public ListAdapter(Context context, Cursor cursor) {
    this.context = context;
    this.cursor = cursor;
  }

  public class ListViewHolder extends RecyclerView.ViewHolder {

    public TextView nameText;
    public TextView countText;

    public ListViewHolder(@NonNull View itemView) {
      super(itemView);

      nameText = itemView.findViewById(R.id.text_view_name_item);
      countText = itemView.findViewById(R.id.text_view_amount_item);
    }
  }

  @NonNull
  @Override
  public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.list_item, parent, false);
    return new ListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
    if (!cursor.moveToPosition(position)) {
      return;
    }

    String name = cursor.getString(cursor.getColumnIndex(ListContract.ListEntry.COLUMN_NAME));
    int amount = cursor.getInt(cursor.getColumnIndex(ListContract.ListEntry.COLUMN_AMOUNT));
    long id = cursor.getLong(cursor.getColumnIndex(ListContract.ListEntry._ID));

    holder.nameText.setText(name);
    holder.countText.setText(String.valueOf(amount));
    holder.itemView.setTag(id);
  }

  @Override
  public int getItemCount() {
    return cursor.getCount();
  }

  public void swapCursor(Cursor newCursor) {
    if (cursor != null) {
      cursor.close();
    }
    cursor = newCursor;
    if (newCursor != null) {
      notifyDataSetChanged();
    }
  }
}
