package com.example.homework03_program1;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "StudentReg.db";
    private static final String STUDENTS_TABLE_NAME = "Students";
    private static final String MAJORS_TABLE_NAME = "Majors";
    private static final String PREFIXES_TABLE_NAME = "Prefixes";


    public DatabaseHelper(Context c)
    {
        // create database and give it a version number
        // change version number to populate a new db
        super(c, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // create the tables in the db
        // create the tables backwards starting with the one with no foreign key
        // cannot create a table that uses a foreign key before the foreign key's table has been created
        db.execSQL("CREATE TABLE " + PREFIXES_TABLE_NAME + " (prefixId integer primary key autoincrement not null, " +
                "prefixName varchar(5));");

        db.execSQL("CREATE TABLE " + MAJORS_TABLE_NAME + " (majorId integer primary key autoincrement not null, " +
                "majorName varchar(50), foreign key (prefixId) references " + PREFIXES_TABLE_NAME + "(prefixId));");

        db.execSQL("CREATE TABLE " + STUDENTS_TABLE_NAME + " (username varchar(50) primary key not null, " +
                "fname varchar(50), lname varchar(50), email varchar(60), age integer, gpa real, " +
                "foreign key (majorId) references " + MAJORS_TABLE_NAME + "(majorId));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + PREFIXES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + MAJORS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME + ";");
        onCreate(db);
    }

    //used for testing
    public String getStudentDbName()
    {
        return STUDENTS_TABLE_NAME;
    }
    public String getMajorsDbName()
    {
        return MAJORS_TABLE_NAME;
    }
    public String getPrefixesDbName()
    {
        return PREFIXES_TABLE_NAME;
    }
    public void populateDummyData()
    {
        prefixesDData();
        majorsDData();
        studentDData();
    }
    private void prefixesDData()
    {
        if(recordCount(PREFIXES_TABLE_NAME) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert predefined major prefix tags
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('AGRI');"); //1
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ART');");  //2
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ASTR');"); //3
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('AUTO');"); //4
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('BIO');");  //5
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('BUS');");  //6
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('CHEM');"); //7
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('CJ');");   //8
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('CIS');");  //9
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('COMM');"); //10
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ECON');"); //11
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('EDU');");  //12
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ELEC');"); //13
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('ENG');");  //14
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('GEOG');"); //15
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('HIST');"); //16
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MATH');"); //17
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MECH');"); //18
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MKT');");  //19
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('MUS');");  //20
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('NURS');"); //21
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('PHIL');"); //22
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('PHYS');"); //23
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('POLI');"); //24
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('PSYC');"); //25
            db.execSQL("INSERT INTO " + PREFIXES_TABLE_NAME + "(prefixName) VALUES ('SOC');");  //26
            db.close();
        }
    }
    private void majorsDData()
    {
        if(recordCount(MAJORS_TABLE_NAME) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert dummy data here
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('American History', 16);");      //1
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Application Development', 9);");//2
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Art', 2);");                    //3
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Business Administration', 6);");//4
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Business Accounting', 6);");    //5
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Computer Science', 9);");       //6
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Criminal Justice', 8);");       //7
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Economics', 11);");             //8
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('English', 14);");               //9
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Marketing', 19);");             //10
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Mechanical Engineering', 18);");//11
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Political Science', 24);");     //12
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Sociology', 26);");             //13
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Nursing', 21);");               //14
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Education', 12);");             //15
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(majorName, prefixId) VALUES ('Automotive Engineering', 4);"); //16
            db.close();
        }
    }
    private void studentDData()
    {
        if(recordCount(STUDENTS_TABLE_NAME) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            // insert dummy data here
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('jdo27', 'John', 'Do', 'jdo@umich.edu', 20, 3.5, 7);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('esmith15', 'Emma', 'Smith', 'esmith@my.monroeccc.edu', 22, 2.8, 13);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('jjohnson42', 'James', 'Johnson', 'jjohnson@emich.edu', 19, 3.9, 14);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('obrown36', 'Olivia', 'Brown', 'obrown@msu.edu', 21, 2.5, 14);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('mjones58', 'Michael', 'Jones', 'mjones@my.monroeccc.edu', 23, 3.2, 6);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('sgarcia14', 'Sophia', 'Garcia', 'sgarcia@umich.edu', 18, 4.0, 2);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('lmiller99', 'Liam', 'Miller', 'lmiller@emich.edu', 20, 3.1, 6);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('idavis73', 'Isabella', 'Davis', 'idavis@msu.edu', 22, 2.9, 2);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('emartinez84', 'Ethan', 'Martinez', 'emartinez@my.monroeccc.edu', 19, 3.4, 1);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('alopez61', 'Ava', 'Lopez', 'alopez@umich.edu', 21, 3.6, 11);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('ngonzalez05', 'Noah', 'Gonzalez', 'ngonzalez@emich.edu', 23, 2.7, 16);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('mwilson19', 'Mia', 'Wilson', 'mwilson@msu.edu', 20, 3.8, 10);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('aanderson37', 'Alexander', 'Anderson', 'aanderson@my.monroeccc.edu', 19, 2.4, 9);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('cthomas28', 'Charlotte', 'Thomas', 'cthomas@umich.edu', 21, 3.0, 5);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('btaylor50', 'Benjamin', 'Taylor', 'btaylor@emich.edu', 22, 2.3, 4);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('hmoore11', 'Henry', 'Moore', 'hmoore@msu.edu', 19, 3.7, 12);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('ggarcia94', 'Grace', 'Garcia', 'ggarcia@my.monroeccc.edu', 20, 3.3, 3);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('wthompson02', 'William', 'Thompson', 'wthompson@umich.edu', 23, 3.9, 8);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('rlong88', 'Rachel', 'Long', 'rlong@emich.edu', 21, 2.6, 16);");
            db.execSQL("INSERT INTO " + STUDENTS_TABLE_NAME + "(username, fname, lname, email, age, gpa, majorId) VALUES ('jsmith66', 'Jacob', 'Smith', 'jsmith@msu.edu', 22, 3.4, 15);");
            db.close();
        }
    }
    private int recordCount(String tname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = (int) DatabaseUtils.queryNumEntries(db, tname);
        db.close();
        return rowCount;
    }
    public String getUsername(String un)
    {

    }
}
