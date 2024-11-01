# messagingApp

# About
This app is a direct messaging app. The frontend is handled entirely by JavaFX, while the backend and database operations are handled by Django. We use SQLite for our database. Our model, myUser, extends the User class provided by django.contrib.auth. 

# NOTE
Please make sure to copy the .gitignore file into your local repository. It should be on the same level as src/ and python_backend/. 
ENSURE THIS FILE EXISTS BEFORE PUSHING CHANGES ONTO THE MAIN REPOSITORY. IT WILL CAUSE LOTS OF ISSUES WHEN OTHERS TRY TO PULL FROM MAIN.

# Setup Instructions 
1. Clone this repository.
2. Add a Python Module.
   - Go to File -> Project Structure -> + -> New Module.
   - Title your module whatever you like. Make sure a Python interpreter is running in base interpreter. This should look like (/usr/local/bin/python3.12). Otherwise, do not change any other settings.
3. Add a Virtual Environment
   - Step 2 should have auto-generated a virtual environment named venv.
   - If not, run python -m venv venv OR python3 -m venv venv. Whatever works.
4. Activate your virtual environment
   - source venv/bin/activate for macOS
   - \venv\Scripts\activate for Windows
   - If successful, you should see a (venv) tag on the left of your terminal.
5. Install requirements
   - Verify that you have a file titled requirements.txt in your project. It should be inside of python_backend/.
   - pip install -r requirements.txt
   - Run pip list. Verify that you have Django (version 5.1.2) and djangorestframework (version 3.15.2).
6. Ensure that migrations have been applied
   - python manage.py makemigrations
   - python manage.py migrate
   - After running these commands, you are safe to move on.
7. Run the Server
   - python manage.py runserver
   - You should be able to view our database at http://127.0.0.1:8000/messaging/users/
8. Lastly, set up JavaFX. 
   - Download the SDK (not jmods) version of JavaFX that is most compatible with your computer.
   - File -> Project Structure -> Libraries -> + -> Java.
   - Once you are here, navigate to your newly downloaded javafx folder. Look for a path similar to: javafx-sdk-23.0.1/
   - Go inside the lib/ folder. Open all files that begin with "javafx" except for "javafx.properties".
   - Add the files to the messagingApp module. Do NOT add it to the python module.
   - Click Apply, then click OK.
9. Set up your JavaFX Application. You will use this to run your JavaFX files.
    - Run -> Edit Configurations -> + -> Application.
    - Name your application JavaFXApp. Run on Local machine.
    - For -cp, select messagingApp. For 'module not specified', select whichever module has the description: SDK of 'messagingApp' module
    - Select Modify options and select Add VM Options
      --module-path "yourpath/yourJavaFXfolder/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
10. Click Apply, and then OK.
11. Select JavaFXApp on the top right corner. Ensure that in terminal, the server is running (python manage.py runserver). Run the program!
