package com.xiaoruiit.knowledge.point.stream;

import com.xiaoruiit.common.utils.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hanxiaorui
 * @date 2024/8/10
 */
@Slf4j
public class FLatMap {
    public static void main(String[] args) {
        List<SpecGroup> specGroups = Arrays.asList(new SpecGroup("1", "分组1", Arrays.asList(new Spec("1", "规格1", "1"), new Spec("2", "规格2", "1"))));

        Map<String, String> specCodeSpecGroupCpde = specGroups.stream().map(SpecGroup::getSpecList).flatMap(List::stream).collect(Collectors.toMap(Spec::getCode, Spec::getSpecGroupCode));
        List<List<Spec>> collect = specGroups.stream().map(SpecGroup::getSpecList).collect(Collectors.toList());

        log.info("flatMap:" + JSON.toJSONString(specCodeSpecGroupCpde));

        Map<String, String> specCodeSpecGroupCpde2 = new HashMap<>();
        specGroups.forEach(specGroup -> {
            specGroup.getSpecList().forEach(spec -> {
                specCodeSpecGroupCpde2.put(spec.getCode(), spec.getSpecGroupCode());
            });
        });

        log.info("for:" + JSON.toJSONString(specCodeSpecGroupCpde));
    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class SpecGroup{
    private String code;
    private String name;

    List<Spec> specList;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Spec{
    private String code;
    private String name;

    private String specGroupCode;
}
