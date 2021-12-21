package com.example.projectnote.db;

import com.example.projectnote.adapter.ListItem;

import java.util.List;

public interface OnDataReceived {
    void onReceived (List<ListItem> list);
}
