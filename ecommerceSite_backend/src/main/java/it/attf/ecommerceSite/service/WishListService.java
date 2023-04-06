package it.attf.ecommerceSite.service;

import it.attf.ecommerceSite.models.WishList;
import it.attf.ecommerceSite.repository.WishListRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WishListService {

    private final WishListRepo wishListRepository;

    public WishListService(WishListRepo wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(Long userId) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}
