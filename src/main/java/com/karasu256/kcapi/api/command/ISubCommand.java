package com.karasu256.kcapi.api.command;

import java.util.ArrayList;
import java.util.List;

/**
 * サブコマンドのインターフェースです
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface ISubCommand extends ICommand {
    /**
     * 子コマンドのリストです
     */
    List<ISubCommand> childCommands = new ArrayList<>();

    /**
     * サブコマンドのリストを取得します
     * @return サブコマンドのリスト
     */
    @Override
    default List<ISubCommand> getSubCommands() {
        return childCommands;
    }

    /**
     * 親コマンドを設定します
     * @param parent 親コマンド
     */
    default void setParentCommand(ICommand parent) {
    }

    /**
     * 親コマンドを取得します。デフォルトではnullが返されます
     * @return 親コマンド
     */
    default ICommand getParentCommand() {
        return null;
    }

    /**
     * 子コマンドを追加します
     * @param child 子コマンド
     */
    default void addChildCommand(ISubCommand child) {
        child.setParentCommand(this);
        childCommands.add(child);
    }
}
