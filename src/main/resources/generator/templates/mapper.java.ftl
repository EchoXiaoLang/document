package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author ${author}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>i
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

  List<${entity}>  get${entity}SqlPageList(IPage<${entity}> iPage, ${entity} i${entity});
}
</#if>
