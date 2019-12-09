package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientInsuranceCoverage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientInsuranceCoverage} entity.
 */
public interface PatientInsuranceCoverageSearchRepository extends ElasticsearchRepository<PatientInsuranceCoverage, Long> {
}
