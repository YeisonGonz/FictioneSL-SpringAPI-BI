package superset.bi.fictionesl.master.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductController(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @GetMapping
    public List<ProductDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@Parameter(description = "ID of the product") @PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product", description = "Add a new product")
    @PostMapping
    public ProductDto create(@RequestBody ProductDto productDto) {
        Product product = mapper.toEntity(productDto);
        Product saved = repository.save(product);
        return mapper.toDto(saved);
    }

    @Operation(summary = "Update an existing product", description = "Update product details")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return repository.findById(id)
                .map(existing -> {
                    Product updated = mapper.toEntity(productDto);
                    updated.setId(existing.getId());
                    Product saved = repository.save(updated);
                    return ResponseEntity.ok(mapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a product", description = "Delete a product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(product -> {
                    repository.delete(product);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
