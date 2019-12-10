package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.EmergencyAmbulance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EmergencyAmbulance} entity.
 */
public interface EmergencyAmbulanceSearchRepository extends ElasticsearchRepository<EmergencyAmbulance, Long> {
}
