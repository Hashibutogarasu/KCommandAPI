package com.karasu256.kcapi.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseCommandクラスは、サブコマンドを管理し、コマンドの実行とタブ補完を処理します。
 * 
 * @author Hashibutogarasu
 * @version 1.0
 */
public class BaseCommand implements CommandExecutor, TabCompleter {
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private static final String DEFAULT_COMMAND = "help";

    /**
     * BaseCommandのコンストラクタです。
     */
    public BaseCommand(){

    }

    /**
     * サブコマンドを登録します
     * @param name サブコマンドの名前
     * @param subCommand サブコマンド
     */
    public void registerSubCommand(String name, SubCommand subCommand) {
        subCommands.put(name.toLowerCase(), subCommand);
    }

    /**
     * このコマンドが実行されたとき、サブコマンドを探索し、そのサブコマンドを実行します。
     * @param sender コマンド送信者
     * @param command コマンド
     * @param label コマンドのラベル
     * @param args コマンド引数
     * @return コマンドが正常に実行されたかを返します
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String subCommandName = (args.length > 0) ? args[0].toLowerCase() : DEFAULT_COMMAND;

        if (!subCommands.containsKey(subCommandName)) {
            subCommandName = DEFAULT_COMMAND;
        }

        String[] subArgs = new String[args.length - 1];
        if (args.length > 1) {
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        }

        return subCommands.get(subCommandName).execute(sender, subArgs);
    }

    /**
     * サブコマンドのタブ補完機能を実装します
     * @param sender コマンド送信者
     * @param command コマンド
     * @param alias コマンドエイリアス
     * @param args コマンド引数
     * @return コマンドの候補リスト
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>(subCommands.keySet());
            completions.removeIf(s -> !s.startsWith(args[0].toLowerCase()));
            return completions;
        } else if (args.length > 1) {
            String subCommandName = args[0].toLowerCase();
            if (subCommands.containsKey(subCommandName)) {
                String[] subArgs = new String[args.length - 1];
                System.arraycopy(args, 1, subArgs, 0, args.length - 1);
                return subCommands.get(subCommandName).tabComplete(sender, subArgs);
            }
        }

        return new ArrayList<>();
    }

    /**
     * サブコマンドのマップを取得します
     * @return サブコマンドのマップ
     */
    public Map<String, SubCommand> getSubCommands() {
        return subCommands;
    }
}
