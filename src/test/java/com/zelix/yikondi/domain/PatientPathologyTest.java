package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientPathologyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientPathology.class);
        PatientPathology patientPathology1 = new PatientPathology();
        patientPathology1.setId(1L);
        PatientPathology patientPathology2 = new PatientPathology();
        patientPathology2.setId(patientPathology1.getId());
        assertThat(patientPathology1).isEqualTo(patientPathology2);
        patientPathology2.setId(2L);
        assertThat(patientPathology1).isNotEqualTo(patientPathology2);
        patientPathology1.setId(null);
        assertThat(patientPathology1).isNotEqualTo(patientPathology2);
    }
}
