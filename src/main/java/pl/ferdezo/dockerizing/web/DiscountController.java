package pl.ferdezo.dockerizing.web;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ferdezo.dockerizing.model.Discount;
import pl.ferdezo.dockerizing.service.DiscountService;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<Discount> create(@RequestBody Discount discount) {
        val createdEntity = ResponseEntity.created(URI.create("/discount"));

        return Option.of(discount)
            .map(discountService::create)
            .map(createdEntity::body)
            .getOrElse(() -> ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscount(@PathVariable String id) {
        return discountService.getById(id)
            .map(ResponseEntity::ok)
            .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Collection<Discount> allDiscounts() {
        return discountService.getAll();
    }
}
