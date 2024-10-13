package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailsActivity extends AppCompatActivity
{
    // Main Details Activity
    Intent da_mainIntent;
    Intent da_addNewDataIntent;
    ImageView iv_jDetails_homeBtn;
    ImageView iv_jDetails_updateBtn;
    TextView tv_jDetails_headerText;
    View da_vsSwitcher_details;
    View da_vsSwitcher_update;
    ViewSwitcher vs_jDetails_viewSwitcher;
    DatabaseHelper da_dbHelper;
    ArrayList<String> da_passedPrefixes;
    ArrayList<String> da_passedMajorNames;
    StudentData da_passedStudentData;
    MajorData da_passedMajorData;
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
    ArrayAdapter<String> da_spMajAdapter;
    ImageView iv_j_vsUpdate_backBtn;
    TextView tv_j_vsUpdate_username;
    TextView tv_j_vsUpdate_addNewMajorBtn;
    EditText et_j_vsUpdate_fname;
    EditText et_j_vsUpdate_lname;
    EditText et_j_vsUpdate_email;
    EditText et_j_vsUpdate_age;
    EditText et_j_vsUpdate_gpa;
    Spinner sp_j_vsUpdate_major;
    private static final String ALLOWED_USERNAME_CHARS = "^[a-zA-Z0-9_.-]+$"; // regex string allowing only upper and lower case along with number and a few symbols
    private static final String ALLOWED_NAME_CHARS = "^[a-zA-Z]+$"; // regex string allowing only upper and lower case
    private static final String ALLOWED_EMAIL_CHARS = "^[a-zA-Z](?:[a-zA-Z0-9_.-]*[a-zA-Z0-9])?+@[a-zA-Z]+\\.edu$"; // regex string formatted so the last char in the email name can't be a special char and the school email can only contain letters and '.' and the last part must have .edu
    private static final String ALLOWED_AGE_CHARS =  "^(1[6789]|[2-9][0-9])$"; // regex string formatted making sure the minimal age is 16 with no maximal
    private static final String ALLOWED_GPA_CHARS = "^(1(\\.\\d+)|2(\\.\\d+)|3(\\.\\d+)|4(\\.0))$"; // regex string formatted so gpa range is 1.0 to 4.0
    private boolean ad_goodUsername;
    private boolean ad_goodFname;
    private boolean ad_goodLname;
    private boolean ad_goodEmail;
    private boolean ad_goodAge;
    private boolean ad_goodGpa;
    private boolean ad_majorSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        DA_InitData();
        DA_ListOfViews();
        DA_SetTextViewsData();
        DA_OnClickListener();
        DA_TextChangedEventListener();

        vs_jDetails_viewSwitcher.setAnimateFirstView(true);
    }
    private void DA_InitData()
    {

        da_mainIntent = new Intent(DetailsActivity.this, MainActivity.class);
        da_addNewDataIntent = new Intent(DetailsActivity.this, AddNewDataActivity.class);
        // inflating the different views to be used with the ViewSwitcher //
        LayoutInflater da_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        da_vsSwitcher_details = da_inflater.inflate(R.layout.da_vswitcher_details_layout, null);
        da_vsSwitcher_update = da_inflater.inflate(R.layout.da_vswitcher_update_layout, null);
        // using dbHelper to get the data from the database
        da_dbHelper = new DatabaseHelper(this);
        da_passedStudentData = da_dbHelper.DB_getSingleStudentData(StudentData.PassStudentData.getLvMainLongClickPos());
        da_passedMajorData = da_dbHelper.DB_getSingleMajorData(da_passedStudentData.getSd_major());
        da_passedMajorNames = da_dbHelper.DB_getListOfMajorNames();
        da_passedPrefixes = da_dbHelper.DB_getListOfPrefixes();
        da_spMajAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, da_passedMajorNames);

    }
    private void DA_ListOfViews()
    {
        // Main Views //
        vs_jDetails_viewSwitcher = findViewById(R.id.vs_vDetails_viewSwitcher);
        vs_jDetails_viewSwitcher.addView(da_vsSwitcher_details);
        vs_jDetails_viewSwitcher.addView(da_vsSwitcher_update);
        tv_jDetails_headerText = findViewById(R.id.tv_vDetails_headerText);
        iv_jDetails_homeBtn = findViewById(R.id.iv_vDetails_homeBtn);
        iv_jDetails_updateBtn = findViewById(R.id.iv_vDetails_updateBtn);
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
        iv_j_vsUpdate_backBtn = findViewById(R.id.iv_vsUpdate_backBtn);
        tv_j_vsUpdate_username = findViewById(R.id.tv_vsUpdate_username);
        et_j_vsUpdate_fname = findViewById(R.id.et_vsUpdate_fname);
        et_j_vsUpdate_lname = findViewById(R.id.et_vsUpdate_lname);
        et_j_vsUpdate_email = findViewById(R.id.et_vsUpdate_email);
        et_j_vsUpdate_age = findViewById(R.id.et_vsUpdate_age);
        et_j_vsUpdate_gpa = findViewById(R.id.et_vsUpdate_gpa);
        tv_j_vsUpdate_addNewMajorBtn = findViewById(R.id.tv_vsUpdate_addNewMajorBtn);
        sp_j_vsUpdate_major = findViewById(R.id.sp_vsUpdate_major);
    }
    private void DA_SetTextViewsData()
    {
        // old way of passing the data with object array of all students before using an object to pass the data for the single student.
//        da_passedStudentData = da_dbHelper.DB_getSingleStudentData(StudentData.PassStudentData.getLvMainLongClickPos());
//        da_passedMajorData = da_dbHelper.DB_getSingleMajorData(da_passedStudentData.getSd_major());
//        da_passedPrefixData = da_dbHelper.DB_getPrefixData();
        // Setting Text for Header //
        if(vs_jDetails_viewSwitcher.getCurrentView() == da_vsSwitcher_details)
        {

        }
        else
        {

        }
        // Setting the TextView data for the Details View //
        tv_j_vsDetails_username.setText(da_passedStudentData.getSd_username());
        tv_j_vsDetails_fname.setText(da_passedStudentData.getSd_fname());
        tv_j_vsDetails_lname.setText(da_passedStudentData.getSd_lname());
        tv_j_vsDetails_email.setText(da_passedStudentData.getSd_email());
        tv_j_vsDetails_age.setText(String.valueOf(da_passedStudentData.getSd_age()));
        tv_j_vsDetails_gpa.setText(String.valueOf(da_passedStudentData.getSd_gpa()));
        tv_j_vsDetails_major.setText(da_passedMajorData.getMd_majorName());
        tv_j_vsDetails_majorPrefix.setText(da_passedPrefixes.get(da_passedMajorData.getMd_majorPrefixId()));

        // Setting the TextView data for the Update View //
        tv_j_vsUpdate_username.setText(da_passedStudentData.getSd_username());
        et_j_vsUpdate_fname.setText(da_passedStudentData.getSd_fname());
        et_j_vsUpdate_lname.setText(da_passedStudentData.getSd_lname());
        et_j_vsUpdate_email.setText(da_passedStudentData.getSd_email());
        et_j_vsUpdate_age.setText(String.valueOf(da_passedStudentData.getSd_age()));
        et_j_vsUpdate_gpa.setText(String.valueOf(da_passedStudentData.getSd_gpa()));
        sp_j_vsUpdate_major.setAdapter(da_spMajAdapter);
        sp_j_vsUpdate_major.setSelection(da_passedStudentData.getSd_major() + 1);
    }
    private void DA_OnClickListener()
    {
        iv_jDetails_homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(da_mainIntent);
            }
        });
        iv_jDetails_updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vs_jDetails_viewSwitcher.getCurrentView() != da_vsSwitcher_update)
                {
                    tv_jDetails_headerText.setText("Update Student");
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
                    tv_jDetails_headerText.setText("Student Details");
                    vs_jDetails_viewSwitcher.showPrevious();
                }
            }
        });
        tv_j_vsUpdate_addNewMajorBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MajorData.PassMajorData.setMP_AddNewMajorSelected(true);
                startActivity(da_addNewDataIntent);
            }
        });
    }
    private boolean DA_BadUserInput(String s, CharSequence cs)
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
    private void DA_TextChangedEventListener()
    {
        et_j_vsUpdate_fname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(DA_BadUserInput(ALLOWED_NAME_CHARS,s))
                {
                    // need to add error text
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsUpdate_lname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsUpdate_email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsUpdate_age.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsUpdate_gpa.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

}