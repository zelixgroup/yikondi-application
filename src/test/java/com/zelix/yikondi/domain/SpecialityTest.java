package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class SpecialityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speciality.class);
        Speciality speciality1 = new Speciality();
        speciality1.setId(1L);
        Speciality speciality2 = new Speciality();
        speciality2.setId(speciality1.getId());
        assertThat(speciality1).isEqualTo(speciality2);
        speciality2.setId(2L);
        assertThat(speciality1).isNotEqualTo(speciality2);
        speciality1.setId(null);
        assertThat(speciality1).isNotEqualTo(speciality2);
    }
}
