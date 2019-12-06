package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Holiday;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Holiday} entity.
 */
public interface HolidaySearchRepository extends ElasticsearchRepository<Holiday, Long> {
}
