package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class LifeConstantUnitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifeConstantUnit.class);
        LifeConstantUnit lifeConstantUnit1 = new LifeConstantUnit();
        lifeConstantUnit1.setId(1L);
        LifeConstantUnit lifeConstantUnit2 = new LifeConstantUnit();
        lifeConstantUnit2.setId(lifeConstantUnit1.getId());
        assertThat(lifeConstantUnit1).isEqualTo(lifeConstantUnit2);
        lifeConstantUnit2.setId(2L);
        assertThat(lifeConstantUnit1).isNotEqualTo(lifeConstantUnit2);
        lifeConstantUnit1.setId(null);
        assertThat(lifeConstantUnit1).isNotEqualTo(lifeConstantUnit2);
    }
}
