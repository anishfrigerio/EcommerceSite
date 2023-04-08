package it.attf.ecommerceSite.controller;


import it.attf.ecommerceSite.common.ApiResponse;
import it.attf.ecommerceSite.config.security.jwt.AuthTokenFilter;
import it.attf.ecommerceSite.config.security.jwt.JwtUtils;
import it.attf.ecommerceSite.dto.Cart.AddToCartDto;
import it.attf.ecommerceSite.dto.Cart.CartDto;
import it.attf.ecommerceSite.exceptions.AuthenticationFailException;
import it.attf.ecommerceSite.exceptions.CartItemNotExistException;
import it.attf.ecommerceSite.exceptions.ProductNotExistException;
import it.attf.ecommerceSite.models.Cart;
import it.attf.ecommerceSite.models.Product;
import it.attf.ecommerceSite.models.User;
import it.attf.ecommerceSite.models.WishList;
import it.attf.ecommerceSite.repository.CartRepo;
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
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtils jwtService;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/cart/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestHeader (name="Authorization") String token) throws  ProductNotExistException {
        User user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token.substring(7, token.length())));
        Product product = productService.getProductById(addToCartDto.getProductId());
        System.out.println("product to add "+  product.getName());

        cartService.addToCart(addToCartDto, product, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }
    @GetMapping("/cart")
    public ResponseEntity<CartDto> getCartItems(@RequestHeader (name="Authorization") String token) throws AuthenticationFailException {

        User user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token.substring(7, token.length())));
        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }
    @PutMapping("/cart/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestHeader (name="Authorization") String token) throws  ProductNotExistException {
        User user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token.substring(7, token.length())));
        Product product = productService.getProductById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user,product);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/cart/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID,@RequestHeader (name="Authorization") String token) throws AuthenticationFailException, CartItemNotExistException {
        User user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token.substring(7, token.length())));
        Optional<Cart> cart = cartRepo.findById(itemID);
        if(cart.isPresent()&&cart.get().getUser().getId().equals(user.getId())) {
            cartService.deleteCartItem(itemID);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
        } else{
            throw new AuthenticationFailException("Not your cart to delete");
        }

    }

}
