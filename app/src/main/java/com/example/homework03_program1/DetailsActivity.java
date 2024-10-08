package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsActivity extends AppCompatActivity
{
    // Main Details Activity
    Intent details_mainIntent;
    ImageView iv_jDetails_backBtn;
    TextView tv_randomTesting;
    View da_vsSwitcher_details;
    View da_vsSwitcher_update;
    ViewSwitcher vs_jDetails_viewSwitcher;
    TextView tv_j_vsDetails_username;
    TextView tv_j_vsDetails_fname;
    TextView tv_j_vsDetails_lname;
    TextView tv_j_vsDetails_email;
    TextView tv_j_vsDetails_age;
    TextView tv_j_vsDetails_gpa;
    TextView tv_j_vsDetails_major;
    int da_selectedStudent;
    // Details View Layout

    // Update Student Layout

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        DA_ListOfIntents();
        DA_ListOfViews();
        DA_OnClickListener();
        DA_SetTextViewData();

        tv_randomTesting.setText(String.valueOf(da_selectedStudent));
        vs_jDetails_viewSwitcher.setAnimateFirstView(true);

//        tv_jRanran.setText(String.valueOf(da_selectedStudent));

    }
    private void DA_ListOfIntents()
    {
        details_mainIntent = new Intent(DetailsActivity.this, MainActivity.class);
    }
    private void DA_ListOfViews()
    {
        // Main Views //
        vs_jDetails_viewSwitcher = findViewById(R.id.vs_vDetails_viewSwitcher);
        iv_jDetails_backBtn = findViewById(R.id.iv_vDetails_backBtn);
        tv_randomTesting = findViewById(R.id.tv_randomContent);
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
        // Update View //

    }
    private void DA_OnClickListener()
    {
        iv_jDetails_backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(details_mainIntent);
            }
        });
    }
    private void DA_SetTextViewData()
    {
        da_selectedStudent = StudentData.PassStudentData.getLvMainLongClickPos();
        StudentData sd = StudentData.PassStudentData.getStudentData();
        tv_j_vsDetails_username.setText(sd.getSd_username());
        tv_j_vsDetails_fname.setText(sd.getSd_fname());
        tv_j_vsDetails_lname.setText(sd.getSd_lname());
        tv_j_vsDetails_email.setText(sd.getSd_email());
        tv_j_vsDetails_age.setText(String.valueOf(sd.getSd_age()));
        tv_j_vsDetails_gpa.setText(String.valueOf(sd.getSd_gpa()));
        // student major needs to be convert from foreign key to text
        tv_j_vsDetails_major.setText(MajorData.PassMajorData.getMp_MajorName(da_selectedStudent));
        Log.d("MajorName", String.valueOf(sd.getSd_major()));
    }
}