# MessagingApp Setup Guide

## About
This app is a direct messaging application. The frontend is handled entirely by JavaFX, while the backend and database operations are managed by Django. We use SQLite for our database. Our user model, `myUser`, extends the `User` class provided by `django.contrib.auth`.

## IMPORTANT
Please make sure .gitignore exists in your project's root directory before making any commits to Github. Otherwise, it will create conflicts for everyone. 

## Note for VS Code users
- VS Code users would mostly follows the Setup Instructions to step 5, after than that they will need another way to run the Java GUI, but before that

### Add javafx .jar files to Referenced Libraries
- Down in the bottom left of the screen, neer the setting, there is JAVA PROJECTS.
- Press and scroll down to find Referenced Libraries, then add all of the .jar files of javafx to it.
- Alternative way, press Ctrl + Shift + P, type Open Project Settings, go to Libraries and add does .jar files.

### Modify launch.json

Add the following line to `launch.json`, in the configurations section, between `request` and `mainClass`
```
"vmArgs": "--module-path \"C:/Program Files/Java/javafx-sdk-23.0.1/lib\" --add-modules javafx.controls,javafx.fxml,javafx.graphics",
```
For example:
```
{
    "type": "java",
    "name": "Current File",
    "request": "launch",
    "vmArgs": "--module-path \"C:/Program Files/Java/javafx-sdk-23.0.1/lib\" --add-modules javafx.controls,javafx.fxml,javafx.graphics",
    "mainClass": "${file}"
},
```
### Install these extensions
- Extension Pack for Java
- JavaFX Support by Shrey Pandya
- Language Support for Java(TM) by Red Hat


### What change after step 5 for VS Code user
- Go the src/java_files, find the MainGUI.java.
- Run it at the main method.
- Alternatively go to the Run Java button on the top right and run the file, and a GUI will pop up.

## Installing python notes
- We are using 3.12.x, which can be download at: https://www.python.org/downloads/release/python-3126.
- Remember to add python to PATH environments variables when setting up, there is a checkbox for that at the bottom left corner when first running the installer file.
- Go to this site to install pip the first time: https://pip.pypa.io/en/stable/installation/, remember to pick your operating system, we recommend using the ensurepip method(the first one on the site).

## Setup Instructions

1. Clone and Pull
   - Make sure this is the first time you are cloning the repository. If you have previously cloned it, delete the instance of the repository on your local machine before re-cloning.

2. Navigate to python_backend/
   - Run `cd python_backend/`

3. Create a Virtual Environment
   1. For macOS/Linux: `python3 -m venv venv`
   2. For Windows: `python -m venv venv`

4. Activate the Virtual Environment
   1. For macOS/Linux: `source venv/bin/activate`
   2. For Windows: `venv\Scripts\activate`

5. Configure the Python Module
   1. Navigate to **File -> Project Structure -> Modules**. Verify that a module named 'python_backend' already exists. Click on it.
   2. Navigate to **Dependencies**. Change the <mark>Module SDK</mark> to your new virtual environment. Ensure that your new SDK is a Python environment.
      1. Click on **Edit** saving the Module SDK. Make sure that the <mark>Python SDK home path:</mark> matches the path of the virtual environment in your project.
   3. Click **Apply**, then **OK**.
   4. Your Python module is set up!

6. Add the JavaFX library
   1. Download the version of JavaFX that is most compatible with your computer.
   2. Navigate to **File -> Project Structure -> Modules**. Verify that a module named 'messagingApp' already exists. Click on it.
   3. Under the **Module SDK** dropdown, you should see a small **+**. Click on it. Then, select **Library...**, and add the necessary JavaFX files.
      1. Only add files in `lib/` that end in `.jar`.
   4. Click **Apply**, then **OK**.
   5. You just imported the JavaFX libraries!

7. Configure the JavaFX Application
   1. Navigate to **Run -> Edit Configurations**.
   2. Click on **+**. Add an **Application**.
   3. Click on **Modify options**. Find 'Add VM options' and make sure it's toggled on.
   4. Fill out the fields as such:
      1. **Name**: "JavaFXApp"
      2. **Run on**: Local Machine
      3. **module not specified**: java 21 SDK of 'messagingApp'
      4. **-cp <no module>**: messagingApp
      5. **VM options**: --module-path "path/to/your/javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
         - Note: replace <mark>"path/to/your/javafx/lib"</mark> with the complete path on your computer where you saved your javaFX `lib/` files.
      6. **Main class**: java_files.mainGUI
   5. Do not modify any other fields. Click **Apply**, then **OK**.
   6. Your JavaFX Application is set up!

8. Run the Program
   1. In the top right corner, select **JavaFXApp** instead of **Current File**.
   2. Run it!

---

All done! 
