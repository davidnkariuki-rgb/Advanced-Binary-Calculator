Advanced Binary Calculator
Overview

The Advanced Binary Calculator is a Java-based programmer's calculator supporting multiple number systems: Binary (BIN), Decimal (DEC), Hexadecimal (HEX), and Octal (OCT). It provides standard arithmetic operations—addition, subtraction, multiplication, and division—with seamless conversion between number systems. The calculator features a modern GUI built with Java Swing and is designed for both educational and professional use.

Features

Multi-base support: BIN, DEC, HEX, OCT.

Arithmetic operations: Addition, Subtraction, Multiplication, Division.

Automatic input validation for each number system.

Real-time number conversion across modes.

Clear (CLR) and Delete (DEL) functions for efficient input management.

Polished GUI with color-coded buttons and responsive design.

Exception handling for invalid inputs and division by zero.

Installation

Ensure Java JDK 8 or above is installed.

Clone the repository:

git clone <repository_url>


Compile the project:

javac -d bin src/**/*.java


Run the application:

java -cp bin Main

Usage

Launch the application.

Select the desired number system mode (BIN, DEC, HEX, OCT).

Enter the first number in the respective field.

Choose an arithmetic operation (+, −, ×, ÷).

Enter the second number.

Press = to compute the result.

Use CLR to clear all fields or DEL to delete the last character.

Switch modes at any time to convert existing numbers automatically.

Code Structure

exceptions: Custom exception classes for calculation and invalid binary inputs.

CalculationException.java

InvalidBinaryException.java

logic: Core calculator logic and number conversions.

BinaryCalculator.java

model: Binary number representation and validation.

BinaryNumber.java

operations: Arithmetic operation classes implementing a common interface.

BinaryOperation.java (interface)

Addition.java, Subtraction.java, Multiplication.java, Division.java

ui: Java Swing GUI classes for the calculator interface.

CalculatorUI.java

Main.java: Entry point to launch the application.

Exception Handling

Invalid input for a specific mode throws IllegalArgumentException.

Division by zero throws ArithmeticException.

Invalid binary numbers are caught with InvalidBinaryException.

Contribution

Contributions are welcome! To contribute:

Fork the repository.

Make your changes.

Submit a pull request with a clear description of your improvements.