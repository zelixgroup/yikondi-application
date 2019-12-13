package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class MedicalPrescriptionDrugTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalPrescriptionDrug.class);
        MedicalPrescriptionDrug medicalPrescriptionDrug1 = new MedicalPrescriptionDrug();
        medicalPrescriptionDrug1.setId(1L);
        MedicalPrescriptionDrug medicalPrescriptionDrug2 = new MedicalPrescriptionDrug();
        medicalPrescriptionDrug2.setId(medicalPrescriptionDrug1.getId());
        assertThat(medicalPrescriptionDrug1).isEqualTo(medicalPrescriptionDrug2);
        medicalPrescriptionDrug2.setId(2L);
        assertThat(medicalPrescriptionDrug1).isNotEqualTo(medicalPrescriptionDrug2);
        medicalPrescriptionDrug1.setId(null);
        assertThat(medicalPrescriptionDrug1).isNotEqualTo(medicalPrescriptionDrug2);
    }
}
