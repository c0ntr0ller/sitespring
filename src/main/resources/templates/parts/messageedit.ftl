<a class="btn btn-primary mt-2" data-toggle="collapse" href="#addMessageForm" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Edit message
</a>

<#--message add form-->
<div class="collapse <#if message??>show</#if>" id="addMessageForm">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control ${(textError??)?string('is-invalid','')}" type="text" name="text"
                       value="<#if message??> ${message.text}</#if>"
                       placeholder="Введите сообщение"/>
                <#--validation errors output-->
                <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control ${(tagError??)?string('is-invalid','')}" type="text" name="tag"
                       value="<#if message??> ${message.tag}</#if>"
                       placeholder="Тэг" />
                <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFile" name="file" />
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??> ${message.id}</#if>"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Send message</button>
            </div>
        </form>
    </div>
</div>