package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.FamilyRelationship;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FamilyRelationship} entity.
 */
public interface FamilyRelationshipSearchRepository extends ElasticsearchRepository<FamilyRelationship, Long> {
}
