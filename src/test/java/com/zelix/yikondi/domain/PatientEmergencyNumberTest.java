package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientEmergencyNumberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientEmergencyNumber.class);
        PatientEmergencyNumber patientEmergencyNumber1 = new PatientEmergencyNumber();
        patientEmergencyNumber1.setId(1L);
        PatientEmergencyNumber patientEmergencyNumber2 = new PatientEmergencyNumber();
        patientEmergencyNumber2.setId(patientEmergencyNumber1.getId());
        assertThat(patientEmergencyNumber1).isEqualTo(patientEmergencyNumber2);
        patientEmergencyNumber2.setId(2L);
        assertThat(patientEmergencyNumber1).isNotEqualTo(patientEmergencyNumber2);
        patientEmergencyNumber1.setId(null);
        assertThat(patientEmergencyNumber1).isNotEqualTo(patientEmergencyNumber2);
    }
}
