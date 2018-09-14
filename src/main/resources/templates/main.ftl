<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <form method="get" action="/main" class="form-inline">
        <input type="text" class="form-control" name="filter" placeholder="filter messages" value="${filter?ifExists}">
        <button type="submit" class="btn btn-primary ml-1">Search</button>
    </form>
</div>

<a class="btn btn-primary mt-2" data-toggle="collapse" href="#addMessageForm" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Add new message
</a>

<div class="collapse" id="addMessageForm">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control" type="text" name="text" placeholder="Введите сообщение"/>
            </div>
            <div class="form-group">
                <input class="form-control" type="text" name="tag" placeholder="Тэг">
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFile" name="file">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Send message</button>
            </div>
        </form>
    </div>
</div>
<div><b>Список сообщений</b></div>
<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
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
</@c.page>