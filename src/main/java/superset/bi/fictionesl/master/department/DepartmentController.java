package superset.bi.fictionesl.master.department;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    public DepartmentController(DepartmentRepository repository, DepartmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DepartmentDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @PostMapping
    public DepartmentDto create(@RequestBody DepartmentDto departmentDto) {
        Department department = mapper.toEntity(departmentDto);
        Department saved = repository.save(department);

        return mapper.toDto(saved);
    }
}
