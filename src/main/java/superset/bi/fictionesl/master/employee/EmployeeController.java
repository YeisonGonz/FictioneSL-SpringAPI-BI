package superset.bi.fictionesl.master.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import superset.bi.fictionesl.master.department.DepartmentRepository;


import java.util.List;

@RestController
@RequestMapping("/employess")
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeRepository repository, EmployeeMapper mapper, DepartmentRepository departmentrepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.departmentRepository = departmentrepository;
    }

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    @GetMapping
    public List<EmployeeDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve an employee by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@Parameter(description = "ID of the employee") @PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new employee", description = "Add a new employee associated with a department")
    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
        var department = departmentRepository.findById(employeeDto.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));

        Employee employee = mapper.toEntity(employeeDto);
        employee.setDepartment(department);

        Employee saved = repository.save(employee);
        return mapper.toDto(saved);
    }

    @Operation(summary = "Update an existing employee", description = "Update employee details and optionally change department")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        return repository.findById(id)
                .map(existing -> {
                    var department = departmentRepository.findById(employeeDto.departmentId())
                            .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));

                    Employee updated = mapper.toEntity(employeeDto);
                    updated.setId(existing.getId());
                    updated.setDepartment(department);

                    Employee saved = repository.save(updated);
                    return ResponseEntity.ok(mapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an employee", description = "Delete an employee by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    repository.delete(employee);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
