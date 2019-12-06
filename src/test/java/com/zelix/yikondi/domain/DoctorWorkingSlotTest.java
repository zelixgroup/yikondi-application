package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class DoctorWorkingSlotTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorWorkingSlot.class);
        DoctorWorkingSlot doctorWorkingSlot1 = new DoctorWorkingSlot();
        doctorWorkingSlot1.setId(1L);
        DoctorWorkingSlot doctorWorkingSlot2 = new DoctorWorkingSlot();
        doctorWorkingSlot2.setId(doctorWorkingSlot1.getId());
        assertThat(doctorWorkingSlot1).isEqualTo(doctorWorkingSlot2);
        doctorWorkingSlot2.setId(2L);
        assertThat(doctorWorkingSlot1).isNotEqualTo(doctorWorkingSlot2);
        doctorWorkingSlot1.setId(null);
        assertThat(doctorWorkingSlot1).isNotEqualTo(doctorWorkingSlot2);
    }
}
