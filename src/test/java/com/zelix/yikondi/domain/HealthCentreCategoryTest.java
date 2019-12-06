package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class HealthCentreCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthCentreCategory.class);
        HealthCentreCategory healthCentreCategory1 = new HealthCentreCategory();
        healthCentreCategory1.setId(1L);
        HealthCentreCategory healthCentreCategory2 = new HealthCentreCategory();
        healthCentreCategory2.setId(healthCentreCategory1.getId());
        assertThat(healthCentreCategory1).isEqualTo(healthCentreCategory2);
        healthCentreCategory2.setId(2L);
        assertThat(healthCentreCategory1).isNotEqualTo(healthCentreCategory2);
        healthCentreCategory1.setId(null);
        assertThat(healthCentreCategory1).isNotEqualTo(healthCentreCategory2);
    }
}
