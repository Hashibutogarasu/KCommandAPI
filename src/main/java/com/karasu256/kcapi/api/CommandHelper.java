package com.karasu256.kcapi.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.karasu256.kcapi.commands.MainCommand;

/**
 * コマンドの登録と管理を簡略化するヘルパークラス
 * 
 * @author Hashibutogarasu
 * @version 1.0
 */
public class CommandHelper {
    /**
     * コマンドのヘルパークラスのインスタンス化を防ぐためのコンストラクタ
     */
    protected CommandHelper() {
    }

    /**
     * コマンドを登録するメソッド
     * 
     * @param <T>          MainCommandを拡張したクラス型
     * @param commandClass コマンドを登録するクラス
     * @param subCommands  サブコマンドのクラスリスト
     * @param executor     コマンド実行時のハンドラ
     */
    public static <T extends MainCommand> void register(Class<T> commandClass, List<Class<T>> subCommands,
            Consumer<String[]> executor) {
        // サブコマンドの階層構造を構築
        List<String> commandPath = new ArrayList<>();
        for (Class<T> subCommand : subCommands) {
            try {
                T instance = subCommand.getDeclaredConstructor().newInstance();
                commandPath.add(instance.getCommandName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BaseCommandにサブコマンドを登録
        BaseCommand.registerSubCommand(commandPath, (sender, command, label, args) -> {
            // サブコマンド部分を除いた引数を渡す
            String[] processedArgs = new String[args.length - commandPath.size()];
            System.arraycopy(args, commandPath.size(), processedArgs, 0, processedArgs.length);
            executor.accept(processedArgs);
            return true;
        });
    }
}
