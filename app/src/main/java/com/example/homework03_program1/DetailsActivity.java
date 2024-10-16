package com.example.homework03_program1;

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

public class DetailsActivity extends AppCompatActivity
{
    // Main Details Activity
    Intent da_mainIntent;
    Intent da_addNewDataIntent;
    ImageView iv_jDetails_homeBtn;
    ImageView iv_j_vsDetails_updateBtn;
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
    ImageView iv_j_vsUpdate_saveChangesBtn;
    TextView tv_j_vsUpdate_username;
    TextView tv_j_vsUpdate_fnameError;
    TextView tv_j_vsUpdate_lnameError;
    TextView tv_j_vsUpdate_emailError;
    TextView tv_j_vsUpdate_ageError;
    TextView tv_j_vsUpdate_gpaError;
    TextView tv_j_vsUpdate_majorError;
    TextView tv_j_vsUpdate_addNewMajorBtn;
    EditText et_j_vsUpdate_fname;
    EditText et_j_vsUpdate_lname;
    EditText et_j_vsUpdate_email;
    EditText et_j_vsUpdate_age;
    EditText et_j_vsUpdate_gpa;
    Spinner sp_j_vsUpdate_major;
    private static final String ALLOWED_USERNAME_CHARS = "^[a-zA-Z0-9_.-]+$"; // regex string allowing only upper and lower case along with number and a few symbols
    private static final String ALLOWED_NAME_CHARS = "^[a-zA-Z]+$"; // regex string allowing only upper and lower case
    private static final String ALLOWED_EMAIL_CHARS = "^[a-zA-Z](?:[a-zA-Z0-9_.-]*[a-zA-Z0-9])?+@[a-zA-Z.]+\\.edu$"; // regex string formatted so the last char in the email name can't be a special char and the school email can only contain letters and '.' and the last part must have .edu
    private static final String ALLOWED_AGE_CHARS =  "^(1[6789]|[2-9][0-9])$"; // regex string formatted making sure the minimal age is 16 with no maximal
    private static final String ALLOWED_GPA_CHARS = "^(1(\\.\\d+)|2(\\.\\d+)|3(\\.\\d+)|4(\\.0))$"; // regex string formatted so gpa range is 1.0 to 4.0
//    private boolean ad_goodUsername;
    private boolean da_goodFname;
    private boolean da_goodLname;
    private boolean da_goodEmail;
    private boolean da_goodAge;
    private boolean da_goodGpa;
    private boolean da_majorSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        DA_InitData();
        DA_ListOfViews();
        DA_SetTextViewsData(da_passedStudentData, da_passedMajorData);
        DA_OnClickListener();
        DA_TextChangedEventListener();

