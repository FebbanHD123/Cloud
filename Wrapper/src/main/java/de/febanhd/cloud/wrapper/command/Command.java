package de.febanhd.cloud.wrapper.command;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class Command {

    private final String command;
    private String usage;
    private String[] aliases;

    public Command(String command, String... aliases) {
        this.command = command.toLowerCase();
        this.aliases = aliases;

        StringBuilder builder = new StringBuilder();
        if(this.getArguments() != null) {
            this.getArguments().forEach(commandArgument -> {
                builder.append("<" + commandArgument.getName() + "(" + commandArgument.getType() + ")> ");
            });
        }
        this.usage = this.command + " " + builder.toString();
    }

    public abstract void execute(String command, String[] args);

    public void sendMessage(String message) {
        System.out.println(message);
    }

    public void sendUsage() {
        sendMessage("Use: " + usage);
    }

    public abstract List<CommandArgument> getArguments();

    public class CommandArgument {

        @Getter
        private final String type;
        @Getter
        private final String name;

        public CommandArgument(String name, String type) {
            this.type = type;
            this.name = name;
        }

        public CommandArgument(String name, Class<?> clazz) {
            this.type = clazz.getSimpleName();
            this.name = name;
        }
    }

    public boolean isCommandNameOrAlias(String input) {
        if(input.equalsIgnoreCase(this.command)) return true;
        for(String alias : this.aliases) {
            if(alias.equalsIgnoreCase(input)) return true;
        }
        return false;
    }

}
