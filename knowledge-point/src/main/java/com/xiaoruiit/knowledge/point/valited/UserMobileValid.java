package com.xiaoruiit.knowledge.point.valited;

import com.xiaoruiit.knowledge.point.valited.annotation.Mobile;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hanxiaorui
 * @date 2022/2/17
 */
@Data
public class UserMobileValid {

    @NotBlank
    private String userId;

    @Mobile
    private String mobile;

}
