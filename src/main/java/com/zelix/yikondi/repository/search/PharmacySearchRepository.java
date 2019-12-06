package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Pharmacy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pharmacy} entity.
 */
public interface PharmacySearchRepository extends ElasticsearchRepository<Pharmacy, Long> {
}
