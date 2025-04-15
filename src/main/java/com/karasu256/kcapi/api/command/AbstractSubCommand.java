package com.karasu256.kcapi.api.command;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

/**
 * サブコマンドの抽象クラスです
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractSubCommand extends AbstractCommand implements ISubCommand {
    /**
     * コマンドの名前
     */
    private final String name;

    /**
     * 親コマンド
     */
    private final ICommand parent;

    /**
     * コンストラクタ
     * 
     * @param name   コマンドの名前
     * @param parent 親コマンド
     */
    public AbstractSubCommand(String name, ICommand parent) {
        super(name);
        this.name = name;
        this.parent = parent;
    }

    /**
     * コマンドの名前を取得します
     * 
     * @return コマンドの名前
     */
    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    public ICommand getParentCommand() {
        return this.parent;
    }

    @Override
    public List<ISubCommand> getSubCommands() {
        return new ArrayList<>();
    }
}
