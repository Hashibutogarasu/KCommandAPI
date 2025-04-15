# カラス印のコマンドAPI 
[![Paper](https://img.shields.io/badge/Paper-1.21.3-blue.svg)](https://papermc.io/)

このAPIを使用することで、階層構造を持つコマンドとサブコマンドを簡単に実装することができます。タブ補完機能も含まれており、コマンド開発をスムーズに行えます。

このプラグインはAPIとして設計されており、github packagesからインポートして別のプロジェクトで使用することを前提としています。

## インストール方法

### Maven

```xml
<dependency>
  <groupId>com.karasu256</groupId>
  <artifactId>kcommandapi</artifactId>
  <version>0.0.1.60</version>
</dependency>
```

### Gradle (Groovy DSL)

```groovy
implementation 'com.karasu256:kcommandapi:0.0.1.60'
```

### Gradle (Kotlin DSL)

```kotlin
implementation("com.karasu256:kcommandapi:0.0.1.60")
```

### build.gradle

```groovy
repositories {
    maven {
        name = "GithubPackages"
        url = uri("https://maven.pkg.github.com/karasu256/KCommandAPI")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'com.karasu256:kcommandapi:0.0.1.60'
}
```

## 使用方法

### 基本的なコマンドの作成

最初に、メインコマンドを作成します。これはプレイヤーが実行する基本コマンドです。

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

メインコマンドの後に実行されるサブコマンドを作成します。例: `/mycommand sub`

```java
public class MySubCommand extends AbstractSubCommand {
    
    public MySubCommand(ICommand parent) {
        super("sub", parent);
    }
    
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        sender.sendMessage("サブコマンドが実行されました");
        return true;
    }
}

// メインコマンドにサブコマンドを追加
MyCommand myCommand = new MyCommand();
myCommand.addSubCommand(new MySubCommand(myCommand));
```

### ネストされたサブコマンドの作成

サブコマンドの下に更に階層を持つネストされたサブコマンドを作成できます。例: `/mycommand sub nested`

```java
public class MyNestedSubCommand extends AbstractNestedSubCommand {
    
    public MyNestedSubCommand(ICommand parent) {
        super("nested", parent);
    }
    
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
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

終端コマンドは子コマンドを持たない最終ノードのコマンドです。このコマンドはタブ補完時に独自のパラメータ候補のみを表示します。例: `/mycommand sub nested end [option1|option2]`

```java
public class MyEndCommand extends AbstractEndOfSubCommand {
    
    public MyEndCommand(ICommand parent) {
        super("end", parent);
    }
    
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
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
nestedSubCommand.addSubCommand(new MyEndCommand(nestedSubCommand));
mySubCommand.addSubCommand(nestedSubCommand);
```

### コマンド構造の設計例

以下のような階層構造を作成できます:

```
/mycommand                  - メインコマンド
├── /mycommand sub          - サブコマンド
│   ├── /mycommand sub nested     - ネストされたサブコマンド
│   │   └── /mycommand sub nested end  - 終端コマンド
│   └── /mycommand sub other      - 別のサブコマンド
└── /mycommand info        - 別のサブコマンド
```

### プラグインへの登録

作成したコマンドをプラグインに登録します。

```java
public class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // メインコマンドの作成
        MyCommand myCommand = new MyCommand();
        
        // サブコマンドの作成と追加
        MySubCommand mySubCommand = new MySubCommand(myCommand);
        myCommand.addSubCommand(mySubCommand);
        
        // ネストされたサブコマンドの追加
        MyNestedSubCommand nestedSubCommand = new MyNestedSubCommand(mySubCommand);
        mySubCommand.addSubCommand(nestedSubCommand);
        
        // 終端コマンドの追加
        nestedSubCommand.addSubCommand(new MyEndCommand(nestedSubCommand));
        
        // コマンドを登録
        getServer().getCommandMap().register("myplugin", myCommand);
    }
}
```

## 注意事項

- サブコマンドを追加する前に、必ず親コマンドのインスタンスを作成してください
- ネストされたサブコマンドは、必ず親のサブコマンドのインスタンスを作成した後に追加してください
- 終端コマンドはサブコマンドを持てませんが、独自のタブ補完候補を提供できます
- `execute` メソッドを使用してコマンドの実行処理を実装してください (`onCommand` ではなく)

## 高度な使用例

### タブ補完の拡張

サブコマンドに対して、動的なタブ補完を実装できます。

```java
@Override
public List<String> getTabCompletions(CommandSender sender, String[] args) {
    if (args.length == 1) {
        List<String> options = new ArrayList<>();
        options.add("create");
        options.add("delete");
        options.add("list");
        return options.stream()
                .filter(option -> option.startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
    } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
        // 「delete」の後には存在するアイテム名を表示
        return getAllItemNames().stream()
                .filter(name -> name.startsWith(args[1].toLowerCase()))
                .collect(Collectors.toList());
    }
    return new ArrayList<>();
}
```

## ライセンス
このレポジトリはCC-BY-4.0 licenseです。
