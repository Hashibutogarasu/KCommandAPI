# カラス印のコマンドAPI

このプラグインは導入することでコマンド、サブコマンドを簡単に実装することができます

このプラグインはAPIなので、github packagesからインポートし、それを別のプロジェクトで使うことを前提にしています。

## インストール方法

### Maven

```xml
<dependency>
  <groupId>com.karasu256</groupId>
  <artifactId>kcommandapi</artifactId>
  <version>0.0.1.46</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.karasu256:kcommandapi:0.0.1.46'
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

### 終端コマンドの作成

終端コマンドは子コマンドを持たない最終ノードのコマンドです。このコマンドはタブ補完時に独自のパラメータ候補のみを表示します。

```java
public class MyEndCommand extends AbstractEndOfSubCommand {
    
    public MyEndCommand(ICommand parent) {
        super("end", parent);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("終端コマンドが実行されました");
        return true;
    }
    
    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        // このコマンド独自のタブ補完候補を返す
        List<String> completions = new ArrayList<>();
        completions.add("option1");
        completions.add("option2");
        return completions;
    }
}

// ネストされたサブコマンドに終端コマンドを追加
MyNestedSubCommand nestedSubCommand = new MyNestedSubCommand(mySubCommand);
nestedSubCommand.addChildCommand(new MyEndCommand(nestedSubCommand));
mySubCommand.addChildCommand(nestedSubCommand);
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