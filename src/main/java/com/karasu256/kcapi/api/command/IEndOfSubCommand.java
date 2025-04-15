package com.karasu256.kcapi.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * コマンドの終端を示すインターフェースです。
 * このインターフェースを実装するサブコマンドは子コマンドを持たず、コマンドの終端となります。
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface IEndOfSubCommand extends ISubCommand {

    /**
     * このコマンドが終端であるかどうかを返します。
     * このインターフェースを実装するサブコマンドは常に true を返します。
     *
     * @return 常に true
     */
    default boolean isEndOfCommand() {
        return true;
    }

    /**
     * 終端コマンドのタブ補完を処理します。
     * 終端コマンドは子コマンドを持たないため、独自のタブ補完のみを提供します。
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
        // 終端コマンドは子コマンドを持たないため、独自のタブ補完候補のみを返す
        return getTabCompletions(sender, args);
    }
}
