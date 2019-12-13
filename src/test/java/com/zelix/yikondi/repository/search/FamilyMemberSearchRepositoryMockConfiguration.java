package com.zelix.yikondi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FamilyMemberSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FamilyMemberSearchRepositoryMockConfiguration {

    @MockBean
    private FamilyMemberSearchRepository mockFamilyMemberSearchRepository;

}
