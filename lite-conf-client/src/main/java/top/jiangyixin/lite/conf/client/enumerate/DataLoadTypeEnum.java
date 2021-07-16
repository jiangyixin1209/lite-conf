package top.jiangyixin.lite.conf.client.enumerate;

/**
 * 数据加载方式枚举
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 下午4:06
 */
public enum DataLoadTypeEnum {
    /** 本地加载 **/
    LOCAL("local"),
    /** 远程加载 **/
    REMOTE("remote");
    private final String value;
    DataLoadTypeEnum(String value) {
        this.value = value;
    }
    public DataLoadTypeEnum loadOfValue(String value) {
        for (DataLoadTypeEnum item: DataLoadTypeEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }
}
