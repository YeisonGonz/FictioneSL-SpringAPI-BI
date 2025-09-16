package superset.bi.fictionesl.master.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends GenericMapper <Employee,EmployeeDto>{

    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto toDto(Employee employee);
    
    @Mapping(source = "departmentId", target = "department.id")
    Employee toEntity(EmployeeDto dto);
}
