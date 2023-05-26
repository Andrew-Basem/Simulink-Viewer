# Simulink Viewer

Simulink Viewer is a software tool that allows users to read Simulink MDL files and display their contents in a user-friendly way using a Java-based graphical user interface (GUI). The project aims to provide a quick and easy way to visualize Simulink models without the need for the Simulink environment itself.

## Project Structure - Maven

The Simulink Viewer software is based on the Maven project structure and will consist of two main components: a Simulink MDL file parser and a Java-based GUI. The parser will be responsible for reading the MDL file and extracting the model information, including the block diagram, parameters, and connections. The GUI will provide a user-friendly interface for displaying the model and enabling user interaction.

All resources, including .mdl files and photos, are located in the simulink\simulink\src\main\resources folder.
Maven dependencies are in pom.xml

## Usage

To use the Simulink Viewer tool, follow these steps:

1. Navigate to the following path: simulink/src/main/java/com/example/simulink/
2. Run the Main.java file.
3. Launch the Java-based GUI.
4. Load a Simulink MDL file using the file menu.
5. Navigate through the model components and see their properties and connections in the hierarchical structure.

## Motivation

Simulink is a popular simulation and modeling environment used in various industries.
The Simulink MDL files contain the model information, which is used to simulate and analyze the system behavior. The aim of this project is to develop a software tool that can read Simulink MDL files and display their contents in a user-friendly way using a Java-based graphical user interface (GUI).

## Key Features

The Simulink Viewer tool will provide the following key features:

- Loading and parsing Simulink MDL files
- Displaying the Simulink model in a hierarchical structure
- Providing real-time visualization of changes made to the model
- Allowing users to modify the model interactively
- Providing a user-friendly interface for navigating and exploring the model

## MDL Files

Simulink MDL files are a file format used by the Simulink modeling and simulation software developed by MathWorks. MDL files are an alternative to theolder Simulink SLX file format and were introduced in Simulink version 6.5. MDL files are XML-based and can be opened and edited using a text editor or XML editor, as well as the Simulink environment. They contain the same information as SLX files, including block diagrams, parameters, and connections between blocks.

In addition to Simulink, MDL files are also used in other MathWorks products like Stateflow and Simscape. They have become the preferred file format for Simulink models in recent versions of the software, and MathWorks recommends that users transition from SLX to MDL files.

## Credits

1)2000261 Andrew Basem Ishak
2)2000916 Carol Botros Wissa
3)2000042 Andrew Gamal Todary
4)2000977 Marline mansour mansy
5)2001814 Jan Farag Hanna

## Example MDL

An example MDL file is included in the Git repository to help you get started with the Simulink Viewer tool.
