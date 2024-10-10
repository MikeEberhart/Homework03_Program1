package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity
{
    // Main Details Activity
    Intent details_mainIntent;
    ImageView iv_jDetails_homeBtn;
    ImageView iv_jDetails_updateBtn;
    View da_vsSwitcher_details;
    View da_vsSwitcher_update;
    ViewSwitcher vs_jDetails_viewSwitcher;
    DatabaseHelper da_dbHelper;
    ArrayList<StudentData> da_passedStudentData;
    ArrayList<MajorData> da_passedMajorData;
    ArrayList<String> da_passedPrefixData;
    ArrayList<String> da_passedMajorNames;
    StudentData passedData;
    // Details View Layout
    TextView tv_j_vsDetails_username;
    TextView tv_j_vsDetails_fname;
    TextView tv_j_vsDetails_lname;
    TextView tv_j_vsDetails_email;
    TextView tv_j_vsDetails_age;
    TextView tv_j_vsDetails_gpa;
    TextView tv_j_vsDetails_major;
    TextView tv_j_vsDetails_majorPrefix;
    // Update Student Layout
    ImageView iv_j_vsUpdate_backBtn;
    TextView tv_j_vsUpdate_username;
    TextView tv_j_vsUpdate_fname;
    TextView tv_j_vsUpdate_lname;
    TextView tv_j_vsUpdate_email;
    TextView tv_j_vsUpdate_age;
    TextView tv_j_vsUpdate_gpa;
    Spinner sp_j_vsUpdate_major;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        DA_ListOfIntents();
        DA_ListOfViews();
        DA_OnClickListener();
        DA_SetDetailsTextViews();
        DA_SetUpdateTextViews();
        vs_jDetails_viewSwitcher.setAnimateFirstView(true);
    }
    private void DA_ListOfIntents()
    {
        da_dbHelper = new DatabaseHelper(this);
        details_mainIntent = new Intent(DetailsActivity.this, MainActivity.class);
    }
    private void DA_ListOfViews()
    {
        // Main Views //
        vs_jDetails_viewSwitcher = findViewById(R.id.vs_vDetails_viewSwitcher);
        iv_jDetails_homeBtn = findViewById(R.id.iv_vDetails_homeBtn);
        iv_jDetails_updateBtn = findViewById(R.id.iv_vDetails_updateBtn);
        LayoutInflater da_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        da_vsSwitcher_details = da_inflater.inflate(R.layout.da_vswitcher_details_layout, null);
        da_vsSwitcher_update = da_inflater.inflate(R.layout.da_vswitcher_update_layout, null);
        vs_jDetails_viewSwitcher.addView(da_vsSwitcher_details);
        vs_jDetails_viewSwitcher.addView(da_vsSwitcher_update);
        // Detail Views //
        tv_j_vsDetails_username = findViewById(R.id.tv_vsDetails_username);
        tv_j_vsDetails_fname = findViewById(R.id.tv_vsDetails_fname);
        tv_j_vsDetails_lname = findViewById(R.id.tv_vsDetails_lname);
        tv_j_vsDetails_email = findViewById(R.id.tv_vsDetails_email);
        tv_j_vsDetails_age = findViewById(R.id.tv_vsDetails_age);
        tv_j_vsDetails_gpa = findViewById(R.id.tv_vsDetails_gpa);
        tv_j_vsDetails_major = findViewById(R.id.tv_vsDetails_major);
        tv_j_vsDetails_majorPrefix = findViewById(R.id.tv_vsDetails_majorPrefix);
        // Update View //
//        da_passedMajorNames = da_dbHelper.DB
//         = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, da_passedMajorNames);
        iv_j_vsUpdate_backBtn = findViewById(R.id.iv_vsUpdate_backBtn);
        tv_j_vsUpdate_username = findViewById(R.id.tv_vsUpdate_username);
        tv_j_vsUpdate_fname = findViewById(R.id.et_vsUpdate_fname);
        tv_j_vsUpdate_lname = findViewById(R.id.et_vsUpdate_lname);
        tv_j_vsUpdate_email = findViewById(R.id.et_vsUpdate_email);
        tv_j_vsUpdate_age = findViewById(R.id.et_vsUpdate_age);
        tv_j_vsUpdate_gpa = findViewById(R.id.et_vsUpdate_gpa);
        sp_j_vsUpdate_major = findViewById(R.id.sp_vsUpdate_major);
    }
    private void DA_OnClickListener()
    {
        iv_jDetails_homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(details_mainIntent);
            }
        });
        iv_jDetails_updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vs_jDetails_viewSwitcher.getCurrentView() != da_vsSwitcher_update)
                {
                    vs_jDetails_viewSwitcher.showNext();
                }
                else if(vs_jDetails_viewSwitcher.getCurrentView() == da_vsSwitcher_update)
                {
                    // put save new data function here
                }
            }
        });
        iv_j_vsUpdate_backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {   // used to go back and cancel the "update info"
                if(vs_jDetails_viewSwitcher.getCurrentView() == da_vsSwitcher_update)
                {
                    vs_jDetails_viewSwitcher.showPrevious();
                }
            }
        });
    }
    private void DA_SetDetailsTextViews()
    {
        passedData = da_dbHelper.DB_getSingleStudentData(StudentData.PassStudentData.getLvMainLongClickPos()); // maybe pass data this way?? do the same for major class too
        da_passedStudentData = da_dbHelper.DB_passStudentData(); // instead of an array could i have a function return a StudentData object
        da_passedMajorData = da_dbHelper.DB_passMajorData(); // same with this can i just have the function return a MajorData object instead
        da_passedPrefixData = da_dbHelper.DB_passPrefixData();
        int pos = StudentData.PassStudentData.getLvMainLongClickPos();
        int majorId = passedData.getSd_major();
        int prefixId = da_passedMajorData.get(majorId).getPrefixId();
        tv_j_vsDetails_username.setText(passedData.getSd_username());
        tv_j_vsDetails_fname.setText(da_passedStudentData.get(pos).getSd_fname());
        tv_j_vsDetails_lname.setText(da_passedStudentData.get(pos).getSd_lname());
        tv_j_vsDetails_email.setText(da_passedStudentData.get(pos).getSd_email());
        tv_j_vsDetails_age.setText(String.valueOf(da_passedStudentData.get(pos).getSd_age()));
        tv_j_vsDetails_gpa.setText(String.valueOf(da_passedStudentData.get(pos).getSd_gpa()));
        tv_j_vsDetails_major.setText(da_passedMajorData.get(majorId).getMajorName());
        tv_j_vsDetails_majorPrefix.setText(da_passedPrefixData.get(prefixId));
    }
    private void DA_SetUpdateTextViews()
    {
        da_passedStudentData = da_dbHelper.DB_passStudentData();
        da_passedMajorData = da_dbHelper.DB_passMajorData();
        da_passedPrefixData = da_dbHelper.DB_passPrefixData();
        int pos = StudentData.PassStudentData.getLvMainLongClickPos();
        int majorId = da_passedStudentData.get(pos).getSd_major();
        int prefixId = da_passedMajorData.get(majorId).getPrefixId();
        tv_j_vsUpdate_username.setText(da_passedStudentData.get(pos).getSd_username());
        tv_j_vsUpdate_fname.setText(da_passedStudentData.get(pos).getSd_fname());
        tv_j_vsUpdate_lname.setText(da_passedStudentData.get(pos).getSd_lname());
        tv_j_vsUpdate_email.setText(da_passedStudentData.get(pos).getSd_email());
        tv_j_vsUpdate_age.setText(String.valueOf(da_passedStudentData.get(pos).getSd_age()));
        tv_j_vsUpdate_gpa.setText(String.valueOf(da_passedStudentData.get(pos).getSd_gpa()));
//        sp_j_vsUpdate_major

    }
}