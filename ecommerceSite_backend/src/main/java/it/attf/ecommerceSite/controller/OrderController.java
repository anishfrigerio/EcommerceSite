package it.attf.ecommerceSite.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import it.attf.ecommerceSite.common.ApiResponse;
import it.attf.ecommerceSite.config.security.jwt.JwtUtils;
import it.attf.ecommerceSite.dto.Checkout.CheckoutItemDto;
import it.attf.ecommerceSite.dto.Checkout.StripeResponse;
import it.attf.ecommerceSite.exceptions.AuthenticationFailException;
import it.attf.ecommerceSite.exceptions.OrderNotFoundException;
import it.attf.ecommerceSite.models.Order;
import it.attf.ecommerceSite.models.User;
import it.attf.ecommerceSite.repository.UserRepo;
import it.attf.ecommerceSite.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtService;
    @Autowired
    UserRepo userRepo;


    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        // create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId) {
        Optional<User> user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
        User user1 = new User(user.get().getUsername(),user.get().getEmail(),user.get().getPassword());
        // place the order
        orderService.placeOrder(user1, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token){
        Optional<User> user = userRepo.findByUsername(jwtService.getUserNameFromJwtToken(token));
        User user1 = new User(user.get().getUsername(),user.get().getEmail(),user.get().getPassword());
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user1);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) {
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

}
