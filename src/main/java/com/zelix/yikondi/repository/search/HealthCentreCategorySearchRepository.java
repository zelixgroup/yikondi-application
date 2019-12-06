package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.HealthCentreCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HealthCentreCategory} entity.
 */
public interface HealthCentreCategorySearchRepository extends ElasticsearchRepository<HealthCentreCategory, Long> {
}
