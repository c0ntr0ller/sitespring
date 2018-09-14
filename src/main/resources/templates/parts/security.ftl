<#if Session.SPRING_SECURITY_CONTEXT??>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    sessionStarted = true
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    sessionStarted = false
    >
</#if>