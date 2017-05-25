# PasswordProtector

PasswordProtector is a desktop application for storing passwords. You only have to register in the application and remember the keyword that you specified during the registration. Then you can store your logins and passwords in it. All your passwords are stored in encrypted form in the SQLite database.

## Getting Started
### Requirements
Java 8, Apache Maven
### Build
Clone the repository:

`https://github.com/ponaguynik/password-protector.git`

Build with maven:

`mvn clean package`

Copy *target/password-protector-1.0.jar* file wherever you want to use it.
## Usage
Run *password-protector-1.0.jar*. *database.db* file will be created where all your data will be stored.

Register a new user in the app so you can store your logins and passwords.

You can move the *database.db* file with your data wherever you want. All data in it are encrypted.

Just run *password-protector-1.0.jar* in the same directory as *database.db* so you can sign in with your login and keyword, all of your data will be there.
