<#import "parts/common.ftl" as c>

<@c.page>
<h1>User edit</h1>
<form action="/user" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="text" name="userId" value="${user.id}"/>
    <input type="text" name="userName" value="${user.username}" />
    <input type="password" name="userPassword" value="" />
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <button type="submit">Save</button>
</form>
</@c.page>