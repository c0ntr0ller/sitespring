<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as l>
<@common.page>
    <#--${message?ifExists}-->
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <@l.login "/login" false />
</@common.page>