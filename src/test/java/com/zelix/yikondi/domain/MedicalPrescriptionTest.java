package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class MedicalPrescriptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalPrescription.class);
        MedicalPrescription medicalPrescription1 = new MedicalPrescription();
        medicalPrescription1.setId(1L);
        MedicalPrescription medicalPrescription2 = new MedicalPrescription();
        medicalPrescription2.setId(medicalPrescription1.getId());
        assertThat(medicalPrescription1).isEqualTo(medicalPrescription2);
        medicalPrescription2.setId(2L);
        assertThat(medicalPrescription1).isNotEqualTo(medicalPrescription2);
        medicalPrescription1.setId(null);
        assertThat(medicalPrescription1).isNotEqualTo(medicalPrescription2);
    }
}
