package superset.bi.fictionesl.master.department;

import superset.bi.fictionesl.master.employee.Employee;

import java.util.List;

public record DepartmentDto(String name, String location, List<Employee> employees) { }
