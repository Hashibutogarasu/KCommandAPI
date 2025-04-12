package com.karasu256.kcapi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * コマンドの基本的な処理を提供するベースクラス
 * 
 * @author Hashibutogarasu
 * @version 1.0
 */
public class BaseCommand implements CommandExecutor, TabCompleter {
    private static final Map<List<String>, CommandExecutor> subCommands = new HashMap<>();

    /**
     * サブコマンドを登録する
     * 
     * @param subCommandPath サブコマンドのパス
     * @param executor コマンド実行時のハンドラ
     */
    public static void registerSubCommand(List<String> subCommandPath, CommandExecutor executor) {
        subCommands.put(subCommandPath, executor);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        for (Map.Entry<List<String>, CommandExecutor> entry : subCommands.entrySet()) {
            List<String> path = entry.getKey();
            if (path.get(0).equals(args[0])) {
                return entry.getValue().onCommand(sender, command, label, args);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            for (List<String> path : subCommands.keySet()) {
                if (path.get(0).startsWith(args[0])) {
                    completions.add(path.get(0));
                }
            }
        }

        return completions;
    }
}