        if(MajorData.PassMajorData.getMP_AddMajorBackToDetails())
        {
            vs_jDetails_viewSwitcher.setAnimateFirstView(true);
            tv_jDetails_headerText.setText("Update Student");
            vs_jDetails_viewSwitcher.showNext();
            MajorData.PassMajorData.setMP_AddMajorBackToDetails(false);
            da_goodFname = true;
            da_goodLname = true;
            da_goodEmail = true;
            da_goodAge = true;
            da_goodGpa = true;
            da_majorSelected = true;
        }
        else
        {
            vs_jDetails_viewSwitcher.setAnimateFirstView(true);
        }
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
        iv_j_vsDetails_updateBtn = findViewById(R.id.iv_vsDetails_updateBtn);
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
        tv_j_vsUpdate_fnameError = findViewById(R.id.tv_vsUpdate_fnameError);
        tv_j_vsUpdate_lnameError = findViewById(R.id.tv_vsUpdate_lnameError);
        tv_j_vsUpdate_emailError = findViewById(R.id.tv_vsUpdate_emailError);
        tv_j_vsUpdate_ageError = findViewById(R.id.tv_vsUpdate_ageError);
        tv_j_vsUpdate_gpaError = findViewById(R.id.tv_vsUpdate_gpaError);
        tv_j_vsUpdate_majorError = findViewById(R.id.tv_vsUpdate_majorError);
        iv_j_vsUpdate_saveChangesBtn = findViewById(R.id.iv_vsUpdate_saveChangesBtn);
        et_j_vsUpdate_fname = findViewById(R.id.et_vsUpdate_fname);
        et_j_vsUpdate_lname = findViewById(R.id.et_vsUpdate_lname);
        et_j_vsUpdate_email = findViewById(R.id.et_vsUpdate_email);
        et_j_vsUpdate_age = findViewById(R.id.et_vsUpdate_age);
        et_j_vsUpdate_gpa = findViewById(R.id.et_vsUpdate_gpa);
        tv_j_vsUpdate_addNewMajorBtn = findViewById(R.id.tv_vsUpdate_addNewMajorBtn);
        sp_j_vsUpdate_major = findViewById(R.id.sp_vsUpdate_major);
        tv_j_vsUpdate_fnameError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_lnameError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_emailError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_ageError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_gpaError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_majorError.setVisibility(View.INVISIBLE);
    }
    private void DA_SetTextViewsData(StudentData sd, MajorData md)
    {
        // old way of passing the data with array of all students before passing the data for just a single student.
//        da_passedStudentData = da_dbHelper.DB_getSingleStudentData(StudentData.PassStudentData.getLvMainLongClickPos());
//        da_passedMajorData = da_dbHelper.DB_getSingleMajorData(da_passedStudentData.getSd_major());
//        da_passedPrefixData = da_dbHelper.DB_getPrefixData();

        // Setting the TextView data for the Details View //
        tv_j_vsDetails_username.setText(sd.getSd_username());
        tv_j_vsDetails_fname.setText(sd.getSd_fname());
        tv_j_vsDetails_lname.setText(sd.getSd_lname());
        tv_j_vsDetails_email.setText(sd.getSd_email());
        tv_j_vsDetails_age.setText(String.valueOf(sd.getSd_age()));
        tv_j_vsDetails_gpa.setText(String.valueOf(sd.getSd_gpa()));
        tv_j_vsDetails_major.setText(md.getMd_majorName());
        tv_j_vsDetails_majorPrefix.setText(da_passedPrefixes.get(md.getMd_majorPrefixId() + 1));

        // Setting the TextView data for the Update View //
        tv_j_vsUpdate_username.setText(sd.getSd_username());
        et_j_vsUpdate_fname.setText(sd.getSd_fname());
        et_j_vsUpdate_lname.setText(sd.getSd_lname());
        et_j_vsUpdate_email.setText(sd.getSd_email());
        et_j_vsUpdate_age.setText(String.valueOf(sd.getSd_age()));
        et_j_vsUpdate_gpa.setText(String.valueOf(sd.getSd_gpa()));
        sp_j_vsUpdate_major.setAdapter(da_spMajAdapter);
        sp_j_vsUpdate_major.setSelection(sd.getSd_major() + 1);

    }
    private void DA_OnClickListener()
    {
        // Main Listeners
        iv_jDetails_homeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(da_mainIntent);
            }
        });
        iv_j_vsDetails_updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vs_jDetails_viewSwitcher.getCurrentView() != da_vsSwitcher_update)
                {
                    tv_jDetails_headerText.setText("Update Student");
                    da_goodFname = true;
                    da_goodLname = true;
                    da_goodEmail = true;
                    da_goodAge = true;
                    da_goodGpa = true;
                    da_majorSelected = true;
                    vs_jDetails_viewSwitcher.showNext();
                }
                else if(vs_jDetails_viewSwitcher.getCurrentView() == da_vsSwitcher_update)
                {
                    // put save new data function here
                    // might not need this since i have a save btn in the view now
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
                MajorData.PassMajorData.setMP_AddMajorFromDetails(true);
                startActivity(da_addNewDataIntent);
            }
        });
        iv_j_vsUpdate_saveChangesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Outside save", "Outside save");
                if(da_goodFname && da_goodLname && da_goodEmail && da_goodAge && da_goodGpa && da_majorSelected && !DA_EmailAlreadyExists())
                {
                    Log.d("Updating Student", "Updating Student");
                    DA_FormatAndSaveUpdatedStudent();
                    da_passedStudentData = da_dbHelper.DB_getSingleStudentData(StudentData.PassStudentData.getLvMainLongClickPos());
                    da_passedMajorData = da_dbHelper.DB_getSingleMajorData(da_passedStudentData.getSd_major());
                    DA_SetTextViewsData(da_passedStudentData, da_passedMajorData);
                    DA_ResetUpdateBoolsAndErrors();
                    if(vs_jDetails_viewSwitcher.getCurrentView() == da_vsSwitcher_update)
                    {
                        tv_jDetails_headerText.setText("Student Details");
                        vs_jDetails_viewSwitcher.showPrevious();
                    }
                }
                else if(DA_EmailAlreadyExists())
                {
                    tv_j_vsUpdate_emailError.setText("Email Not Available");
                    tv_j_vsUpdate_emailError.setVisibility(View.VISIBLE);
                }
                else
                {
                    DA_EmptyInputErrorChecker();
                }

            }
        });
        sp_j_vsUpdate_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0)
                {
                    da_majorSelected = true;
                }
                else
                {
                    da_majorSelected = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
    private boolean DA_BadUserInput(String s, CharSequence cs)
    {
        // was error checking with a for loop but read up on regex for java.
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
                    tv_j_vsUpdate_fnameError.setText("Letters Only");
                    tv_j_vsUpdate_fnameError.setVisibility(View.VISIBLE);
                    da_goodFname = false;
                    // need to add error text
                }
                else
                {
                    tv_j_vsUpdate_fnameError.setVisibility(View.INVISIBLE);
                    da_goodFname = true;
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
                if(DA_BadUserInput(ALLOWED_NAME_CHARS,s))
                {
                    tv_j_vsUpdate_lnameError.setText("Letters Only");
                    tv_j_vsUpdate_lnameError.setVisibility(View.VISIBLE);
                    da_goodLname = false;
                    // need to add error text
                }
                else
                {
                    tv_j_vsUpdate_lnameError.setVisibility(View.INVISIBLE);
                    da_goodLname = true;
                }
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
                if(DA_BadUserInput(ALLOWED_EMAIL_CHARS,s))
                {
                    tv_j_vsUpdate_emailError.setText("Format = email@school.edu");
                    tv_j_vsUpdate_emailError.setVisibility(View.VISIBLE);
                    da_goodEmail = false;
                    // need to add error text
                }
                else
                {
                    tv_j_vsUpdate_emailError.setVisibility(View.INVISIBLE);
                    da_goodEmail = true;
                }
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
                if(DA_BadUserInput(ALLOWED_AGE_CHARS,s))
                {
                    tv_j_vsUpdate_ageError.setText("Numbers Only (Min = 16, Max = 99)");
                    tv_j_vsUpdate_ageError.setVisibility(View.VISIBLE);
                    da_goodAge = false;
                    // need to add error text
                }
                else
                {
                    tv_j_vsUpdate_ageError.setVisibility(View.INVISIBLE);
                    da_goodAge = true;
                }
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
                if(DA_BadUserInput(ALLOWED_GPA_CHARS,s))
                {
                    tv_j_vsUpdate_gpaError.setText("GPA range = 1.0 to 4.0 \n1 decimal place required");
                    tv_j_vsUpdate_gpaError.setVisibility(View.VISIBLE);
                    da_goodGpa = false;
                    // need to add error text
                }
                else
                {
                    tv_j_vsUpdate_gpaError.setVisibility(View.INVISIBLE);
                    da_goodGpa = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }
    private void DA_EmptyInputErrorChecker()
    {
        if(vs_jDetails_viewSwitcher.getCurrentView() == da_vsSwitcher_update)
        {
            if(et_j_vsUpdate_fname.getText().toString().isEmpty())
            {
                tv_j_vsUpdate_fnameError.setText("Enter a First Name");
                tv_j_vsUpdate_fnameError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsUpdate_lname.getText().toString().isEmpty())
            {
                tv_j_vsUpdate_lnameError.setText("Enter a Last Name");
                tv_j_vsUpdate_lnameError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsUpdate_email.getText().toString().isEmpty())
            {
                tv_j_vsUpdate_emailError.setText("Enter a Email");
                tv_j_vsUpdate_emailError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsUpdate_age.getText().toString().isEmpty())
            {
                tv_j_vsUpdate_ageError.setText("Enter an Age");
                tv_j_vsUpdate_ageError.setVisibility(View.VISIBLE);
            }
            if(et_j_vsUpdate_gpa.getText().toString().isEmpty())
            {
                tv_j_vsUpdate_gpaError.setText("Enter a GPA");
                tv_j_vsUpdate_gpaError.setVisibility(View.VISIBLE);
            }
            if(!da_majorSelected)
            {
                tv_j_vsUpdate_majorError.setText("Select a Major");
                tv_j_vsUpdate_majorError.setVisibility(View.VISIBLE);
            }
        }
    }
    private void DA_ResetUpdateBoolsAndErrors()
    {
        da_goodFname = false;
        da_goodLname = false;
        da_goodEmail = false;
        da_goodAge = false;
        da_goodGpa = false;
        da_majorSelected = false;
        tv_j_vsUpdate_fnameError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_lnameError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_emailError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_ageError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_gpaError.setVisibility(View.INVISIBLE);
        tv_j_vsUpdate_majorError.setVisibility(View.INVISIBLE);
    }
    private void DA_FormatAndSaveUpdatedStudent()
    {
        String un = da_passedStudentData.getSd_username();
        String fn = et_j_vsUpdate_fname.getText().toString();
        String ln = et_j_vsUpdate_lname.getText().toString();
        String em = et_j_vsUpdate_email.getText().toString();
        int age = Integer.parseInt(et_j_vsUpdate_age.getText().toString());
        double gpa = Double.parseDouble(et_j_vsUpdate_gpa.getText().toString());
        int maj = sp_j_vsUpdate_major.getSelectedItemPosition() - 1;

        fn = fn.toLowerCase();
        ln = ln.toLowerCase();
        fn = fn.substring(0,1).toUpperCase() + fn.substring(1);
        ln = ln.substring(0,1).toUpperCase() + ln.substring(1);
        da_dbHelper.DB_SaveUpdatedStudentData(un,fn,ln,em,age,gpa,maj);

    }
    private boolean DA_EmailAlreadyExists()
    {
        String email = et_j_vsUpdate_email.getText().toString();
        int currentStudent = StudentData.PassStudentData.getLvMainLongClickPos();
        for(int i = 0; i < da_dbHelper.DB_StudentRecordCount(); i++)
        {
            if(email.equalsIgnoreCase(da_dbHelper.DB_getSingleStudentData(i).getSd_email()) && currentStudent != i)
            {
                return true;
            }
        }
        return false;
    }

}