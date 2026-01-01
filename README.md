# Movie Database Project 

# 📂 Project Structure
## There are three logical sections of code (packages) that keep everything clean:
models: The building blocks of the app (Media, Movie, Series, Episode, and Documentary).

services: This is responsible for the operations of searching, sorting, and storing information into files.

ui: This comprises user interface components, such as a main menu system and a test data loader.


# 🚀 How to Run the Project
Since the files are organized into packages:

1. Compile the Project
Run this command from the main project folder to prepare all the files at once:

`javac -d . src/models/*.java src/services/*.java src/ui/*.java`


2. Load Sample Data (Optional)
If you want to test the app without typing in a bunch of movies manually, run the TestDataLoader. This was created in order to test the project while it was being made but it can also be used as a pre-set list of media:

`java ui.TestDataLoader`

4. Run the Application
Run the main app which has the ui menu where you can access your database:

`java ui.Application`
