package uz.itcenter.service.mapper;


import uz.itcenter.domain.*;
import uz.itcenter.service.dto.CoursePlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CoursePlan} and its DTO {@link CoursePlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoursePlanMapper extends EntityMapper<CoursePlanDTO, CoursePlan> {



    default CoursePlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoursePlan coursePlan = new CoursePlan();
        coursePlan.setId(id);
        return coursePlan;
    }
}
