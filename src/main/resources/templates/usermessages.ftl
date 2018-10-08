<#import "parts/common.ftl" as common>
<@common.page>
<#if isCurrentUser>
    <#include "parts/messageedit.ftl">
</#if>

<#include "parts/messagelist.ftl">
</@common.page>