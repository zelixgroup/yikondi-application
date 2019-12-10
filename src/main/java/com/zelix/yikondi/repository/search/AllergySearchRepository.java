package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Allergy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Allergy} entity.
 */
public interface AllergySearchRepository extends ElasticsearchRepository<Allergy, Long> {
}
