package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientInsuranceCoverageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientInsuranceCoverage.class);
        PatientInsuranceCoverage patientInsuranceCoverage1 = new PatientInsuranceCoverage();
        patientInsuranceCoverage1.setId(1L);
        PatientInsuranceCoverage patientInsuranceCoverage2 = new PatientInsuranceCoverage();
        patientInsuranceCoverage2.setId(patientInsuranceCoverage1.getId());
        assertThat(patientInsuranceCoverage1).isEqualTo(patientInsuranceCoverage2);
        patientInsuranceCoverage2.setId(2L);
        assertThat(patientInsuranceCoverage1).isNotEqualTo(patientInsuranceCoverage2);
        patientInsuranceCoverage1.setId(null);
        assertThat(patientInsuranceCoverage1).isNotEqualTo(patientInsuranceCoverage2);
    }
}
