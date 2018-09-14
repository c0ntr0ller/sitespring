<#macro login path isRegisterform>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label"> User Name: </label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-3">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password"/>
            </div>
        </div>

        <#if isRegisterform>
        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label"> E-mail: </label>
            <div class="col-sm-3">
                <input type="email" class="form-control" id="email" name="email" placeholder="E-mail"/>
            </div>
        </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group row">
            <div class="col-sm-4">
            </div>

            <#if !isRegisterform>
                <a href="/registration">Регистрация</a>
            </#if>

            <div class="col-sm-2">
                <button type="submit" class="btn btn-primary">

                    <#if isRegisterform>
                        Register
                    <#else>
                        Sign in
                    </#if>

                </button>
            </div>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Sign Out</button>
    </form>
</#macro>