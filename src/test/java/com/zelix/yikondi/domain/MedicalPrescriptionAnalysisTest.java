package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class MedicalPrescriptionAnalysisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalPrescriptionAnalysis.class);
        MedicalPrescriptionAnalysis medicalPrescriptionAnalysis1 = new MedicalPrescriptionAnalysis();
        medicalPrescriptionAnalysis1.setId(1L);
        MedicalPrescriptionAnalysis medicalPrescriptionAnalysis2 = new MedicalPrescriptionAnalysis();
        medicalPrescriptionAnalysis2.setId(medicalPrescriptionAnalysis1.getId());
        assertThat(medicalPrescriptionAnalysis1).isEqualTo(medicalPrescriptionAnalysis2);
        medicalPrescriptionAnalysis2.setId(2L);
        assertThat(medicalPrescriptionAnalysis1).isNotEqualTo(medicalPrescriptionAnalysis2);
        medicalPrescriptionAnalysis1.setId(null);
        assertThat(medicalPrescriptionAnalysis1).isNotEqualTo(medicalPrescriptionAnalysis2);
    }
}
