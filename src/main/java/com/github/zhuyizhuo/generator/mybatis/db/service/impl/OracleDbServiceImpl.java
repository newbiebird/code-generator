package com.github.zhuyizhuo.generator.mybatis.db.service.impl;

import com.github.zhuyizhuo.generator.mybatis.db.service.abst.AbstractDbService;
import com.github.zhuyizhuo.generator.mybatis.vo.TableInfo;
import com.github.zhuyizhuo.generator.utils.TypeConversion;
import com.google.common.collect.Lists;
import com.github.zhuyizhuo.generator.mybatis.database.mapper.OracleDataBaseMapper;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.ColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.DataBaseInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.DbTableInfo;
import com.github.zhuyizhuo.generator.mybatis.utils.SqlSessionUtils;
import com.github.zhuyizhuo.generator.utils.LogUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * class: OracleDbServiceImpl <br>
 * description: oracle数据库查询表结构实现 <br>
 * time: 2018/8/6 12:58
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public class OracleDbServiceImpl extends AbstractDbService {

    @Override
    public List<TableInfo> getTableColumns() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        OracleDataBaseMapper mapper = sqlSession.getMapper(OracleDataBaseMapper.class);
        DataBaseInfo dataBaseInfo = getDataBaseInfo();
        List<DbTableInfo> tableList  = mapper.getTableNameListBySchema(dataBaseInfo);
        LogUtils.printInfo("共查询出" + tableList.size() + "张表.");
        List<TableInfo> tableInfos = getTableInfos(mapper, tableList);
        sqlSession.close();
        return tableInfos;
    }

    private List<TableInfo> getTableInfos(OracleDataBaseMapper mapper, List<DbTableInfo> tableList) {
        List<TableInfo> tableInfos = Lists.newArrayList();
        TableInfo tableInfo = null;
        for (int i = 0; i < tableList.size(); i++) {
            DbTableInfo dbTableInfo = tableList.get(i);
            String tableName = dbTableInfo.getTableName();
            DbTableInfo allColumnsByTable = mapper.getAllColumnsByTable(dbTableInfo.getTableSchema(), tableName);
            tableInfo = new TableInfo();
            setTableInfo(allColumnsByTable,tableInfo);
            tableInfo.setJavaTableName(getJavaTableName(tableName));
            tableInfo.addPrimaryKeyColumn(getPrimaryKeys(mapper,dbTableInfo));
            tableInfos.add(tableInfo);
            LogUtils.printInfo(tableName + "表共" + allColumnsByTable.getColumnLists().size() + "列");
        }
        return tableInfos;
    }

    private List<ColumnInfo> getPrimaryKeys(OracleDataBaseMapper mapper, DbTableInfo dbTableInfo) {
        return mapper.getPrimaryKeys(dbTableInfo);
    }

    @Override
    protected String getJavaDataType(ColumnInfo columnInfo) {
        return TypeConversion.getTypeByMap(TypeConversion.oracleDbType2JavaMap, getDataType(columnInfo.getDataType()));
    }
}
