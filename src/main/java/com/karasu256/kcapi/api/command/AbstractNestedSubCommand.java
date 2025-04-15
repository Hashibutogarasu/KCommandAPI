package com.karasu256.teamUtils.api.command;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNestedSubCommand extends AbstractSubCommand implements INestedSubCommand {
    private final String name;
    private final ICommand parentCommand;

    public AbstractNestedSubCommand(String name, ICommand parent) {
        super(name);
        this.name = name;
        this.parentCommand = parent;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public ICommand getParentCommand() {
        return parentCommand;
    }

    @Override
    public List<ISubCommand> getSubCommands() {
        return new ArrayList<>();
    }
}
