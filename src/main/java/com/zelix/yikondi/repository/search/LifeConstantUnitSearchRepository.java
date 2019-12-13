package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.LifeConstantUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LifeConstantUnit} entity.
 */
public interface LifeConstantUnitSearchRepository extends ElasticsearchRepository<LifeConstantUnit, Long> {
}
