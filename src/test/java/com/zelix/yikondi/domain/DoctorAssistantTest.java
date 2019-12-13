package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class DoctorAssistantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorAssistant.class);
        DoctorAssistant doctorAssistant1 = new DoctorAssistant();
        doctorAssistant1.setId(1L);
        DoctorAssistant doctorAssistant2 = new DoctorAssistant();
        doctorAssistant2.setId(doctorAssistant1.getId());
        assertThat(doctorAssistant1).isEqualTo(doctorAssistant2);
        doctorAssistant2.setId(2L);
        assertThat(doctorAssistant1).isNotEqualTo(doctorAssistant2);
        doctorAssistant1.setId(null);
        assertThat(doctorAssistant1).isNotEqualTo(doctorAssistant2);
    }
}
