package com.karasu256.kcapi.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * コマンドのインターフェースです
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface ICommand extends CommandExecutor, TabCompleter {
    /**
     * 子コマンドのリスト
     */
    List<ISubCommand> subCommand = new ArrayList<>();

    /**
     * サブコマンドのリストを取得します。
     * 
     * @return サブコマンドのリスト
     */
    default List<ISubCommand> getSubCommands() {
        return subCommand;
    }

    /**
     * コマンドの名前を取得します
     * 
     * @return コマンド名
     */
    String getName();

    /**
     * コマンドに対するタブ補完候補を提供します。
     * サブコマンドやその子コマンドが存在しない場合や、
     * 最終階層のコマンドに対する補完候補を提供する場合に使用します。
     * 
     * @param sender コマンド送信者
     * @param args   コマンド引数
     * @return タブ補完候補のリスト、候補がない場合は空のリスト
     */
    default List<String> getTabCompletions(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias,
            String[] args);
}
