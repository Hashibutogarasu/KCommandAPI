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
    boolean execute(CommandSender sender, String[] args);

    List<String> tabComplete(CommandSender sender, String[] args);

    default void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "このsubcommand情報は登録されていません。");
    }

    default SubCommandHelper getHelper() {
        return this::showHelp;
    }

    @FunctionalInterface
    interface SubCommandHelper {
        void showHelp(CommandSender sender);
    }
}
