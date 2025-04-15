package com.karasu256.kcapi.api.command;

/**
 * ネストされたサブコマンドの抽象クラスです
 * このクラスを継承して、ネストされたサブコマンドを実装してください
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractNestedSubCommand extends AbstractSubCommand implements INestedSubCommand {
    /**
     * コンストラクタ
     * 
     * @param name   コマンドの名前
     * @param parent 親コマンド
     */
    public AbstractNestedSubCommand(String name, ICommand parent) {
        super(name, parent);
    }
}
