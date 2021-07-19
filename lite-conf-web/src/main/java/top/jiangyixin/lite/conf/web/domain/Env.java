package top.jiangyixin.lite.conf.web.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2021/7/19 下午3:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("lite_env")
public class Env extends BaseModel {
    private String name;
    private String code;
    @TableField(value = "`desc`")
    private String desc;
    private Boolean active;
}
