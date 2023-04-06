package it.attf.ecommerceSite.repository;

import it.attf.ecommerceSite.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItem,Integer> {
}
