package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Analysis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Analysis} entity.
 */
public interface AnalysisSearchRepository extends ElasticsearchRepository<Analysis, Long> {
}
