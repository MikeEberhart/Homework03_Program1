package com.example.homework03_program1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewDataActivity extends AppCompatActivity
{
    // Main Views //
    Intent ad_detailsIntent;
    Intent ad_searchIntent;
    Intent ad_mainIntent;
    TextView tv_jAddNewData_headerText;
    ViewSwitcher vs_jAddNewData_viewSwitcher;
    ImageView iv_jAddNewData_homeBtn;
    View ad_vsSwitcher_addStudent;
    View ad_vsSwitcher_addMajor;
    DatabaseHelper ad_dbHelper;
    ArrayList<String> ad_passedPrefixes;
    ArrayList<String> ad_passedMajorNames;
    // Add New Student Views //
    ImageView iv_j_vsAddNewStudent_addStudentBtn;
    EditText et_j_vsAddNewStudent_username;
    EditText et_j_vsAddNewStudent_fname;
    EditText et_j_vsAddNewStudent_lname;
    EditText et_j_vsAddNewStudent_email;
    EditText et_j_vsAddNewStudent_age;
    EditText et_j_vsAddNewStudent_gpa;
    TextView tv_j_vsAddNewStudent_usernameError;
    TextView tv_j_vsAddNewStudent_fnameError;
    TextView tv_j_vsAddNewStudent_lnameError;
    TextView tv_j_vsAddNewStudent_emailError;
    TextView tv_j_vsAddNewStudent_ageError;
    TextView tv_j_vsAddNewStudent_gpaError;
    TextView tv_j_vsAddNewStudent_majorError;
    TextView tv_j_vsAddNewStudent_addNewMajorBtn;
    Spinner sp_j_vsAddNewStudent_major;
    ArrayAdapter<String> ad_spMajAdapter;
    // Add New Major Views //
    ImageView iv_j_vsAddNewMajor_backBtn;
    ImageView iv_j_vsAddNewMajor_addNewMajorBtn;
    TextView tv_j_vsAddNewMajor_majorNameError;
    TextView tv_j_vsAddNewMajor_majorPrefixError;
    EditText et_j_vsAddNewMajor_majorName;
    Spinner sp_j_vsAddNewMajor_majorPrefix;
    ArrayAdapter<String> ad_spPrefixAdapter;
    private static final String ALLOWED_USERNAME_CHARS = "^[a-zA-Z0-9_.-]+$"; // regex string allowing only upper and lower case along with number and a few symbols
    private static final String ALLOWED_NAME_CHARS = "^[a-zA-Z\\s]+$"; // regex string allowing only upper and lower case
    private static final String ALLOWED_EMAIL_CHARS = "^[a-zA-Z](?:[a-zA-Z0-9_.-]*[a-zA-Z0-9])?+@[a-zA-Z.]+\\.edu$"; // regex string formatted so the last char in the email name can't be a special char and the school email can only contain letters and '.' and the last part must have .edu
    private static final String ALLOWED_AGE_CHARS =  "^(1[6789]|[2-9][0-9])$"; // regex string formatted making sure the minimal age is 16 with no maximal
    private static final String ALLOWED_GPA_CHARS = "^(1(\\.\\d+)|2(\\.\\d+)|3(\\.\\d+)|4(\\.0))$"; // regex string formatted so gpa range is 1.0 to 4.0
    private boolean ad_goodUsername;
    private boolean ad_goodFname;
    private boolean ad_goodLname;
    private boolean ad_goodEmail;
    private boolean ad_goodAge;
    private boolean ad_goodGpa;
    private boolean ad_majorSelected;
    private boolean ad_initByDetailsActivity;
    private boolean ad_prefixSelected;
    private boolean ad_goodMajorName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_data);

        AD_InitData();
        AD_ListOfViews();
        AD_OnClickListener();
        AD_TextChangeEventListener();

        Log.d("add major outside", "if passedIntent");
        if(MajorData.PassMajorData.getMP_AddMajorFromDetails())
        {
            ad_initByDetailsActivity = true;
            Log.d("add major inside", "if passedIntent");
            tv_jAddNewData_headerText.setText("Add New Major");
            MajorData.PassMajorData.setMP_AddMajorFromDetails(false);
            vs_jAddNewData_viewSwitcher.setAnimateFirstView(true);
            vs_jAddNewData_viewSwitcher.showNext();
        }
        else
        {
            ad_initByDetailsActivity = false;
            tv_jAddNewData_headerText.setText("Add New Student");
            vs_jAddNewData_viewSwitcher.setAnimateFirstView(true);
        }
    }
    private void AD_InitData()
    {

        ad_mainIntent = new Intent(AddNewDataActivity.this, MainActivity.class);
        ad_detailsIntent = new Intent(AddNewDataActivity.this, DetailsActivity.class);
        ad_searchIntent = new Intent(AddNewDataActivity.this, SearchActivity.class);
        // inflating the different views to be used with the ViewSwitcher //
        LayoutInflater ad_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ad_vsSwitcher_addStudent = ad_inflater.inflate(R.layout.ad_vswitcher_addstudent_layout, null);
        ad_vsSwitcher_addMajor = ad_inflater.inflate(R.layout.ad_vswitcher_addmajor_layout, null);
        // using dbHelper to get the data from the database
        ad_dbHelper = new DatabaseHelper(this);
        ad_passedMajorNames = ad_dbHelper.DB_getListOfMajorNames();
        ad_passedPrefixes = ad_dbHelper.DB_getListOfPrefixes();
        ad_spMajAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ad_passedMajorNames);
        ad_spPrefixAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ad_passedPrefixes);

    }
    private void AD_ListOfViews()
    {
        // Main Views //
        vs_jAddNewData_viewSwitcher = findViewById(R.id.vs_vAddNewData_viewSwitcher);
        vs_jAddNewData_viewSwitcher.addView(ad_vsSwitcher_addStudent);
        vs_jAddNewData_viewSwitcher.addView(ad_vsSwitcher_addMajor);
        tv_jAddNewData_headerText = findViewById(R.id.tv_vAddNewData_headerText);
        iv_jAddNewData_homeBtn = findViewById(R.id.iv_vAddNewData_homeBtn);
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
        iv_j_vsAddNewStudent_addStudentBtn = findViewById(R.id.iv_vsAddNewStudent_addStudentBtn);
        tv_j_vsAddNewStudent_usernameError = findViewById(R.id.tv_vsAddNewStudent_usernameError);
        tv_j_vsAddNewStudent_fnameError = findViewById(R.id.tv_vsAddNewStudent_fnameError);
        tv_j_vsAddNewStudent_lnameError = findViewById(R.id.tv_vsAddNewStudent_lnameError);
        tv_j_vsAddNewStudent_emailError = findViewById(R.id.tv_vsAddNewStudent_emailError);
        tv_j_vsAddNewStudent_ageError = findViewById(R.id.tv_vsAddNewStudent_ageError);
        tv_j_vsAddNewStudent_gpaError = findViewById(R.id.tv_vsAddNewStudent_gpaError);
        tv_j_vsAddNewStudent_majorError = findViewById(R.id.tv_vsAddNewStudent_majorError);
        tv_j_vsAddNewStudent_usernameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_fnameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_lnameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_emailError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_ageError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_gpaError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_majorError.setVisibility(View.INVISIBLE);
        // Add New Major Views //
        iv_j_vsAddNewMajor_backBtn = findViewById(R.id.iv_vsAddNewMajor_backBtn);
        iv_j_vsAddNewMajor_addNewMajorBtn = findViewById(R.id.iv_vsAddNewMajor_addNewMajorBtn);
        tv_j_vsAddNewMajor_majorNameError = findViewById(R.id.tv_vsAddNewMajor_majorNameError);
        tv_j_vsAddNewMajor_majorPrefixError = findViewById(R.id.tv_vsAddNewMajor_majorPrefixError);
        et_j_vsAddNewMajor_majorName = findViewById(R.id.et_vsAddNewMajor_majorName);
        sp_j_vsAddNewMajor_majorPrefix = findViewById(R.id.sp_vsAddNewMajor_majorPrefix);
        sp_j_vsAddNewMajor_majorPrefix.setAdapter(ad_spPrefixAdapter);
        tv_j_vsAddNewMajor_majorNameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewMajor_majorPrefixError.setVisibility(View.INVISIBLE);

    }
    private void AD_OnClickListener()
    {
        // Main Listeners
        iv_jAddNewData_homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(ad_mainIntent);
            }
        });
        // AddNewStudent Listeners
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
        iv_j_vsAddNewStudent_addStudentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ad_goodUsername && ad_goodFname && ad_goodLname && ad_goodEmail && ad_goodAge && ad_goodGpa && ad_majorSelected)
                {

                    Log.d("ADDED NEW STUDENT", String.valueOf(sp_j_vsAddNewStudent_major.getSelectedItemPosition()));
                    Log.d("First Name", et_j_vsAddNewStudent_fname.toString());
                    AD_FormatAndSaveNewStudentData();
                    AD_ResetNewStudentTextAndBools();
                }
                else
                {
                    AD_EmptyInputErrorCheck();
                    Log.d("NOT ADDED NEW STUDENT", "NOT ADDED NEW STUDENT");
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
                    tv_j_vsAddNewStudent_majorError.setVisibility(View.INVISIBLE);

                }
                else
                {
                    ad_majorSelected = false;
//                    tv_j_vsAddNewStudent_majorError.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        // AddNewMajor Listeners
        iv_j_vsAddNewMajor_backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ad_initByDetailsActivity)
                {
                    MajorData.PassMajorData.setMP_AddMajorBackToDetails(true);
                    ad_initByDetailsActivity = false;
                    startActivity(ad_detailsIntent);
                }
                else
                {
                    ad_passedMajorNames = ad_dbHelper.DB_getListOfMajorNames();
                    ad_passedPrefixes = ad_dbHelper.DB_getListOfPrefixes();
                    ad_spMajAdapter.clear();
                    ad_spPrefixAdapter.clear();
                    ad_spMajAdapter.addAll(ad_passedMajorNames);
                    ad_spPrefixAdapter.addAll(ad_passedPrefixes);
                    ad_spMajAdapter.notifyDataSetChanged();
                    ad_spPrefixAdapter.notifyDataSetChanged();
                    MajorData.PassMajorData.setMP_AddMajorBackToDetails(false);
                    tv_jAddNewData_headerText.setText("Add New Student");
                    vs_jAddNewData_viewSwitcher.showPrevious();
                }

            }
        });
        iv_j_vsAddNewMajor_addNewMajorBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!ad_prefixSelected)
                {
                    tv_j_vsAddNewMajor_majorPrefixError.setVisibility(View.VISIBLE);
                    if(et_j_vsAddNewMajor_majorName.getText().toString().isEmpty())
                    {
                        tv_j_vsAddNewMajor_majorNameError.setText("Enter Major Name");
                        tv_j_vsAddNewMajor_majorNameError.setVisibility(View.VISIBLE);
                    }
                }
                else if(AD_MajorNameAlreadyExists(et_j_vsAddNewMajor_majorName.getText().toString()))
                {
                    tv_j_vsAddNewMajor_majorNameError.setText("Major Already Exists");
                    tv_j_vsAddNewMajor_majorNameError.setVisibility(View.VISIBLE);
                }
                else if(ad_goodMajorName && ad_prefixSelected)
                {
                    AD_FormatAndSaveNewStudentData();
                    AD_ResetNewMajorTextAndBools();
                }
            }
        });
        sp_j_vsAddNewMajor_majorPrefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position != 0)
                {
                    tv_j_vsAddNewMajor_majorPrefixError.setVisibility(View.INVISIBLE);
                    ad_prefixSelected = true;
                }
                else
                {
                    ad_prefixSelected = false;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
    private void AD_TextChangeEventListener()
    {
        // if CharSequence doesn't equal allowed_chars error messages = visible
        // AddNewStudent TextChange Listeners
        et_j_vsAddNewStudent_username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(AD_BadUserInput(ALLOWED_USERNAME_CHARS, s))
                {
                    tv_j_vsAddNewStudent_usernameError.setText("Only Letters/Numbers/_.- Allowed");
                    tv_j_vsAddNewStudent_usernameError.setVisibility(View.VISIBLE);
                    ad_goodUsername = false;
                }
                else
                {
                    tv_j_vsAddNewStudent_usernameError.setVisibility(View.INVISIBLE);
                    ad_goodUsername = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsAddNewStudent_fname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(AD_BadUserInput(ALLOWED_NAME_CHARS, s))
                {
                    tv_j_vsAddNewStudent_fnameError.setText("Letters Only");
                    tv_j_vsAddNewStudent_fnameError.setVisibility(View.VISIBLE);
                    ad_goodFname = false;
                }
                else
                {
                    tv_j_vsAddNewStudent_fnameError.setVisibility(View.INVISIBLE);
                    ad_goodFname = true;
                }

            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsAddNewStudent_lname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(AD_BadUserInput(ALLOWED_NAME_CHARS, s))
                {
                    tv_j_vsAddNewStudent_lnameError.setText("Letters Only");
                    tv_j_vsAddNewStudent_lnameError.setVisibility(View.VISIBLE);
                    ad_goodLname = false;
                }
                else
                {
                    tv_j_vsAddNewStudent_lnameError.setVisibility(View.INVISIBLE);
                    ad_goodLname = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsAddNewStudent_email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(AD_BadUserInput(ALLOWED_EMAIL_CHARS, s))
                {
                    tv_j_vsAddNewStudent_emailError.setText("Format = email@school.edu");
                    tv_j_vsAddNewStudent_emailError.setVisibility(View.VISIBLE);
                    ad_goodEmail = false;
                }
                else
                {
                    tv_j_vsAddNewStudent_emailError.setVisibility(View.INVISIBLE);
                    ad_goodEmail = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsAddNewStudent_age.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(AD_BadUserInput(ALLOWED_AGE_CHARS, s))
                {
                    tv_j_vsAddNewStudent_ageError.setText("Numbers Only (Min = 16, Max = 99)");
                    tv_j_vsAddNewStudent_ageError.setVisibility(View.VISIBLE);
                    ad_goodAge = false;
                }
                else
                {
                    tv_j_vsAddNewStudent_ageError.setVisibility(View.INVISIBLE);
                    ad_goodAge = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        et_j_vsAddNewStudent_gpa.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(AD_BadUserInput(ALLOWED_GPA_CHARS, s))
                {
                    tv_j_vsAddNewStudent_gpaError.setText("GPA range = 1.0 to 4.0 \n1 decimal place required");
                    tv_j_vsAddNewStudent_gpaError.setVisibility(View.VISIBLE);
                    ad_goodGpa = false;
                }
                else
                {
                    tv_j_vsAddNewStudent_gpaError.setVisibility(View.INVISIBLE);
                    ad_goodGpa = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        // AddNewMajor TextChange Listener
        et_j_vsAddNewMajor_majorName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(et_j_vsAddNewMajor_majorName.getText().toString().isEmpty())
                {
                    ad_goodMajorName = false;
                    tv_j_vsAddNewMajor_majorNameError.setText("Enter Major Name");
                    tv_j_vsAddNewMajor_majorNameError.setVisibility(View.VISIBLE);
                }
                else if(AD_BadUserInput(ALLOWED_NAME_CHARS, s))
                {
                    ad_goodMajorName = false;
                    tv_j_vsAddNewMajor_majorNameError.setText("Letters Only");
                    tv_j_vsAddNewMajor_majorNameError.setVisibility(View.VISIBLE);
                }
                else
                {
                    ad_goodMajorName = true;
                    tv_j_vsAddNewMajor_majorNameError.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

    }
    private void AD_EmptyInputErrorCheck() // check to see if text box is empty // maybe combine this BadDataChecker?
    {
        if(vs_jAddNewData_viewSwitcher.getCurrentView() == ad_vsSwitcher_addStudent)
        {
            if(et_j_vsAddNewStudent_username.getText().toString().isEmpty())
            {
                tv_j_vsAddNewStudent_usernameError.setText("Enter a Username");
                tv_j_vsAddNewStudent_usernameError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsAddNewStudent_fname.getText().toString().isEmpty())
            {
                tv_j_vsAddNewStudent_fnameError.setText("Enter a First Name");
                tv_j_vsAddNewStudent_fnameError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsAddNewStudent_lname.getText().toString().isEmpty())
            {
                tv_j_vsAddNewStudent_lnameError.setText("Enter a Last Name");
                tv_j_vsAddNewStudent_lnameError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsAddNewStudent_email.getText().toString().isEmpty())
            {
                tv_j_vsAddNewStudent_emailError.setText("Enter a Email");
                tv_j_vsAddNewStudent_emailError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsAddNewStudent_age.getText().toString().isEmpty())
            {
                tv_j_vsAddNewStudent_ageError.setText("Enter an Age");
                tv_j_vsAddNewStudent_ageError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsAddNewStudent_gpa.getText().toString().isEmpty())
            {
                tv_j_vsAddNewStudent_gpaError.setText("Enter a GPA");
                tv_j_vsAddNewStudent_gpaError.setVisibility(View.VISIBLE);
            }
            if(!ad_majorSelected)
            {
                tv_j_vsAddNewStudent_majorError.setText("Select a Major");
                tv_j_vsAddNewStudent_majorError.setVisibility(View.VISIBLE);
            }
        }
        else if(vs_jAddNewData_viewSwitcher.getCurrentView() == ad_vsSwitcher_addMajor)
        {
            if(et_j_vsAddNewMajor_majorName.getText().toString().isEmpty())
            {
                tv_j_vsAddNewMajor_majorNameError.setText("Enter a Major");
                tv_j_vsAddNewMajor_majorNameError.setVisibility(View.VISIBLE);
            }
            if(ad_prefixSelected)
            {
                tv_j_vsAddNewMajor_majorPrefixError.setText("Select a Prefix");
                tv_j_vsAddNewMajor_majorPrefixError.setVisibility(View.VISIBLE);
            }
        }
    }
    private boolean AD_BadUserInput(String s, CharSequence cs)
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
    private boolean AD_MajorNameAlreadyExists(String s)
    {
        for(int i = 0; i < ad_passedMajorNames.size(); i++)
        {
            if(ad_passedMajorNames.get(i).equals(s))
            {
                return true;
            }
        }
        return false;
    }
    private void AD_FormatAndSaveNewStudentData() // convert input text to proper format before saving to the database
    {
        if(vs_jAddNewData_viewSwitcher.getCurrentView() == ad_vsSwitcher_addStudent)
        {
            String un = et_j_vsAddNewStudent_username.getText().toString();
            String fn = et_j_vsAddNewStudent_fname.getText().toString();
            String ln = et_j_vsAddNewStudent_lname.getText().toString();
            String em = et_j_vsAddNewStudent_email.getText().toString();
            int age = Integer.parseInt(et_j_vsAddNewStudent_age.getText().toString());
            double gpa = Double.parseDouble(et_j_vsAddNewStudent_gpa.getText().toString());
            int maj = sp_j_vsAddNewStudent_major.getSelectedItemPosition() - 1;
            // Converting all to lowercase then only the first letter to uppercase
            fn = fn.toLowerCase();
            ln = ln.toLowerCase();
            fn = fn.substring(0,1).toUpperCase() + fn.substring(1);
            ln = ln.substring(0,1).toUpperCase() + ln.substring(1);
            Log.d("new first name", fn + " " + ln);
            ad_dbHelper.DB_addNewStudentToDatabase(un,fn,ln,em,age,gpa,maj);
        }
        else if(vs_jAddNewData_viewSwitcher.getCurrentView() == ad_vsSwitcher_addMajor)
        {
            String addedMaj = et_j_vsAddNewMajor_majorName.getText().toString();
            int prefix = sp_j_vsAddNewMajor_majorPrefix.getSelectedItemPosition() - 1;
            addedMaj = addedMaj.toLowerCase();
            addedMaj = addedMaj.substring(0,1).toUpperCase() + addedMaj.substring(1);
            ad_dbHelper.DB_addNewMajorToDatabase(addedMaj, prefix);
        }

    }
    private void AD_ResetNewStudentTextAndBools()
    {
        ad_goodUsername = false;
        ad_goodFname = false;
        ad_goodLname = false;
        ad_goodEmail = false;
        ad_goodAge = false;
        ad_goodGpa = false;
        ad_majorSelected = false;
        et_j_vsAddNewStudent_username.setText("");
        et_j_vsAddNewStudent_fname.setText("");
        et_j_vsAddNewStudent_lname.setText("");
        et_j_vsAddNewStudent_email.setText("");
        et_j_vsAddNewStudent_age.setText("");
        et_j_vsAddNewStudent_gpa.setText("");
        sp_j_vsAddNewStudent_major.setSelection(0);
        tv_j_vsAddNewStudent_usernameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_fnameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_lnameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_emailError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_ageError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_gpaError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewStudent_majorError.setVisibility(View.INVISIBLE);
    }
    private void AD_ResetNewMajorTextAndBools()
    {
        et_j_vsAddNewMajor_majorName.setText("");
        sp_j_vsAddNewMajor_majorPrefix.setSelection(0);
        tv_j_vsAddNewMajor_majorNameError.setVisibility(View.INVISIBLE);
        tv_j_vsAddNewMajor_majorPrefixError.setVisibility(View.INVISIBLE);
        ad_goodMajorName = false;
        ad_prefixSelected = false;
    }
}