<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as l>
<@common.page>
    ${message?ifExists}
    <@l.login "/login" false />
</@common.page>