package com.namrata.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name = "orders")
public class Order {

	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @ManyToOne
	    private User user;

	    private double total;
	    
	  

	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<OrderItem> orderItems;

	    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	    @JoinColumn(name = "payment_id")
	    private Payment payment;

	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Order order = (Order) o;
	        return id != null && id.equals(order.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

}
