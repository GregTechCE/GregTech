It's recommended to use Intellij Idea as IDE, as it provides many powerful inspections to check & cleanup your code.


# Naming conventions:
* Standard java class naming convention (UpperCamelCase)
* Standard java field & method naming convention (lowerCamelCase)
* lower_camel_case for registry entries, string constant identifiers and keys

# Class conventions:
* Do not add unused code, like public modifiers in interfaces (tip: IDEA reports these just fine)
* Fields are always declared in top of class
* Use this.someField when changing self field value (makes code easier to understand without IDE)
* Dedicate big logic into multiple methods
* Do not have >1 nested classes, just create them in dedicated source files
* Use blanks lines to dedicate logic statements in methods.

# Minecraft-specific:
* Avoid marking things as tickable or having tile entities unless absolutely required.
* If creating interface in API, it's recommended to implement it as capability.
* Implement capabilities in dedicated classes.
* Always use GTLog.logger for logging purposes.
* Use mutable block pos if doing something often or comparing many blocks (and mutable objects in general)
* Prefer widget system to direct drawing in UI.

Failing to match these will get your changes 100% rejected.
