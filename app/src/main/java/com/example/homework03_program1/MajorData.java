package com.example.homework03_program1;

import java.util.ArrayList;

public class MajorData
{
    int md_majorId;
    String md_majorName;
    int md_majorPrefix;

    public MajorData()
    {

    }
    public MajorData(int id, String name, int prefix)
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
    public int getPrefixId()
    {
        return md_majorPrefix;
    }
    public String getMajorName()
    {
        return md_majorName;
    }
    // Setters //
//    Is this even needed since major id is set in the database
//    Maybe use it to change the order of the database? would need to store in temp
//    Database then overwrite the old one. Or is there something in the sqlite I could use?
//    public void setMajorId(int id)
//    {
//        md_majorId = id;
//    }
//    public void setMajorName(String name)
//    {
//        md_majorName = name;
//    }
//    public void setMd_majorPrefix(int prefix)
//    {
//        md_majorPrefix = prefix;
//    }
    static class PassMajorData
    {
        static ArrayList<MajorData> passedMajorData;
        static ArrayList<String> mp_majorPrefixes;
        static ArrayList<String> mp_majorNames;
        public static void setMp_MajorPrefixes(ArrayList<String> mp)
        {
            mp_majorPrefixes = mp;
        }
        public static void setMp_MajorNames(ArrayList<String> mn)
        {
            mp_majorNames = mn;
        }
        public static ArrayList<String> getMp_allPrefixes() // used for the spinner in the add new major screen
        {
            return mp_majorPrefixes;
        }
        public static ArrayList<String> getMp_allMajorNames() // used for the spinner in the search screen
        {
            return mp_majorNames;
        }
        public static String getMp_PrefixName(int pos)
        {
            return mp_majorPrefixes.get(pos);
        }
        public static String getMp_MajorName(int pos)
        {
            return mp_majorNames.get(pos);
        }
        public static void setPassedMajorData(ArrayList<MajorData> md)
        {
            passedMajorData = md;
        }
        public static MajorData getMajorData(int pos)
        {
            return passedMajorData.get(pos);
        }
    }

}
