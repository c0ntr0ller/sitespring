<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as l>
<@common.page>
    <p>Login page</p>
    <@l.login "/login" />
    <a href="/registration">Регистрация</a>
</@common.page>