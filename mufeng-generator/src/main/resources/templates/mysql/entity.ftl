package ${basePackage}.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "${entity.tableName}")
public class ${entity.className} {
<#list entity.fields as field>
    <#if field.isId>
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    </#if>
    @Column(name = "${field.columnName}"<#if !field.nullable>, nullable = false</#if><#if field.length??>, length = ${field.length}</#if>)
    private ${field.type} ${field.name};

</#list>
}