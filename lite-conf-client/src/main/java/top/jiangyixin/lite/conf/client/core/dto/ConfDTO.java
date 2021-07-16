package top.jiangyixin.lite.conf.client.core.dto;

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
public class ConfDTO {
    private String id;
    private String env;
    private String project;
    private String config;
    private String key;
    private String value;
    private String note;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
