package it.attf.ecommerceSite.dto.Order;

import it.attf.ecommerceSite.models.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private @NotNull Long userId;

    public OrderDto(Order order) {
        this.setId(order.getId());
        //this.setUserId(order.getUserId());
    }


}
