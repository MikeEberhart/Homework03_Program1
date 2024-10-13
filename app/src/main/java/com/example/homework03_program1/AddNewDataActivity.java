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
    Intent passedIntent;
    Intent ad_detailsIntent;
    Intent ad_searchIntent;
    Intent ad_mainIntent;
    TextView tv_jAddNewData_headerText;
    ViewSwitcher vs_jAddNewData_viewSwitcher;
    ImageView iv_jAddNewData_homeBtn;
    ImageView iv_jAddNewData_addBtn;
    View ad_vsSwitcher_addStudent;
    View ad_vsSwitcher_addMajor;
    DatabaseHelper ad_dbHelper;
    ArrayList<String> ad_passedPrefixes;
    ArrayList<String> ad_passedMajorNames;
    // Add New Student Views //
    ImageView iv_j_vsAddNewStudent_addStudentBtn;
    ImageView iv_j_vsAddNewStudent_backBtn;
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
        AD_TextChangeEventListener();

        Log.d("add major outside", "if passedIntent");
        if(MajorData.PassMajorData.getMP_AddNewMajorSelected())
        {
            Log.d("add major inside", "if passedIntent");
            vs_jAddNewData_viewSwitcher.setAnimateFirstView(true);
            vs_jAddNewData_viewSwitcher.showNext();
        }
        else
        {
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
        iv_j_vsAddNewStudent_addStudentBtn = findViewById(R.id.iv_vsAddNewStudent_addStudentBtn);
        iv_j_vsAddNewStudent_backBtn = findViewById(R.id.iv_vsAddNewStudent_backBtn);
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
                // make not use this btn on the add new student activity
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
        iv_j_vsAddNewStudent_addStudentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ad_goodUsername && ad_goodFname && ad_goodLname && ad_goodEmail && ad_goodAge && ad_goodGpa && ad_majorSelected)
                {

                    Log.d("ADDED NEW STUDENT", String.valueOf(sp_j_vsAddNewStudent_major.getSelectedItemPosition()));
                    Log.d("First Name", et_j_vsAddNewStudent_fname.toString());
                    AD_FormatAndSaveData();
                    AD_ResetAllTextAndBools();
                }
                else if(!ad_majorSelected)
                {
                    tv_j_vsAddNewStudent_majorError.setVisibility(View.VISIBLE);
                }
                else
                {
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

    }
    private void AD_TextChangeEventListener()
    {
        // if CharSequence doesn't equal allowed_chars error messages = visible
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
    }
    private boolean AD_NoEmptyInput() // check to see if text box is empty // maybe combine this BadDataChecker?
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

    private void AD_FormatAndSaveData() // convert input text to proper format before saving to the database
    {
        String un = et_j_vsAddNewStudent_username.getText().toString();
        String fn = et_j_vsAddNewStudent_fname.getText().toString();
        String ln = et_j_vsAddNewStudent_lname.getText().toString();
        String em = et_j_vsAddNewStudent_email.getText().toString();
        int age = Integer.parseInt(et_j_vsAddNewStudent_age.getText().toString());
        double gpa = Double.parseDouble(et_j_vsAddNewStudent_gpa.getText().toString());
        int maj = sp_j_vsAddNewStudent_major.getSelectedItemPosition() - 1;
        fn = fn.toLowerCase();
        ln = ln.toLowerCase();
        fn = fn.substring(0,1).toUpperCase() + fn.substring(1);
        ln = ln.substring(0,1).toUpperCase() + ln.substring(1);
        Log.d("new first name", fn + " " + ln);
        ad_dbHelper.DB_addNewStudentToDatabase(un,fn,ln,em,age,gpa,maj);
    }

    private void AD_ResetAllTextAndBools()
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
}