package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.Doctor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Doctor} entity.
 */
public interface DoctorSearchRepository extends ElasticsearchRepository<Doctor, Long> {
}
