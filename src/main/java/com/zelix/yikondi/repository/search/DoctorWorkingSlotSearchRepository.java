package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.DoctorWorkingSlot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DoctorWorkingSlot} entity.
 */
public interface DoctorWorkingSlotSearchRepository extends ElasticsearchRepository<DoctorWorkingSlot, Long> {
}
