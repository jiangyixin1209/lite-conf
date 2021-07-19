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
 * @date 2021/7/19 下午3:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("lite_system")
public class System extends BaseModel {
    
}
