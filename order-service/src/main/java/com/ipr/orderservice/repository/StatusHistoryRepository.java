package com.ipr.orderservice.repository;

import com.ipr.orderservice.entity.StatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistoryEntity, Long> {
    public List<StatusHistoryEntity> getStatusHistoryByOrderId(Long orderId);
}
