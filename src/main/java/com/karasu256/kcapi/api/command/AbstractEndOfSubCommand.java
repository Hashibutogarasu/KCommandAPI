package com.karasu256.kcapi.api.command;

/**
 * 終端サブコマンドの基本的な実装を提供する抽象クラスです。
 * このクラスを継承することで、簡単に終端サブコマンドを実装できます。
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractEndOfSubCommand extends AbstractSubCommand implements IEndOfSubCommand {
    /**
     * 親コマンド
     */
    private ICommand parentCommand;

    /**
     * 終端サブコマンドを初期化します。
     * 
     * @param name サブコマンド名
     */
    public AbstractEndOfSubCommand(String name, ISubCommand subCommand) {
        super(name, subCommand);
        this.parentCommand = subCommand;
    }

    /**
     * 終端サブコマンドを初期化します。
     * 
     * @param name サブコマンド名
     */
    public AbstractEndOfSubCommand(String name, ICommand subCommand) {
        super(name, subCommand);
        this.parentCommand = subCommand;
    }

    /**
     * 親コマンドを設定します。
     * 
     * @param parentCommand 親コマンド
     */
    @Override
    public void setParentCommand(ICommand parentCommand) {
        this.parentCommand = parentCommand;
    }

    /**
     * 親コマンドを取得します。
     *
     * @return 親コマンド
     */
    @Override
    public ICommand getParentCommand() {
        return this.parentCommand;
    }
}
