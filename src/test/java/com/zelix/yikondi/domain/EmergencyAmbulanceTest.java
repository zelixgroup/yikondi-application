package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class EmergencyAmbulanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyAmbulance.class);
        EmergencyAmbulance emergencyAmbulance1 = new EmergencyAmbulance();
        emergencyAmbulance1.setId(1L);
        EmergencyAmbulance emergencyAmbulance2 = new EmergencyAmbulance();
        emergencyAmbulance2.setId(emergencyAmbulance1.getId());
        assertThat(emergencyAmbulance1).isEqualTo(emergencyAmbulance2);
        emergencyAmbulance2.setId(2L);
        assertThat(emergencyAmbulance1).isNotEqualTo(emergencyAmbulance2);
        emergencyAmbulance1.setId(null);
        assertThat(emergencyAmbulance1).isNotEqualTo(emergencyAmbulance2);
    }
}
