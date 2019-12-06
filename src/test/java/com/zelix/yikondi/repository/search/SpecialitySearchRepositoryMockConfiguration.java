package com.zelix.yikondi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SpecialitySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SpecialitySearchRepositoryMockConfiguration {

    @MockBean
    private SpecialitySearchRepository mockSpecialitySearchRepository;

}
