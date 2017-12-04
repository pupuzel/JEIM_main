package com.example.jock.jeim_main.Major;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

/**
 * Created by Jock on 2017-11-21.
 */

public class Major_board_myboard extends AppCompatActivity {
    private TextView txt_back;
    private ListView listView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_board_myboard);

        txt_back = (TextView) findViewById(R.id.department_myboard_back);
        listView = (ListView) findViewById(R.id.department_myboard_listview);
        swipe = (SwipeRefreshLayout) findViewById(R.id.department_myboard_swipe);
        progressBar = (ProgressBar) findViewById(R.id.department_myboard_progressbar);
    }
}
