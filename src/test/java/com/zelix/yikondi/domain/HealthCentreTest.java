package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class HealthCentreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthCentre.class);
        HealthCentre healthCentre1 = new HealthCentre();
        healthCentre1.setId(1L);
        HealthCentre healthCentre2 = new HealthCentre();
        healthCentre2.setId(healthCentre1.getId());
        assertThat(healthCentre1).isEqualTo(healthCentre2);
        healthCentre2.setId(2L);
        assertThat(healthCentre1).isNotEqualTo(healthCentre2);
        healthCentre1.setId(null);
        assertThat(healthCentre1).isNotEqualTo(healthCentre2);
    }
}
