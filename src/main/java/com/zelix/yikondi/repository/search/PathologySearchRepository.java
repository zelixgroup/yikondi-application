package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Pathology;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pathology} entity.
 */
public interface PathologySearchRepository extends ElasticsearchRepository<Pathology, Long> {
}
