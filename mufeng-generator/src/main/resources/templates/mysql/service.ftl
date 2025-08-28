package ${basePackage}.service;

import ${basePackage}.entity.${entity.className};
import java.util.List;

public interface ${entity.className}Service {

    List<${entity.className}> findAll();

    ${entity.className} save(${entity.className} ${entity.className?uncap_first});

    ${entity.className} findById(String id);

    void deleteById(String id);

    ${entity.className} update(${entity.className} ${entity.className?uncap_first});
}