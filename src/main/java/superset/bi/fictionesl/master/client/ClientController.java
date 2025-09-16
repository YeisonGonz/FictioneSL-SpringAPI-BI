package superset.bi.fictionesl.master.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import superset.bi.fictionesl.master.order.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final OrderRepository orderRepository;

    public ClientController(ClientRepository repository, ClientMapper mapper, OrderRepository orderRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
    }

    @Operation(summary = "Get all clients", description = "Retrieve a list of all clients")
    @GetMapping
    public List<ClientDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Operation(summary = "Get client by ID", description = "Retrieve a client by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(
            @Parameter(description = "ID of the client to retrieve") @PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new client", description = "Add a new client to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created successfully")
    })
    @PostMapping
    public ClientDto create(
            @Parameter(description = "Client data to create") @RequestBody ClientDto clientDto) {
        Client client = mapper.toEntity(clientDto);
        Client saved = repository.save(client);
        return mapper.toDto(saved);
    }

    @Operation(summary = "Update an existing client", description = "Update client information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(
            @Parameter(description = "ID of the client to update") @PathVariable Long id,
            @Parameter(description = "Updated client data") @RequestBody ClientDto clientDto) {
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
