package com.namrata.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.namrata.global.GlobalData;
import com.namrata.model.BillingDetails;
import com.namrata.model.CartItem;
import com.namrata.model.Order;
import com.namrata.model.OrderItem;
import com.namrata.model.Payment;
import com.namrata.model.Product;
import com.namrata.model.User;
import com.namrata.repository.BillingDetailsRepository;
import com.namrata.repository.OrderRepository;
import com.namrata.repository.PaymentRepository;
import com.namrata.service.CartItemService;
import com.namrata.service.ProductService;
import com.namrata.service.UserService;

@Controller
public class CartController {

	 @Autowired
	    private ProductService productService;

	    @Autowired
	    private CartItemService cartItemService;

	    @Autowired
	    private OrderRepository orderRepository;
	    
	    @Autowired
	    private PaymentRepository paymentRepository;
	    
	    @Autowired
	    private UserService userService;
	   

	    private User getCurrentUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        return userService.findByEmail(email);
	    }

	    @PostMapping("/addToCart/{id}")
	    public String addToCart(@PathVariable Long id) {
	        Product product = productService.getProductById(id).orElseThrow();
	        User user = getCurrentUser();

	        CartItem cartItem = cartItemService.findByUserAndProduct(user, product)
	            .orElse(new CartItem());
	        cartItem.setUser(user);
	        cartItem.setProduct(product);
	        cartItem.setQuantity(cartItem.getQuantity() + 1); // Increment quantity
	        GlobalData.cart.add(productService.getProductById(id).get());
	        cartItemService.save(cartItem);

	        return "redirect:/shop";
	    }

	    @GetMapping("/cart")
	    public String viewCart(Model model) {
	    	
	        User user = getCurrentUser();
	        model.addAttribute("cartCount", GlobalData.cart.size());
	        model.addAttribute("cartItems", cartItemService.findByUser(user));
	        model.addAttribute("total", cartItemService.calculateTotal(user));
	        model.addAttribute("cart", GlobalData.cart);

	        return "cart";
	    }

		/*
		 * @GetMapping("/cart/removeItem/{index}") public String
		 * removeFromCart(@PathVariable Long index) { User user = getCurrentUser();
		 * cartItemService.deleteByIdAndUser(index, user);
		 * 
		 * return "redirect:/cart"; }
		 */

	    
	    @GetMapping("/cart/removeItem/{productId}")
	    public String removeFromCart(@PathVariable Long productId) {
	        User user = getCurrentUser();
	        cartItemService.deleteByProductIdAndUser(productId, user);
	        GlobalData.cart.removeIf(product -> product.getId().equals(productId));
	        return "redirect:/cart";
	    }
	    
	    @GetMapping("/checkout")
	    public String checkout(Model model) {
	        User user = getCurrentUser();
	        List<CartItem> cartItems = cartItemService.findByUser(user);
	        double total = cartItemService.calculateTotal(user);

	        // Create a new Order
	        Order order = new Order();
	        order.setUser(user);
	        order.setTotal(total);

	        // Create OrderItems
	        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
	            OrderItem orderItem = new OrderItem();
	            orderItem.setOrder(order);
	            orderItem.setProduct(cartItem.getProduct());
	            orderItem.setQuantity(cartItem.getQuantity());
	            return orderItem;
	        }).collect(Collectors.toList());

	        order.setOrderItems(orderItems);

	        // Create Payment
	        Payment payment = new Payment();
	        payment.setPaymentMethod("Credit Card"); // This can be dynamic based on user input
	        payment.setAmount(total);
	        payment.setOrder(order);
	        order.setPayment(payment);

	        // Save Order and Payment
	        orderRepository.save(order);
	        paymentRepository.save(payment);

	        // Clear the cart
	        cartItemService.clearCart(user);
	        GlobalData.cart.clear();

	        model.addAttribute("total", total);
	        model.addAttribute("order", order);

	        return "checkout";
	    }
	    
	 
}
