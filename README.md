# MessagingApp Setup Guide

## About
This app is a direct messaging application. The frontend is handled entirely by JavaFX, while the backend and database operations are managed by Django. We use SQLite for our database. Our user model, `myUser`, extends the `User` class provided by `django.contrib.auth`.

**Note:** Please make sure to copy the `.gitignore` file into your local repository. It should be on the same level as `src/` and `python_backend/`. **Ensure this file exists before pushing changes to the main repository**, as it will prevent issues when others try to pull from the main.

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
- Verify the presence of `requirements.txt` in the `python_backend/` directory.
- Run:
  ```
  pip install -r requirements.txt
  ```
- Verify installation by running `pip list`. Ensure you have the following:
  - Django (version 5.1.2)
  - djangorestframework (version 3.15.2)

### 6. Apply Database Migrations
- Run the following commands to ensure migrations are applied:
  ```
  python manage.py makemigrations
  python manage.py migrate
  ```
- After running these commands, you can proceed.

### 7. Run the Server
- Run:
  ```
  python manage.py runserver
  ```
- You should be able to view the database at http://127.0.0.1:8000/messaging/users/.
- You can test the database on Postman too: https://learning.postman.com/docs/designing-and-developing-your-api/testing-an-api/
  - Use the endpoint "http://127.0.0.1:8000/messaging/users/" 

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
