package com.example.homework03_program1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsAdapter extends BaseAdapter
{
    Context results_context;
    ArrayList<StudentData> results_listOfStudents;
    ArrayList<MajorData> results_listOfMajors;
    ArrayList<String> results_listOfPrefixes;

    public SearchResultsAdapter(Context c, ArrayList<StudentData> sd, ArrayList<MajorData> md, ArrayList<String> p)
    {
        results_context = c;
        results_listOfStudents = sd;
        results_listOfMajors = md;
        results_listOfPrefixes = p;
    }
    @Override
    public int getCount()
    {
        return results_listOfStudents.size();
    }
    @Override
    public Object getItem(int i)
    {
        return results_listOfStudents.get(i);
    }
    @Override
    public long getItemId(int i)
    {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent)
    {
        Log.d("not empty list", "not empty list");
        if(view == null)
        {
            LayoutInflater resultsListInflater = (LayoutInflater) results_context.getSystemService(SearchActivity.LAYOUT_INFLATER_SERVICE);
            view = resultsListInflater.inflate(R.layout.search_results_list_cell, null);
        }
        StudentData sd = results_listOfStudents.get(i);
        MajorData major = results_listOfMajors.get(sd.getSd_major());
        String prefix = results_listOfPrefixes.get(major.getMd_majorPrefixId() + 1);
        String prefixWithMajorName = prefix + " - " + major.getMd_majorName();
        TextView tv_j_rlCell_username = view.findViewById(R.id.tv_rlCell_username);
        TextView tv_j_rlCell_fname = view.findViewById(R.id.tv_rlCell_fname);
        TextView tv_j_rlCell_lname = view.findViewById(R.id.tv_rlCell_lname);
        TextView tv_j_rlCell_email = view.findViewById(R.id.tv_rlCell_email);
        TextView tv_j_rlCell_age = view.findViewById(R.id.tv_rlCell_age);
        TextView tv_j_rlCell_gpa = view.findViewById(R.id.tv_rlCell_gpa);
        TextView tv_j_rlCell_major = view.findViewById(R.id.tv_rlCell_major);
        tv_j_rlCell_username.setText(sd.getSd_username());
        tv_j_rlCell_fname.setText(sd.getSd_fname());
        tv_j_rlCell_lname.setText(sd.getSd_lname());
        tv_j_rlCell_email.setText(sd.getSd_email());
        tv_j_rlCell_age.setText(String.valueOf(sd.getSd_age()));
        tv_j_rlCell_gpa.setText(String.valueOf(sd.getSd_gpa()));
        tv_j_rlCell_major.setText(prefixWithMajorName);
        return view;
    }
}
