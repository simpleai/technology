package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

/**
 * @author hanxiaorui
 * @date 2023/10/24
 */
public final class VersionedRef <T>{
    final T value;

    final long version;

    public VersionedRef(T value, long version) {
        this.value = value;
        this.version = version;
    }
}
