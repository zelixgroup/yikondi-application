package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PatientFavoritePharmacyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientFavoritePharmacy.class);
        PatientFavoritePharmacy patientFavoritePharmacy1 = new PatientFavoritePharmacy();
        patientFavoritePharmacy1.setId(1L);
        PatientFavoritePharmacy patientFavoritePharmacy2 = new PatientFavoritePharmacy();
        patientFavoritePharmacy2.setId(patientFavoritePharmacy1.getId());
        assertThat(patientFavoritePharmacy1).isEqualTo(patientFavoritePharmacy2);
        patientFavoritePharmacy2.setId(2L);
        assertThat(patientFavoritePharmacy1).isNotEqualTo(patientFavoritePharmacy2);
        patientFavoritePharmacy1.setId(null);
        assertThat(patientFavoritePharmacy1).isNotEqualTo(patientFavoritePharmacy2);
    }
}
