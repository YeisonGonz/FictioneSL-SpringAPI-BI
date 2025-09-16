package superset.bi.fictionesl.master.employee;

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

    @GetMapping
    public List<EmployeeDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
        var department = departmentRepository.findById(employeeDto.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));

        Employee employee = mapper.toEntity(employeeDto);
        employee.setDepartment(department);

        Employee saved = repository.save(employee);
        return mapper.toDto(saved);
    }
}
