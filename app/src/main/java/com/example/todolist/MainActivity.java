package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {


    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskadapter;
    private FloatingActionButton fab;


    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        db.openDatabase();

       taskList = new ArrayList<>();

       taskRecyclerView =findViewById(R.id.tasksRecyclerView);
       taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       taskadapter = new ToDoAdapter(db, this);
       taskRecyclerView.setAdapter(taskadapter);

       fab = findViewById(R.id.fab);

       ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskadapter));
       itemTouchHelper.attachToRecyclerView(taskRecyclerView);

       taskList = db.getAllTask();
       Collections.reverse(taskList);
       taskadapter.setTask(taskList);

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
           }
       });


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTask();
        Collections.reverse(taskList);
        taskadapter.setTask(taskList);
        taskadapter.notifyDataSetChanged();


    }
}