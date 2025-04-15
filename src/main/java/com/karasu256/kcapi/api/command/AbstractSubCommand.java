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
    private ICommand parent;

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
    public void addSubCommand(ISubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    @Override
    public List<ISubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public void setParentCommand(ICommand parentCommand) {
        this.parent = parentCommand;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias,
            @NotNull String[] args) throws IllegalArgumentException {
        for (String arg : args) {
            if (arg.isEmpty()) {
                return new ArrayList<>();
            }
        }
        
        return this.getSubCommands().stream()
                .filter(subCommand -> subCommand.getName().startsWith(args[args.length - 1]))
                .map(subCommand -> subCommand.getName())
                .toList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args.length == 0) {
            return this.execute(sender, label, args);
        }

        String subCommandName = args[0];
        var foundCommand = this.getSubCommands().stream()
                .filter(subCommand -> subCommand.getName().toLowerCase().startsWith(subCommandName.toLowerCase()))
                .findFirst()
                .orElse(null);

        if (foundCommand != null) {
            return foundCommand.execute(sender, label, args);
        }

        // サブコマンドが見つからない場合は自身を実行
        return this.execute(sender, label, args);
    }
}
