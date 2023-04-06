package it.attf.ecommerceSite.controller;


import it.attf.ecommerceSite.common.ApiResponse;
import it.attf.ecommerceSite.config.security.jwt.JwtUtils;
import it.attf.ecommerceSite.dto.Cart.AddToCartDto;
import it.attf.ecommerceSite.dto.Cart.CartDto;
import it.attf.ecommerceSite.exceptions.AuthenticationFailException;
import it.attf.ecommerceSite.exceptions.CartItemNotExistException;
import it.attf.ecommerceSite.exceptions.ProductNotExistException;
import it.attf.ecommerceSite.models.Product;
import it.attf.ecommerceSite.models.User;
import it.attf.ecommerceSite.models.WishList;
import it.attf.ecommerceSite.repository.UserRepo;
import it.attf.ecommerceSite.service.CartService;
import it.attf.ecommerceSite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtils jwtService;
    @Autowired
    UserRepo userRepo;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) throws  ProductNotExistException {
        Optional<User> user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
        User user1 = new User(user.get().getUsername(),user.get().getEmail(),user.get().getPassword());
        Product product = productService.getProductById(addToCartDto.getProductId());
        System.out.println("product to add"+  product.getName());
        cartService.addToCart(addToCartDto, product, user1);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {

        Optional<User> user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
        User user1 = new User(user.get().getUsername(),user.get().getEmail(),user.get().getPassword());
        CartDto cartDto = cartService.listCartItems(user1);

        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestParam("token") String token) throws  ProductNotExistException {
        Optional<User> user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
        User user1 = new User(user.get().getUsername(),user.get().getEmail(),user.get().getPassword());
        Product product = productService.getProductById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user1,product);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID,@RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
        Optional<User> user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
        Long userId = user.get().getId();
        cartService.deleteCartItem(itemID, userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

}
