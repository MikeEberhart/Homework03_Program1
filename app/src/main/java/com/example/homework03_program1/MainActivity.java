//=================================================================================================//
// Name: Mike Eberhart
// Date: 30 September 2024
// Desc: An application that will allow an admin(you) to add/edit/remove students into the registry
//=================================================================================================//
package com.example.homework03_program1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView lv_jMain_listOfStudents;
    ImageView iv_jMain_searchBtn;
    ImageView iv_jMain_addBtn;
    ImageView iv_jMain_deleteBtn;
    TextView tv_jMain_deleteStudentError;
    Intent main_searchIntent;
    Intent main_addIntent;
    Intent main_detailsIntent;
    StudentListAdapter main_lv_studentList_adapter;
    ArrayList<StudentData> main_listOfStudents;
    DatabaseHelper dbHelper;
    private int selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Main_InitData();
        Main_ListOfViews();
        Main_OnClickListeners();
    }
    // used to Initialize the Data for the adapter, Intents, and Array from the database //
    private void Main_InitData()
    {
        main_searchIntent = new Intent(this, SearchActivity.class);
        main_addIntent = new Intent(this, AddNewDataActivity.class);
        main_detailsIntent = new Intent(this, DetailsActivity.class);
        dbHelper = new DatabaseHelper(this);
        dbHelper.DB_PopulateData();
        main_listOfStudents = dbHelper.DB_getListOfStudentData(); // gets an arraylist<StudentData> from db to be used to populate the listView data
        main_lv_studentList_adapter = new StudentListAdapter(MainActivity.this, main_listOfStudents);
        selectedStudent = -1;
    }
    // used to Initialize all the Views //
    private void Main_ListOfViews()
    {
        lv_jMain_listOfStudents = findViewById(R.id.lv_vMain_listOfStudents);
        lv_jMain_listOfStudents.setAdapter(main_lv_studentList_adapter);
        iv_jMain_searchBtn = findViewById(R.id.iv_vMain_searchBtn);
        iv_jMain_addBtn = findViewById(R.id.iv_vMain_addBtn);
        iv_jMain_deleteBtn = findViewById(R.id.iv_vMain_deleteBtn);
        tv_jMain_deleteStudentError = findViewById(R.id.tv_v_main_deleteStudentError);
        tv_jMain_deleteStudentError.setVisibility(View.INVISIBLE);
    }
    // used to house all the OnClickListeners for the ImageView buttons and the ListView OnClick//
    private void Main_OnClickListeners()
    {
        iv_jMain_searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(main_searchIntent);
            }
        });
        iv_jMain_addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(main_addIntent);
            }
        });
        iv_jMain_deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(selectedStudent != -1)
                {
                    dbHelper.DB_deleteStudentFromDatabase(main_listOfStudents.get(selectedStudent).getSd_username());
                    main_listOfStudents.remove(selectedStudent);
                    main_lv_studentList_adapter.notifyDataSetChanged();
                    selectedStudent = -1;
                }
                else
                {
                    tv_jMain_deleteStudentError.setVisibility(View.VISIBLE);
                    // need error text maybe to let user know to select a student? //
                }
            }
        });
        lv_jMain_listOfStudents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                tv_jMain_deleteStudentError.setVisibility(View.INVISIBLE);
                selectedStudent = position;
            }
            //look for something to edit the background color so it changes with whoever is selected then changes back when they are no longer selected
        });
        lv_jMain_listOfStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                StudentData.PassStudentData.setLvMainLongClickPos(position);
                startActivity(main_detailsIntent);
                return false;
            }
        });
    }
}