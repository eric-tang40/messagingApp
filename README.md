# MessagingApp Setup Guide

## About
This app is a direct messaging application. The frontend is handled entirely by JavaFX, while the backend and database operations are managed by Django. We use SQLite for our database. Our user model, `myUser`, extends the `User` class provided by `django.contrib.auth`.

**Note:** Please make sure to copy the `.gitignore` file into your local repository. It should be on the same level as `src/` and `python_backend/`. 
**Ensure this file exists before pushing changes to the main repository**, as it will prevent issues when others try to pull from the main.

## Note for VS Code users
- VS Code users would mostly follows the Setup Instructions to step 7, after than that they will need another way to run the Java GUI.
- VS Code user would also won't need to do step 2 beside checking if they have Python (preferably some of the later version, we work on 3.12.x) installed or not.

### What change after step 7 for VS Code user
- Go the src/java_files, find the MainGUI.java, and either run it at the main method or just go to the Run code/ Run Java button on the top right and run the file, and a GUI will pop up.

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

### Installing python notes
- We are using 3.12.x, which can be download at: https://www.python.org/downloads/release/python-3126.
- Remember to add python to PATH environments variables when setting up, there is a checkbox for that at the bottom left corner when first running the installer file.
- Go to this site to install pip the first time: https://pip.pypa.io/en/stable/installation/, remember to pick your operating system, we recommend using the ensurepip method(the first one on the site).

## Setup Instructions

### 1. Clone the Repository
Clone this repository to your local machine.

### 2. Add a Python Module
- Navigate to **File → Project Structure → + → New Module**.
- Title the module as you prefer. Ensure a Python interpreter is running in the base interpreter (it should look like `/usr/local/bin/python3.12`).
- Do not modify any other settings.
- Create the module.

### 3. Add a Virtual Environment
- Step 2 should auto-generate a virtual environment named `venv`.
- If not, run:
  - **macOS/Linux:** `python3 -m venv venv`
  - **Windows:** `python -m venv venv`

### 4. Activate the Virtual Environment
- **macOS/Linux:** `source venv/bin/activate`
- **Windows:** `venv\Scripts\activate`
- If successful, you should see a `(venv)` tag on the left of your terminal.

### 5. Install Requirements
- Verify that `requirements.txt` in the `python_backend/` directory.
- Run:
  ```
  pip install -r python_backend/requirements.txt
  ```
- Verify installation by running `pip list`. Ensure you have the following:
  - Django (version 5.1.2)
  - djangorestframework (version 3.15.2)

### 6. Apply Database Migrations
- Run the following commands to ensure migrations are applied:
  ```
  python python_backend/manage.py makemigrations
  python python_backend/manage.py migrate
  ```
- After running these commands, you can proceed.

### 7. Run the Server
- Run:
  ```
  python python_backend/manage.py runserver
  ```
- You should be able to view the database at http://127.0.0.1:8000/messaging/users/.
- You can test the database on Postman too: https://learning.postman.com/docs/designing-and-developing-your-api/testing-an-api/
- Use the endpoint "http://127.0.0.1:8000/messaging/users/" 

#### VS Code users check notes above from now on

### 8. Set Up JavaFX 
- Download the SDK (not jmods) version of JavaFX that is compatible with your computer.
- Go to **File → Project Structure → Libraries → + → Java**.
- Navigate to your newly downloaded JavaFX folder, path should look similar to: `javafx-sdk-23.0.1/`.
- Inside the `lib/` folder, open all files that begin with "javafx" except for `javafx.properties`.
- Add these files to the **messagingApp** module only (do NOT add them to the Python module).
- Click **Apply**, then **OK**.

### 9. Set Up JavaFX Application
- Navigate to **Run → Edit Configurations → + → Application**.
- Name your application `JavaFXApp`. Ensure it runs on **Local machine**.
- For **-cp**, select **messagingApp**. For **'module not specified'**, select the module described as `SDK of 'messagingApp' module`.
- Click on **Modify options** and select **Add VM Options**.
  - Add the following VM options: 
    ```
    --module-path "yourpath/yourJavaFXfolder/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
    ```
  - Don't forget to replace "yourpath/yourJavaFXfolder/lib" with the path to your newly downloaded javaFX folder. 

### 10. Final Configuration
- Click **Apply**, then **OK**.
- Select `JavaFXApp` from the top right corner.
- Make sure that the server is running in your terminal (`python manage.py runserver`).
- Run the JavaFX program!

---

All done! 
