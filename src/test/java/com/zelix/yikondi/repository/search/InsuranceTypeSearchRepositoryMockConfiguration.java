package com.zelix.yikondi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InsuranceTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InsuranceTypeSearchRepositoryMockConfiguration {

    @MockBean
    private InsuranceTypeSearchRepository mockInsuranceTypeSearchRepository;

}
