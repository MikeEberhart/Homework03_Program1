package com.example.homework03_program1;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "StudentReg.db";
    private static final String STUDENTS_TABLE_NAME = "Students";
    private static final String MAJORS_TABLE_NAME = "Majors";
    private static final String PREFIXES_TABLE_NAME = "Prefixes";
    ArrayList<StudentData>  db_listOfStudents;
    ArrayList<MajorData> db_listOfMajors;
    ArrayList<String> db_listOfMajNames;
    ArrayList<String> db_listOfPrefixes;

    public DatabaseHelper(Context c)
    {
        // create database and give it a version number
        // change version number to populate a new db
        super(c, DATABASE_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Db_CreateTables(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + PREFIXES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + MAJORS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME + ";");
        onCreate(db);
    }
    private void Db_CreateTables(SQLiteDatabase db)
    {
        Log.d("DB_OnCreate", "DB OnCreate Function");
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
    public void Db_PopulateData() // called in main to populate the object classes // might change all this
    {
        Log.d("Database Pop", "Database Pop");
        Db_PrefixesDData();
        Db_MajorsDData();
        Db_StudentDData();
        Db_readPrefixData();
        Db_readMajorData();
        Db_readStudentData();

    }
    private void Db_PrefixesDData() // adding dummy prefix data
    {
        if(RecordCount(PREFIXES_TABLE_NAME) == 0)
        {
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
    private void Db_MajorsDData() // adding dummy major data
    {
        if(RecordCount(MAJORS_TABLE_NAME) == 0)
        {
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
    private void Db_StudentDData() // adding dummy student data
    {
        if(RecordCount(STUDENTS_TABLE_NAME) == 0)
        {
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
    public void Db_readStudentData() // saving data from STUDENT_TABLE_NAME to StudentData
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_sdataCursor = db.rawQuery("SELECT * FROM " + STUDENTS_TABLE_NAME, null);
        db_listOfStudents = new ArrayList<>();
        int i = 0;
        if(db_sdataCursor.moveToFirst())
        {
            do {
//                int pos = db_sdataCursor.getInt(6);
//                String sdMajor = MajorData.PassMajorData.getMp_MajorName(pos);
                Log.d("readStuData", "ReadStuData" + i);
                db_listOfStudents.add(new StudentData(db_sdataCursor.getString(0),
                        db_sdataCursor.getString(1),
                        db_sdataCursor.getString(2),
                        db_sdataCursor.getString(3),
                        db_sdataCursor.getInt(4),
                        db_sdataCursor.getDouble(5),
                        db_sdataCursor.getInt(6)));
                i++;
            }while(db_sdataCursor.moveToNext());
        }
        db_sdataCursor.close();
        StudentData.PassStudentData.setPassedStudentData(db_listOfStudents);
    }
    public void Db_readMajorData() // saving data from MAJOR_TABLE_NAME to MajorData
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_mdataCursor = db.rawQuery("SELECT * FROM " + MAJORS_TABLE_NAME, null);
        db_listOfMajors = new ArrayList<>();
        db_listOfMajNames = new ArrayList<>();
        if (db_mdataCursor.moveToFirst())
        {
            do {
                db_listOfMajors.add(new MajorData(db_mdataCursor.getInt(0), db_mdataCursor.getString(1), db_mdataCursor.getInt(2)));
                db_listOfMajNames.add(db_mdataCursor.getString(1));
//                Log.d("READ MAJOR DATA", db_listOfMajors.toString());
            } while (db_mdataCursor.moveToNext());
        }
        db_mdataCursor.close();
        MajorData.PassMajorData.setPassedMajorData(db_listOfMajors);
        MajorData.PassMajorData.setMp_MajorNames(db_listOfMajNames);
    }
    public void Db_readPrefixData() // saving data from MAJOR_TABLE_NAME//prefixName to MajorData.MajorNameAndPrefix
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_pdataCursor = db.rawQuery("SELECT prefixName FROM " + PREFIXES_TABLE_NAME, null);
        db_listOfPrefixes = new ArrayList<>();
        if(db_pdataCursor.moveToFirst())
        {
            do{
                db_listOfPrefixes.add(db_pdataCursor.getString(0));
            }while(db_pdataCursor.moveToNext());
            db_pdataCursor.close();
        }
        MajorData.PassMajorData.setMp_MajorPrefixes(db_listOfPrefixes);
    }
    public ArrayList<StudentData> Db_passStudentData() // used to both save database to StudentData and return an ArrayList of the data
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_sdataCursor = db.rawQuery("SELECT * FROM " + STUDENTS_TABLE_NAME, null);
        db_listOfStudents = new ArrayList<>();
        if(db_sdataCursor.moveToFirst())
        {
            do {
                db_listOfStudents.add(new StudentData(db_sdataCursor.getString(0),
                        db_sdataCursor.getString(1),
                        db_sdataCursor.getString(2),
                        db_sdataCursor.getString(3),
                        db_sdataCursor.getInt(4),
                        db_sdataCursor.getDouble(5),
                        db_sdataCursor.getInt(6)));
            }while(db_sdataCursor.moveToNext());
        }
        db_sdataCursor.close();
        return db_listOfStudents;
    }
    public ArrayList<MajorData> Db_passMajorData() // might not need // used to both save database to MajorData and return an ArrayList of the data
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor db_mdataCursor = db.rawQuery("SELECT * FROM " + MAJORS_TABLE_NAME, null);
        db_listOfMajors = new ArrayList<>();
//        db_listOfMajNames = new ArrayList<>();
        if (db_mdataCursor.moveToFirst())
        {
            do {
                db_listOfMajors.add(new MajorData(db_mdataCursor.getInt(0), db_mdataCursor.getString(1), db_mdataCursor.getInt(2)));
//                db_listOfMajNames.add(db_mdataCursor.getString(1));
//                Log.d("READ MAJOR DATA", db_listOfMajors.toString());
            } while (db_mdataCursor.moveToNext());
        }
        db_mdataCursor.close();
        return db_listOfMajors;
    }
//    public ArrayList<String> db_passPrefixData() // might not need ??
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor db_pdataCursor = db.rawQuery("SELECT prefixName FROM " + PREFIXES_TABLE_NAME, null);
//        db_listOfPrefixes = new ArrayList<>();
//        if(db_pdataCursor.moveToFirst())
//        {
//            do{
//                db_listOfPrefixes.add(db_pdataCursor.getString(0));
//            }while(db_pdataCursor.moveToNext());
//            db_pdataCursor.close();
//        }
//        return db_listOfPrefixes;
//    }
//    public ArrayList<String> db_passMajNames() // returns an ArrayList<String> of the major names
//    {
//        return db_listOfMajNames;
//    }
    public int RecordCount(String tname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = (int) DatabaseUtils.queryNumEntries(db, tname);
        db.close();
        return rowCount;
    }
    public String get_dbUsername()
    {
        String un = "";
//        String selectUsername = "SELECT "
         return un;
    }

}
