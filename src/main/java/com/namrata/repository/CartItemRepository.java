package com.namrata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.namrata.model.CartItem;
import com.namrata.model.Product;
import com.namrata.model.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	 Optional<CartItem> findByUserAndProduct(User user, Product product);

	    List<CartItem> findByUser(User user);

	    void deleteByIdAndUser(Long id, User user);
	    
		/*
		 * @Query("SELECT c FROM CartItem c WHERE c.id = :id AND c.user = :user")
		 * Optional<CartItem> findByIdAndUser(@Param("id") Long id, @Param("user") User
		 * user);
		 */
	    
	    @Query("SELECT c FROM CartItem c WHERE c.product.id = :productId AND c.user = :user")
	    Optional<CartItem> findByProductIdAndUser(@Param("productId") Long productId, @Param("user") User user);
}