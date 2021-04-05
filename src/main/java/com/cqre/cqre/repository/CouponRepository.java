package com.cqre.cqre.repository;

import com.cqre.cqre.entity.shop.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c")
    Page<Coupon> findAllCoupon(Pageable pageable);

    @Query("select c from Coupon c where c.name = :name")
    Coupon findByName(@Param("name") String name);

    @Query("select c from Coupon c where c.id in :ids")
    Page<Coupon> findCouponsById(@Param("ids") List<Long> ids, Pageable pageable);

    @Query("select c from Coupon c where c.id in :ids")
    List<Coupon> findCouponsByIdNotPaging(@Param("ids") List<Long> ids);
}
