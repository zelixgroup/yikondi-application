package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.DoctorSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DoctorSchedule} entity.
 */
public interface DoctorScheduleSearchRepository extends ElasticsearchRepository<DoctorSchedule, Long> {
}
