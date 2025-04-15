package com.karasu256.teamUtils.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractCommand extends Command implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(AbstractCommand.class.getName());
    private final String name;
    private final List<ISubCommand> subCommands = new ArrayList<>();

    public AbstractCommand(String name, ISubCommand... subCommands) {
        super(name);
        this.name = name;
        this.subCommands.addAll(Arrays.stream(subCommands).toList());
    }

    public void addSubCommand(ISubCommand subCommand) {
        if(!this.subCommands.contains(subCommand)) {
            this.subCommands.add(subCommand);
        } else {
            LOGGER.warning("Subcommand already exists or subCommands list is null.");
        }
    }

    @Override
    public List<ISubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args.length == 0) {
            return execute(sender, label, args);
        }

        for (ISubCommand subCmd : getSubCommands()) {
            if (subCmd.getName().equalsIgnoreCase(args[0])) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                if (newArgs.length == 0) {
                    return subCmd.onCommand(sender, command, label, newArgs);
                }

                for (ISubCommand childCmd : subCmd.getSubCommands()) {
                    if (childCmd.getName().equalsIgnoreCase(newArgs[0])) {
                        String[] grandChildArgs = new String[newArgs.length - 1];
                        System.arraycopy(newArgs, 1, grandChildArgs, 0, newArgs.length - 1);

                        if (grandChildArgs.length == 0) {
                            return childCmd.onCommand(sender, command, label, grandChildArgs);
                        }

                        return childCmd.onCommand(sender, command, label, grandChildArgs);
                    }
                }

                return subCmd.onCommand(sender, command, label, newArgs);
            }
        }

        return execute(sender, label, args);
    }
}
