package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PharmacyAllNightPlanningTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmacyAllNightPlanning.class);
        PharmacyAllNightPlanning pharmacyAllNightPlanning1 = new PharmacyAllNightPlanning();
        pharmacyAllNightPlanning1.setId(1L);
        PharmacyAllNightPlanning pharmacyAllNightPlanning2 = new PharmacyAllNightPlanning();
        pharmacyAllNightPlanning2.setId(pharmacyAllNightPlanning1.getId());
        assertThat(pharmacyAllNightPlanning1).isEqualTo(pharmacyAllNightPlanning2);
        pharmacyAllNightPlanning2.setId(2L);
        assertThat(pharmacyAllNightPlanning1).isNotEqualTo(pharmacyAllNightPlanning2);
        pharmacyAllNightPlanning1.setId(null);
        assertThat(pharmacyAllNightPlanning1).isNotEqualTo(pharmacyAllNightPlanning2);
    }
}
