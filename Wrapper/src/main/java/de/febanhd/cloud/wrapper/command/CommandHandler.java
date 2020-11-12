package de.febanhd.cloud.wrapper.command;

import de.febanhd.cloud.wrapper.command.impl.CreateServerGroupCommand;
import de.febanhd.cloud.wrapper.command.impl.StopCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandHandler {

    private final CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();

    public CommandHandler() {
        //register commands
        commands.add(new CreateServerGroupCommand());
        commands.add(new StopCommand());

    }

    public void startInputThread() {
        new Thread(() -> {
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while((line = reader.readLine()) != null) {
                    String[] args = line.split(" ");
                    String commandString = args[0];
                    String[] finalArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, finalArgs, 0, args.length - 1);
                    boolean foundCommand = false;

                    for(int i = 0; i < this.commands.size(); i++) {
                        Command command = this.commands.get(i);
                        if(command.isCommandNameOrAlias(commandString)) {
                            if(!foundCommand) foundCommand = true;
                            command.execute(commandString, finalArgs);
                        }
                    }

                    if(!foundCommand) {
                        System.out.println("Command '" + commandString + "' not found.");
                        System.out.println("Commands:");
                        commands.forEach(command -> System.out.println("- " + command.getUsage()));
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "CommandInputThread").start();
    }
}
