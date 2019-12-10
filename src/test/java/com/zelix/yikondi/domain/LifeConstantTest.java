package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class LifeConstantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifeConstant.class);
        LifeConstant lifeConstant1 = new LifeConstant();
        lifeConstant1.setId(1L);
        LifeConstant lifeConstant2 = new LifeConstant();
        lifeConstant2.setId(lifeConstant1.getId());
        assertThat(lifeConstant1).isEqualTo(lifeConstant2);
        lifeConstant2.setId(2L);
        assertThat(lifeConstant1).isNotEqualTo(lifeConstant2);
        lifeConstant1.setId(null);
        assertThat(lifeConstant1).isNotEqualTo(lifeConstant2);
    }
}
