Sean's Console Draw App
=============
This project uses Core scala

## Components Overview
Here's an overview of the three different components that make this application.

- ConsoleDrawerApp : Main terminal app
- CommandParser : Parsing Command Input to a case class
- PictureCreator : Manipulate the canvas according to the command
- PicturePrinter : Changing Canvas(Array[Array[String]]) to a log and print on console. 

## How to run
Execute ConsoleDrawerApp, please. Doesn't require an environment variables.

## How to run Tests
sbt clean it:test test