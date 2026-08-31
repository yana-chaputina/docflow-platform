package com.ipr.orderservice.controller;

import com.ipr.orderservice.dto.OrderDto;
import com.ipr.orderservice.dto.StatusHistoryDto;
import com.ipr.orderservice.service.OrderService;
import com.ipr.orderservice.service.StatusHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final StatusHistoryService statusHistoryService;

    @Autowired
    public OrderController(OrderService orderService, StatusHistoryService statusHistoryService) {
        this.orderService = orderService;
        this.statusHistoryService = statusHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(){
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById (@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto,
                                                BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/status-history")
    public ResponseEntity<List<StatusHistoryDto>> getStatusHistoryByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(statusHistoryService.getStatusHistoryByOrderId(id));
    }
}
