package com.thinkful.noteful.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
  Account findByUsername(String username);
}