package uz.itcenter.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CoursePlanMapperTest {

    private CoursePlanMapper coursePlanMapper;

    @BeforeEach
    public void setUp() {
        coursePlanMapper = new CoursePlanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(coursePlanMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coursePlanMapper.fromId(null)).isNull();
    }
}
