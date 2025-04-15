package com.karasu256.kcapi.api.command;

import java.util.ArrayList;
import java.util.List;

/**
 * ネストされたサブコマンドの抽象クラスです
 * このクラスを継承して、ネストされたサブコマンドを実装してください
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractNestedSubCommand extends AbstractSubCommand implements INestedSubCommand {
    private final List<ISubCommand> subCommands;

    /**
     * コンストラクタ
     * 
     * @param name   コマンドの名前
     * @param parent 親コマンド
     */
    public AbstractNestedSubCommand(String name, ICommand parent) {
        super(name, parent);
        this.subCommands = new ArrayList<>();
    }

    @Override
    public void addSubCommand(ISubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    @Override
    public List<ISubCommand> getSubCommands() {
        return this.subCommands;
    }
}
