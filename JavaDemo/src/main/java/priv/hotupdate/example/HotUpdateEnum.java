package priv.hotupdate.example;

/**
 * 新增枚举无法通过Instrument热更。调用redefinition会抛出 class redefinition failed: attempted to change the schema异常
 */
public enum HotUpdateEnum {
    One,
    Two,
    Three,
}
