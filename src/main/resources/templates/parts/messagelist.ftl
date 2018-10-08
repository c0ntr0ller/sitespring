<div><b>Список сообщений</b></div>
<div class="card-columns">
    <#list messages as message>
    <div class="card my-3 border border-info rounded">
        <p class="card-header text-muted">${message.id!}</p>
        <div class="card-body m-2">
            <p class="card-text">${message.text!}</p>
            <p class="card-text">${message.tag!}</p>
        </div>
        <#if message.filename??>
        <div class="card-img-bottom m-2">
            <img src="/img/${message.filename}">
        </div>
        </#if>
        <p class="card-footer text-muted">${message.authorName!}</p>
    </div>
    <#else>
        No messages
    </#list>
</div>