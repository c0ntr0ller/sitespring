<#macro login path isRegisterform>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label"> User Name: </label>
            <div class="col-sm-3">
                <input type="text"
                       class="form-control ${(usernameError??)?string('is-invalid','')}"
                       value = "<#if user??>${user.username}</#if>"
                       id="username" name="username" placeholder="Username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-3">
                <input type="password"
                       class="form-control ${(passwordError??)?string('is-invalid','')}"
                       id="password" name="password" placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterform>
        <div class="form-group row">
            <label for="password2" class="col-sm-2 col-form-label"> Password repeat: </label>
            <div class="col-sm-3">
                <input type="password"
                       class="form-control  ${(password2Error??)?string('is-invalid','')}"
                       id="password2" name="password2" placeholder="Retype password"/>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label"> E-mail: </label>
            <div class="col-sm-3">
                <input type="email"
                       class="form-control ${(emailError??)?string('is-invalid','')}"
                       value = "<#if user??>${user.email}</#if>"
                       id="email" name="email" placeholder="E-mail"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group row">
            <div class="col-sm-4">
            </div>

            <#if !isRegisterform>
                <a class="badge badge-primary" href="/registration">Регистрация</a>
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