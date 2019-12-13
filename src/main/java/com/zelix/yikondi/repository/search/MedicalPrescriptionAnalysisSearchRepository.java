package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.MedicalPrescriptionAnalysis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalPrescriptionAnalysis} entity.
 */
public interface MedicalPrescriptionAnalysisSearchRepository extends ElasticsearchRepository<MedicalPrescriptionAnalysis, Long> {
}
