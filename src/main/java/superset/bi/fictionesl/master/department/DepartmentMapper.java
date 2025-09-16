package superset.bi.fictionesl.master.department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import superset.bi.fictionesl.master.employee.EmployeeMapper;
import superset.bi.fictionesl.shared.GenericMapper;

// Le indicas que tiene que usar este mapper en el
// DTO de Department para que pase correctamente de Employee a EmployeeDto
@Mapper(
        componentModel = "spring",
        uses = EmployeeMapper.class,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface DepartmentMapper extends GenericMapper<Department, DepartmentDto> { }
