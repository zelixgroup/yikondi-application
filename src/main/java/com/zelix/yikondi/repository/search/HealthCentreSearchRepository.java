package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.HealthCentre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HealthCentre} entity.
 */
public interface HealthCentreSearchRepository extends ElasticsearchRepository<HealthCentre, Long> {
}
