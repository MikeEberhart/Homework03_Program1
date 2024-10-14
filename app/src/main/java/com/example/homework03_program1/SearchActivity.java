package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    DatabaseHelper sa_dbHelper;
    // Search View Layout //
    EditText et_j_vsSearch_username;
    EditText et_j_vsSearch_fname;
    EditText et_j_vsSearch_lname;
    EditText et_j_vsSearch_email;
    EditText et_j_vsSearch_gpa;
    TextView tv_j_vsSearch_usernameError;
    TextView tv_j_vsSearch_fnameError;
    TextView tv_j_vsSearch_lnameError;
    TextView tv_j_vsSearch_emailError;
    TextView tv_j_vsSearch_gpaError;
    Spinner sp_j_vsSearch_gpaRange;
    Spinner sp_j_vsSearch_majorList;
    ArrayList<String> sa_listOfRangeOperators;
    ArrayList<String> sa_listOfMajNames;
    ArrayAdapter<String> sa_spMajAdapter;
    ArrayAdapter<String> sa_spGpaRangeAdapter;
    // Results View Layout //
    ListView lv_j_vsResults_searchResults;
    ImageView iv_j_vsResults_backBtn;
    // Used Variables //
    private static final String ALLOWED_USERNAME_CHARS = "^[a-zA-Z0-9_.-]+$"; // regex string allowing only upper and lower case along with number and a few symbols
    private static final String ALLOWED_NAME_CHARS = "^[a-zA-Z]+$"; // regex string allowing only upper and lower case
    private static final String ALLOWED_EMAIL_CHARS = "^[a-zA-Z](?:[a-zA-Z0-9_.-]*[a-zA-Z0-9])?+@[a-zA-Z.]+\\.edu$"; // regex string formatted so the last char in the email name can't be a special char and the school email can only contain letters and '.' and the last part must have .edu
    private static final String ALLOWED_GPA_CHARS = "^(1(\\.\\d+)|2(\\.\\d+)|3(\\.\\d+)|4(\\.0))$";  // regex string formatted so gpa range is 1.0 to 4.0


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        SA_ListOfIntents();
        SA_ListOfViews();
        SA_OnClickListeners();
        SA_TextChangedEventListener();
        vs_jSearch_viewSwitcher.setAnimateFirstView(true);
    }
    private void SA_ListOfIntents()
    {
        sa_mainIntent = new Intent(this, MainActivity.class);
        sa_detailsIntent = new Intent(this, DetailsActivity.class);
        sa_dbHelper = new DatabaseHelper(this);
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
        sa_listOfMajNames = sa_dbHelper.DB_getListOfMajorNames();
        sa_listOfRangeOperators = new ArrayList<>();
        sa_listOfRangeOperators.add("=");
        sa_listOfRangeOperators.add(">");
        sa_listOfRangeOperators.add("<");
        sa_spGpaRangeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sa_listOfRangeOperators);
        sa_spMajAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sa_listOfMajNames);
        et_j_vsSearch_username = findViewById(R.id.et_vsSearch_username);
        et_j_vsSearch_fname = findViewById(R.id.et_vsSearch_fname);
        et_j_vsSearch_lname = findViewById(R.id.et_vsSearch_lname);
        et_j_vsSearch_gpa = findViewById(R.id.et_vsSearch_gpa);
        et_j_vsSearch_email = findViewById(R.id.et_vsSearch_email);
        sp_j_vsSearch_gpaRange = findViewById(R.id.sp_vsSearch_gpaRange);
        sp_j_vsSearch_majorList = findViewById(R.id.sp_vsSearch_majorList);
        sp_j_vsSearch_majorList.setAdapter(sa_spMajAdapter);
        tv_j_vsSearch_usernameError = findViewById(R.id.tv_vsSearch_usernameError);
        tv_j_vsSearch_fnameError = findViewById(R.id.tv_vsSearch_fnameError);
        tv_j_vsSearch_lnameError = findViewById(R.id.tv_vsSearch_lnameError);
        tv_j_vsSearch_emailError = findViewById(R.id.tv_vsSearch_emailError);
        tv_j_vsSearch_gpaError = findViewById(R.id.tv_vsSearch_gpaError);
        tv_j_vsSearch_usernameError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_fnameError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_lnameError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_emailError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_gpaError.setVisibility(View.INVISIBLE);
        sp_j_vsSearch_gpaRange.setAdapter(sa_spGpaRangeAdapter);
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
                if(vs_jSearch_viewSwitcher.getCurrentView() != sa_vsSwitcher_results);
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
    private void SA_TextChangedEventListener()
    {
        et_j_vsSearch_username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if(SA_BadUserInput(ALLOWED_USERNAME_CHARS, s) && !et_j_vsSearch_username.getText().toString().isEmpty())
                {
                    tv_j_vsSearch_usernameError.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_j_vsSearch_usernameError.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
        et_j_vsSearch_fname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if(SA_BadUserInput(ALLOWED_NAME_CHARS, s) && !et_j_vsSearch_fname.getText().toString().isEmpty())
                {
                    tv_j_vsSearch_fnameError.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_j_vsSearch_fnameError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        et_j_vsSearch_lname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if(SA_BadUserInput(ALLOWED_NAME_CHARS, s) && !et_j_vsSearch_lname.getText().toString().isEmpty())
                {
                    tv_j_vsSearch_lnameError.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_j_vsSearch_lnameError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        et_j_vsSearch_email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if(SA_BadUserInput(ALLOWED_EMAIL_CHARS, s) && !et_j_vsSearch_email.getText().toString().isEmpty())
                {
                    tv_j_vsSearch_emailError.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_j_vsSearch_emailError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        et_j_vsSearch_gpa.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if(SA_BadUserInput(ALLOWED_GPA_CHARS, s) && !et_j_vsSearch_gpa.getText().toString().isEmpty())
                {
                    tv_j_vsSearch_gpaError.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv_j_vsSearch_gpaError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

    }
    private boolean SA_BadUserInput(String s, CharSequence cs)
    {
        // was error checking with this for loop but read up on regex for java. pretty similar to how it's used in python as well.
        Pattern goodChars = Pattern.compile(s);
        Matcher checkingChars = goodChars.matcher(cs);
        boolean dataCheck = checkingChars.find();
        if(dataCheck)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


}