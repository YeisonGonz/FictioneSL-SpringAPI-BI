package superset.bi.fictionesl.master.department;
import org.mapstruct.Mapper;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends GenericMapper<Department, DepartmentDto> { }
