package com.zelix.yikondi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.yikondi.web.rest.TestUtil;

public class DoctorScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorSchedule.class);
        DoctorSchedule doctorSchedule1 = new DoctorSchedule();
        doctorSchedule1.setId(1L);
        DoctorSchedule doctorSchedule2 = new DoctorSchedule();
        doctorSchedule2.setId(doctorSchedule1.getId());
        assertThat(doctorSchedule1).isEqualTo(doctorSchedule2);
        doctorSchedule2.setId(2L);
        assertThat(doctorSchedule1).isNotEqualTo(doctorSchedule2);
        doctorSchedule1.setId(null);
        assertThat(doctorSchedule1).isNotEqualTo(doctorSchedule2);
    }
}
