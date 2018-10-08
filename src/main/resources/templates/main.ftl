<#import "parts/common.ftl" as common>

<@common.page>
<div class="form-row">
    <form method="get" action="/main" class="form-inline">
        <input type="text" class="form-control" name="filter" placeholder="filter messages" value="${filter?ifExists}">
        <button type="submit" class="btn btn-primary ml-1">Search</button>
    </form>
</div>

<#include "parts/messageedit.ftl">

<#include "parts/messagelist.ftl">
</@common.page>