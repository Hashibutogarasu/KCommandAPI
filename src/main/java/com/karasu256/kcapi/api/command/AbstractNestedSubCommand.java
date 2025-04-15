package com.karasu256.kcapi.api.command;

import org.jetbrains.annotations.NotNull;

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
    private final String name;
    private final ICommand parentCommand;

    /**
     * コンストラクタ
     * @param name コマンドの名前
     * @param parent 親コマンド
     */
    public AbstractNestedSubCommand(String name, ICommand parent) {
        super(name);
        this.name = name;
        this.parentCommand = parent;
    }

    /**
     * コマンドの名前を取得します
     * @return コマンドの名前
     */
    @Override
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * 親コマンドを取得します
     * @return 親コマンド
     */
    @Override
    public ICommand getParentCommand() {
        return parentCommand;
    }

    /**
     * サブコマンドを取得します。上書きしない場合はこのメソッドが呼び出されます
     * @return サブコマンドのリスト
     */
    @Override
    public List<ISubCommand> getSubCommands() {
        return new ArrayList<>();
    }
}
