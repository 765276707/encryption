package com.github.xzb617.encryption.autoconfigure.consoles;

import com.github.xzb617.encryption.autoconfigure.consoles.details.ConsoleDetails;

/**
 * 加密的上下文
 * @author xzb617
 * @date 2022/5/10 12:40
 * @description:
 */
public class EncryptionContextHolder {

    private final static ThreadLocal<ConsoleDetails> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(ConsoleDetails consoleDetails) {
        THREAD_LOCAL.set(consoleDetails);
    }

    public static ConsoleDetails get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
