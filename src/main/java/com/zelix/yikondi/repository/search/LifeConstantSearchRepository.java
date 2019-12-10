package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.LifeConstant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LifeConstant} entity.
 */
public interface LifeConstantSearchRepository extends ElasticsearchRepository<LifeConstant, Long> {
}
