package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author ${author}
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private  ${table.mapperName}  i${table.mapperName};

    @Override
    public IPage<${entity}> get${entity}PageList(IPage<${entity}> iPage, ${entity}  i${entity}) {
      try {
           LambdaQueryWrapper<${entity}> queryWrapper = new LambdaQueryWrapper<>();
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("get${entity}PageList", e);
           return null;
       }
    }

    @Override
    public IPage<${entity}> get${entity}SqlPageList(IPage<${entity}> iPage, ${entity} i${entity}) {
        iPage.setRecords( i${table.mapperName}.get${entity}SqlPageList(iPage, i${entity}));
        return iPage;
        }
}
</#if>
