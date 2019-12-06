package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientFavoriteDoctorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientFavoriteDoctor.class);
        PatientFavoriteDoctor patientFavoriteDoctor1 = new PatientFavoriteDoctor();
        patientFavoriteDoctor1.setId(1L);
        PatientFavoriteDoctor patientFavoriteDoctor2 = new PatientFavoriteDoctor();
        patientFavoriteDoctor2.setId(patientFavoriteDoctor1.getId());
        assertThat(patientFavoriteDoctor1).isEqualTo(patientFavoriteDoctor2);
        patientFavoriteDoctor2.setId(2L);
        assertThat(patientFavoriteDoctor1).isNotEqualTo(patientFavoriteDoctor2);
        patientFavoriteDoctor1.setId(null);
        assertThat(patientFavoriteDoctor1).isNotEqualTo(patientFavoriteDoctor2);
    }
}
