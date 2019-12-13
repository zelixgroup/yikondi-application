package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.MedicalRecordAuthorization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalRecordAuthorization} entity.
 */
public interface MedicalRecordAuthorizationSearchRepository extends ElasticsearchRepository<MedicalRecordAuthorization, Long> {
}
