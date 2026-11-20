# Student Marks Manager (Java Console)

A Java console app I made for practice. It captures students and their marks for three subjects, prints a class report with averages and results, finds the top student, and saves everything to a CSV file.

## What I practised
- Arrays and `ArrayList`
- Reading and writing files (`FileWriter`, `BufferedReader`)
- Converting objects to and from lines of text
- Input validation (marks must be 0–100)
- Simple algorithms: averages and finding a maximum

## How to run
You need Java 17 or newer.

```bash
cd src
javac *.java
java MarksApp
```

The app creates `students.csv` in the folder you run it from.

## Example report
```
Number     Name            Programm Web Deve Database Avg     Result
S001       Lerato          80       72       90       80.7    Distinction
S002       Sipho           55       48       61       54.7    Pass
Class average: 67.7%
```

## Known limitations
- The subjects are fixed in the code.
- Names can't contain commas, because the file uses commas to separate fields.
- No way to edit a student's marks yet. For now you delete and re-add them.
