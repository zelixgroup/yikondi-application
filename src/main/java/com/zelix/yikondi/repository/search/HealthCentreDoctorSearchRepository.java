package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.HealthCentreDoctor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HealthCentreDoctor} entity.
 */
public interface HealthCentreDoctorSearchRepository extends ElasticsearchRepository<HealthCentreDoctor, Long> {
}
