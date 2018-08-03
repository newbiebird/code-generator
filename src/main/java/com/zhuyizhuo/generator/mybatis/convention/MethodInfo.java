package com.zhuyizhuo.generator.mybatis.convention;

import java.text.MessageFormat;

/**
 * 方法名信息
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/29 15:40
 */
public class MethodInfo {

    /** 新增方法名 */
    private static final String INSERT_METHOD_FORMAT = "insert{0}";
    /** 删除方法名 */
    private static final String DELETE_METHOD_FORMAT = "delete{0}ByPrimaryKey";
    /** 更新方法名 */
    private static final String UPDATE_METHOD_FORMAT = "update{0}";
    /** 查询方法名 */
    private static final String QUERY_METHOD_FORMAT = "query{0}";
    /** 批量新增方法名*/
    private static final String BATCH_INSERT_METHOD_FORMAT = "batchInsert{0}";
    /** 分页查询方法名 */
    private static final String PAGING_QUERY_METHOD_FORMAT = "pagingQuery{0}";
    /** 查询总数方法名 */
    private static final String COUNT_METHOD_FORMAT = "countTotal{0}";

    private String insertMethodName;

    public String getInsertMethodName() {
        return insertMethodName;
    }

    public void setInsertMethodName(String insertMethodName) {
        this.insertMethodName = formatMethodName(INSERT_METHOD_FORMAT,insertMethodName);
    }

    /**
     * 格式化方法名
     * @param format 方法名格式化模板 例如 countTotal{0}
     * @param tableName 表名
     * @return 格式化后的方法名
     */
    public static String formatMethodName(String format,String tableName){
        return MessageFormat.format(format,tableName);
    }

    public void initMethodName(String javaTableName) {
        setInsertMethodName(javaTableName);
    }

}
