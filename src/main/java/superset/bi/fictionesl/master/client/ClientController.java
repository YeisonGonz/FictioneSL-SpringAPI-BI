package superset.bi.fictionesl.master.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import superset.bi.fictionesl.master.order.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final OrderRepository orderRepositry;

    public ClientController(ClientRepository repository, ClientMapper mapper, OrderRepository orderRepositry) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderRepositry = orderRepositry;
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClientDto create(@RequestBody ClientDto clientDto) {
        Client client = mapper.toEntity(clientDto);
        Client saved = repository.save(client);

        return mapper.toDto(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id,
                                            @RequestBody ClientDto clientDto) {
        return repository.findById(id)
                .map(existing -> {
                    Client updated = mapper.toEntity(clientDto);
                    updated.setId(existing.getId()); // mantener el id original
                    Client saved = repository.save(updated);
                    return ResponseEntity.ok(mapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
