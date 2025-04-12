package com.karasu256.kcapi;

import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import com.karasu256.kcapi.api.BaseCommand;

/**
 * このプラグインのメインクラスです。初期化やコマンドの登録を行います。
 * 
 * @author Hashibutogarasu
 * @version 1.0
 */
public final class KarasuCommandAPI extends JavaPlugin {
    private static KarasuCommandAPI instance;

    /**
     * プラグインが有効化された時に呼ばれる関数です。
     * 
     * @author Hashibutogarasu
     */
    @Override
    public void onEnable() {
        instance = this;
    }

    /**
     * このプラグインが無効化された時に実行される関数です。
     * メモリの開放などを行います。
     * 
     * @author Hashibutogarasu
     */
    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * このプラグインをインスタンスとして取得します
     * 
     * @return プラグインのインスタンス
     * @author Hashibutogarasu
     */
    public static KarasuCommandAPI getInstance() {
        return instance;
    }

    /**
     * コマンドを登録し、BaseCommandインスタンスを返します
     * 
     * @param commandName plugin.ymlに登録されているコマンド名
     * @return 作成されたBaseCommandインスタンス
     * 
     * @author Hashibutogarasu
     */
    public BaseCommand registerCommand(String commandName) {
        BaseCommand baseCommand = new BaseCommand();
        if (this.getCommand(commandName) != null) {
            this.getCommand(commandName).setExecutor(baseCommand);
            if (baseCommand instanceof TabCompleter) {
                this.getCommand(commandName).setTabCompleter((TabCompleter) baseCommand);
            }
            return baseCommand;
        }
        getLogger().warning("Command '" + commandName + "' not found in plugin.yml!");
        return null;
    }
}
