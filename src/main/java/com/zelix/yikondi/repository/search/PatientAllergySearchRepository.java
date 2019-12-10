package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientAllergy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientAllergy} entity.
 */
public interface PatientAllergySearchRepository extends ElasticsearchRepository<PatientAllergy, Long> {
}
