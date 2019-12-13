package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.MedicalPrescriptionDrug;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalPrescriptionDrug} entity.
 */
public interface MedicalPrescriptionDrugSearchRepository extends ElasticsearchRepository<MedicalPrescriptionDrug, Long> {
}
