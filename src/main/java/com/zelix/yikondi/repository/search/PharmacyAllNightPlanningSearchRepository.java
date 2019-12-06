package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PharmacyAllNightPlanning;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PharmacyAllNightPlanning} entity.
 */
public interface PharmacyAllNightPlanningSearchRepository extends ElasticsearchRepository<PharmacyAllNightPlanning, Long> {
}
