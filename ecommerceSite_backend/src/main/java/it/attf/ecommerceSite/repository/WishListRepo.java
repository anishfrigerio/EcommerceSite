package it.attf.ecommerceSite.repository;

import it.attf.ecommerceSite.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Long> {

    List<WishList> findAllByUserIdOrderByCreatedDateDesc(Long userId);


}