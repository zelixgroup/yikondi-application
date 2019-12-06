package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class HealthCentreDoctorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthCentreDoctor.class);
        HealthCentreDoctor healthCentreDoctor1 = new HealthCentreDoctor();
        healthCentreDoctor1.setId(1L);
        HealthCentreDoctor healthCentreDoctor2 = new HealthCentreDoctor();
        healthCentreDoctor2.setId(healthCentreDoctor1.getId());
        assertThat(healthCentreDoctor1).isEqualTo(healthCentreDoctor2);
        healthCentreDoctor2.setId(2L);
        assertThat(healthCentreDoctor1).isNotEqualTo(healthCentreDoctor2);
        healthCentreDoctor1.setId(null);
        assertThat(healthCentreDoctor1).isNotEqualTo(healthCentreDoctor2);
    }
}
