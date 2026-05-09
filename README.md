# JSON Tools Application

## Project Description
An application designed for programmers who need to reformat or filter data structures saved in JSON format, and to compare these structures with each other. 

## Key Features
The JSON tools application allows you to perform the following operations:
* **Minification:** Minify an unminified JSON representation to save space.
* **Formatting:** Unminify JSON (reverse operation) by adding appropriate blanks and new lines for human readability.
* **Filtering & Comparison:** Filter specific data structures and compare different JSON structures against each other.
* **Accessibility:** The application will be available via a Graphical User Interface (GUI), as well as a remote API, allowing it to be easily integrated with existing developer tools.

## Project Documentation
* [Sprint Grading Rules](https://docs.google.com/spreadsheets/d/1EcWb4sr937r_odf31cbxdsNQNDbsQ71L/edit?usp=sharing&ouid=100614361376581630223&rtpof=true&sd=true)

---
*Developed as part of the Software Engineering course.*


## To run locally:
```bash
git clone https://github.com/Pandoxo/ComputerEngineering/SoftwareEngineeringProject
cd SoftwareEngineeringProject
mvn spring-boot:run
cd frontend
npm install
npm run dev
```

