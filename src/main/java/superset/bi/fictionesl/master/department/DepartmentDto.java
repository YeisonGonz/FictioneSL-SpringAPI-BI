package superset.bi.fictionesl.master.department;

import superset.bi.fictionesl.master.employee.EmployeeDto;

import java.util.List;

public record DepartmentDto(
        String name,
        String location,
        List<EmployeeDto> employees
) {
    public DepartmentDto {
        if (employees == null) {
            employees = List.of(); // o new ArrayList<>()
        }
    }
}

