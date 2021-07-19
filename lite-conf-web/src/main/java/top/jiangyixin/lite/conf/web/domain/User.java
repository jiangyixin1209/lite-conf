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
@TableName("lite_user")
public class User extends BaseModel {
    private String username;
    private String password;
    /** env:system:configName，多个以 , 分隔 **/
    private String authority;
}
