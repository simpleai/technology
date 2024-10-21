package com.xiaoruiit.knowledge.point.TecentMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author hanxiaorui
 * @date 2024/9/19
 *
 */
@Data
@RefreshScope
@NoArgsConstructor
@AllArgsConstructor
public class DistanceMatrix {

    private String from;

    private String key;

    private String mode;

    private String to;

    public DistanceMatrix(String from, String to) {
        this.from = from;
        this.to = to;
    }

}
