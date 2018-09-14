<#import "parts/common.ftl" as common>

<@common.page>
<h5>${username}</h5>
${message?ifExists}
<form method="post">
    <div class="form-group row">
        <label for="password" class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-3">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password"/>
        </div>
    </div>

    <div class="form-group row">
        <label for="email" class="col-sm-2 col-form-label"> E-mail: </label>
        <div class="col-sm-3">
            <input type="email" class="form-control" id="email" name="email" placeholder="E-mail" value="${email!""}"/>
        </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="form-group row">
        <div class="col-sm-4">
        </div>
        <div class="col-sm-2">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>
</form>
</@common.page>