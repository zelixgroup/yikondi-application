package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.PatientFavoritePharmacy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PatientFavoritePharmacy} entity.
 */
public interface PatientFavoritePharmacySearchRepository extends ElasticsearchRepository<PatientFavoritePharmacy, Long> {
}
