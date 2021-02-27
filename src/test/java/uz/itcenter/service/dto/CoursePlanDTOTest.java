package uz.itcenter.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.itcenter.web.rest.TestUtil;

public class CoursePlanDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursePlanDTO.class);
        CoursePlanDTO coursePlanDTO1 = new CoursePlanDTO();
        coursePlanDTO1.setId(1L);
        CoursePlanDTO coursePlanDTO2 = new CoursePlanDTO();
        assertThat(coursePlanDTO1).isNotEqualTo(coursePlanDTO2);
        coursePlanDTO2.setId(coursePlanDTO1.getId());
        assertThat(coursePlanDTO1).isEqualTo(coursePlanDTO2);
        coursePlanDTO2.setId(2L);
        assertThat(coursePlanDTO1).isNotEqualTo(coursePlanDTO2);
        coursePlanDTO1.setId(null);
        assertThat(coursePlanDTO1).isNotEqualTo(coursePlanDTO2);
    }
}
