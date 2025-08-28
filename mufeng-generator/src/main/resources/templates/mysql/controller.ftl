package ${basePackage}.controller;

import ${basePackage}.entity.${entity.className};
import ${basePackage}.service.${entity.className}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ${basePackage}.entity.RespResult;
import java.util.List;
import java.util.Optional;

/**
* ${table.tableComment}控制器
*
* @author mufeng
* @date ${date}
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${entity.className?uncap_first}s")
public class ${entity.className}Controller {

    private final ${entity.className}Service ${entity.className?uncap_first}Service;

    @GetMapping
    public RespResult<List<${entity.className}>> getAll() {
        List<${entity.className}> result = ${entity.className?uncap_first}Service.findAll();
        return RespResult.success(result);
    }

    @PostMapping
    public RespResult<${entity.className}> create(@RequestBody ${entity.className} ${entity.className?uncap_first}) {
        ${entity.className} saved = ${entity.className?uncap_first}Service.save(${entity.className?uncap_first});
        return RespResult.success(saved);
    }

    @GetMapping("/{id}")
    public RespResult<${entity.className}> getById(@PathVariable String id) {
        ${entity.className} result = ${entity.className?uncap_first}Service.findById(id);
        return RespResult.success(result);
    }

    @PutMapping("/{id}")
    public RespResult<${entity.className}> update(@RequestBody ${entity.className} ${entity.className?uncap_first}) {
            ${entity.className} updated = ${entity.className?uncap_first}Service.update(${entity.className?uncap_first});
            return RespResult.success(updated);
    }

    @DeleteMapping("/{id}")
    public RespResult<Void> delete(@PathVariable String id) {
            ${entity.className?uncap_first}Service.deleteById(id);
            return RespResult.success("删除");
        }
}
