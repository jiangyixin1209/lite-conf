package top.jiangyixin.lite.conf.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.jiangyixin.lite.conf.web.domain.base.BaseModel;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/19 下午3:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("lite_conf")
public class Conf extends BaseModel {
    private String env;
    private String system;
    private String config;
    private String key;
    private String value;
    private String desc;
}
