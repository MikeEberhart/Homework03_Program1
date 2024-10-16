package com.example.homework03_program1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter
{
    Context main_context;
    ArrayList<StudentData> main_studentList;

    public StudentListAdapter(Context c, ArrayList<StudentData> sd)
    {
        main_context = c;
        main_studentList = sd;
    }

    @Override
    public int getCount()
    {
        return main_studentList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return main_studentList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup g)
    {
        if(view == null)
        {
            LayoutInflater mainListInflater = (LayoutInflater) main_context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mainListInflater.inflate(R.layout.main_student_list_cell, null);
        }
        StudentData sd = main_studentList.get(i);
        TextView tv_j_sla_username = view.findViewById(R.id.tv_slCell_username);
        TextView tv_j_sla_fname = view.findViewById(R.id.tv_slCell_fname);
        TextView tv_j_sla_lname = view.findViewById(R.id.tv_slCell_lname);
        tv_j_sla_username.setText(sd.getSd_username());
        tv_j_sla_fname.setText(sd.getSd_fname());
        tv_j_sla_lname.setText(sd.getSd_lname());
        return view;
    }
}
