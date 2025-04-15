package com.karasu256.kcapi.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * コマンドの抽象クラスです
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractCommand extends Command implements ICommand {
    /**
     * このコマンドのロガー変数です 外部から参照できます
     */
    public static final Logger LOGGER = Logger.getLogger(AbstractCommand.class.getName());

    /**
     * コマンドの名前です
     */
    private final String name;

    /**
     * サブコマンドのリストです
     */
    private final List<ISubCommand> subCommands = new ArrayList<>();

    /**
     * コンストラクタ
     *
     * @param name        コマンドの名前
     * @param subCommands サブコマンドのリスト
     */
    public AbstractCommand(String name, ISubCommand... subCommands) {
        super(name);
        this.name = name;
        this.subCommands.addAll(Arrays.stream(subCommands).toList());
    }


    /**
     * サブコマンドを追加します
     *
     * @param subCommand 追加するサブコマンド
     */
    public void addSubCommand(ISubCommand subCommand) {
        if (!this.subCommands.contains(subCommand)) {
            this.subCommands.add(subCommand);
        } else {
            LOGGER.warning("Subcommand already exists or subCommands list is null.");
        }
    }

    /**
     * サブコマンドのリストを取得します
     *
     * @return サブコマンドのリスト
     */
    @Override
    public List<ISubCommand> getSubCommands() {
        return subCommands;
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

    /**
     * コマンドを実行します
     *
     * @param sender       Source object which is executing this command
     * @param commandLabel The alias of the command used
     * @param args         All arguments passed to the command, split via ' '
     * @return コマンドが正常に実行されたかを返す
     */
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    /**
     * コマンドを実行します
     * <p>
     * サブコマンドがある場合は、そのサブコマンドが実行されます。
     * ネストされたサブコマンドにも対応しています。
     * </p>
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return コマンドが正常に実行されたかを返す
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (args.length == 0) {
            return execute(sender, label, args);
        }

        for (ISubCommand subCmd : getSubCommands()) {
            if (subCmd.getName().equalsIgnoreCase(args[0])) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                if (newArgs.length == 0) {
                    return subCmd.onCommand(sender, command, label, newArgs);
                }

                for (ISubCommand childCmd : subCmd.getSubCommands()) {
                    if (childCmd.getName().equalsIgnoreCase(newArgs[0])) {
                        String[] grandChildArgs = new String[newArgs.length - 1];
                        System.arraycopy(newArgs, 1, grandChildArgs, 0, newArgs.length - 1);

                        if (grandChildArgs.length == 0) {
                            return childCmd.onCommand(sender, command, label, grandChildArgs);
                        }

                        return childCmd.onCommand(sender, command, label, grandChildArgs);
                    }
                }

                return subCmd.onCommand(sender, command, label, newArgs);
            }
        }

        return execute(sender, label, args);
    }
}