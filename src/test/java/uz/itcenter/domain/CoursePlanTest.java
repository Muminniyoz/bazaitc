package uz.itcenter.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.itcenter.web.rest.TestUtil;

public class CoursePlanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursePlan.class);
        CoursePlan coursePlan1 = new CoursePlan();
        coursePlan1.setId(1L);
        CoursePlan coursePlan2 = new CoursePlan();
        coursePlan2.setId(coursePlan1.getId());
        assertThat(coursePlan1).isEqualTo(coursePlan2);
        coursePlan2.setId(2L);
        assertThat(coursePlan1).isNotEqualTo(coursePlan2);
        coursePlan1.setId(null);
        assertThat(coursePlan1).isNotEqualTo(coursePlan2);
    }
}
