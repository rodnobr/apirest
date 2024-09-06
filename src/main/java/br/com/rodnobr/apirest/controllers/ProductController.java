package br.com.rodnobr.apirest.controllers;

import br.com.rodnobr.apirest.models.Product;
import br.com.rodnobr.apirest.repositories.ProductRepository;
import br.com.rodnobr.apirest.utils.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Exibir uma colecao de produtos
    @GetMapping("")
    public List<Product> listar() {
        return productRepository.findAll();
    }

    // Consultar um produto
    @GetMapping("/{id}")
    public ResponseEntity<Object> obter(@PathVariable int id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            //  Retornar o status code 404
            return ResponseHandle.generate("Produto nao encontrado", HttpStatus.NOT_FOUND);
        }
        // Retornar o produto
        return new ResponseEntity<Object>(product.get(), HttpStatus.OK);
    }

    // Cadastrar um produto
    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Product product) {
        if(product.getName().isEmpty()) {
            return ResponseHandle.generate("Nome do produto e obrigatorio", HttpStatus.BAD_REQUEST);
        }
        if(product.getPrice() == null) {
            return ResponseHandle.generate("Preco do produto e obrigatorio", HttpStatus.BAD_REQUEST);
        }

        Product newProduct = productRepository.save(product);
        return new ResponseEntity<Object>(newProduct, HttpStatus.CREATED);
    }

    // Editar um produto
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if (!oldProduct.isPresent()) {
            //  Retornar o status code 404
            return ResponseHandle.generate("Produto nao encontrado", HttpStatus.NOT_FOUND);
        }
        if(product.getName().isEmpty()) {
            return ResponseHandle.generate("Nome do produto e obrigatorio", HttpStatus.BAD_REQUEST);
        }
        if(product.getPrice() == null) {
            return ResponseHandle.generate("Preco do produto e obrigatorio", HttpStatus.BAD_REQUEST);
        }

        Product updatedProduct = oldProduct.get();
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());

        productRepository.save(updatedProduct);
        return ResponseEntity.noContent().build();
    }

    // Excluir um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if (!oldProduct.isPresent()) {
            //  Retornar o status code 404
            return ResponseHandle.generate("Produto nao encontrado", HttpStatus.NOT_FOUND);
        }

        productRepository.delete(oldProduct.get());
        return ResponseEntity.noContent().build();
    }
}
