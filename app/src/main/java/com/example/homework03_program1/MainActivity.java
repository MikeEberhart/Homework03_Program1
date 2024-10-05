//=========================================================================================
// Name: Mike Eberhart
// Date: 30 September 2024
// Desc: An application that will allow an admin(you) to add new students into the system
//=========================================================================================
package com.example.homework03_program1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    ListView lv_jMain_listOfStudents;
    ImageView iv_jMain_searchBtn;
    ImageView iv_jMain_addBtn;
    ImageView iv_jMain_deleteBtn;
    Intent main_searchIntent;
    Intent main_addIntent;
    Intent main_detailsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Main_ListOfIntents();
        Main_ListOfViews();
        Main_OnClickListeners();
    }

    private void Main_ListOfIntents()
    {
        main_searchIntent = new Intent(this, SearchActivity.class);
        main_addIntent = new Intent(this, AddActivity.class);
        main_detailsIntent = new Intent(this, DetailsActivity.class);

    }

    private void Main_ListOfViews()
    {
        lv_jMain_listOfStudents = findViewById(R.id.lv_vMain_listOfStudents);
        iv_jMain_searchBtn = findViewById(R.id.iv_vMain_searchBtn);
        iv_jMain_addBtn = findViewById(R.id.iv_vMain_addBtn);
        iv_jMain_deleteBtn = findViewById(R.id.iv_vMain_deleteBtn);
    }

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

            }
        });
        lv_jMain_listOfStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                return false;
            }
        });
//        same as above but using a lambda function
//        iv_jMain_searchBtn.setOnClickListener(v ->
//        {
//
//        });
    }
}