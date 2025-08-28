package ${basePackage}.repository;

import ${basePackage}.entity.${entity.className};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ${entity.className}Repository extends JpaRepository<${entity.className}, Long>
        , JpaSpecificationExecutor<${entity.className}> {

}