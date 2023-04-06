package it.attf.ecommerceSite.repository;

import it.attf.ecommerceSite.models.Order;
import it.attf.ecommerceSite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

}
