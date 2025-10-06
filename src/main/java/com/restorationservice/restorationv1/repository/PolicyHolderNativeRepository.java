package com.restorationservice.restorationv1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PolicyHolderNativeRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional
  public void addPolicyHolder(long policyId, String name) {
    String sql = "insert into policy_holder (policy_id, holder_name) values (?, ?)";
    jdbcTemplate.update(sql, policyId, name);
  }

  @Transactional
  public void removePolicyHolder(long policyId, String name) {
    String sql = "delete from policy_holder where policy_id = ? and name = ?";
    jdbcTemplate.update(sql, policyId, name);
  }

  @Transactional
  public void removeAllPolicyHolders(long policyId) {
    String sql = "delete from policy_holder where policy_id = ?";
    jdbcTemplate.update(sql, policyId);
  }
}
