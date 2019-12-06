package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PharmacyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pharmacy.class);
        Pharmacy pharmacy1 = new Pharmacy();
        pharmacy1.setId(1L);
        Pharmacy pharmacy2 = new Pharmacy();
        pharmacy2.setId(pharmacy1.getId());
        assertThat(pharmacy1).isEqualTo(pharmacy2);
        pharmacy2.setId(2L);
        assertThat(pharmacy1).isNotEqualTo(pharmacy2);
        pharmacy1.setId(null);
        assertThat(pharmacy1).isNotEqualTo(pharmacy2);
    }
}
