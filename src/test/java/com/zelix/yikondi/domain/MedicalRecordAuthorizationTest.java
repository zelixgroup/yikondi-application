package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class MedicalRecordAuthorizationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalRecordAuthorization.class);
        MedicalRecordAuthorization medicalRecordAuthorization1 = new MedicalRecordAuthorization();
        medicalRecordAuthorization1.setId(1L);
        MedicalRecordAuthorization medicalRecordAuthorization2 = new MedicalRecordAuthorization();
        medicalRecordAuthorization2.setId(medicalRecordAuthorization1.getId());
        assertThat(medicalRecordAuthorization1).isEqualTo(medicalRecordAuthorization2);
        medicalRecordAuthorization2.setId(2L);
        assertThat(medicalRecordAuthorization1).isNotEqualTo(medicalRecordAuthorization2);
        medicalRecordAuthorization1.setId(null);
        assertThat(medicalRecordAuthorization1).isNotEqualTo(medicalRecordAuthorization2);
    }
}
