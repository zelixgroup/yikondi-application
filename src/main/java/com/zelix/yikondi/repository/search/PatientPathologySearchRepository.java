package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientPathology;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientPathology} entity.
 */
public interface PatientPathologySearchRepository extends ElasticsearchRepository<PatientPathology, Long> {
}
