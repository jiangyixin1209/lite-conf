package top.jiangyixin.lite.conf.client.core;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 配置信息集合
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午5:50
 */
@Data
public class Conf {
    private String id;
    private String env;
    private String system;
    private String config;
    private String key;
    private Object value;
    private String desc;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
