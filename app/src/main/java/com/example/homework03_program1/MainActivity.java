//=========================================================================================
// Name: Mike Eberhart
// Date: 30 September 2024
// Desc: An application that will allow an admin(you) to add new students into the system
//=========================================================================================
package com.example.homework03_program1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    ListView lv_j_listOfStudents;
    ImageView iv_j_searchStudentBtn;
    ImageView iv_j_addStudentBtn;
    ImageView iv_j_deleteStudentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ListOfViews();
    }

    private void ListOfViews()
    {
        lv_j_listOfStudents = findViewById(R.id.lv_v_listOfStudents);
        iv_j_searchStudentBtn = findViewById(R.id.iv_v_searchStudentBtn);
        iv_j_addStudentBtn = findViewById(R.id.iv_v_addStudentBtn);
        iv_j_deleteStudentBtn = findViewById(R.id.iv_v_deleteStudentBtn);
    }
}