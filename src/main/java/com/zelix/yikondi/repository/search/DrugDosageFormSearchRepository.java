package com.zelix.yikondi.repository.search;
import com.zelix.yikondi.domain.DrugDosageForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DrugDosageForm} entity.
 */
public interface DrugDosageFormSearchRepository extends ElasticsearchRepository<DrugDosageForm, Long> {
}
