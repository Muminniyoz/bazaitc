package uz.itcenter.service.mapper;


import uz.itcenter.domain.*;
import uz.itcenter.service.dto.RegisteredDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Registered} and its DTO {@link RegisteredDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CourseMapper.class})
public interface RegisteredMapper extends EntityMapper<RegisteredDTO, Registered> {

    @Mapping(source = "modifiedBy.id", target = "modifiedById")
    @Mapping(source = "course.id", target = "courseId")
    RegisteredDTO toDto(Registered registered);

    @Mapping(source = "modifiedById", target = "modifiedBy")
    @Mapping(source = "courseId", target = "course")
    Registered toEntity(RegisteredDTO registeredDTO);

    default Registered fromId(Long id) {
        if (id == null) {
            return null;
        }
        Registered registered = new Registered();
        registered.setId(id);
        return registered;
    }
}
