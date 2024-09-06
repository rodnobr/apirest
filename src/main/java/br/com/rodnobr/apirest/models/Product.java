package br.com.rodnobr.apirest.models;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column()
    private String description;
}
