package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author ${author}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

 IPage<${entity}> get${entity}PageList(IPage<${entity}> iPage , ${entity} i${entity});

 IPage<${entity}> get${entity}SqlPageList(IPage<${entity}> iPage , ${entity} i${entity});
}
</#if>
