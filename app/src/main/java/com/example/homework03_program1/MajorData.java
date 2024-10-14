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
    public int getMd_majorId()
    {
        return md_majorId;
    }
    public String getMd_majorName()
    {
        return md_majorName;
    }
    public int getMd_majorPrefixId()
    {
        return md_majorPrefix;
    }
    // Setters //
    public void setMd_majorId(int id)
    {
        md_majorId = id;
    }
    public void setMd_majorName(String name)
    {
        md_majorName = name;
    }
    public void setMd_majorPrefixId(int prefix)
    {
        md_majorPrefix = prefix;
    }

    // static class for MajorData //
    static class PassMajorData
    {
        private static boolean addMajorSelected;
        private static boolean  backToDetails;
        static ArrayList<MajorData> passedMajorData;
        static ArrayList<String> mp_majorPrefixes;
        static ArrayList<String> mp_majorNames;

        public static void setMP_AddMajorFromDetails(boolean selected)
        {
            addMajorSelected = selected;
        }
        public static boolean getMP_AddMajorFromDetails()
        {
            return addMajorSelected;
        }
        public static void setMP_AddMajorBackToDetails(boolean back)
        {
            backToDetails = back;
        }
        public static boolean getMP_AddMajorBackToDetails()
        {
            return backToDetails;
        }


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
