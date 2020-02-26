package com.hismart.document.common.function;

/**
 * @author echo_
 */
@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
