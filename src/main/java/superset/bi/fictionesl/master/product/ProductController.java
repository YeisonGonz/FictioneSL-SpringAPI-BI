package superset.bi.fictionesl.master.product;

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

    @GetMapping
    public List<ProductDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @PostMapping
    public ProductDto create(@RequestBody ProductDto productDto) {
        Product department = mapper.toEntity(productDto);
        Product saved = repository.save(department);

        return mapper.toDto(saved);
    }
}
