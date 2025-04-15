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
     * 子コマンドのリストです
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

        // 最後の引数が空文字列の場合（スペースが打たれた直後）
        boolean lastArgEmpty = args[args.length - 1].isEmpty();

        if (args.length == 1) {
            // 最初の引数が空の場合、全サブコマンド名を返す
            if (args[0].isEmpty()) {
                return getSubCommands().stream()
                        .map(ICommand::getName)
                        .collect(Collectors.toList());
            }

            // 最初の引数に基づいてサブコマンドをフィルタリング
            List<String> candidates = getSubCommands().stream()
                    .map(ICommand::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());

            // サブコマンド候補がない場合は、独自のタブ補完候補を提供
            return candidates.isEmpty() ? getTabCompletions(sender, args) : candidates;
        } else {
            // 最初の引数に一致するサブコマンドを検索
            for (ISubCommand subCmd : getSubCommands()) {
                if (subCmd.getName().equalsIgnoreCase(args[0])) {
                    // 残りの引数を渡す
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                    // 最後の引数が空白の場合（例："/kteam config gui "）で、
                    // サブコマンドが子コマンドを持っている場合は、その子コマンドの候補を表示
                    if (lastArgEmpty) {
                        // 現在のパスに一致するサブコマンドを見つける
                        ISubCommand currentSubCmd = subCmd;
                        boolean pathMatches = true;

                        // 途中のパスを検証（例：config → gui と辿る）
                        for (int i = 0; i < newArgs.length - 1; i++) {
                            boolean found = false;
                            for (ISubCommand childCmd : currentSubCmd.getSubCommands()) {
                                if (childCmd.getName().equalsIgnoreCase(newArgs[i])) {
                                    currentSubCmd = childCmd;
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                pathMatches = false;
                                break;
                            }
                        }

                        // パスが一致し、最後の引数が空なら子コマンドの候補を返す
                        if (pathMatches && newArgs[newArgs.length - 1].isEmpty()) {
                            return currentSubCmd.getSubCommands().stream()
                                    .map(ICommand::getName)
                                    .collect(Collectors.toList());
                        }
                    }

                    // サブコマンドのタブ補完処理を呼び出す前に現在のコマンドパスをログに出力
                    StringBuilder commandPath = new StringBuilder(subCmd.getName());
                    for (int i = 0; i < newArgs.length - 1; i++) {
                        commandPath.append(" ").append(newArgs[i]);
                    }
                    return subCmd.onTabComplete(sender, command, alias, newArgs);
                }
            }

            // 一致するサブコマンドがない場合は独自の補完候補を提供
            return getTabCompletions(sender, args);
        }
    }
}
