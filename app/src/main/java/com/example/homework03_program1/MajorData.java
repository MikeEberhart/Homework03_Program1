package com.example.homework03_program1;

public class MajorData
{
    private int md_majorId;
    private String md_majorName;
    private String md_majorPrefix;

    public MajorData()
    {

    }
    public MajorData(int id, String name, String prefix)
    {
        md_majorId = id;
        md_majorName = name;
        md_majorPrefix = prefix;
    }
    // Getters //
    public int getMajorId()
    {
        return md_majorId;
    }
    public String getMajorName()
    {
        return md_majorName;
    }
    public String getMajorPrefix()
    {
        return md_majorPrefix;
    }
    // Setters //
//    Is this even needed since major id is set in the database
//    Maybe use it to change the order of the database? would need to store in temp
//    Database then overwrite the old one. Or is there something in the sqlite I could use?
//    public void setMajorId(int id)
//    {
//        md_majorId = id;
//    }
    public void setMajorName(String name)
    {
        md_majorName = name;
    }
    public void setMd_majorPrefix(String prefix)
    {
        md_majorPrefix = prefix;
    }
}
