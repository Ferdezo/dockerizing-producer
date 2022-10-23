package pl.ferdezo.dockerizing.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Document
public class Discount implements Identifiable, Serializable {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal oldPrice;
}
