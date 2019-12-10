package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientLifeConstantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientLifeConstant.class);
        PatientLifeConstant patientLifeConstant1 = new PatientLifeConstant();
        patientLifeConstant1.setId(1L);
        PatientLifeConstant patientLifeConstant2 = new PatientLifeConstant();
        patientLifeConstant2.setId(patientLifeConstant1.getId());
        assertThat(patientLifeConstant1).isEqualTo(patientLifeConstant2);
        patientLifeConstant2.setId(2L);
        assertThat(patientLifeConstant1).isNotEqualTo(patientLifeConstant2);
        patientLifeConstant1.setId(null);
        assertThat(patientLifeConstant1).isNotEqualTo(patientLifeConstant2);
    }
}
