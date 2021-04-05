package com.cqre.cqre.repository;

import com.cqre.cqre.entity.shop.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("select uc from UserCoupon uc join fetch uc.coupon where uc.user.id = :userId")
    List<UserCoupon> findUserCouponsByUserIdWithCoupon(@Param("userId") Long userId);

    @Query("select uc from UserCoupon uc join fetch uc.coupon where uc.id= :userCouponId")
    UserCoupon findUserCouponByUserCouponIdWithCoupon(@Param("userCouponId") Long userCouponId);
}
