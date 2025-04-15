package com.karasu256.kcapi.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ネストされたサブコマンドのインターフェースです。
 * ネストされたサブコマンドの候補も表示します。
 * 終端コマンド（IEndOfSubCommand）を子コマンドとして持つことができます。
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface INestedSubCommand extends ISubCommand {

    /**
     * このコマンドが終端であるかどうかを返します。
     * デフォルトでは false を返します。
     *
     * @return 終端コマンドの場合は true、そうでない場合は false
     */
    default boolean isEndOfCommand() {
        return false;
    }

    /**
     * ネストされたサブコマンドのタブ補完を処理します。
     * 子コマンドの候補も表示します。
     * 終端コマンド（IEndOfSubCommand）も適切に処理します。
     *
     * @param sender  コマンド送信者
     * @param command コマンド
     * @param alias   エイリアス
     * @param args    コマンド引数
     * @return タブ補完候補のリスト
     */
    @Override
    default List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias,
            String[] args) {
        // 終端コマンドの場合は独自のタブ補完候補のみを提供
        if (isEndOfCommand()) {
            return getTabCompletions(sender, args);
        }

        // 引数がない場合、すべての子コマンド名を返す
        if (args.length == 0) {
            return getSubCommands().stream()
                    .map(ICommand::getName)
                    .collect(Collectors.toList());
        }        // 最後の引数が空文字列の場合（スペースが打たれた直後）
        boolean lastArgEmpty = args[args.length - 1].isEmpty();

        if (args.length == 1) {
            // 最初の引数が空の場合、すべての子コマンド名を返す
            if (args[0].isEmpty() || lastArgEmpty) {
                return getSubCommands().stream()
                        .map(ICommand::getName)
                        .collect(Collectors.toList());
            }

            // 最初の引数に基づいて子コマンドをフィルタリング
            List<String> candidates = getSubCommands().stream()
                    .map(ICommand::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());

            // 子コマンドの候補がない場合は、独自のタブ補完候補を提供
            return candidates.isEmpty() ? getTabCompletions(sender, args) : candidates;
        } else {
            // 最初の引数に一致する子コマンドを検索
            for (ISubCommand subCmd : getSubCommands()) {
                if (subCmd.getName().equalsIgnoreCase(args[0])) {
                    // 終端コマンドの場合、そのコマンド自身のタブ補完候補を提供
                    if (subCmd instanceof IEndOfSubCommand) {
                        // 残りの引数を渡す
                        String[] newArgs = new String[args.length - 1];
                        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
                        return subCmd.getTabCompletions(sender, newArgs);
                    }

                    // 残りの引数を渡す
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, args.length - 1);
                    return subCmd.onTabComplete(sender, command, alias, newArgs);
                }
            }

            // 一致する子コマンドがない場合は独自の補完候補を提供
            return getTabCompletions(sender, args);
        }
    }
}
