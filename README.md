<h1 align="center">Movie Database Project</h1>


# 📂 Project Structure
## There are three logical sections of code (packages) that keep everything clean:
models: The building blocks of the app (Media, Movie, Series, Episode, and Documentary).

services: This is responsible for the operations of searching, sorting, and storing information into files.

ui: This comprises user interface components, such as a main menu system and a test data loader.


# How to Run the Project
Since the files are organized into packages:

1. Compile the Project,
Run this command from the main project folder to prepare all the files at once:

`javac -d . src/models/*.java src/services/*.java src/ui/*.java`


2. Load Sample Data (Optional),
If you want to test the app without typing in a bunch of movies manually run the TestDataLoader.java, it was created in order to test the project while it was being made but it can also be used as a pre-set list of media:

`java ui.TestDataLoader`

4. Run the Application,
Run the main app which has the ui menu where you can access your database:

`java ui.Application`

---
## Application Preview

Here's how the main ui and its features look like:

### 1. Using (TestDataLoader) For Sample Database
Before running the main app you can run this file in order to populate and fill up you database with already existing data to simplify testing. it'll display this:

<p align="center">
  <img width="494" height="1090" alt="Screenshot 2026-01-01 235937" src="https://github.com/user-attachments/assets/c7d397ce-a3eb-498b-ba95-375480ae0d78" />
</p>
<br>

### 2. Main Menu and Adding a Movie
This represents the main UI for the app where you can select what you want to do based on the numbers on a numbered list:

<p align="center">
  <img width="883" height="649" alt="Screenshot 2026-01-03 090635" src="https://github.com/user-attachments/assets/7ffcf51d-3874-4494-851e-24a5c87e38fb" />

</p>
<br>

### 3. Searching the Database
This shows how the search functionality works to retrieve data ,when searching by title for "Inception" we get what we wanted:

<p align="center">
  <img width="509" height="528" alt="image" src="https://github.com/user-attachments/assets/4a4a7a90-a3ea-4633-9a3d-423320675144" />

</p>
<br>

### 4. Database Statistics
i added a statistics section to the app where calculation involves real time data regarding your collection, this renders your total media number and specifies each type of media as well as your overall ratings from the entire database:

<p align="center">
  <img width="482" height="340" alt="image" src="https://github.com/user-attachments/assets/3de4f457-92c5-45ee-80d0-ae95c56391d1" />
</p>
<br>

### 5. (Save & Load)
This section shows the application's Saving/Loading system. When running the TestDataLoader or saving manually, what happens is that a folder "data/" which contains a file named "media_database.ser" gets created By implementing the Serializable interface that i put in my data classes.

<p align="center">
 
<img width="493" height="85" alt="Screenshot 2026-01-03 091540" src="https://github.com/user-attachments/assets/6458242f-55b7-470a-8da0-47138ba589c0" />
<img width="476" height="85" alt="Screenshot 2026-01-03 091553" src="https://github.com/user-attachments/assets/3153a9ca-b036-4bad-9cf3-540433b8cff7" />


</p>
<br>
