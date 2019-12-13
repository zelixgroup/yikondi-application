package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class DrugAdministrationRouteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugAdministrationRoute.class);
        DrugAdministrationRoute drugAdministrationRoute1 = new DrugAdministrationRoute();
        drugAdministrationRoute1.setId(1L);
        DrugAdministrationRoute drugAdministrationRoute2 = new DrugAdministrationRoute();
        drugAdministrationRoute2.setId(drugAdministrationRoute1.getId());
        assertThat(drugAdministrationRoute1).isEqualTo(drugAdministrationRoute2);
        drugAdministrationRoute2.setId(2L);
        assertThat(drugAdministrationRoute1).isNotEqualTo(drugAdministrationRoute2);
        drugAdministrationRoute1.setId(null);
        assertThat(drugAdministrationRoute1).isNotEqualTo(drugAdministrationRoute2);
    }
}
