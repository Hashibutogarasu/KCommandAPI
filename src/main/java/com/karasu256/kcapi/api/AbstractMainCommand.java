package com.karasu256.kcapi.api;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * プラグインのメインコマンドの基底クラス
 * このクラスを継承して各プラグインのメインコマンドを実装します
 */
public abstract class AbstractMainCommand {

    /**
     * コマンドのコンストラクタ
     * 
     * @param plugin プラグインのインスタンス
     */
    public AbstractMainCommand(JavaPlugin plugin) {
    }

    /**
     * コマンドの名前を取得します
     * 
     * @return コマンドの名前
     */
    public abstract String getCommandName();
}
