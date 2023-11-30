# Vacation Tracker
### D308 Mobile Applications Development Task 1
### Authored by Marcus Lull

## Vacation Tracker v.1.0
Compile SDK: 34  
Target SDK: 33  
Minimum SDK 26  
gitlab: https://gitlab.com/wgu-gitlab-environment/student-repos/mlull/d308-mobile-application-development-android/-/tree/working_branch?ref_type=heads

## About
The Vacation Tracker app is a final project for WGU D308 Mobile Application Development (Android).

The application purpose is to demonstrate the application of technical programming skills acquired
thus far. The application was planned to resemble a real-world scenario using Android Studio, Java,  Room Framework
and SQLite to build a basic vacation tracking application.

The app utilizes a number of features of the Android SDK including:
* Activities
* Fragments
* Intents
* Permissions
* Alerts
* Notifications
* ORM
* Database

## Usage
After installing and launching the Vacation Tracker app you will land at the home page which will  display
vacations as you add them.

### Add a vacation
Press the + floating action button in the bottom right
Enter the vacation information, all fields are required
Press SAVE to write your vacation to the database
Note - The start date must be prior to the end date

### View vacation details
Press a vacation entry button from the home page

### Edit a vacation
From the vacation details page, press the EDIT button
OR optionally, long press a vacation entry button from the home page and press EDIT
Make any necessary edits to the vacation details
Press SAVE to update your vacation in the database

### Share a vacation
From the vacation details page press the SHARE button
The Android share window will popup giving you sharing options
Note - Some starter sharing text is pre-populated

### Delete a vacation
From the vacation details screen, press the DELETE button
OR optionally, long press a vacation entry button from the home page and press DELETE
Confirm the delete by pressing YES
Note - A vacation can only be deleted if it has no attached excursions

### Add an excursion
From the appropriate vacation details screen press the ADD A NEW EXCURSION button
Enter a title and date
Press save to save the excursion to the database.
Note - The excursion will only show up for the associated vacation
Note - The excursion date must be during the associated vacation's time frame

### View excursion details
from the vacation details page, press the appropriate excursion
The excursion details page will now be displayed

### Edit an excursion
From the excursion details page, press EDIT
Or optionally, long press the excursion from the excursion page and press EDIT on the context menu
Make the necessary updates and press save
The updates will be saved to the database
Note - You cannot move an excursion from one vacation to another

### Delete an excursion
From the excursion details page, press DELETE
Or optionally, long press the excursion from the excursion page and press DELETE
Confirm the delete by pressing yes

### Set Notifications for vacation or excursion start/end times
From the vacation or excursion details screens press to toggle the notification switch
This will send an alert to your phone on the start date of your vacation or excursion and the end date of your vacation.

### Navigation
Navigation between the screens is provided by pressing on the resource required, the up arrow on the app bar or your phone's back button 






<strong> **DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

# WESTERN GOVERNOR UNIVERSITY 
## D308 – MOBILE APPLICATION DEVELOPMENT (ANDROID)
Welcome to Mobile Application Development (Android)! This is an opportunity for students to create page layouts with clean navigation, design mobile application infrastructure and user interfaces, develop secure database-backed mobile applications in an object-oriented language, document solutions for application requirements with storyboards and emulators, articulate challenges in the development process, and describe alternatives methods in overcoming mobile application development problems. 

FOR SPECIFIC TASK INSTRUCTIONS AND REQUIREMENTS FOR THIS ASSESSMENT, PLEASE REFER TO THE COURSE PAGE.
## BASIC INSTRUCTIONS
For this assessment, you have an opportunity to test your competency as a mobile application developer. Your understanding of mobile application structure and design will help you to develop applications to meet customer requirements. This task will allow you to demonstrate your ability to apply the skills learned in the course.

## SUPPLEMENTAL RESOURCES 
1.	How to clone a project to Android Studio using Git?

> Ensure that Android Studio and Git are installed on your system.  New Project, Get from VCS button or the File/New/Project from Version Control. This will open a window  with a prompt to clone the project. Save it in a safe location for the directory and press clone. IntelliJ will prompt you for your credentials. Enter in your WGU Credentials and the project will be cloned onto your local machine.

2. How to create a branch and start Development?

- GitLab method
> Press the '+' button located near your branch name. In the dropdown list, press the 'New branch' button. This will allow you to create a name for your branch. Once the branch has been named, you can select 'Create Branch' to push the branch to your repository.

- Android Studio method
> In Android Studio, Go to the 'Git' button on the top toolbar. Select the new branch option and create a name for the branch. Make sure checkout branch is selected and press create. You can now add a commit message and push the new branch to the local repo.

## SUPPORT
If you need additional support, please navigate to the course page and reach out to your course instructor.
## FUTURE USE
Take this opportunity to create or add to a simple resume portfolio to highlight and showcase your work for future use in career search, experience, and education!

