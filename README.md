# MessagingApp Setup Guide

## About
This app is a direct messaging application. The frontend is handled entirely by JavaFX, while the backend and database operations are managed by Django. We use SQLite for our database. Our user model, `myUser`, extends the `User` class provided by `django.contrib.auth`.

## Setup Instructions

1. Clone and Pull
   - Make sure this is the first time you are cloning the repository. If you have previously cloned it, delete the instance of the repository on your local machine before pulling.

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
   2. Navigate to **Dependencies**. Change the ==Module SDK== to your new virtual environment. Ensure that your new SDK is a Python environment.
      1. Click on **Edit** saving the Module SDK. Make sure that the 'Python SDK home path:' matches the path of the virtual environment in your project.
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
      5. **VM options**: `--module-path "pathtojavaFX/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics`
      6. **Main class**: `java_files.mainGUI`
   5. Do not modify any other fields. Click **Apply**, then **OK**.
   6. Your JavaFX Application is set up!

8. Run the Program
   1. In the top right corner, select **JavaFXApp** instead of **Current File**.
   2. Run it!

---

All done! 
