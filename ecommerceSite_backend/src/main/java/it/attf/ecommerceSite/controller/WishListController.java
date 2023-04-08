package it.attf.ecommerceSite.controller;



import it.attf.ecommerceSite.common.ApiResponse;
import it.attf.ecommerceSite.config.security.jwt.JwtUtils;
import it.attf.ecommerceSite.dto.JwtResponse;
import it.attf.ecommerceSite.dto.Product.ProductDto;
import it.attf.ecommerceSite.models.Product;
import it.attf.ecommerceSite.models.User;
import it.attf.ecommerceSite.models.WishList;
import it.attf.ecommerceSite.repository.UserRepo;
import it.attf.ecommerceSite.service.ProductService;
import it.attf.ecommerceSite.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

        @Autowired
        private WishListService wishListService;

        @Autowired
        private JwtUtils jwtService;
        @Autowired
        UserRepo userRepo;


        @GetMapping("/{token}")
        public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
                User user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
                List<WishList> body = wishListService.readWishList(user.getId());
                List<ProductDto> products = new ArrayList<ProductDto>();
                for (WishList wishList : body) {
                        products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
                }

                return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
        }

        @PostMapping("/add")
        public ResponseEntity<ApiResponse> addWishList(@RequestBody Product product, @RequestParam("token") String token) {
                User user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
                WishList wishList = new WishList(user, product);
                wishListService.createWishlist(wishList);


                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Add to wishlist"), HttpStatus.CREATED);

        }


}
