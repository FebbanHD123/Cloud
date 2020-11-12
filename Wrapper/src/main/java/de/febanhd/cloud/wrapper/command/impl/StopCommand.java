package de.febanhd.cloud.wrapper.command.impl;

import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.command.Command;

import java.util.List;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop", "exit");
    }

    @Override
    public void execute(String command, String[] args) {
        System.exit(0);
    }

    @Override
    public List<CommandArgument> getArguments() {
        return null;
    }
}
