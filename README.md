# Student Marks Manager (Java Console)

Hi there! 👋

This is a Java console app I made to practise working with files and lists. You add students and their marks for three subjects, and it gives you a class report with averages, results and the top student. It also saves everything to a CSV file, so your class is still there the next time you open it.

## What you'll need

Only **Java 17 or newer** (the JDK, since you'll compile the code yourself).

Quick check in a terminal:

```bash
java -version
javac -version
```

If both say 17 or higher, you're set. If not, you can download a free JDK from [Adoptium](https://adoptium.net/).

## Running it

From the project folder:

```bash
cd src
javac *.java
java MarksApp
```

Heads up: the app saves to a file called `students.csv` in whatever folder you run it from. If you follow the steps above, that'll be inside `src`.

## Taking it for a spin

You'll get this menu:

```
1. Add student
2. Show class report
3. Search student
4. Show top student
5. Delete student
0. Save and exit
```

Add a few students (marks have to be between 0 and 100, and it will nag you if they aren't), then check the report. It looks something like this:

```
Number     Name            Programm Web Deve Database Avg     Result
S001       Lerato          80       72       90       80.7    Distinction
S002       Sipho           55       48       61       54.7    Pass
Class average: 67.7%
```

Here's how the results are worked out from the average:

| Average   | Result      |
|-----------|-------------|
| 75 and up | Distinction |
| 60 – 74   | Merit       |
| 50 – 59   | Pass        |
| Below 50  | Fail        |

Make sure you choose **0 to save and exit** when you're done, so your changes are written to the file.

## What I was practising

- Arrays and `ArrayList`
- Reading and writing files with `FileWriter` and `BufferedReader`
- Turning objects into lines of text and back again
- Input validation (no marks of 150, sorry)
- Simple algorithms like averages and finding the highest score

## Things I know aren't perfect

- The three subjects are fixed in the code, so you can't add your own yet.
- Names can't have commas in them, because the CSV file uses commas to separate the fields.
- You can't edit a student's marks yet. For now you have to delete them and add them again.

Thanks for having a look! Any feedback is very welcome.
