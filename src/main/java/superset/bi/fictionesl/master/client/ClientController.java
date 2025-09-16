package superset.bi.fictionesl.master.client;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public ClientController(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @PostMapping
    public ClientDto create(@RequestBody ClientDto clientDto) {
        Client client = mapper.toEntity(clientDto);
        Client saved = repository.save(client);

        return mapper.toDto(saved);
    }
}
