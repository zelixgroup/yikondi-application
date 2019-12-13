package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class FamilyRelationshipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyRelationship.class);
        FamilyRelationship familyRelationship1 = new FamilyRelationship();
        familyRelationship1.setId(1L);
        FamilyRelationship familyRelationship2 = new FamilyRelationship();
        familyRelationship2.setId(familyRelationship1.getId());
        assertThat(familyRelationship1).isEqualTo(familyRelationship2);
        familyRelationship2.setId(2L);
        assertThat(familyRelationship1).isNotEqualTo(familyRelationship2);
        familyRelationship1.setId(null);
        assertThat(familyRelationship1).isNotEqualTo(familyRelationship2);
    }
}
