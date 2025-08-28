package ${basePackage}.service.impl;

import ${basePackage}.entity.${entity.className};
import ${basePackage}.repository.${entity.className}Repository;
import ${basePackage}.service.${entity.className}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ${entity.className}ServiceImpl implements ${entity.className}Service {

    private final ${entity.className}Repository ${entity.className?uncap_first}Repository;

    @Override
    public List<${entity.className}> findAll() {
        return ${entity.className?uncap_first}Repository.findAll();
    }

    @Override
    public ${entity.className} save(${entity.className} ${entity.className?uncap_first}) {
        return ${entity.className?uncap_first}Repository.save(${entity.className?uncap_first});
    }

    @Override
    public Optional<${entity.className}> findById(String id) {
        return ${entity.className?uncap_first}Repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        ${entity.className?uncap_first}Repository.deleteById(id);
    }

    @Override
    public ${entity.className} update(${entity.className} ${entity.className?uncap_first}) {
        return ${entity.className?uncap_first}Repository.save(${entity.className?uncap_first})
    }
}