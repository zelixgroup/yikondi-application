package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Speciality;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Speciality} entity.
 */
public interface SpecialitySearchRepository extends ElasticsearchRepository<Speciality, Long> {
}
