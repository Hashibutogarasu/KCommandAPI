# カラス印のコマンドAPI

このプラグインは導入することでコマンド、サブコマンドを簡単に実装することができます

このプラグインはAPIなので、github packagesからインポートし、それを別のプロジェクトで使うことを前提にしています。

## インストール方法

### Maven

```xml
<dependency>
  <groupId>com.karasu256</groupId>
  <artifactId>kcommandapi</artifactId>
  <version>0.0.1.42</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.karasu256:kcommandapi:0.0.1.42'
```

## 使用方法

### 基本的なコマンドの作成

```java
public class MyCommand extends AbstractCommand {
    
    public MyCommand() {
        super("mycommand");
        // コマンドの説明などを設定
        setDescription("マイコマンドの説明");
        setUsage("/mycommand");
    }
    
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        sender.sendMessage("コマンドが実行されました");
        return true;
    }
}
```

### サブコマンドの作成と追加

```java
public class MySubCommand extends AbstractSubCommand {
    
    public MySubCommand(ICommand parent) {
        super("sub", parent);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("サブコマンドが実行されました");
        return true;
    }
}

// メインコマンドにサブコマンドを追加
MyCommand myCommand = new MyCommand();
myCommand.addSubCommand(new MySubCommand(myCommand));
```

### ネストされたサブコマンドの作成

```java
public class MyNestedSubCommand extends AbstractNestedSubCommand {
    
    public MyNestedSubCommand(ICommand parent) {
        super("nested", parent);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("ネストされたサブコマンドが実行されました");
        return true;
    }
}

// サブコマンドにネストされたサブコマンドを追加
MySubCommand mySubCommand = new MySubCommand(myCommand);
mySubCommand.addSubCommand(new MyNestedSubCommand(mySubCommand));
myCommand.addSubCommand(mySubCommand);
```

### プラグインへの登録

```java
public class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        MyCommand myCommand = new MyCommand();
        // サブコマンドの追加
        myCommand.addSubCommand(new MySubCommand(myCommand));
        
        // コマンドを登録
        getServer().getCommandMap().register("myplugin", myCommand);
    }
}
```

## 注意事項

- サブコマンドを追加する前に、必ず親コマンドのインスタンスを作成してください
- ネストされたサブコマンドは、必ず親のサブコマンドのインスタンスを作成した後に追加してください