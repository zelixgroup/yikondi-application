package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class DrugDosageFormTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugDosageForm.class);
        DrugDosageForm drugDosageForm1 = new DrugDosageForm();
        drugDosageForm1.setId(1L);
        DrugDosageForm drugDosageForm2 = new DrugDosageForm();
        drugDosageForm2.setId(drugDosageForm1.getId());
        assertThat(drugDosageForm1).isEqualTo(drugDosageForm2);
        drugDosageForm2.setId(2L);
        assertThat(drugDosageForm1).isNotEqualTo(drugDosageForm2);
        drugDosageForm1.setId(null);
        assertThat(drugDosageForm1).isNotEqualTo(drugDosageForm2);
    }
}
