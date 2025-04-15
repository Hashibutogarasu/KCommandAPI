package com.karasu256.kcapi.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * サブコマンドの抽象クラスです
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractSubCommand extends AbstractCommand implements ISubCommand {
    /**
     * サブコマンドのリスト
     */
    private final List<ISubCommand> subCommands = new ArrayList<>();

    /**
     * コマンドの名前
     */
    private final String name;

    /**
     * 親コマンド
     */
    private final ICommand parent;

    /**
     * コンストラクタ
     * 
     * @param name   コマンドの名前
     * @param parent 親コマンド
     */
    public AbstractSubCommand(String name, ICommand parent) {
        super(name);
        this.name = name;
        this.parent = parent;
    }

    /**
     * コマンドの名前を取得します
     * 
     * @return コマンドの名前
     */
    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    public ICommand getParentCommand() {
        return this.parent;
    }

    @Override
    public List<ISubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public void setParentCommand(ICommand parentCommand) {
        // 既に親コマンドが設定されているため、何もしない
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            String[] args) {
        if (args.length == 0) {
            // 引数がない場合は、このサブコマンド自体を実行
            return this.onCommand(sender, command, label, args);
        }

        // 次のサブコマンドを探す
        String subCommandName = args[0];
        for (ISubCommand subCommand : this.subCommands) {
            if (subCommand.getName().equalsIgnoreCase(subCommandName)) {
                // 残りの引数を渡す
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                // 子サブコマンドに処理を委譲
                return subCommand.onCommand(sender, command, label, newArgs);
            }
        }

        // 一致するサブコマンドがない場合、このサブコマンド自体で処理
        return this.onCommand(sender, command, label, args);
    }
}
