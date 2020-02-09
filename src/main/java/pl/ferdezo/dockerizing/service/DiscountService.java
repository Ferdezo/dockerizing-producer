package pl.ferdezo.dockerizing.service;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.ferdezo.dockerizing.model.Discount;
import pl.ferdezo.dockerizing.notification.Notifier;
import pl.ferdezo.dockerizing.repository.DiscountRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiscountService {
    private final Notifier notifier;
    private final DiscountRepository discountRepository;

    public Option<Discount> getById(String id) {
        return Option.ofOptional(discountRepository.findById(id));
    }

    public Collection<Discount> getAll() {
        return discountRepository.findAll();
    }

    public Discount create(Discount discount) {
        return Option.of(discount)
            .peek(log::info)
            .map(discountRepository::save)
            .peek(log::info)
            .peek(notifier::accept)
            .getOrElseThrow(() -> new IllegalArgumentException("missing discount"));
    }
}
