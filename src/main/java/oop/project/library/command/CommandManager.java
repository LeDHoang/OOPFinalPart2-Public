// File: oop/project/library/command/CommandManager.java
package oop.project.library.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(Command command) {
        if (commands.containsKey(command.getName())) {
            throw new IllegalArgumentException("Command already registered: " + command.getName());
        }
        commands.put(command.getName(), command);
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
