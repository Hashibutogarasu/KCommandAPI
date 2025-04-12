package com.karasu256.kcapi.api;

import org.bukkit.command.CommandSender;
import net.md_5.bungee.api.ChatColor;

import java.util.List;

/**
 * SubCommandインターフェースは、サブコマンドの実行とタブ補完を処理するためのメソッドを定義します。
 * <p>
 * このインターフェースを実装することで、特定のサブコマンドの動作を定義できます。
 * </p>
 * 
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface SubCommand {
    /**
     * コマンドを実行します。
     * @param sender コマンドの送信者　コマンドブロックからの実行では実行者を取得するとnullになります
     * @param args コマンドの引数です
     * @return コマンドが正常に実行されたかを返します
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * コマンドのタブ補完を実装します。
     * @param sender コマンドの送信者
     * @param args コマンドの引数
     * @return 補完のリスト
     */
    List<String> tabComplete(CommandSender sender, String[] args);

    /**
     * コマンドの使い方を表示します。実装されていない場合は登録されていないメッセージを表示します
     * @param sender
     */
    default void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "このサブコマンドの使い方は登録されていません。");
    }

    /**
     * このコマンドのヘルパークラスを取得します。
     * @return サブコマンドヘルパーインターフェース
     */
    default SubCommandHelper getHelper() {
        return this::showHelp;
    }

    /**
     * サブコマンドのヘルパーインターフェースです。
     */
    @FunctionalInterface
    interface SubCommandHelper {
        /**
         * サブコマンドの使い方を表示します。
         * @param sender コマンド送信者
         */
        void showHelp(CommandSender sender);
    }
}
