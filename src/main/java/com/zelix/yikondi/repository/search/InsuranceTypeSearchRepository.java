package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.InsuranceType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InsuranceType} entity.
 */
public interface InsuranceTypeSearchRepository extends ElasticsearchRepository<InsuranceType, Long> {
}
