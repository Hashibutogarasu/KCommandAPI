package com.karasu256.kcapi.example;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.karasu256.kcapi.api.CommandHelper;
import com.karasu256.kcapi.commands.MainCommand;

/**
 * KCommandAPIの使用例を示すサンプルコマンドクラス
 */
public class ExampleCommand extends MainCommand {

    /**
     * ExampleCommandのコンストラクタ
     * 
     * @param plugin プラグインのインスタンス
     */
    public ExampleCommand(JavaPlugin plugin) {
        super(plugin);
        registerExampleCommands();
    }

    /**
     * 例のコマンドを登録します
     */
    private void registerExampleCommands() {
        CommandHelper.register(
                ExampleCommand.class,
                List.of(),
                args -> {
                    if (args.length > 0) {
                        // 最後の引数を数値として処理
                        try {
                            int value = Integer.parseInt(args[0]);
                            // コマンドの処理をここに実装
                            getPlugin().getLogger().info("Example command executed with value: " + value);
                        } catch (NumberFormatException e) {
                            getPlugin().getLogger().warning("Invalid number format: " + args[0]);
                        }
                    }
                });
    }

    @Override
    public String getCommandName() {
        return "kcapiexample";
    }
}
