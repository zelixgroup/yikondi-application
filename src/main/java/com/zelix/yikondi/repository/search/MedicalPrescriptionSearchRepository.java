package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.MedicalPrescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalPrescription} entity.
 */
public interface MedicalPrescriptionSearchRepository extends ElasticsearchRepository<MedicalPrescription, Long> {
}
