package com.karasu256.kcapi.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import com.karasu256.kcapi.api.AbstractMainCommand;
import com.karasu256.kcapi.api.BaseCommand;

/**
 * プラグインのメインコマンドを処理するクラス
 * 
 * このクラスはKCommandAPIプラグインのコマンド構造を管理し、
 * サブコマンドの登録と実行のハンドリングを担当します。
 * ベースコマンドを通じてユーザーからの入力を各サブコマンドに委譲します。
 * 
 * @author Hashibutogarasu
 * @version 1.0
 */
public class MainCommand extends AbstractMainCommand {
    /** プラグインのインスタンス */
    private final JavaPlugin plugin;
    /** コマンド実行を処理するベースコマンド */
    private final CommandExecutor baseCommand;

    /**
     * MainCommandのコンストラクタ
     * 
     * 与えられたプラグインインスタンスを保存し、
     * ベースコマンドを初期化して必要なサブコマンドを登録します。
     * 
     * @param plugin プラグインのインスタンス
     */
    public MainCommand(JavaPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.baseCommand = new BaseCommand();
        registerCommands();
    }

    /**
     * サブコマンドの登録とプラグインコマンドの設定を行います。
     * 
     * ベースコマンドに各サブコマンドを登録し、
     * Bukkitのコマンドシステムにエグゼキュータとタブコンプリータを設定します。
     * plugin.ymlに登録されていないコマンドの場合は警告ログを出力します。
     */
    private void registerCommands() {
        String commandName = "kcapi";
        if (plugin.getCommand(commandName) != null) {
            plugin.getCommand(commandName).setExecutor(baseCommand);
            if (baseCommand instanceof TabCompleter tabCompleter) {
                plugin.getCommand(commandName).setTabCompleter(tabCompleter);
            }
        } else {
            plugin.getLogger().warning("Command '" + commandName + "' not found in plugin.yml!");
        }
    }

    /**
     * コマンドの名前を取得します。
     * @return コマンドの名前
     */
    @Override
    public String getCommandName() {
        return "kcapi";
    }
    
    /**
     * プラグインのインスタンスを取得します。
     * @return プラグインのインスタンス
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
