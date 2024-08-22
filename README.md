# BebraLib
MC 1.16.5+

## Usage example:
### Commands:
> default:

Main.java
```java
public class Main extends JavaPlugin{
    @Override
    public void onEnable(){
        getCommand("test").setExecutor((cs, cmd, l, args) -> {
            if (cs instanceof Player){
                cs.sendMessage("test");
                return true;
            }

            return true;
        });
    }
}
```
plugin.yml
```yaml
...bla bla
commands:
  test:
    usage: test
```

> Using BebraLib:

Main.java
```java
public class Main extends EzPlugin{

    public final CommandManager commandManager = new CommandManager(this);

    @Override
    public void onEnable(){
        new EzCommand("test").onCommand((cs, l, args) -> {
            if (player()) cs.sendMessage("test");
        }).register(commandManager); 
    }
}
```

### Installing:

>create copy and do "mvn install"

```xml
      <dependency>
          <groupId>by.likebebras</groupId>
          <artifactId>BebraLib</artifactId>
          <version>1.0</version>
          <scope>provided</scope>
      </dependency>
```
