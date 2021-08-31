![Build Status](https://codebuild.eu-west-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiOFpwNmFkb1oxVUhBY250SmZDaXI1eFczb0M2Z01FRnozeXJRbldZVWtQbUNLRGhkYTFOUUVhblVndmtuWTlmMnJ4ZGIwTFZLdXNWRXlIdnRjdk5BMmdrPSIsIml2UGFyYW1ldGVyU3BlYyI6IlBlNXFYbHoxdW5WTFE2djQiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master)

Sean's Console Draw App
=============
This project uses Core scala 2.13.6

## Components Overview
Here's an overview of the three different components that make this application.

- ConsoleDrawerApp : Main terminal app
- CommandParser : Parsing Command Input to a case class
- PictureCreator : Manipulate the canvas according to the command
- PicturePrinter : Changing Canvas(Array[Array[String]]) to a log and print on console. 

## How to run
Execute ConsoleDrawerApp, please. Doesn't require an environment variables.

## How to run Tests
sbt clean test