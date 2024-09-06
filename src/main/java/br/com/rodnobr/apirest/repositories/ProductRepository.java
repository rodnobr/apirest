package br.com.rodnobr.apirest.repositories;

import br.com.rodnobr.apirest.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>  {

}
