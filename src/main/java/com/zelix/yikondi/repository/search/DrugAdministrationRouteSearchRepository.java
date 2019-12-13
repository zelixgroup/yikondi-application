package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.DrugAdministrationRoute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DrugAdministrationRoute} entity.
 */
public interface DrugAdministrationRouteSearchRepository extends ElasticsearchRepository<DrugAdministrationRoute, Long> {
}
