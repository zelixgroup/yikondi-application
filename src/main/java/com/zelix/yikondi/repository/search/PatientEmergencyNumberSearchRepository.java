package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientEmergencyNumber;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientEmergencyNumber} entity.
 */
public interface PatientEmergencyNumberSearchRepository extends ElasticsearchRepository<PatientEmergencyNumber, Long> {
}
