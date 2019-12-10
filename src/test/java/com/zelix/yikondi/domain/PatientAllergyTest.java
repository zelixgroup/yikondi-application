package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientAllergyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientAllergy.class);
        PatientAllergy patientAllergy1 = new PatientAllergy();
        patientAllergy1.setId(1L);
        PatientAllergy patientAllergy2 = new PatientAllergy();
        patientAllergy2.setId(patientAllergy1.getId());
        assertThat(patientAllergy1).isEqualTo(patientAllergy2);
        patientAllergy2.setId(2L);
        assertThat(patientAllergy1).isNotEqualTo(patientAllergy2);
        patientAllergy1.setId(null);
        assertThat(patientAllergy1).isNotEqualTo(patientAllergy2);
    }
}
