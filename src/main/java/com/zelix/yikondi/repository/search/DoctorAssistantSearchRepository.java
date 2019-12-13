package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.DoctorAssistant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DoctorAssistant} entity.
 */
public interface DoctorAssistantSearchRepository extends ElasticsearchRepository<DoctorAssistant, Long> {
}
