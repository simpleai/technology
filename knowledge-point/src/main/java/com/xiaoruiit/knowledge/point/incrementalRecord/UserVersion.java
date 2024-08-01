package com.xiaoruiit.knowledge.point.incrementalRecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "版本表")
public class UserVersion implements Serializable {
    private static final long serialVersionUID = -35226665679432505L;

    private Long id;

    @ApiModelProperty(value = "名称")
    private String userName;

    @ApiModelProperty(value = "版本时间")
    private Date createTime;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

    @ApiModelProperty(value = "删除时间，插入时数据库默认生成最大时间，代表未删除")
    private Date deletedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof UserVersion)) {
            return false;
        }

        UserVersion item = (UserVersion) o;

        return new EqualsBuilder()
                .append(userName, item.userName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .toHashCode();
    }

}

