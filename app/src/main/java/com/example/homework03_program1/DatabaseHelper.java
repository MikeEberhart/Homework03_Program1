//=================================================================================================//
// Name: Mike Eberhart
// Date: 30 September 2024
// Desc: An application that will allow an admin(you) to add/edit/remove students into the registry
//=================================================================================================//
package com.example.homework03_program1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentReg.db";
    private static final String STUDENTS_TABLE_NAME = "Students";
    private static final String MAJORS_TABLE_NAME = "Majors";
    private static final String PREFIXES_TABLE_NAME = "Prefixes";
    ArrayList<StudentData> db_listOfStudents;
    ArrayList<StudentData> db_listOfSearchResults;
    ArrayList<MajorData> db_listOfMajors;
    ArrayList<String> db_listOfMajNames;
    ArrayList<String> db_listOfPrefixes;

    public DatabaseHelper(Context c)
    {
        // create database and give it a version number
        // change version number to populate a new db
        super(c, DATABASE_NAME, null, 11);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        DB_CreateTables(db);
    }
    // used to drop the old tables so new ones with changes can be populated to the database //
    // android studio recommended @NonNull so I added it
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + PREFIXES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + MAJORS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME + ";");
        onCreate(db);
    }
    // used to create the tables for the database //
    // android studio recommended @NonNull so I added it
    private void DB_CreateTables(@NonNull SQLiteDatabase db)
    {
        // create the tables in the db
        // create the tables backwards starting with the one with no foreign key
        // cannot create a table that uses a foreign key before the foreign key's table has been created
        db.execSQL("CREATE TABLE " + PREFIXES_TABLE_NAME + " (prefixId integer primary key autoincrement not null, " +
                "prefixName varchar(5));");

        db.execSQL("CREATE TABLE " + MAJORS_TABLE_NAME + " (majorId integer primary key autoincrement not null, " +
                "majorName varchar(50), prefixId integer, foreign key (prefixId) references " + PREFIXES_TABLE_NAME + "(prefixId));");

        db.execSQL("CREATE TABLE " + STUDENTS_TABLE_NAME + " (username varchar(50) primary key not null, " +
                "fname varchar(50), lname varchar(50), email varchar(60), age integer, gpa double, " +
                "majorId integer, foreign key (majorId) references " + MAJORS_TABLE_NAME + "(majorId));");
    }
    // called in main to populate the database with dummy data //
    // tables must be created in this order so create the tables don't create errors with primary keys and foreign keys //
    public void DB_PopulateData()
    {
        DB_PrefixesDData();
        DB_MajorsDData();
        DB_StudentDData();
    }
    // used to add dummy prefix data //
    private void DB_PrefixesDData()
    {
        if (DB_RecordCount(PREFIXES_TABLE_NAME) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert predefined major prefix tags
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('AGRI');"); //0
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ART');");  //1
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ASTR');"); //2
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('AUTO');"); //3
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('BIO');");  //4
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('BUS');");  //5
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('CHEM');"); //6
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('CJ');");   //7
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('CIS');");  //8
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('COMM');"); //9
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ECON');"); //10
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('EDU');");  //11
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ELEC');"); //12
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ENG');");  //13
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('GEOG');"); //14
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('HIST');"); //15
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MATH');"); //16
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MECH');"); //17
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MKT');");  //18
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MUS');");  //19
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('NURS');"); //20
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('PHIL');"); //21
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('PHYS');"); //22
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('POLI');"); //23
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('PSYC');"); //24
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('SOC');");  //25
            db.close();
        }
    }
    // used to add dummy major data //
    private void DB_MajorsDData()
    {
        if (DB_RecordCount(MAJORS_TABLE_NAME) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert dummy data here
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('American History', 15);");      //0
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Application Development', 8);");//1
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Art', 1);");                    //2
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Business Administration', 5);");//3
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Business Accounting', 5);");    //4
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Computer Science', 8);");       //5
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Criminal Justice', 7);");       //6
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Economics', 10);");             //7
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('English', 13);");               //8
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Marketing', 18);");             //9
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Mechanical Engineering', 17);");//10
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Political Science', 23);");     //11
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Sociology', 25);");             //12
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Nursing', 20);");               //13
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Education', 11);");             //14
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Automotive Engineering', 3);"); //15
            db.close();
        }
    }
    // used to add dummy student data //
    private void DB_StudentDData()
    {
        if (DB_RecordCount(STUDENTS_TABLE_NAME) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert dummy data here
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('jdo27', 'John', 'Do', 'jdo@umich.edu', 20, 3.5, 6);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('esmith15', 'Emma', 'Smith', 'esmith@my.monroeccc.edu', 22, 2.8, 12);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('jjohnson42', 'James', 'Johnson', 'jjohnson@emich.edu', 19, 3.9, 13);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('obrown36', 'Olivia', 'Brown', 'obrown@msu.edu', 21, 2.5, 13);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('mjones58', 'Michael', 'Jones', 'mjones@my.monroeccc.edu', 23, 3.2, 5);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('sgarcia14', 'Sophia', 'Garcia', 'sgarcia@umich.edu', 18, 4.0, 1);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('lmiller99', 'Liam', 'Miller', 'lmiller@emich.edu', 20, 3.1, 5);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('idavis73', 'Isabella', 'Davis', 'idavis@msu.edu', 22, 2.9, 1);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('emartinez84', 'Ethan', 'Martinez', 'emartinez@my.monroeccc.edu', 19, 3.4, 0);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('alopez61', 'Ava', 'Lopez', 'alopez@umich.edu', 21, 3.6, 10);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('ngonzalez05', 'Noah', 'Gonzalez', 'ngonzalez@emich.edu', 23, 2.7, 14);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('mwilson19', 'Mia', 'Wilson', 'mwilson@msu.edu', 20, 3.8, 9);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('aanderson37', 'Alexander', 'Anderson', 'aanderson@my.monroeccc.edu', 19, 2.4, 8);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('cthomas28', 'Charlotte', 'Thomas', 'cthomas@umich.edu', 21, 3.0, 4);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('btaylor50', 'Benjamin', 'Taylor', 'btaylor@emich.edu', 22, 2.3, 3);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('hmoore11', 'Henry', 'Moore', 'hmoore@msu.edu', 19, 3.7, 11);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('ggarcia94', 'Grace', 'Garcia', 'ggarcia@my.monroeccc.edu', 20, 3.3, 2);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('wthompson02', 'William', 'Thompson', 'wthompson@umich.edu', 23, 3.9, 7);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('rlong88', 'Rachel', 'Long', 'rlong@emich.edu', 21, 2.6, 14);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('jsmith66', 'Jacob', 'Smith', 'jsmith@msu.edu', 22, 3.4, 15);");
            db.close();
        }
    }
    // used to return an Array of StudentData for the list view adapter for the main list of students //
    public ArrayList<StudentData> DB_getListOfStudentData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_sdataCursor = db.rawQuery("SELECT * FROM " + STUDENTS_TABLE_NAME, null);
        db_listOfStudents = null;
        if (db_sdataCursor.moveToFirst())
        {
            db_listOfStudents = new ArrayList<>();
            do {
                db_listOfStudents.add(new StudentData(db_sdataCursor.getString(0),
                        db_sdataCursor.getString(1),
                        db_sdataCursor.getString(2),
                        db_sdataCursor.getString(3),
                        db_sdataCursor.getInt(4),
                        db_sdataCursor.getDouble(5),
                        db_sdataCursor.getInt(6)));
            } while (db_sdataCursor.moveToNext());
            db_sdataCursor.close();
            db.close();
        }
        return db_listOfStudents;
    }
    // used to return an Array of MajorData for the list view adapter for the search results //
    public ArrayList<MajorData> DB_getListOfMajorData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_mdataCursor = db.rawQuery("SELECT * FROM " + MAJORS_TABLE_NAME, null);
        db_listOfMajors = null;
        if (db_mdataCursor.moveToFirst())
        {
            db_listOfMajors = new ArrayList<>();
            do{
                db_listOfMajors.add(new MajorData(db_mdataCursor.getInt(0), db_mdataCursor.getString(1), db_mdataCursor.getInt(2)));
            } while (db_mdataCursor.moveToNext());
            db_mdataCursor.close();
            db.close();
        }
        return db_listOfMajors;
    }
    // used to return a String Array of major names from the Major table //
    // while also adding a default string("-Select a Major-") at db_listOfMajNames[0] for a display placeholder for the spinner //
    public ArrayList<String> DB_getListOfMajorNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_mdataCursor = db.rawQuery("SELECT * FROM " + MAJORS_TABLE_NAME, null);
        db_listOfMajNames = null;
        Log.d("db before", "db before");
        if (db_mdataCursor.moveToFirst())
        {
            Log.d("db inside", "db inside");
            db_listOfMajNames = new ArrayList<>();
            db_listOfMajNames.add("-Select a Major-"); // setting pos 0 to -Select Major- to act as a null selection and display mainly for AddNewStudent spinner
            do {
                db_listOfMajNames.add(db_mdataCursor.getString(1));

            } while (db_mdataCursor.moveToNext());
            db_mdataCursor.close();
            db.close();
            Log.d("major names", db_listOfMajNames.toString());
        }
        Log.d("db after", "db after");
        return db_listOfMajNames;
    }
    // used to return a String Array of prefixes from the Prefix table //
    public ArrayList<String> DB_getListOfPrefixes() // used for spinners
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_pdataCursor = db.rawQuery("SELECT * FROM " + PREFIXES_TABLE_NAME, null);
        db_listOfPrefixes = null;
        if (db_pdataCursor.moveToFirst())
        {
            db_listOfPrefixes = new ArrayList<>();
            db_listOfPrefixes.add("-Select a Prefix-");
            do {
                db_listOfPrefixes.add(db_pdataCursor.getString(1));
            } while (db_pdataCursor.moveToNext());
            db_pdataCursor.close();
            db.close();
        }
        return db_listOfPrefixes;
    }
    // used to count the records in a passed table when populating the dummy record data //
    // to ensure that if return count > 0 dummy records DO NOT get added again to the database //
    public int DB_RecordCount(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String rowCnt = "SELECT COUNT(*) FROM " + tableName + ";";
        int count = 0;
        Cursor db_rowCnt = db.rawQuery(rowCnt, null);
        if(db_rowCnt.moveToFirst())
        {
            count = db_rowCnt.getInt(0);
            db_rowCnt.close();
            db.close();
        }
        return count;
    }
    // used to count and return the total number of records in the Student table //
    public int DB_StudentRecordCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String rowCnt = "SELECT COUNT(*) FROM " + STUDENTS_TABLE_NAME + ";";
        int count = 0;
        Cursor db_rowCnt = db.rawQuery(rowCnt, null);
        if(db_rowCnt.moveToFirst())
        {
            count = db_rowCnt.getInt(0);
            db_rowCnt.close();
            db.close();
        }
        return count;
    }
    // used to return the data for a single selected student //
    public StudentData DB_getSingleStudentData(int i)
    {
        StudentData temp_student = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String getRowData = "SELECT * FROM " + STUDENTS_TABLE_NAME + " LIMIT 1 OFFSET " + i + ";";
        Cursor db_rowData = db.rawQuery(getRowData, null);
        if (db_rowData.moveToFirst())
        {
            temp_student = new StudentData();
            temp_student.setSd_username(db_rowData.getString(0));
            temp_student.setSd_fname(db_rowData.getString(1));
            temp_student.setSd_lname(db_rowData.getString(2));
            temp_student.setSd_email(db_rowData.getString(3));
            temp_student.setSd_age(db_rowData.getInt(4));
            temp_student.setSd_gpa(db_rowData.getDouble(5));
            temp_student.setSd_major(db_rowData.getInt(6));
            db_rowData.close();
            db.close();
        }
        return temp_student;
    }
    // used to return the data for a single selected major //
    public MajorData DB_getSingleMajorData(int i)
    {
        MajorData temp_major = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String getRowData = "SELECT * FROM " + MAJORS_TABLE_NAME + " LIMIT 1 OFFSET " + i + ";";
        Cursor db_rowData = db.rawQuery(getRowData, null);
        if (db_rowData.moveToFirst())
        {
            temp_major = new MajorData();
            temp_major.setMd_majorId(db_rowData.getInt(0));
            temp_major.setMd_majorName(db_rowData.getString(1));
            temp_major.setMd_majorPrefixId(db_rowData.getInt(2));
            db_rowData.close();
            db.close();
        }
        return temp_major;
    }
    // used to add a new student to the database //
    public void DB_addNewStudentToDatabase(String uname, String fname, String lname, String email, int age, double gpa, int major)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addStudent = new ContentValues();
        addStudent.put("username", uname);
        addStudent.put("fname", fname);
        addStudent.put("lname", lname);
        addStudent.put("email", email);
        addStudent.put("age", age);
        addStudent.put("gpa", gpa);
        addStudent.put("majorId", major);
        db.insert(STUDENTS_TABLE_NAME, null, addStudent);
        db.close();
    }
    // used to delete a selected student from the database //
    public void DB_deleteStudentFromDatabase(String student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + STUDENTS_TABLE_NAME + " WHERE username='" + student + "';");
        db.close();
    }
    // used to add a new major to the database //
    public void DB_addNewMajorToDatabase(String maj, int p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues addMajor = new ContentValues();
        addMajor.put("majorName", maj);
        addMajor.put("prefixId", p);
        db.insert(MAJORS_TABLE_NAME, null, addMajor);
        db.close();
    }
    // used to save the updated data to the database for student username = un //
    public void DB_SaveUpdatedStudentData(String un, String fn, String ln, String em, int a, double g, int maj)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateStudent = new ContentValues();
        updateStudent.put("fname", fn);
        updateStudent.put("lname", ln);
        updateStudent.put("email", em);
        updateStudent.put("age", a);
        updateStudent.put("gpa", g);
        updateStudent.put("majorId", maj);
        // update to STUDENTS_TABLE_NAME, passing 'updateStudent', where 'username = ?', where ? = un
        // 'username = ?' is used to set the where with ? used as a placeholder to be replaced by the string 'un'
        // String[] is used to prevent SQL injection by turning the user input into a string literal
        // making sure the input is read as a string in the query statement and not part of the statement code
        db.update(STUDENTS_TABLE_NAME,updateStudent,"username = ?", new String[]{un});
        db.close();
    }
    // used to search and return an Array of StudentData that met the search results //
    public ArrayList<StudentData> DB_SearchForSetData(String sel)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db_listOfSearchResults = null;
        String selectedSearch = "Select * FROM " + STUDENTS_TABLE_NAME + sel + ";";
        Cursor db_searchQuery = db.rawQuery(selectedSearch, null);
        if(db_searchQuery.moveToFirst() && DB_CountOfSearchResults(sel) != 0)
        {
            db_listOfSearchResults = new ArrayList<>();
            do{
                db_listOfSearchResults.add(new StudentData(db_searchQuery.getString(0),
                        db_searchQuery.getString(1),
                        db_searchQuery.getString(2),
                        db_searchQuery.getString(3),
                        db_searchQuery.getInt(4),
                        db_searchQuery.getDouble(5),
                        db_searchQuery.getInt(6)));
            } while (db_searchQuery.moveToNext());
            db_searchQuery.close();
            db.close();
        }
        return db_listOfSearchResults;
    }
    // used to count the number of search results //
    public int DB_CountOfSearchResults(String sel)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT COUNT(*) FROM " + STUDENTS_TABLE_NAME + sel + ";";
        Cursor db_resultCnt = db.rawQuery(count, null);
        int cnt;
        if(db_resultCnt.moveToFirst())
        {
            cnt = db_resultCnt.getInt(0);
            db_resultCnt.close();
        }
        else
        {
            cnt = 0;
        }
        return cnt;
    }
}
