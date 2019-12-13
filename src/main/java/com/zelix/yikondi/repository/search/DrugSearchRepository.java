package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Drug;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Drug} entity.
 */
public interface DrugSearchRepository extends ElasticsearchRepository<Drug, Long> {
}
