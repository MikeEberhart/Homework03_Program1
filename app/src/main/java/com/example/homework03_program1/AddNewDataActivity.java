package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddNewDataActivity extends AppCompatActivity
{
    // Main Views //
    TextView tv_jAddNewData_headerText;
    ViewSwitcher vs_jAddNewData_viewSwitcher;
    ImageView iv_jAddNewData_homeBtn;
    ImageView iv_jAddNewData_addBtn;
    Intent ad_mainIntent;
    View ad_vsSwitcher_addStudent;
    View ad_vsSwitcher_addMajor;
    DatabaseHelper ad_dbHelper;
    ArrayList<String> ad_passedPrefixes;
    ArrayList<String> ad_passedMajorNames;
    // Add New Student Views //
    ImageView iv_j_vsAddNewStudent_saveChangesBtn;
    ImageView iv_j_vsAddNewStudent_backBtn;
    EditText et_j_vsAddNewStudent_username;
    EditText et_j_vsAddNewStudent_fname;
    EditText et_j_vsAddNewStudent_lname;
    EditText et_j_vsAddNewStudent_email;
    EditText et_j_vsAddNewStudent_age;
    EditText et_j_vsAddNewStudent_gpa;
    TextView tv_j_vsAddNewStudent_addNewMajorBtn;
    Spinner sp_j_vsAddNewStudent_major;
    ArrayAdapter<String> ad_spMajAdapter;
    private boolean ad_majorSelected;

    // Add New Major Views //

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_data);

        AD_InitData();
        AD_ListOfViews();
        AD_OnClickListener();

        vs_jAddNewData_viewSwitcher.setAnimateFirstView(true);
    }
    private void AD_InitData()
    {
        ad_mainIntent = new Intent(AddNewDataActivity.this, MainActivity.class);
        // inflating the different views to be used with the ViewSwitcher //
        LayoutInflater ad_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ad_vsSwitcher_addStudent = ad_inflater.inflate(R.layout.ad_vswitcher_addstudent_layout, null);
        ad_vsSwitcher_addMajor = ad_inflater.inflate(R.layout.ad_vswitcher_addmajor_layout, null);
        // using dbHelper to get the data from the database
        ad_dbHelper = new DatabaseHelper(this);
        ad_passedMajorNames = ad_dbHelper.DB_getListOfMajorNames();
        ad_passedPrefixes = ad_dbHelper.DB_getListOfPrefixes();
        ad_spMajAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ad_passedMajorNames);

    }
    private void AD_ListOfViews()
    {
        // Main Views //
        vs_jAddNewData_viewSwitcher = findViewById(R.id.vs_vAddNewData_viewSwitcher);
        vs_jAddNewData_viewSwitcher.addView(ad_vsSwitcher_addStudent);
        vs_jAddNewData_viewSwitcher.addView(ad_vsSwitcher_addMajor);
        tv_jAddNewData_headerText = findViewById(R.id.tv_vAddNewData_headerText);
        iv_jAddNewData_homeBtn = findViewById(R.id.iv_vAddNewData_homeBtn);
        iv_jAddNewData_addBtn = findViewById(R.id.iv_vAddNewData_addStudentBtn);
        // Add New Student Views //
        et_j_vsAddNewStudent_username = findViewById(R.id.et_vsAddNewStudent_username);
        et_j_vsAddNewStudent_fname = findViewById(R.id.et_vsAddNewStudent_fname);
        et_j_vsAddNewStudent_lname = findViewById(R.id.et_vsAddNewStudent_lname);
        et_j_vsAddNewStudent_email = findViewById(R.id.et_vsAddNewStudent_email);
        et_j_vsAddNewStudent_age = findViewById(R.id.et_vsAddNewStudent_age);
        et_j_vsAddNewStudent_gpa = findViewById(R.id.et_vsAddNewStudent_gpa);
        tv_j_vsAddNewStudent_addNewMajorBtn = findViewById(R.id.tv_vsAddNewStudent_addNewMajorBtn);
        sp_j_vsAddNewStudent_major = findViewById(R.id.sp_vsAddNewStudent_major);
        sp_j_vsAddNewStudent_major.setAdapter(ad_spMajAdapter);
        iv_j_vsAddNewStudent_saveChangesBtn = findViewById(R.id.iv_vsAddNewStudent_saveChangesBtn);
        iv_j_vsAddNewStudent_backBtn = findViewById(R.id.iv_vsAddNewStudent_backBtn);
        // Add New Major Views //

    }
    private void AD_OnClickListener()
    {
        iv_jAddNewData_homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ad_mainIntent);
            }
        });
        iv_jAddNewData_addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // add code/functions to add new student to the database
            }
        });
        tv_j_vsAddNewStudent_addNewMajorBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vs_jAddNewData_viewSwitcher.getCurrentView() == ad_vsSwitcher_addStudent)
                {
                    tv_jAddNewData_headerText.setText("Add New Major");
                    vs_jAddNewData_viewSwitcher.showNext();
                }

            }
        });
        iv_j_vsAddNewStudent_saveChangesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(AD_NoEmptyInput())
                {
                    Log.d("RETURN TRUE", "RETURN TRUE");
                    ad_majorSelected = false;
                }
                else
                {
                    Log.d("RETURN FALSE", "RETURN FALSE");
                }
            }
        });
        sp_j_vsAddNewStudent_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0)
                {
                    ad_majorSelected = true;
                }
                else
                {
                    // please select a major error message
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private boolean AD_NoEmptyInput() // check to see if text box is empty
    {
        if(et_j_vsAddNewStudent_username.getText().toString().isEmpty())
        {
            return false;
        }
        else if(et_j_vsAddNewStudent_fname.getText().toString().isEmpty())
        {
            return false;
        }
        else if(et_j_vsAddNewStudent_lname.getText().toString().isEmpty())
        {
            return false;
        }
        else if(et_j_vsAddNewStudent_email.getText().toString().isEmpty())
        {
            return false;
        }
        else if(et_j_vsAddNewStudent_age.getText().toString().isEmpty())
        {
            return false;
        }
        else if(et_j_vsAddNewStudent_gpa.getText().toString().isEmpty())
        {
            return false;
        }
        else if(!ad_majorSelected)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}