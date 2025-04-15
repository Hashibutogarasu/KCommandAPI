package com.karasu256.kcapi.api.command;

/**
 * ネストされたサブコマンドのインターフェースです。
 * ネストされたサブコマンドの候補も表示します。
 * 終端コマンド（IEndOfSubCommand）を子コマンドとして持つことができます。
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public interface INestedSubCommand extends ISubCommand {
    /**
     * このコマンドが終端であるかどうかを返します。
     * デフォルトでは false を返します。
     *
     * @return 終端コマンドの場合は true、そうでない場合は false
     */
    default boolean isEndOfCommand() {
        return false;
    }
}
