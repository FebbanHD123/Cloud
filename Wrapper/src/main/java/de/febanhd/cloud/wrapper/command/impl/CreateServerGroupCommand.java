package de.febanhd.cloud.wrapper.command.impl;

import de.febanhd.cloud.server.ServerType;
import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.command.Command;
import de.febanhd.cloud.wrapper.util.FormatUtil;

import java.util.Arrays;
import java.util.List;

public class CreateServerGroupCommand extends Command {

    public CreateServerGroupCommand() {
        super("creategroup");
    }

    @Override
    public void execute(String command, String[] args) {
        if(args.length != 6) {
            this.sendUsage();
            return;
        }

        String groupName = args[0];
        if(groupName.length() > 32) {
            sendMessage("The group name is too long (> 32)");
            return;
        }

        if(!FormatUtil.isServerType(args[1].toUpperCase())) {
            sendMessage("The type isn't correct. Types: MINECRAFT, PROXY");
            return;
        }
        ServerType serverType = ServerType.valueOf(args[1].toUpperCase());

        if(!FormatUtil.isBoolean(args[2].toLowerCase())) {
            sendMessage("The argument static must be a boolean (true or false)!");
            return;
        }

        boolean staticServer = Boolean.parseBoolean(args[2].toLowerCase());

        int minOnlineServer;
        if(FormatUtil.isInteger(args[3])) {
            minOnlineServer = Integer.parseInt(args[3]);
            if(minOnlineServer < 0) {
                sendMessage("The minimum of online servers can't be smaller than 0");
                return;
            }
        }else return;

        int maxMemory;
        if(FormatUtil.isInteger(args[4])) {
            maxMemory = Integer.parseInt(args[4]);
            if(maxMemory < 50) {
                sendMessage("The minimum of ram for a servers can't be smaller than 50MB");
                return;
            }
        }else return;

        int maxPlayers;
        if(FormatUtil.isInteger(args[4])) {
            maxPlayers = Integer.parseInt(args[4]);
            if(maxPlayers < 1) {
                sendMessage("The maxplayers of a server can't be smaller than 1");
                return;
            }
        }else return;
        CloudWrapper.getInstance().getServerGroupManager().createServerGroup(serverType, groupName, staticServer, minOnlineServer, maxMemory, maxPlayers);
    }

    @Override
    public List<CommandArgument> getArguments() {
        return Arrays.asList(new CommandArgument("GroupName", String.class), new CommandArgument("serverType", "MINECRAFT/PROXY"),
                new CommandArgument("static", Boolean.class), new CommandArgument("MinServerOnline", Integer.class), new CommandArgument("MinMemoryInMB", Integer.class),
                new CommandArgument("MaxPlayers", Integer.class));
    }
}
