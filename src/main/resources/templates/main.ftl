<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout />
    <span><a href="/user">Users list</a> </span>
</div>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите сообщение" />
        <input type="text" name="tag" placeholder="Тэг">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
</div>
<div><b>Список сообщений</b></div>
<form method="get" action="/main">
    <input type="text" name="filter" value="${filter?ifExists}">
    <button type="submit">Найти</button>
</form>
<div class="container">
    <#list messages as message>
        <div class="message row">
            <div class="message_id col-1">${message.id}</div>
            <div class="message_text col-4">
                <span>${message.text!}</span>
            </div>
            <div class="message_tag col-2">${message.tag!}</div>
            <div class="message_author col-1">${message.authorName!}</div>
            <div class="message_image col-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}">
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>
</@c.page>