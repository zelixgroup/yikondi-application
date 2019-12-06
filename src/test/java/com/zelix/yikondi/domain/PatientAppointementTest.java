package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientAppointementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientAppointement.class);
        PatientAppointement patientAppointement1 = new PatientAppointement();
        patientAppointement1.setId(1L);
        PatientAppointement patientAppointement2 = new PatientAppointement();
        patientAppointement2.setId(patientAppointement1.getId());
        assertThat(patientAppointement1).isEqualTo(patientAppointement2);
        patientAppointement2.setId(2L);
        assertThat(patientAppointement1).isNotEqualTo(patientAppointement2);
        patientAppointement1.setId(null);
        assertThat(patientAppointement1).isNotEqualTo(patientAppointement2);
    }
}
