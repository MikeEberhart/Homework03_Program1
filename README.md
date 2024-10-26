# Homework03_Program1
// Name: Mike Eberhart
// Date: 25-Oct-2024
// Desc: Check below for description

 This application will allow an admin(you) to add new students into the registry. 
 When the app first loads you are met with the current list of students in the registry.
 Under the list is 3 buttons label with corresponding images. They are the "Search" button,
 the "Add New Student" button and the "Delete Student" button. The "Search" button will bring you
 to the Search page the which will let you enter in certain fields to search the registry with. Once
 the desired fields are filled out and the search button is clicked, you will be brought to a new
 view displaying the results of your search. The "Add New Student" button will bring you to a page 
 that will allow you to add new students to the registry. At the bottom the page are two buttons. 
 One taking you back to the home page, and another allow you to add a new major to the list of majors. 
 Once added the student will appear on the list on the main/home page. Back on the main/home page, 
 if you perform a single-click on a student in the list you will be able to then click the delete 
 button to remove the student. But if no student is selected you will be met with text displaying 
 an error. If you perform a long-click on a student in the list you will be brought to the Details 
 page showing all the details of the current student. At the bottom of that page is the "Home" button, 
 the "Update" button, and the "Add New Major" button. If you need to change anything about the current 
 student just click the update button and you will be brought to screen allow you to update everything 
 but the "Username" of the current student. You can even add a new major from this page if you desire.
 Hopefully that wasn't to long of a run down of the app description. ENJOY!!!

// Other Comments //
 I had some issues at first with how I should properly handle the database reading and writing of the 
 data. But after some messing with it and talks we've had in class, I think I've gotten a better understanding
 of it works. I also had a few issues with the way I set up my app. I used ViewSwitcher to cut down on the 
 number of activities. I mainly did it this way just to see if I could. ViewSwitcher can hold 2 child views
 so switching between them I had a few issues. So I added some checks to see which view was active and
 which wasn't. Doing so fixed some of my issues. There were other small issues I ran across but nothing 
 that gave me to much trouble.