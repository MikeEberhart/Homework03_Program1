package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SearchActivity extends AppCompatActivity
{
//    View vs_jSearch_viewSwitcher;
    ImageView iv_jSearch_homeBtn;
    ImageView iv_jSearch_searchBtn;
    Intent sa_mainIntent;
    Intent sa_detailsIntent;
    View sa_vsSwitcher_search;
    View sa_vsSwitcher_results;
    ViewSwitcher vs_jSearch_viewSwitcher;

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
        vs_jSearch_viewSwitcher = findViewById(R.id.vs_vSearch_viewSwitcher);
        iv_jSearch_homeBtn = findViewById(R.id.iv_vSearch_homeBtn);
        iv_jSearch_searchBtn = findViewById(R.id.iv_vSearch_searchBtn);

//        LayoutInflater sa_inflater = LayoutInflater.from(this);
        LayoutInflater sa_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sa_vsSwitcher_search = sa_inflater.inflate(R.layout.sa_vswitcher_search_layout, null);
        sa_vsSwitcher_results = sa_inflater.inflate(R.layout.sa_vswitcher_results_layout, null);
        vs_jSearch_viewSwitcher.addView(sa_vsSwitcher_search);
        vs_jSearch_viewSwitcher.addView(sa_vsSwitcher_results);

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
    }
}