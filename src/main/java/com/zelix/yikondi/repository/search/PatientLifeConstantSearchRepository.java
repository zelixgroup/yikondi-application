package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientLifeConstant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientLifeConstant} entity.
 */
public interface PatientLifeConstantSearchRepository extends ElasticsearchRepository<PatientLifeConstant, Long> {
}
