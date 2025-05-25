
# 🔐 Password Manager – Java Project

A secure and modular password management system built in Java.  
The application allows users to store, retrieve, and manage encrypted passwords locally using a clean JSON structure.  
It includes a command-line interface (CLI) as well as a fully featured graphical user interface (GUI) for an intuitive experience.

## 🚀 Features
- Password storage with AES encryption
- Master password access control
- JSON-based local data storage
- Graphical user interface (GUI) using JavaFX or Swing
- Strong password generator
- Real-time password strength rating

## Team
- Yuval Bershtansky
- Ofir Grinholtz
- Adam Smushkevich

## How to run

1. Download maven from here : Binary zip archive
https://maven.apache.org/download.cgi

2. Add the maven bin folder into your system environment variables

3. check that maven is working: mvn --version  

4. compile:
mvn clean compile

5. run the program:
mvn javafx:run




Past methods:
javac -cp lib/json-20231013.jar -d out src/*.java src/PasswordManager/*.java
java -cp "lib/json-20231013.jar;out" Main

javac --module-path lib --add-modules javafx.controls,javafx.base,javafx.graphics -cp lib/json-20231013.jar -d out src/*.java src/PasswordManager/*.java
java --module-path lib --add-modules javafx.controls,javafx.base,javafx.graphics -cp "lib/json-20231013.jar;out" PasswordManagerGUI

