package com.karasu256.teamUtils.api.command;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractSubCommand extends AbstractCommand implements ISubCommand {
    private final String name;

    public AbstractSubCommand(String name) {
        super(name);
        this.name = name;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }
}
