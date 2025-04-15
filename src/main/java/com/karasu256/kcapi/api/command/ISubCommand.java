package com.karasu256.teamUtils.api.command;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ISubCommand extends ICommand {
    List<ISubCommand> childCommands = new ArrayList<>();

    @Override
    default List<ISubCommand> getSubCommands() {
        return childCommands;
    }

    default void setParentCommand(ICommand parent) {
    }

    default ICommand getParentCommand() {
        return null;
    }

    default void addChildCommand(ISubCommand child) {
        child.setParentCommand(this);
        childCommands.add(child);
    }
}
