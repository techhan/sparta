package com.sparta.java_02.domain.refund.repository;

import com.sparta.java_02.domain.refund.entity.RefundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundItemRepository extends JpaRepository<RefundItem, Long> {

}
