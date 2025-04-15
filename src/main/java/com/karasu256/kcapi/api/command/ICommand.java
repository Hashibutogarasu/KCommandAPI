package com.karasu256.kcapi.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    default List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias,
            String[] args) {
        // 引数がない場合、全サブコマンド名を返す
        if (args.length == 0) {
            return getSubCommands().stream()
                    .map(ICommand::getName)
                    .collect(Collectors.toList());
        }

        // 最初の引数に一致するサブコマンドを探す
        if (args.length >= 1) {
            String firstArg = args[0];
            
            // 最初の引数が空の場合、全サブコマンド名を返す
            if (firstArg.isEmpty()) {
                return getSubCommands().stream()
                        .map(ICommand::getName)
                        .collect(Collectors.toList());
            }
            
            // 最初の引数に基づいてサブコマンドをフィルタリング
            if (args.length == 1) {
                List<String> candidates = getSubCommands().stream()
                        .map(ICommand::getName)
                        .filter(name -> name.toLowerCase().startsWith(firstArg.toLowerCase()))
                        .collect(Collectors.toList());
                
                // サブコマンド候補がない場合は、独自のタブ補完候補を提供
                return candidates.isEmpty() ? getTabCompletions(sender, args) : candidates;
            }
            
            // 適切なサブコマンドを特定し、残りの引数を渡す
            for (ISubCommand subCmd : getSubCommands()) {
                if (subCmd.getName().equalsIgnoreCase(firstArg)) {
                    // 最初の引数を取り除いた新しい引数配列を作成
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, args.length - 1);
                    
                    // サブコマンドが終端コマンドの場合
                    if (subCmd instanceof IEndOfSubCommand endOfSubCommand && 
                        (endOfSubCommand.isEndOfCommand() && newArgs.length > 0)) {
                        return subCmd.getTabCompletions(sender, newArgs);
                    }
                    
                    // サブコマンドのタブ補完処理を呼び出す
                    return subCmd.onTabComplete(sender, command, alias, newArgs);
                }
            }
            
            // 適切なサブコマンドが見つからない場合
            return getTabCompletions(sender, args);
        }
        
        return new ArrayList<>();
    }
}
