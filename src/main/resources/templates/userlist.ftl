<#import "parts/common.ftl" as c>

<@c.page>
<h1>Users list</h1>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>
                <#list user.roles as role>
                    ${role}<#sep>,
                </#list>
        </td>
        <td><a href="/user/${user.id}">edit</a> </td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>