package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{
    // Main Search Activity //
    ImageView iv_jSearch_homeBtn;
    ImageView iv_jSearch_searchBtn;
    Intent sa_mainIntent;
    Intent sa_detailsIntent;
    View sa_vsSwitcher_search;
    View sa_vsSwitcher_results;
    ViewSwitcher vs_jSearch_viewSwitcher;
    // Search View Layout //
    EditText et_j_vsSearch_username;
    EditText et_j_vsSearch_fname;
    EditText et_j_vsSearch_lname;
    EditText et_j_vsSearch_gpa;
    Spinner sp_j_vsSearch_gpaRange;
    Spinner sp_j_vsSearch_majorList;
    ArrayList<String> sa_listOfMajNames;
    ArrayAdapter<String> sa_spMajAdapter;
    // Results View Layout //
    ListView lv_j_vsResults_searchResults;
    ImageView iv_j_vsResults_backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        SA_ListOfIntents();
        SA_ListOfViews();
        SA_OnClickListeners();
        vs_jSearch_viewSwitcher.setAnimateFirstView(true);
    }
    private void SA_ListOfIntents()
    {
        sa_mainIntent = new Intent(this, MainActivity.class);
        sa_detailsIntent = new Intent(this, DetailsActivity.class);
    }
    private void SA_ListOfViews()
    {
        // Main Search Activity //
        vs_jSearch_viewSwitcher = findViewById(R.id.vs_vSearch_viewSwitcher);
        iv_jSearch_homeBtn = findViewById(R.id.iv_vSearch_homeBtn);
        iv_jSearch_searchBtn = findViewById(R.id.iv_vSearch_searchBtn);
        // LayoutInflater sa_inflater = LayoutInflater.from(this); another way of inflating the layout
        LayoutInflater sa_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sa_vsSwitcher_search = sa_inflater.inflate(R.layout.sa_vswitcher_search_layout, null);
        sa_vsSwitcher_results = sa_inflater.inflate(R.layout.sa_vswitcher_results_layout, null);
        vs_jSearch_viewSwitcher.addView(sa_vsSwitcher_search);
        vs_jSearch_viewSwitcher.addView(sa_vsSwitcher_results);
        // Search View Layout //

        sa_listOfMajNames  = new ArrayList<>();
        sa_listOfMajNames = MajorData.PassMajorData.getMp_allMajorNames();
        sa_spMajAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sa_listOfMajNames);
        et_j_vsSearch_username = findViewById(R.id.et_vsSearch_username);
        et_j_vsSearch_fname = findViewById(R.id.et_vsSearch_fname);
        et_j_vsSearch_lname = findViewById(R.id.et_vsSearch_lname);
        et_j_vsSearch_gpa = findViewById(R.id.et_vsSearch_gpa);
        sp_j_vsSearch_gpaRange = findViewById(R.id.sp_vsSearch_gpaRange);
        sp_j_vsSearch_majorList = findViewById(R.id.sp_vsSearch_majorList);
        sp_j_vsSearch_majorList.setAdapter(sa_spMajAdapter);
        // Results View Layout //
        lv_j_vsResults_searchResults = findViewById(R.id.lv_vsResults_searchResults);
        iv_j_vsResults_backBtn = findViewById(R.id.im_vsResults_backBtn);

    }
    private void SA_OnClickListeners()
    {
        iv_jSearch_homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(sa_mainIntent);
            }
        });
        iv_jSearch_searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vs_jSearch_viewSwitcher.getCurrentView() == sa_vsSwitcher_search)
                {
                    vs_jSearch_viewSwitcher.showNext();
                }
            }
        });
        iv_j_vsResults_backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if( vs_jSearch_viewSwitcher.getCurrentView() == sa_vsSwitcher_results)
                {
                    vs_jSearch_viewSwitcher.showNext();
                }
            }
        });
    }
}