package com.karasu256.kcapi.api.command;

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
     * コンストラクタ
     * @param name コマンドの名前
     */
    public AbstractSubCommand(String name) {
        super(name);
        this.name = name;
    }

    /**
     * コマンドの名前を取得します
     * @return コマンドの名前
     */
    @Override
    @NotNull
    public String getName() {
        return this.name;
    }
}
