<#macro pager url page>

    <#if page.getTotalPages() gt 7>
        <#assign
            totalPages = page.getTotalPages()
            pageNumber = page.getNumber() + 1

            head = (pageNumber gt 4)?then([-1], [1, 2, 3])
            tail = (pageNumber lt totalPages - 3)?then([-1], [totalPages - 2, totalPages - 1, totalPages])
            bodyBefore = (pageNumber gt 4 && (pageNumber < totalPages - 1))?then([pageNumber - 2, pageNumber - 1], [])
            bodyAfter = (pageNumber gt 2 && (pageNumber < totalPages - 3))?then([pageNumber + 1, pageNumber + 2], [])
            body = head + bodyBefore + (pageNumber gt 3 && (pageNumber < totalPages - 2))?then([pageNumber], []) + bodyAfter + tail
        >
    <#else>
        <#assign
            body = 1..page.getTotalPages()
        >
    </#if>
<#--<div>-->
    <#--<p>head=<#if head??><#list head as cell>${cell}</#list></#if></p>-->
    <#--<p>tail=<#if tail??><#list tail as cell>${cell}</#list></#if></p>-->
    <#--<p>bodyBefore=<#if bodyBefore??><#list bodyBefore as cell>${cell}</#list></#if></p>-->
    <#--<p>bodyAfter=<#if bodyAfter??><#list bodyAfter as cell>${cell}</#list></#if></p>-->
    <#--<p>body=<#if body??><#list body as cell>${cell}</#list> </#if></p>-->

<#--</div>-->
    <div class="mt-3">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="${url}?page=0&size=${page.getSize()}" tabindex="-1">First</a>
            </li>
            <#list body as pl>
                <#if (pl - 1) == page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${pl}</a>
                    </li>
                    <#elseif pl = -1>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${pl - 1}&size=${page.getSize()}" tabindex="-1">${pl}</a>
                    </li>
                </#if>
            </#list>
            <li class="page-item">
                <a class="page-link" href="${url}?page=${page.getTotalPages()-1}&size=${page.getSize()}">Last</a>
            </li>
        </ul>
    </div>
    <div class="mt-3">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Элементов на странице</a>
            </li>
            <#list [3,5,10,20,50] as s>
                <#if s == page.getSize()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${s}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${page.getNumber()}&size=${s}" tabindex="-1">${s}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</#macro>