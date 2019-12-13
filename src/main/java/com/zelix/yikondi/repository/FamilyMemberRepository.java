package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.FamilyMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FamilyMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

}
