package com.xiaoruiit.knowledge.point.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xiaoruiit.knowledge.point.mybatis.typehandler.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author hanxiaorui
 * @date 2024/10/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntityMappingDatabase {

    public Long id;

    public Enum status;

    public ExtInfo extInfo;

    public Set<String> roleCodes;

    public List<AddressDetail> addressDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddressDetail {
        private String address;
        private String zipCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExtInfo {

        private String msg;

        private String operateBy;

    }

    public enum StatusEnum implements CodeEnum{

        TO_BE_REVIEWED(1, "待审核"),
        REVIEWED(2, "已审核"),
        ;

        private final int code;

        private final String text;

        StatusEnum(int code, String text) {
            this.code = code;
            this.text = text;
        }

        @JsonCreator
        public static StatusEnum codeOf(Integer code) {
            if (code == null) {
                return null;
            }
            for (StatusEnum value : StatusEnum.values()) {
                if (value.code == code) {
                    return value;
                }
            }
            return null;
        }

        @JsonValue
        public int code() {
            return code;
        }

        public String text() {
            return text;
        }

    }
}
