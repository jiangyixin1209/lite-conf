package top.jiangyixin.lite.conf.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("lite_update_log")
public class UpdateLog extends BaseModel {
    private Long userId;
    private Long confId;
    private String oldValue;
    private String newValue;
    private String desc;
}
