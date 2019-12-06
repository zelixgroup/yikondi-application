package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientFavoriteDoctor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientFavoriteDoctor} entity.
 */
public interface PatientFavoriteDoctorSearchRepository extends ElasticsearchRepository<PatientFavoriteDoctor, Long> {
}
