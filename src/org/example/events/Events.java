
 package org.example.events;

import static android.provider.BaseColumns._ID;
import static org.example.events.Constants.TABLE_NAME;
import static org.example.events.Constants.TIME;
import static org.example.events.Constants.TITLE;
import static org.example.events.Constants.DATE;
import static org.example.events.Constants.HIGHSCORE;
import static org.example.events.Constants.USERNAME;
import android.app.ListActivity;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.widget.SimpleCursorAdapter;
// ...



public class Events extends ListActivity {
   // ...
   
   private static String[] FROM = {_ID, USERNAME, HIGHSCORE, DATE, TIME, TITLE};
   private static String ORDER_BY = TIME + " DESC";
   
   private static int[] TO = { R.id.rowid,R.id.username, R.id.highscore,
	   R.id.date,R.id.time, R.id.title,  };
   
   private EventsData events;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      events = new EventsData(this);
      try {
         addEvent("David Tenent",45000,(int)System.currentTimeMillis());
         Cursor cursor = getEvents();
         showEvents(cursor);
      } finally {
         events.close();
      }
   }

   private void addEvent(String username, int highscore, int date) {
      // Insert a new record into the Events data source.
      // You would do something similar for delete and update.
      SQLiteDatabase db = events.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(USERNAME, username);
      values.put(HIGHSCORE, highscore);
      values.put(DATE, date);
      values.put(TIME, System.currentTimeMillis());
      values.put(TITLE, "MichaelTable");
      db.insertOrThrow(TABLE_NAME, null, values);
   }

   private Cursor getEvents() {
      // Perform a managed query. The Activity will handle closing
      // and re-querying the cursor when needed.
      SQLiteDatabase db = events.getReadableDatabase();
      Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
            null, ORDER_BY);
      startManagingCursor(cursor);
      return cursor;
   }

   
   private void showEvents(Cursor cursor) {
      // Set up data binding
      SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
            R.layout.item, cursor, FROM, TO);
      setListAdapter(adapter);
   }
   
   
}