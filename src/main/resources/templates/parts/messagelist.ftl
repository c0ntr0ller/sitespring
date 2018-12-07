<#include "security.ftl">

<div><b>Список сообщений</b></div>
<div class="row" id="message-list">
    <#list messages as message>
        <div class="col-sm-4 py-2" data-id="${message.id}">
            <div class="card h-100 my-3 border border-info rounded">
                <p class="card-header text-muted">
                    <#if currentUserId == message.author.id>
                        <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">${message.id}</a>
                        <#else>
                        <a class="btn btn-info">${message.id}</a>
                    </#if>
                </p>
                <div class="card-body m-2">
                    <p class="card-text text">${message.text!}</p>
                    <p class="card-text tag">#${message.tag!}</p>
                </div>
                <#if message.filename??>
                <div class="card-img-bottom m-2">
                    <img src="/img/${message.filename}" />
                </div>
                </#if>
                <p class="card-footer text-muted">
                    <a href="/user-messages/${message.author.id}">${message.authorName!}</a>
                </p>
            </div>
        </div>
    <#else>
        No messages
    </#list>
</div>