package com.zdzimi.miolingo.data.repository;

import com.zdzimi.miolingo.data.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByActivationCode(String code);

}
