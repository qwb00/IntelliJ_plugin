# Code Statistics IntelliJ Plugin

An IntelliJ IDEA plugin that calculates and displays code statistics in real-time for Java files.
The plugin shows the total number of lines, classes, interfaces, methods, variables, comments, and
the average method length in the currently open Java file. Statistics are updated live as you edit
your code.

## Requirements

- JDK 11 or newer
- IntelliJ IDEA 2023.3+ (Community Edition)

The Gradle wrapper is included, so no separate Gradle installation is needed.

## Usage

Run the plugin in a sandbox IDE:

```bash
./gradlew runIde
```

Once the plugin is active, open the **Code Stats** tool window on the right side of the IDE and
open any `.java` file. The panel fills in immediately and refreshes as you type:

```
Total lines: 128
Classes: 2
Interfaces: 1
Methods: 9
Variables: 14
Comments: 6
Average method length: 7 lines
```

Switching to a non-Java file shows *No file selected*.

## How it works

| Class | Role |
| --- | --- |
| `CodeStatsService` | Walks the PSI tree with `JavaRecursiveElementWalkingVisitor` and collects the counts |
| `CodeStatistics` | Plain data holder for the collected metrics |
| `StatsPanel` | Swing panel rendering the numbers, registered as a project-level service |
| `StatsToolWindowFactory` | Registers the tool window |
| `EditorEventListener` | Recalculates on document and PSI changes |
| `MyFileEditorManagerListener` | Recalculates when you switch tabs |

## Notes

- Java only — other languages show no statistics.
- Constructors are excluded from the method count and from the average method length.
- *Variables* covers both fields and local variables.
