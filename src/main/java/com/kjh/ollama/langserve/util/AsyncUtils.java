package com.kjh.ollama.langserve.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.kjh.ollama.langserve.util.MessageUtils.*;

/**
 * 비동기 처리를 위한 유틸 클래스
 */
@Component
@Slf4j
public class AsyncUtils {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static <T> void fork(Supplier<T> task) {
        CompletableFuture.runAsync(() -> {
            try{
                task.get();
            } catch (Exception e) {
                log.error(toErrorMessageFormat(e));
            }
        }, executor);
    }
}
