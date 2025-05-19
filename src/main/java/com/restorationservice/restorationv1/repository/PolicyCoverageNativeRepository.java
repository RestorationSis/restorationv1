package com.restorationservice.restorationv1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PolicyCoverageNativeRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional
  public void addPolicyCoverage(long policyId, long coverageId) {
    String sql = "insert into policy_coverage (policy_fk, coverage_fk) values (?, ?)";
    jdbcTemplate.update(sql, policyId, coverageId);
  }

  @Transactional
  public void removePolicyCoverage(long policyId, long coverageId) {
    String sql = "delete from policy_coverage where policy_fk = ? and coverage_fk = ?";
    jdbcTemplate.update(sql, policyId, coverageId);
  }
}