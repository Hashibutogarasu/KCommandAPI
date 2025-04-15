package com.karasu256.kcapi;

import org.bukkit.plugin.java.JavaPlugin;

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
}
