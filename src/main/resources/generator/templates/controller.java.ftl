package ${package.Controller};


import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.service.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import com.hismart.document.common.controller.BaseController;
import com.hismart.document.common.domain.HismartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* @author ${author}
* @since ${date}
*/
@Slf4j
@Validated
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}/api/v1")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>

    public class ${table.controllerName} extends BaseController{
    private final ${table.serviceName}  i${table.serviceName};

    @Autowired
    public  ${table.controllerName} ( ${table.serviceName}   i${table.serviceName}) {
    this. i${table.serviceName} =  i${table.serviceName};
      }

    /**
    * ${entity} 分页查询数据
    *
    * @param request       request
    * @param i${entity}    i${entity}
    * @return HismartResponse
    */
    @GetMapping("/get/page")
    public HismartResponse get${entity}PageList(QueryRequest request, ${entity} i${entity}) {
        try {
            IPage< ${entity}> page = this.getPage(request);
            return success(this. i${table.serviceName}.get${entity}PageList(page, i${entity}));
        } catch (Exception e) {
            log.error("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}/api/v1/get/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * ${entity} sql分页查询数据
    *
    * @param request       request
    * @param i${entity}    i${entity}
    * @return HismartResponse
    */
    @GetMapping("/get/sql/page")
    public HismartResponse get${entity}SqlPageList(QueryRequest request, ${entity} i${entity}) {
        try {
            IPage< ${entity}> page = this.getPage(request);
            return success(this. i${table.serviceName}.get${entity}SqlPageList(page, i${entity}));
        } catch (Exception e) {
            log.error("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}/api/v1/get/sql/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * ${entity} 新增
    *
    * @param i${entity}    i${entity}
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PutMapping("/add")
    public HismartResponse add${entity}(@Valid ${entity} i${entity}, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
             return success(this.i${table.serviceName}.save(i${entity}));
        } catch (Exception e) {
            log.error("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }



    /**
    * ${entity} 修改
    *
    * @param i${entity}     i${entity}
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid ${entity} i${entity}, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
           return success(this.i${table.serviceName}.updateById(i${entity}));
        } catch (Exception e) {
            log.error("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
    * ${entity} 根据id删除
    *
    * @param id 主键Id
    * @return HismartResponse
    */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.i${table.serviceName}.removeById(id));
        } catch (Exception e) {
           log.error("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}/api/v1/delete/{}  error,[{}]", id, e.getMessage());
           return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

</#if>
