package com.namrata.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namrata.model.CartItem;
import com.namrata.model.Product;
import com.namrata.model.User;
import com.namrata.repository.CartItemRepository;
import com.namrata.repository.UserRepository;

@Service
public class CartItemService {

	 @Autowired
	    private CartItemRepository cartItemRepository;

	    @Autowired 
	    private UserRepository userRepository;

	    public Optional<CartItem> findByUserAndProduct(User user, Product product) {
	        return cartItemRepository.findByUserAndProduct(user, product);
	    }

	    public List<CartItem> findByUser(User user) {
	        return cartItemRepository.findByUser(user);
	    }

	    public void save(CartItem cartItem) {
	        cartItemRepository.save(cartItem);
	    }

		/*
		 * @Transactional public void deleteByIdAndUser(Long id, User user) {
		 * Optional<CartItem> cartItemOptional = cartItemRepository.findByIdAndUser(id,
		 * user); cartItemOptional.ifPresent(cartItemRepository::delete); }
		 */

	    @Transactional
	    public void deleteByProductIdAndUser(Long productId, User user) {
	        Optional<CartItem> cartItemOptional = cartItemRepository.findByProductIdAndUser(productId, user);
	        cartItemOptional.ifPresent(cartItemRepository::delete);
	    }
	    public double calculateTotal(User user) {
	        return cartItemRepository.findByUser(user).stream()
	            .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
	            .sum();
	    }
	    @Transactional
	    public void clearCart(User user) {
	        cartItemRepository.deleteAll(cartItemRepository.findByUser(user));
	    }

}
