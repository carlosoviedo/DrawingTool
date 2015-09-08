# DrawingTool
Programming exercise

### Build Commands
Commands that can be executed against the build:

* **./gradlew clean**: clean generated artifacts
* **./gradlew jacocoTestReport**: generate test coverage reports. This reports are generated under 
  *${projectDir}/docs/tests/coverage*, where *${projectDir}* is the directory that contains the *build.gradle* file.
* **./gradlew test**: run junit tests. This reports are generated under 
  *${projectDir}/docs/tests/junit*.
* **./gradlew run**: run the application using the example *input.txt* that is found under *${projectDir}*. Change this file
  to see how the application behaves with different drawing commands.

----

### Design Patterns Used

* **Observable**: notifies its subscribers when a new command is read from the input file.
* **Observer**: subscribes to an observer to process the new drawing command
* **FactoryMethod** and **AbstractFactory**: to create the different objects used throughout the application.
* **Strategy**: used by the resource loader to load files using different algorithms.
* **Chain of Responsibility**: used to chain different strategy implementations to try different resource loading algorithms.
* **Template method**: to define the main algorithm used by the resource loader strategy objects.
* **Interpreter**: used by one of the observers that subscribes to the expression observable; each interpreter interprets its
  drawing command with respect to the Canvas model object.
  
----

### How it works

> (1) **ExpressiosnOservable** -> (2) **Parser** -> (3) **ObjectFactory** -> (4) **DrawingExpression** -> (5) **Canvas**

1. **ExpressionObservable** notifies the **Parser** for every drawing command found in the input file.
2. **Parser** transforms each line in the file to a **Parse.Result** object that stores the name and arguments of the command. 
3. **ObjectFactory** receives the command name and arguments from the **Parser**, and creates a **DrawingExpression** for each 
   **Parse.Result**.
4. **DrawingExpression** interprets the command and arguments using the **Canvas** model object as its interpretation context.
5. **Canvas** draws the resulting internal state of the canvas model object.
