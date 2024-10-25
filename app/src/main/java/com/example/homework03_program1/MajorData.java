//=================================================================================================//
// Name: Mike Eberhart
// Date: 30 September 2024
// Desc: An application that will allow an admin(you) to add/edit/remove students into the registry
//=================================================================================================//
package com.example.homework03_program1;

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

    // used to pass bools to which view is accessing the add new major view //
    // mainly needed since I'm using ViewSwitcher to switch between views //
    static class PassMajorData
    {
        private static boolean addMajorSelected;
        private static boolean  backToDetails;

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
    }

}
