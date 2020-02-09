package pl.ferdezo.dockerizing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.ferdezo.dockerizing.model.Discount;

public interface DiscountRepository extends MongoRepository<Discount, String> { }
