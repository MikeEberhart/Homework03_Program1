//=================================================================================================//
// Name: Mike Eberhart
// Date: 30 September 2024
// Desc: An application that will allow an admin(you) to add/edit/remove students into the registry
//=================================================================================================//
package com.example.homework03_program1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
    TextView tv_j_vSearch_headerText;
    TextView tv_j_vsSearch_emptySearchError;
    Intent sa_mainIntent;
    Intent sa_detailsIntent;
    View sa_vsSwitcher_search;
    View sa_vsSwitcher_results;
    ViewSwitcher vs_jSearch_viewSwitcher;
    DatabaseHelper sa_dbHelper;
    // Search View Layout //
    ImageView iv_j_vsSearch_searchBtn;
    ImageView iv_j_vsSearch_resetBtn;
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
    SearchResultsAdapter sa_lv_resultsList_adapter;
    ListView lv_j_vsResults_searchResults;
    ImageView iv_j_vsResults_backBtn;
    TextView tv_j_vsResults_noResultsFoundError;
    ArrayList<StudentData> sa_listOfSearchResults;
    // Used Variables //
    private static final String ALLOWED_USERNAME_CHARS = "^[a-zA-Z0-9_.-]+$"; // regex string allowing only upper and lower case along with number and a few symbols
    private static final String ALLOWED_NAME_CHARS = "^[a-zA-Z]+$"; // regex string allowing only upper and lower case
    private static final String ALLOWED_EMAIL_CHARS = "^[a-zA-Z](?:[a-zA-Z0-9_.-]*[a-zA-Z0-9])?+@[a-zA-Z.]+\\.edu$"; // regex string formatted so the last char in the email name can't be a special char and the school email can only contain letters and '.' and the last part must have .edu
    private static final String ALLOWED_GPA_CHARS = "^(1(\\.\\d+)|2(\\.\\d+)|3(\\.\\d+)|4(\\.0))$";  // regex string formatted so gpa range is 1.0 to 4.0
    private boolean sa_goodUsername;
    private boolean sa_goodFname;
    private boolean sa_goodLname;
    private boolean sa_goodEmail;
    private boolean sa_goodGpa;
    private boolean sa_majorSelected;
    private boolean sa_isReadyToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        SA_InitData();
        SA_ListOfViews();
        SA_OnClickListeners();
        SA_TextChangedEventListener();
        vs_jSearch_viewSwitcher.setAnimateFirstView(true);
    }
    // used to Initialized the Data for the adapters and Intents along with the Arrays from the database //
    private void SA_InitData()
    {
        sa_mainIntent = new Intent(this, MainActivity.class);
        sa_detailsIntent = new Intent(this, DetailsActivity.class);
        // LayoutInflater sa_inflater = LayoutInflater.from(this); another way of inflating the layout
        LayoutInflater sa_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sa_vsSwitcher_search = sa_inflater.inflate(R.layout.sa_vswitcher_search_layout, null);
        sa_vsSwitcher_results = sa_inflater.inflate(R.layout.sa_vswitcher_results_layout, null);
        sa_dbHelper = new DatabaseHelper(this);
        sa_listOfRangeOperators = new ArrayList<>();
        sa_listOfRangeOperators.add("=");
        sa_listOfRangeOperators.add(">");
        sa_listOfRangeOperators.add("<");
        sa_listOfRangeOperators.add(">=");
        sa_listOfRangeOperators.add("<=");
        sa_listOfMajNames = sa_dbHelper.DB_getListOfMajorNames();
        sa_spGpaRangeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sa_listOfRangeOperators);
        sa_spMajAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sa_listOfMajNames);
    }
    // used to house all the Views being Initialized //
    private void SA_ListOfViews()
    {
        // Main Search Activity //
        vs_jSearch_viewSwitcher = findViewById(R.id.vs_vSearch_viewSwitcher);
        vs_jSearch_viewSwitcher.addView(sa_vsSwitcher_search);
        vs_jSearch_viewSwitcher.addView(sa_vsSwitcher_results);
        iv_jSearch_homeBtn = findViewById(R.id.iv_vSearch_homeBtn);
        tv_j_vSearch_headerText = findViewById(R.id.tv_vSearch_headerText);
        tv_j_vsSearch_emptySearchError = findViewById(R.id.tv_vsSearch_emptySearchError);
        // Search View Layout //
        iv_j_vsSearch_searchBtn = findViewById(R.id.iv_vsSearch_searchBtn);
        iv_j_vsSearch_resetBtn = findViewById(R.id.iv_vsSearch_resetBtn);
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
        tv_j_vsResults_noResultsFoundError = findViewById(R.id.tv_vsResults_noResultsFoundError);
    }
    // used to house all the OnClickListeners for all the ImageView buttons and Spinner//
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
        iv_j_vsSearch_searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vs_jSearch_viewSwitcher.getCurrentView() != sa_vsSwitcher_results && sa_isReadyToSearch)
                {
                    tv_j_vsResults_noResultsFoundError.setVisibility(View.INVISIBLE);
                    SA_GetSearchFields();
                    tv_j_vSearch_headerText.setText("Search Results");
                    if(sa_listOfSearchResults == null || sa_listOfSearchResults.isEmpty())
                    {
                        tv_j_vsResults_noResultsFoundError.setVisibility(View.VISIBLE);
                    }
                    sa_isReadyToSearch = false;
                    vs_jSearch_viewSwitcher.showNext();
                }
                else if(!sa_isReadyToSearch)
                {
                    tv_j_vsSearch_emptySearchError.setVisibility(View.VISIBLE);
                }

            }
        });
        iv_j_vsSearch_resetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SA_ClearText();
                SA_ResetBoolsAndErrors();
            }
        });
        iv_j_vsResults_backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if( vs_jSearch_viewSwitcher.getCurrentView() == sa_vsSwitcher_results)
                {
                    SA_ResetBoolsAndErrors();
                    tv_j_vSearch_headerText.setText("Student Search");
                    tv_j_vsResults_noResultsFoundError.setVisibility(View.INVISIBLE);
                    vs_jSearch_viewSwitcher.showNext();
                }
            }
        });
        sp_j_vsSearch_majorList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0)
                {
                    sa_majorSelected = true;
                    sa_isReadyToSearch = true;
                }
                else
                {
                    sa_majorSelected = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }
    // used to house all the TextChangedEventListeners for all the EditText //
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
                    sa_goodUsername = false;
                    tv_j_vsSearch_usernameError.setVisibility(View.VISIBLE);
                }
                else if(et_j_vsSearch_username.getText().toString().isEmpty())
                {
                    sa_goodUsername = false;
                }
                else
                {
                    sa_goodUsername = true;
                    sa_isReadyToSearch = true;
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
                    sa_goodFname = false;
                    tv_j_vsSearch_fnameError.setVisibility(View.VISIBLE);
                }
                else if(et_j_vsSearch_fname.getText().toString().isEmpty())
                {
                    sa_goodFname = false;
                }
                else
                {
                    sa_goodFname = true;
                    sa_isReadyToSearch = true;
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
                    sa_goodLname = false;
                    tv_j_vsSearch_lnameError.setVisibility(View.VISIBLE);
                }
                else if(et_j_vsSearch_lname.getText().toString().isEmpty())
                {
                    sa_goodLname = false;
                }
                else
                {
                    sa_goodLname = true;
                    sa_isReadyToSearch = true;
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
                    sa_goodEmail = false;
                    tv_j_vsSearch_emailError.setVisibility(View.VISIBLE);
                }
                else if(et_j_vsSearch_email.getText().toString().isEmpty())
                {
                    sa_goodEmail = false;
                }
                else
                {
                    sa_goodEmail = true;
                    sa_isReadyToSearch = true;
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
                    sa_goodGpa = false;
                    tv_j_vsSearch_gpaError.setVisibility(View.VISIBLE);
                }
                else if(et_j_vsSearch_gpa.getText().toString().isEmpty())
                {
                    sa_goodGpa = false;
                }
                else
                {
                    sa_goodGpa = true;
                    sa_isReadyToSearch = true;
                    tv_j_vsSearch_gpaError.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
    }
    // used to check for bad user input depending on the "valid" input pattern (s) and text being entered (cs)
    private boolean SA_BadUserInput(String s, CharSequence cs)
    {
        // was error checking with this for loop but read up on regex for java. //
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
    // used to create the query select statement depending on what valid data was entered
    private void SA_GetSearchFields()
    {
        String searchData = " WHERE ";
        boolean notFirstEntry = false;
        if(sa_goodUsername)
        {
            searchData = searchData + "username = '" + et_j_vsSearch_username.getText().toString() + "' ";
            notFirstEntry = true;
        }
        if(sa_goodFname)
        {
            String fn = et_j_vsSearch_fname.getText().toString();
            fn = fn.toLowerCase();
            fn = fn.substring(0,1).toUpperCase() + fn.substring(1);
            if(notFirstEntry)
            {
                searchData = searchData + "AND fname = '" + fn + "' ";
            }
            else
            {
                searchData = searchData + "fname = '" + fn + "' ";
                notFirstEntry = true;
            }
        }
        if(sa_goodLname)
        {
            String ln = et_j_vsSearch_lname.getText().toString();
            ln = ln.toLowerCase();
            ln = ln.substring(0,1).toUpperCase() + ln.substring(1);
            if(notFirstEntry)
            {
                searchData = searchData + "AND lname = '" + ln + "' ";
            }
            else
            {
                searchData = searchData + "lname = '" + ln + "' ";
                notFirstEntry = true;
            }
        }
        if(sa_goodEmail)
        {
            if(notFirstEntry)
            {
                searchData = searchData + "AND email = '" + et_j_vsSearch_email.getText().toString() + "' ";
            }
            else
            {
                searchData = searchData + "email = '" + et_j_vsSearch_email.getText().toString() + "' ";
                notFirstEntry = true;
            }
        }
        if(sa_goodGpa)
        {
            if(notFirstEntry)
            {
                searchData = searchData + "AND gpa " + sa_listOfRangeOperators.get(sp_j_vsSearch_gpaRange.getSelectedItemPosition()) + " " + et_j_vsSearch_gpa.getText().toString() + " ";
            }
            else
            {
                searchData = searchData + "gpa " + sa_listOfRangeOperators.get(sp_j_vsSearch_gpaRange.getSelectedItemPosition()) + " " + et_j_vsSearch_gpa.getText().toString() + " ";
                notFirstEntry = true;
            }
        }
        if(sa_majorSelected)
        {
            int selectedNum = sp_j_vsSearch_majorList.getSelectedItemPosition() - 1;
            if(notFirstEntry)
            {
                searchData = searchData + "AND majorId = " + selectedNum + " ";
            }
            else
            {
                searchData = searchData + "majorId = " + selectedNum + " ";
            }
        }
        if(sa_goodUsername || sa_goodFname || sa_goodLname || sa_goodEmail || sa_goodGpa || sa_majorSelected)
        {
            tv_j_vsSearch_emptySearchError.setVisibility(View.INVISIBLE);
            sa_listOfSearchResults = sa_dbHelper.DB_SearchForSetData(searchData);
            sa_lv_resultsList_adapter = new SearchResultsAdapter(SearchActivity.this, sa_listOfSearchResults, sa_dbHelper.DB_getListOfMajorData(), sa_dbHelper.DB_getListOfPrefixes());
            lv_j_vsResults_searchResults.setAdapter(sa_lv_resultsList_adapter);
        }
    }
    // used to clear the text and set the spinners back to the default position //
    private void SA_ClearText()
    {
        et_j_vsSearch_username.setText("");
        et_j_vsSearch_fname.setText("");
        et_j_vsSearch_lname.setText("");
        et_j_vsSearch_email.setText("");
        et_j_vsSearch_gpa.setText("");
        sp_j_vsSearch_gpaRange.setSelection(0);
        sp_j_vsSearch_majorList.setSelection(0);
    }
    // used to reset all the textboxes and bools //
    private void SA_ResetBoolsAndErrors()
    {
        tv_j_vsSearch_usernameError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_fnameError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_lnameError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_emailError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_gpaError.setVisibility(View.INVISIBLE);
        tv_j_vsResults_noResultsFoundError.setVisibility(View.INVISIBLE);
        tv_j_vsSearch_emptySearchError.setVisibility(View.INVISIBLE);
        sa_goodUsername = false;
        sa_goodFname = false;
        sa_goodLname = false;
        sa_goodEmail = false;
        sa_goodGpa = false;
        sa_majorSelected = false;
        sa_isReadyToSearch = false;
    }
}