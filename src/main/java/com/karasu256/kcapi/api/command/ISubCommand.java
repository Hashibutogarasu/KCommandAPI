package com.karasu256.kcapi.api.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * サブコマンドのインターフェースです。
 * 親コマンドの下に位置するサブコマンドを表します。
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface ISubCommand extends ICommand {
    /**
     * このサブコマンドの親コマンドを取得します。
     * 
     * @return 親コマンド
     */
    default ICommand getParentCommand() {
        return null;
    }

    /**
     * このサブコマンドの親コマンドを設定します。
     * 
     * @param parentCommand 親コマンド
     */
    void setParentCommand(ICommand parentCommand);

    /**
     * 完全なコマンドパスを取得します。
     * 
     * @return 親コマンドから始まる完全なコマンドパス（例：parent subcommand）
     */
    default String getFullPath() {
        ICommand parent = getParentCommand();
        if (parent == null) {
            return getName();
        }

        if (parent instanceof ISubCommand) {
            return ((ISubCommand) parent).getFullPath() + " " + getName();
        }

        return parent.getName() + " " + getName();
    }

    /**
     * コマンドを実行します
     *
     * @param sender       Source object which is executing this command
     * @param commandLabel The alias of the command used
     * @param args         All arguments passed to the command, split via ' '
     * @return コマンドが正常に実行されたかを返す
     */
    default boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }
}
