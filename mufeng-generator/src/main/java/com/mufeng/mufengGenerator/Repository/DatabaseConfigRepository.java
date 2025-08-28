package com.mufeng.mufengGenerator.Repository;

import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseConfigRepository extends JpaRepository<DatabaseConfig, String>
        , JpaSpecificationExecutor<DatabaseConfig> {

}
