<#if Session.SPRING_SECURITY_CONTEXT??>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    currentUserId = user.getId()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    currentUserId = -1
    >
</#if>