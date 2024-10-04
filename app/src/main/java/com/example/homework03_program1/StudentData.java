package com.example.homework03_program1;

public class StudentData
{
    private String sd_username;
    private String sd_fname;
    private String sd_lname;
    private String sd_email;
    private int sd_age;
    private float sd_gpa;
    private String sd_major;

    public StudentData()
    {

    }

    public StudentData(String un, String fn, String ln, String em, int a, float g, String maj)
    {
        sd_username = un;
        sd_fname = fn;
        sd_lname = ln;
        sd_email = em;
        sd_age = a;
        sd_gpa = g;
        sd_major = maj;
    }
    // Getters //
    public String getSd_username()
    {
        return sd_username;
    }
    public String getSd_fname()
    {
        return sd_fname;
    }
    public String getSd_lname()
    {
        return sd_lname;
    }
    public String getSd_email()
    {
        return sd_email;
    }
    public int getSd_age()
    {
        return sd_age;
    }
    public float getSd_gpa()
    {
        return sd_gpa;
    }
    public String getSd_major()
    {
        return sd_major;
    }
    // Setters //
    public void setSd_username(String un)
    {
        sd_username = un;
    }
    public void setSd_fname(String fn)
    {
        sd_fname = fn;
    }
    public void setSd_lname(String ln)
    {
        sd_lname = ln;
    }
    public void setSd_email(String em)
    {
        sd_email = em;
    }
    public void setSd_age(int a)
    {
        sd_age = a;
    }
    public void setSd_gpa(float g)
    {
        sd_gpa = g;
    }
    public void setSd_major(String maj)
    {
        sd_major = maj;
    }

}
