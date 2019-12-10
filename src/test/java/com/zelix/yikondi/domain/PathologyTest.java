package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class PathologyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pathology.class);
        Pathology pathology1 = new Pathology();
        pathology1.setId(1L);
        Pathology pathology2 = new Pathology();
        pathology2.setId(pathology1.getId());
        assertThat(pathology1).isEqualTo(pathology2);
        pathology2.setId(2L);
        assertThat(pathology1).isNotEqualTo(pathology2);
        pathology1.setId(null);
        assertThat(pathology1).isNotEqualTo(pathology2);
    }
}
