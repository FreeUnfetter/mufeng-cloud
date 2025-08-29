package com.mufeng.mufengConstants.constants;

/**
 * 代码生成模块常量
 */
public interface GeneratorConstant {
    /** 模版常量 */
    String T_CONTROLLER = "controller.java.vm";
    String T_SERVICE = "service.java.vm";
    String T_SERVICE_IMPL = "serviceImpl.java.vm";
    String T_ENTITY = "entity.java.vm";
    String T_REPOSITORY = "repository.java.vm";

    /** 路径常量 */
    String P_CONTROLLER = "controller";
    String P_SERVICE = "service";
    String P_SERVICE_IMPL = "service/impl";
    String P_ENTITY = "entity";
    String P_REPOSITORY = "repository";

    /** 下载常量 */
    String FILE_NAME = "batch-package";
    String CONTENT_TYPE = "application/zip";
    String HEADER = "Content-Disposition";
}
