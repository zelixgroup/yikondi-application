package com.zelix.yikondi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PatientLifeConstantSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PatientLifeConstantSearchRepositoryMockConfiguration {

    @MockBean
    private PatientLifeConstantSearchRepository mockPatientLifeConstantSearchRepository;

}
