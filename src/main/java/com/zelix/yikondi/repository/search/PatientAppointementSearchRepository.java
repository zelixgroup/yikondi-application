package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientAppointement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientAppointement} entity.
 */
public interface PatientAppointementSearchRepository extends ElasticsearchRepository<PatientAppointement, Long> {
}
