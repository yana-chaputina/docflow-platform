package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class OrderValidityCheckerImpl implements OrderValidityChecker {

    private final Executor virtualThreadExecutor;

    public OrderValidityCheckerImpl(Executor virtualThreadExecutor) {
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    @Override
    public boolean validateOrder(OrderDto orderDto) {
            CompletableFuture<Boolean> userBlackListFuture = CompletableFuture.supplyAsync(() ->
                isUserNotInBlacklist(orderDto.userId()),virtualThreadExecutor);
            CompletableFuture<Boolean> scoringCheckFuture = CompletableFuture.supplyAsync(() ->
                scoringCheck(orderDto),virtualThreadExecutor);

            return userBlackListFuture.join()
                    && scoringCheckFuture.join();
    }

    private boolean scoringCheck(OrderDto orderDto) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Scoring check failed");
        }
        return true;
    }

    private boolean isUserNotInBlacklist(Long userId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Blacklist check failed");
        }
        return true;
    }
}
