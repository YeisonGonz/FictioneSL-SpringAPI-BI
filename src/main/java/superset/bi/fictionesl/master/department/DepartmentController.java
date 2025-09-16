package superset.bi.fictionesl.master.department;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Get all departments", description = "Retrieve a list of all departments")
    @GetMapping
    public List<DepartmentDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Operation(summary = "Get department by ID", description = "Retrieve a department by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department found"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(
            @Parameter(description = "ID of the department to retrieve") @PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new department", description = "Add a new department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department created successfully")
    })
    @PostMapping
    public DepartmentDto create(
            @Parameter(description = "Department data to create") @RequestBody DepartmentDto departmentDto) {
        Department department = mapper.toEntity(departmentDto);
        Department saved = repository.save(department);
        return mapper.toDto(saved);
    }

    @Operation(summary = "Update an existing department", description = "Update department information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department updated successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> update(
            @Parameter(description = "ID of the department to update") @PathVariable Long id,
            @Parameter(description = "Updated department data") @RequestBody DepartmentDto departmentDto) {
        return repository.findById(id)
                .map(existing -> {
                    Department updated = mapper.toEntity(departmentDto);
                    updated.setId(existing.getId());
                    Department saved = repository.save(updated);
                    return ResponseEntity.ok(mapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a department", description = "Delete a department by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(department -> {
                    repository.delete(department); // borra los empleados en cascada
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
