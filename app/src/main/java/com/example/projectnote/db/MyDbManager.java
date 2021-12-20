package com.example.projectnote.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectnote.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }

    public void openDb() {
        db = myDbHelper.getWritableDatabase();
    }

    public void insertToDb(String title, String disc, String uri) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TITLE, title);
        cv.put(MyConstants.DISC, disc);
        cv.put(MyConstants.URI, uri);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public void delete (int id){
        String selection = MyConstants._ID + "=" + id;
        db.delete(MyConstants.TABLE_NAME, selection, null);
    }
    public List<ListItem> getFromDb(String searchText) {
        List<ListItem> tempList = new ArrayList<>();
        String selection = MyConstants.TITLE + " like ?";
        Cursor cursor = db.query(MyConstants.TABLE_NAME,
                null, selection, new String[]{"%" + searchText + "%"},
                null, null, null);

        while (cursor.moveToNext()) {
            ListItem item = new ListItem();
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.TITLE));
            String disc = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.DISC));
            String uri = cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.URI));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(MyConstants._ID));

            item.setTitle(title);
            item.setDesc(disc);
            item.setUri(uri);
            item.setId(_id);
            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public void closeDb() {
        myDbHelper.close();
    }
}