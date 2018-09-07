<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as l>
<@common.page>
    <p>Add new user</p>
    <div id="message">
        {$message?ifExists}
    </div>
    <div id="registration">
        <@l.login "/registration" />
    </div>
</@common.page>