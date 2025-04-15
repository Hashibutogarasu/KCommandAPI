package com.karasu256.kcapi.api.command;

import java.util.ArrayList;
import java.util.List;

/**
 * コマンドの終端を示す抽象クラスです。
 * このクラスを継承するサブコマンドは子コマンドを持たず、コマンドの終端となります。
 *
 * @author Hashibutogarasu
 * @version 1.0
 */
public abstract class AbstractEndOfSubCommand extends AbstractSubCommand implements IEndOfSubCommand {

    /**
     * コンストラクタ
     * 
     * @param name   コマンドの名前
     * @param parent 親コマンド
     */
    public AbstractEndOfSubCommand(String name, ICommand parent) {
        super(name, parent);
    }

    /**
     * サブコマンドのリストを取得します。
     * 終端コマンドは子コマンドを持たないため、常に空のリストを返します。
     *
     * @return 空のサブコマンドリスト
     */
    @Override
    public List<ISubCommand> getSubCommands() {
        return new ArrayList<>();
    }

    /**
     * 子コマンドを追加しようとしても無視されます。
     * 終端コマンドは子コマンドを持てません。
     *
     * @param child 追加しようとする子コマンド（無視されます）
     */
    @Override
    public void addChildCommand(ISubCommand child) {
        // 終端コマンドは子コマンドを持てないため、このメソッドは何もしません
        AbstractCommand.LOGGER.warning(this.getName() + " は終端コマンドであるため、子コマンドを追加できません。");
    }

    /**
     * このコマンドが終端であるかどうかを返します。
     * 常に true を返します。
     *
     * @return 常に true
     */
    @Override
    public boolean isEndOfCommand() {
        return true;
    }
}
